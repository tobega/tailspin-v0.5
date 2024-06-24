package tailspin.language.factory;

import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.Node;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.BigIntegerLiteral;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.MathModNode;
import tailspin.language.nodes.numeric.MultiplyNode;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.numeric.TruncateDivideNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Templates;

public class NodeFactory {

  private final TailspinLanguage language;

  List<Integer> cvSlot = new ArrayList<>();
  { cvSlot.add(CV_SLOT);}
  int currentValueSlot() {
    return cvSlot.getLast();
  }

  List<Scope> scopes = new ArrayList<>();
  private void enterNewScope() {
    scopes.addLast(new Scope());
  }

  private Scope exitScope() {
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
    StatementNode programBody = switch (program.content()) {
      case ParseNode(String name, ParseNode statement) when name.equals("statement") -> visitStatement(statement);
      case List<?> statements -> BlockNode.create(statements.stream()
          .map(s -> visitStatement((ParseNode) s)).toList());
      default -> throw new IllegalStateException("Unexpected value: " + program.content());
    };
    Scope scope = exitScope();
    return ProgramRootNode.create(language, scope.getRootFd(), scope.getScopeFd(), programBody);
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
      List<TransformNode> stages = new ArrayList<>();
      stages.add(asTransformNode(visitSource(source)));
      for (Object stage : chain.subList(1, chain.size())) {
        TransformNode stageNode = switch (stage) {
          case ParseNode(String name, Object transform) when name.equals("transform") -> visitTransform(transform);
          default -> throw new IllegalStateException("Unexpected value: " + stage);
        };
        stages.add(stageNode);
      }
      ChainSlots chainSlots = currentScope().newChainSlots();
      return ChainNode.create(chainSlots.values(), chainSlots.cv(), chainSlots.result(), stages);
    }
    throw new IllegalStateException("Unexpected value " + valueChain.content());
  }

  private TransformNode asTransformNode(Node node) {
    if (node instanceof ValueNode v)
      return ResultAggregatingNode.create(v);
    if (node instanceof TransformNode t)
      return t;
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
      case ParseNode(String name, ParseNode(String withBlock, ParseNode statement)) when withBlock.equals("with-block") ->
          visitStatement((ParseNode) statement.content());
      default -> throw new IllegalStateException("Unexpected value: " + body);
    };
  }

  private TailspinNode visitSource(ParseNode source) {
    return switch (source) {
      case ParseNode(String name, ParseNode ae) when name.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      default -> throw new IllegalStateException("Unexpected value: " + source);
    };
  }

  private ValueNode visitArithmeticExpression(ParseNode ae) {
    return switch (ae) {
      case ParseNode(String name, ParseNode term) when name.equals("term") -> visitTerm(term);
      case ParseNode(String name, List<?> addition) when name.equals("addition") -> visitAddition(addition);
      case ParseNode(String name, List<?> multiplication) when name.equals("multiplication") -> visitMultiplication(multiplication);
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
      case ParseNode(String name, Long value) when name.equals("INT") -> IntegerLiteral.create(value);
      case ParseNode(String name, BigInteger value) when name.equals("INT") -> BigIntegerLiteral.create(value);
      case ParseNode(String name, ParseNode ae) when name.equals("parentheses") -> visitArithmeticExpression(ae);
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
