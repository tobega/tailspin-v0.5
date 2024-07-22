package tailspin.language.factory;

import static tailspin.language.parser.ParseNode.normalizeValues;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.STATE_SLOT;

import com.oracle.truffle.api.CallTarget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.array.ArrayWriteNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.AllOfNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.ArrayTypeMatcherNode;
import tailspin.language.nodes.matchers.ConditionNode;
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
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.DoNothingNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.nodes.value.VoidValue;
import tailspin.language.nodes.value.WriteContextValueNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Reference;
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
    scopes.addLast(new Scope(scopes.isEmpty() ? null : scopes.getLast()));
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
    return scope.createProgramRootNode(language, programBody);
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
      case ParseNode(String name, ParseNode stmt) when name.equals("statement") -> visitStatement(stmt);
      case ParseNode(String name, ParseNode valueChain) when name.equals("emit") -> EmitNode.create(asTransformNode(visitValueChain(valueChain)));
      case ParseNode(String name, List<?> def) when name.equals("definition") -> visitDefinition(def);
      case ParseNode(String name, Object setExpr) when name.equals("set-state") -> visitSetState(setExpr);
      case ParseNode(String type, List<?> content) when type.equals("templates") -> {
        String name = (String) content.getLast();
        String templateType = (String) content.getFirst();
        enterNewScope();
        visitTemplatesBody((ParseNode) content.get(1));
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setType(templateType);
        templates.setDefinitionLevel(scopes.size());
        currentScope().registerTemplates(name, templates);
        yield DoNothingNode.create();
      }
      default -> throw new IllegalStateException("Unexpected value: " + statement);
    };
  }

  private StatementNode visitSetState(Object expr) {
    currentScope().accessState();
    TailspinNode value;
    switch (expr) {
      case ParseNode valueChain -> value = visitValueChain(valueChain);
      case List<?> l -> {
        value = visitValueChain((ParseNode) l.getLast());
        ValueNode receiver = ReadContextValueNode.create(0, STATE_SLOT);
        if (l.getFirst() instanceof ParseNode(String type, ParseNode lensExpr) && type.equals("lens-expression")) {
          value = visitWriteLensExpression(receiver, lensExpr, value);
        }
      }
      default -> throw new IllegalStateException("Unexpected value: " + expr);
    }
    return WriteContextValueNode.create(0, STATE_SLOT, asSingleValueNode(value));
  }

  private ValueNode visitWriteLensExpression(ValueNode receiver, Object lensExpression, TailspinNode value) {
    return switch (lensExpression) {
      case ParseNode(String type, ParseNode source) when type.equals("source")
          -> ArrayWriteNode.create(receiver, asSingleValueNode(visitSource(source)), asSingleValueNode(value));
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
    };
  }

  private StatementNode visitDefinition(List<?> def) {
    if (def.getFirst() instanceof ParseNode(String name, String identifier) && name.equals("ID")) {
      Reference defined = currentScope().defineValue(identifier);
      TailspinNode expr = visitValueChain((ParseNode) def.getLast());
      return WriteContextValueNode.create(defined, asSingleValueNode(expr));
    }
    throw new IllegalStateException("Unexpected value: " + def);
  }

  private TailspinNode visitValueChain(ParseNode valueChain) {
    if (!valueChain.name().equals("value-chain")) throw new IllegalStateException("Unexpected value: " + valueChain);
    if (valueChain.content() instanceof ParseNode(String name, ParseNode source) && name.equals("source")) {
      return visitSource(source);
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
        enterNewScope();
        visitTemplatesBody(body);
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setDefinitionLevel(scopes.size());
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(), templates);
      }
      case String crosshatch when crosshatch.equals("#") -> {
        Templates matchers = currentScope().getOrCreateMatcherTemplates();
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size() - 1, matchers);
      }
      case ParseNode(String type, ParseNode id) when type.equals("templates-call")
          -> SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(),
          currentScope().findTemplates((String) id.content()));
      default -> throw new IllegalStateException("Unexpected value: " + transform);
    };
  }

  private void visitTemplatesBody(ParseNode body) {
    switch (body) {
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object block))
          when name.equals("with-block"):
        Object matchBlock = null;
        if (block instanceof List<?> list
            && list.getLast() instanceof ParseNode(String type, Object m)
            && type.equals("matchers")) {
          matchBlock = m;
          block = list.subList(0, list.size() - 1);
        }
        StatementNode blockNode = visitBlock(block);
        currentScope().setBlock(blockNode);
        if (matchBlock != null) {
          currentScope().verifyMatcherIsCalled();
          visitMatchers(matchBlock);
        }
      break;
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object matchers))
          when name.equals("matchers"):
        visitMatchers(matchers);
      break;
      default: throw new IllegalStateException("Unexpected value: " + body);
    }
  }

  private void visitMatchers(Object matchers) {
    MatchBlockNode matchBlockNode = switch (matchers) {
      case ParseNode(String name, List<?> matchTemplate) when name.equals("match-template") -> MatchBlockNode.create(List.of(visitMatchTemplate(matchTemplate)));
      case List<?> matchTemplates -> MatchBlockNode.create(matchTemplates.stream()
          .map(mt -> {
            if (mt instanceof ParseNode(String name, List<?> matchTemplate) && name.equals("match-template")) return visitMatchTemplate(matchTemplate);
            else throw new IllegalStateException("Expected match-template got " + mt);
          }).toList());
      default -> throw new IllegalStateException("Unexpected value: " + matchers);
    };
    currentScope().getOrCreateMatcherTemplates().setDefinitionLevel(scopes.size() - 1);
    currentScope().makeMatcherCallTarget(matchBlockNode);
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
      case "array-match" -> {
        List<MatcherNode> conditionNodes = new ArrayList<>();
        conditionNodes.addLast(ArrayTypeMatcherNode.create());
        if (typeMatch.content() instanceof List<?> conditions) {
          if (conditions.getLast() instanceof ParseNode(String type, ParseNode content)
              && type.equals("array-length-condition")) {
            conditionNodes.addLast(visitArrayLengthCondition(content));
            conditions = conditions.subList(0, conditions.size() - 1);
          }
          if (conditions.size() > 1) throw new UnsupportedOperationException(conditions.toString());
        }
        yield conditionNodes;
      }
      default -> throw new IllegalStateException("Unexpected value: " + typeMatch.name());
    };
  }

  private MatcherNode visitArrayLengthCondition(ParseNode content) {
    List<MatcherNode> lengthCondition = switch (content) {
      case ParseNode(String type, ParseNode(String name, ParseNode value)) when type.equals("literal-match") && name.equals("source")
          -> List.of(EqualityMatcherNode.create(asSingleValueNode(visitSource(value))));
      case ParseNode(String type, List<?> range) when type.equals("range-match") -> visitRangeMatch(range);
      default -> throw new IllegalStateException("Unexpected value: " + content);
    };
    return ConditionNode.create(
        MessageNode.create("length", ReadContextValueNode.create(-1, CV_SLOT)),
        lengthCondition.size() == 1 ? lengthCondition.getFirst() : AllOfNode.create(lengthCondition));
  }

  private List<MatcherNode> visitRangeMatch(List<?> content) {
    List<MatcherNode> bounds = new ArrayList<>();
    int separator = content.indexOf("..");
    if (separator > 0) {
      boolean inclusive = separator == 1;
      ValueNode low = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.getFirst()).content()));
      bounds.add(GreaterThanMatcherNode.create(inclusive, low));
    }
    if (separator + 1 < content.size()) {
      boolean inclusive = separator + 2 == content.size();
      ValueNode high = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.getLast()).content()));
      bounds.add(LessThanMatcherNode.create(inclusive, high));
    }
    return bounds;
  }

  private TailspinNode visitSource(ParseNode source) {
    return switch (source) {
      case ParseNode(String name, ParseNode ae) when name.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      case ParseNode(String name, Object ref) when name.equals("reference") -> visitReference(ref);
      case ParseNode(String name, ParseNode literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, ParseNode vc) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      case ParseNode(String name, Object contents) when name.equals("array-literal") -> visitArrayLiteral(contents);
      default -> throw new IllegalStateException("Unexpected value: " + source);
    };
  }

  private ValueNode visitArrayLiteral(Object contents) {
    List<TransformNode> contentNodes = switch (contents) {
      case String bracket when bracket.equals("[") -> List.of();
      case ParseNode(String name, ParseNode value) when name.equals("array-contents")
          -> List.of(asTransformNode(visitValueChain(value)));
      case ParseNode(String name, List<?> values) when name.equals("array-contents") -> {
        List<TransformNode> valueNodes = new ArrayList<>();
        valueNodes.add(asTransformNode(visitValueChain((ParseNode) values.getFirst())));
        for (int i = 1; i < values.size(); i++) {
          Object valueChain = ((ParseNode) values.get(i)).content();
          valueNodes.add(asTransformNode(visitValueChain((ParseNode) valueChain)));
        }
        yield valueNodes;
      }
      default -> throw new IllegalStateException("Unexpected value: " + contents);
    };
    return ArrayLiteral.create(currentScope().createBuildSlot(), contentNodes);
  }

  private ValueNode visitNumericLiteral(ParseNode literal) {
    return switch (literal) {
      case ParseNode(String name, Long value) when name.equals("INT") -> IntegerLiteral.create(value);
      case ParseNode(String name, BigInteger value) when name.equals("INT") -> BigIntegerLiteral.create(value);
      default -> throw new IllegalStateException("Unexpected value: " + literal);
    };
  }

  private TailspinNode visitReference(Object ref) {
    List<?> predicate = List.of();
    TailspinNode value = switch (ref) {
      case String s when s.equals("$") -> ReadContextValueNode.create(-1, currentValueSlot());
      case List<?> l when l.getFirst().equals("$") && l.get(1) instanceof ParseNode(String ignored, String identifier) -> {
        Object source = currentScope().getSource(identifier, -1);
        if (source instanceof Reference reference) {
          predicate = l.subList(2, l.size());
          yield ReadContextValueNode.create(reference);
        } else if (source instanceof Templates templates){
          if (l.size() > 2) throw new IllegalStateException("Cannot have lens expressions or message sends on call to " + identifier);
          yield SendToTemplatesNode.create(VoidValue.create(), scopes.size(), templates);
        } else {
          throw new IllegalStateException("Cannot use " + identifier + " as a source");
        }
      }
      case List<?> l when l.getFirst().equals("$") && l.get(1).equals("@") -> {
        predicate = l.subList(2, l.size());
        yield ReadContextValueNode.create(currentScope().accessState(), STATE_SLOT);
      }
      case List<?> l when l.getFirst().equals("$") -> {
        predicate = l.subList(1, l.size());
        yield ReadContextValueNode.create(-1, currentValueSlot());
      }
      default -> throw new IllegalStateException("Unexpected value: " + ref);
    };
    if (predicate.isEmpty()) return value;
    if (predicate.getFirst() instanceof ParseNode(String type, Object lensExpression) && type.equals("lens-expression")){
      value = visitReadLensExpression(asSingleValueNode(value), lensExpression);
    }
    if (predicate.getLast() instanceof ParseNode(String sendType, ParseNode(String ignored, String message))
        && sendType.equals("message-send")){
      value = MessageNode.create(message, asSingleValueNode(value));
    }
    return value;
  }

  private ValueNode visitReadLensExpression(ValueNode value, Object lensExpression) {
    return switch (lensExpression) {
      case ParseNode(String type, ParseNode source) when type.equals("source")
          -> ArrayReadNode.create(value, asSingleValueNode(visitSource(source)));
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
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
      case ParseNode(String name, Object ref) when name.equals("reference") -> asSingleValueNode(visitReference(ref));
      case ParseNode(String name, ParseNode vc) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
