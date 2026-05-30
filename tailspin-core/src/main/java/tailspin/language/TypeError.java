package tailspin.language;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;

public class TypeError extends AbstractTruffleException {
  public TypeError(String message, Node node) {
    super(buildDetailedMessage("TypeError: " + message, node), node);
  }

  private static String buildDetailedMessage(String baseMessage, Node location) {
    if (location == null) return baseMessage;

    SourceSection section = location.getSourceSection();
    if (section == null) return baseMessage;

    CharSequence offendingCode = section.getCharacters();
    String sourceName = section.getSource().getName();
    int line = section.getStartLine();
    int col = section.getStartColumn();

    return String.format("%s at %s:%d:%d in \"%s\"",
        baseMessage, sourceName, line, col, offendingCode);
  }}
