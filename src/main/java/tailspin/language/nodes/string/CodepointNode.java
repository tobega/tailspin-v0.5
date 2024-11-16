package tailspin.language.nodes.string;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinStrings;

@NodeChild(type = ValueNode.class)
public abstract class CodepointNode extends ValueNode {

  public CodepointNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Specialization
  TruffleString doLong(long codepoint,
      @Cached @Shared TruffleString.FromCodePointNode fromCodePointNode) {
    return TailspinStrings.fromCodepoint((int) codepoint, fromCodePointNode);
  }

  @Specialization
  TruffleString doBigNumber(BigNumber codepoint,
      @Cached @Shared TruffleString.FromCodePointNode fromCodePointNode) {
    return TailspinStrings.fromCodepoint(codepoint.intValueExact(), fromCodePointNode);
  }

  public static CodepointNode create(ValueNode codepoint, SourceSection sourceSection) {
    return CodepointNodeGen.create(sourceSection, codepoint);
  }
}
