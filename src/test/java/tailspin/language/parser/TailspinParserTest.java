package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class TailspinParserTest {
  @Test
  void testSimpleArithmetic() {
    List<ParseNode> parseNodes = TailspinParser.parseRule("arithmeticExpression", "1 + 2");
    assertEquals(
        List.of(new ParseNode("arithmeticExpression",
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
