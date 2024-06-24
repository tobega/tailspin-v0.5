package tailspin.language.factory;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import tailspin.language.runtime.Templates;

public class Scope {
  Builder rootFdb = Templates.createBasicFdb();
  Builder scopeFdb = Templates.createScopeFdb();

  public FrameDescriptor getRootFd() {
    return rootFdb.build();
  }

  public FrameDescriptor getScopeFd() {
    return scopeFdb.build();
  }

  public ChainSlots newChainSlots() {
    return ChainSlots.on(rootFdb);
  }
}
