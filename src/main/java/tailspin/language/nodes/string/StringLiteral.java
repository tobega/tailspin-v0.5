package tailspin.language.nodes.string;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.string.StringLiteralNodeGen.AppendStringNodeGen;
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
      @Cached(neverDefault = true) AppendStringNode appendNode) {
    TruffleString result = TailspinStrings.EMPTY;
    for (ValueNode part : parts) {
      result = appendNode.executeAppend(result, part.executeGeneric(frame));
    }
    return result;
  }

  @GenerateInline(value = false)
  static abstract class AppendStringNode extends Node {
    abstract TruffleString executeAppend(TruffleString prefix, Object suffix);

    @Specialization(guards = "suffix != null")
    TruffleString doAppendString(TruffleString prefix, TruffleString suffix,
        @Cached @Shared TruffleString.ConcatNode concatNode) {
      return TailspinStrings.concat(prefix, suffix, concatNode);
    }

    @Specialization(guards = "suffix != null")
    TruffleString doAppendMany(TruffleString prefix, ArrayList<?> suffix) {
      TruffleString result = prefix;
      for (Object part : suffix) {
        result = executeAppend(result, part);
      }
      return result;
    }

    @Specialization(guards = "suffix != null")
    TruffleString doAppendObject(TruffleString prefix, Object suffix,
        @Cached @Shared TruffleString.ConcatNode concatNode,
        @Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
      return TailspinStrings.concat(prefix,
          TailspinStrings.fromJavaString(Objects.toString(suffix), fromJavaStringNode), concatNode);
    }

    @Specialization(guards = "ignored == null")
    TruffleString doAppendNone(TruffleString prefix, Object ignored) {
      return prefix;
    }

    static AppendStringNode create() {
      return AppendStringNodeGen.create();
    }
  }
}
