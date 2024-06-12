package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class TailspinParserTest {
  @Test
  void testSimpleArithmetic() {
    List<ParseNode> parseNodes = TailspinParser.parseRule("arithmetic-expression", "1 + 2");
    assertEquals(
        List.of(new ParseNode("arithmetic-expression",
            new ParseNode("addition",
                List.of(
                    new ParseNode("term", new ParseNode("INT", 1L)),
                    "+",
                    new ParseNode("term", new ParseNode("INT", 2L))
                )))),
        parseNodes
    );
  }
}
