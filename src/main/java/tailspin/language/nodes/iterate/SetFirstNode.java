package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

@GenerateInline
public abstract class SetFirstNode extends Node {

  abstract void execute(VirtualFrame frame, Node node, int slot, boolean value);

  @Specialization
  void doSet(VirtualFrame frame, int slot, boolean value){
    frame.setBooleanStatic(slot, value);
  }

  @NeverDefault
  static SetFirstNode create() {
    return SetFirstNodeGen.create();
  }
}
