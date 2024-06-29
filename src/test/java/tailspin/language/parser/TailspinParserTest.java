package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class TailspinParserTest {
  @Test
  void testSimpleArithmetic() {
    ParseNode parseNode = TailspinParser.parseRule("arithmetic-expression", "1 + 2");
    assertEquals(
        new ParseNode("arithmetic-expression",
            new ParseNode("addition",
                List.of(
                    new ParseNode("term", new ParseNode("numeric-literal", new ParseNode("INT", 1L))),
                    "+",
                    new ParseNode("term", new ParseNode("numeric-literal", new ParseNode("INT", 2L)))
                ))),
        parseNode
    );
  }
}
