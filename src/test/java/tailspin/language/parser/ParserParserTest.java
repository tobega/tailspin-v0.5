package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;

public class ParserParserTest {
  @Test
  void single_named_rule() {
    assertEquals(Map.of("a", List.of(new NamedComposition("b"))),
        ParserParser.createSyntaxRules("rule a: <b>"));
  }

}
