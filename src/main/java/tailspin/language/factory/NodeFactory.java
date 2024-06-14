package tailspin.language.factory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import java.util.List;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Templates;

public class NodeFactory {

  private final TailspinLanguage language;
  FrameDescriptor.Builder rootFd = Templates.createBasicFdb();

  public NodeFactory(TailspinLanguage tailspinLanguage) {
    this.language = tailspinLanguage;
  }

  public CallTarget createCallTarget(ParseNode program) {
    return ProgramRootNode.create(language, rootFd.build(), BlockNode.create(List.of()));
  }
}
