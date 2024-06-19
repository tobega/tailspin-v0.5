package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;

public record TemplatesInstance(MaterializedFrame definingScope, CallTarget callTarget) {

}
