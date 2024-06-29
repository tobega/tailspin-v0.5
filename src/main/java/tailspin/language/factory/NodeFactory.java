package tailspin.language.factory;

import static tailspin.language.parser.ParseNode.normalizeValues;
import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.CallTarget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.AllOfNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNode;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.BigIntegerLiteral;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.MathModNode;
import tailspin.language.nodes.numeric.MultiplyNode;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.numeric.TruncateDivideNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Templates;

public class NodeFactory {

  private final TailspinLanguage language;

  List<Integer> cvSlot = new ArrayList<>();
  { cvSlot.add(CV_SLOT);}
  int currentValueSlot() {
    return cvSlot.getLast();
  }

  void pushCvSlot(int slot) {
    cvSlot.addLast(slot);
  }

  void popCvSlot() {
    cvSlot.removeLast();
  }

  List<Scope> scopes = new ArrayList<>();
  private void enterNewScope() {
    scopes.addLast(new Scope());
    pushCvSlot(CV_SLOT);
  }

  private Scope exitScope() {
    popCvSlot();
    return scopes.removeLast();
  }

  private Scope currentScope() {
    return scopes.getLast();
  }

  public NodeFactory(TailspinLanguage tailspinLanguage) {
    this.language = tailspinLanguage;
  }

  public CallTarget createCallTarget(ParseNode program) {
    enterNewScope();
    StatementNode programBody = visitBlock(program.content());
    Scope scope = exitScope();
    return ProgramRootNode.create(language, scope.getRootFd(), scope.getScopeFd(), programBody);
  }

  private StatementNode visitBlock(Object block) {
    return switch (block) {
      case ParseNode(String name, ParseNode statement) when name.equals("statement") -> visitStatement(statement);
      case List<?> statements -> BlockNode.create(statements.stream()
          .map(s -> visitStatement((ParseNode) s)).toList());
      default -> throw new IllegalStateException("Unexpected value: " + block);
    };
  }

  private StatementNode visitStatement(ParseNode statement) {
    return switch (statement) {
      case ParseNode(String name, ParseNode valueChain) when name.equals("emit") -> EmitNode.create(visitValueChain(valueChain));
      default -> throw new IllegalStateException("Unexpected value: " + statement);
    };
  }

  private TransformNode visitValueChain(ParseNode valueChain) {
    if (!valueChain.name().equals("value-chain")) throw new IllegalStateException("Unexpected value: " + valueChain);
    if (valueChain.content() instanceof ParseNode(String name, ParseNode source) && name.equals("source")) {
      return asTransformNode(visitSource(source));
    } else if (valueChain.content() instanceof List<?> chain
        && chain.getFirst() instanceof ParseNode(String firstName, ParseNode source) && firstName.equals("source")) {
      ChainSlots chainSlots = currentScope().newChainSlots();
      List<TransformNode> stages = new ArrayList<>();
      stages.add(asTransformNode(visitSource(source)));
      pushCvSlot(chainSlots.cv());
      for (Object stage : chain.subList(1, chain.size())) {
        TransformNode stageNode = switch (stage) {
          case ParseNode(String name, Object transform) when name.equals("transform") -> visitTransform(transform);
          default -> throw new IllegalStateException("Unexpected value: " + stage);
        };
        stages.add(stageNode);
      }
      popCvSlot();
      return ChainNode.create(chainSlots.values(), chainSlots.cv(), chainSlots.result(), stages);
    }
    throw new IllegalStateException("Unexpected value " + valueChain.content());
  }

  private TransformNode asTransformNode(TailspinNode node) {
    if (node instanceof ValueNode v)
      return ResultAggregatingNode.create(v);
    if (node instanceof TransformNode t)
      return t;
    throw new IllegalStateException("Unknown source node " + node);
  }

  private ValueNode asSingleValueNode(TailspinNode node) {
    if (node instanceof ValueNode v)
      return v;
    if (node instanceof TransformNode t)
      return SingleValueNode.create(t);
    throw new IllegalStateException("Unknown source node " + node);
  }

  private TransformNode visitTransform(Object transform) {
    return switch(transform) {
      case ParseNode(String name, ParseNode source) when name.equals("source") -> asTransformNode(visitSource(source));
      case ParseNode(String name, ParseNode body) when name.equals("inline-templates-call") -> {
        Templates templates = new Templates();
        enterNewScope();
        StatementNode bodyNode = visitTemplatesBody(body);
        Scope scope = exitScope();
        templates.setCallTarget(TemplatesRootNode.create(scope.getRootFd(), scope.getScopeFd(), bodyNode));
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), templates, -1);
      }
      default -> throw new IllegalStateException("Unexpected value: " + transform);
    };
  }

  private StatementNode visitTemplatesBody(ParseNode body) {
    return switch (body) {
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object block))
          when name.equals("with-block") -> visitBlock(block);
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object matchers))
          when name.equals("matchers") -> visitMatchers(matchers);
      default -> throw new IllegalStateException("Unexpected value: " + body);
    };
  }

  private MatchBlockNode visitMatchers(Object matchers) {
    return switch (matchers) {
      case ParseNode(String name, List<?> matchTemplate) when name.equals("match-template") -> MatchBlockNode.create(List.of(visitMatchTemplate(matchTemplate)));
      case List<?> matchTemplates -> MatchBlockNode.create(matchTemplates.stream()
          .map(mt -> {
            if (mt instanceof ParseNode(String name, List<?> matchTemplate) && name.equals("match-template")) return visitMatchTemplate(matchTemplate);
            else throw new IllegalStateException("Expected match-template got " + mt);
          }).toList());
      default -> throw new IllegalStateException("Unexpected value: " + matchers);
    };
  }

  private MatchTemplateNode visitMatchTemplate(List<?> matchTemplate) {
    MatcherNode matcherNode = visitMatcher((ParseNode) matchTemplate.getFirst());
    StatementNode block = visitBlock(normalizeValues(matchTemplate.subList(1, matchTemplate.size())));
    return MatchTemplateNode.create(matcherNode, block);
  }

  private MatcherNode visitMatcher(ParseNode matcher) {
    return switch (matcher) {
      case ParseNode(String name, @SuppressWarnings("unused") Object c)
          when name.equals("otherwise") -> AlwaysTrueMatcherNode.create();
      case ParseNode(String name, Object membranes)
          when name.equals("when-do") -> visitAlternativeMembranes(membranes);
      default -> throw new IllegalStateException("Unexpected value: " + matcher);
    };
  }

  private MatcherNode visitAlternativeMembranes(Object membranes) {
    return switch (membranes) {
      case ParseNode(String m, ParseNode single) when m.equals("membrane") -> visitMembrane(single);
      default -> throw new IllegalStateException("Unexpected value: " + membranes);
    };
  }

  private MatcherNode visitMembrane(Object membranes) {
    List<MatcherNode> conjunction = switch (membranes) {
      case ParseNode(String type, ParseNode typeMatch) when type.equals("type-match") -> visitTypeMatch(typeMatch);
      case ParseNode(String type, ParseNode(String name, ParseNode value)) when type.equals("literal-match") && name.equals("source")
          -> List.of(EqualityMatcherNode.create(asSingleValueNode(visitSource(value))));
      default -> throw new IllegalStateException("Unexpected value: " + membranes);
    };
    if (conjunction.size() == 1) {
      return conjunction.getFirst();
    }
    return AllOfNode.create(conjunction);
  }

  private List<MatcherNode> visitTypeMatch(ParseNode typeMatch) {
    return switch (typeMatch.name()) {
      case "range-match" -> visitRangeMatch((List<?>) typeMatch.content());
      default -> throw new IllegalStateException("Unexpected value: " + typeMatch.name());
    };
  }

  private List<MatcherNode> visitRangeMatch(List<?> content) {
    List<MatcherNode> bounds = new ArrayList<>();
    int separator = content.indexOf("..");
    if (separator > 0) {
      boolean inclusive = separator == 1;
      ValueNode low = asSingleValueNode(visitSource((ParseNode) content.getFirst()));
      bounds.add(GreaterThanMatcherNode.create(inclusive, low));
    }
    if (separator + 1 < content.size()) {
      boolean inclusive = separator + 2 == content.size();
      ValueNode high = asSingleValueNode(visitSource((ParseNode) content.getLast()));
      bounds.add(LessThanMatcherNode.create(inclusive, high));
    }
    return bounds;
  }

  private TailspinNode visitSource(ParseNode source) {
    return switch (source) {
      case ParseNode(String name, ParseNode ae) when name.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      case ParseNode(String name, Object ref) when name.equals("reference") -> visitReference(ref);
      case ParseNode(String name, ParseNode literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      default -> throw new IllegalStateException("Unexpected value: " + source);
    };
  }

  private ValueNode visitNumericLiteral(ParseNode literal) {
    return switch (literal) {
      case ParseNode(String name, Long value) when name.equals("INT") -> IntegerLiteral.create(value);
      case ParseNode(String name, BigInteger value) when name.equals("INT") -> BigIntegerLiteral.create(value);
      default -> throw new IllegalStateException("Unexpected value: " + literal);
    };
  }

  private TailspinNode visitReference(Object ref) {
    return switch (ref) {
      case String s when s.equals("$") -> ReadContextValueNode.create(-1, currentValueSlot());
      default -> throw new IllegalStateException("Unexpected value: " + ref);
    };
  }

  private ValueNode visitArithmeticExpression(ParseNode ae) {
    return switch (ae) {
      case ParseNode(String name, ParseNode literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, List<?> addition) when name.equals("addition") -> visitAddition(addition);
      case ParseNode(String name, List<?> multiplication) when name.equals("multiplication") -> visitMultiplication(multiplication);
      // We also handle term here just to simplify the recursive expression parsing
      case ParseNode(String name, ParseNode term) when name.equals("term") -> visitTerm(term);
      default -> throw new IllegalStateException("Unexpected value: " + ae);
    };
  }

  private ValueNode visitAddition(List<?> addition) {
    ValueNode left = visitArithmeticExpression((ParseNode) addition.getFirst());
    ValueNode right = visitArithmeticExpression((ParseNode) addition.getLast());
    if(addition.get(1).equals("+")) {
      return AddNode.create(left, right);
    } else if(addition.get(1).equals("-")) {
      return SubtractNode.create(left, right);
    }
    throw new IllegalStateException("Unexpected value: " + addition);
  }

  private ValueNode visitMultiplication(List<?> multiplication) {
    ValueNode left = visitArithmeticExpression((ParseNode) multiplication.getFirst());
    ValueNode right = visitArithmeticExpression((ParseNode) multiplication.getLast());
    if(multiplication.get(1).equals("*")) {
      return MultiplyNode.create(left, right);
    } else if(multiplication.get(1).equals("~/")) {
      return TruncateDivideNode.create(left, right);
    } else if(multiplication.get(1).equals("mod")) {
      return MathModNode.create(left, right);
    }
    throw new IllegalStateException("Unexpected value: " + multiplication);
  }

  private ValueNode visitTerm(ParseNode term) {
    return switch (term) {
      case ParseNode(String name, ParseNode literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, ParseNode(String aeName, ParseNode ae))
          when name.equals("parentheses") && aeName.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      case ParseNode(String name, Object ref) when name.equals("reference") -> asSingleValueNode(visitReference(ref));
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
