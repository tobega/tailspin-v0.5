package tailspin.language.nodes.string;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinStrings;

public abstract class StringPart extends ValueNode {
  final String value;

  StringPart(String value, SourceSection sourceSection) {
    super(sourceSection);
    this.value = value;
  }

  public static StringPart create(String value, SourceSection sourceSection) {
    return StringPartNodeGen.create(value, sourceSection);
  }

  @Specialization
  public TruffleString doString(@Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
    return TailspinStrings.fromJavaString(value, fromJavaStringNode);
  }
}
