package tailspin.language.nodes.string;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinStrings;

public abstract class StringLiteral extends ValueNode {
  @Children
  private final ValueNode[] parts;

  StringLiteral(List<ValueNode> parts) {
    this.parts = parts.toArray(ValueNode[]::new);
  }

  public static StringLiteral create(List<ValueNode> parts) {
    return StringLiteralNodeGen.create(parts);
  }

  @Specialization
  TruffleString doBuild(VirtualFrame frame,
      @Cached TruffleString.ConcatNode concatNode) {
    TruffleString result = TailspinStrings.EMPTY;
    for (ValueNode part : parts) {
      result = TailspinStrings.concat(result, (TruffleString) part.executeGeneric(frame), concatNode);
    }
    return result;
  }
}
