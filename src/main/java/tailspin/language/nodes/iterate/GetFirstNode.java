package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

@GenerateInline
public abstract class GetFirstNode extends Node {

  abstract boolean execute(VirtualFrame frame, Node node, int slot);

  @Specialization
  boolean doGet(VirtualFrame frame, int slot){
    return frame.getBooleanStatic(slot);
  }

  @NeverDefault
  static GetFirstNode create() {
    return GetFirstNodeGen.create();
  }
}
