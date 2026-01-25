package tailspin.language;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;

public class RejectSinkReached extends AbstractTruffleException {
  public RejectSinkReached(String message, Node node) {
    super(message, node);
  }
}
