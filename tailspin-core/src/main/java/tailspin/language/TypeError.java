package tailspin.language;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;

public class TypeError extends AbstractTruffleException {
  public TypeError(String message, Node node) {
    super("TypeError: " + message, node);
  }
}
