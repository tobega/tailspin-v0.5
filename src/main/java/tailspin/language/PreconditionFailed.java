package tailspin.language;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;

public class PreconditionFailed extends AbstractTruffleException {
  public PreconditionFailed(String message, Node node) {
    super("PreconditionFailed: " + message, node);
  }
}
