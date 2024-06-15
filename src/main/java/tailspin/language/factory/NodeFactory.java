package tailspin.language.factory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import java.math.BigInteger;
import java.util.List;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.numeric.BigIntegerLiteral;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Templates;

public class NodeFactory {

  private final TailspinLanguage language;
  Builder rootFd = Templates.createBasicFdb();

  public NodeFactory(TailspinLanguage tailspinLanguage) {
    this.language = tailspinLanguage;
  }

  public CallTarget createCallTarget(ParseNode program) {
    StatementNode programBody = switch (program.content()) {
      case ParseNode(String name, ParseNode statement) when name.equals("statement") -> visitStatement(statement);
      case List<?> statements -> BlockNode.create(statements.stream()
          .map(s -> visitStatement((ParseNode) s)).toList());
      default -> throw new IllegalStateException("Unexpected value: " + program.content());
    };
    return ProgramRootNode.create(language, rootFd.build(), programBody);
  }

  private StatementNode visitStatement(ParseNode statement) {
    return switch (statement) {
      case ParseNode(String name, ParseNode valueChain) when name.equals("emit") -> EmitNode.create(visitValueChain(valueChain));
      default -> throw new IllegalStateException("Unexpected value: " + statement);
    };
  }

  private TransformNode visitValueChain(ParseNode valueChain) {
    if (!valueChain.name().equals("value-chain")) throw new IllegalStateException("Unexpected value: " + valueChain);
    return visitTransform(valueChain.content());
  }

  private TransformNode visitTransform(Object transform) {
    TailspinNode node = switch(transform) {
      case ParseNode(String name, ParseNode source) when name.equals("source") -> visitSource(source);
      default -> throw new IllegalStateException("Unexpected value: " + transform);
    };
    if (node instanceof TransformNode tn) return tn;
    else return ResultAggregatingNode.create((ValueNode) node);
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
      default -> throw new IllegalStateException("Unexpected value: " + ae);
    };
  }

  private ValueNode visitTerm(ParseNode term) {
    return switch (term) {
      case ParseNode(String name, Long value) when name.equals("INT") -> IntegerLiteral.create(value);
      case ParseNode(String name, BigInteger value) when name.equals("INT") -> BigIntegerLiteral.create(value);
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
