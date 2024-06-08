package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.ChoiceComposition;
import tailspin.language.parser.composer.CompositionSpec.InverseComposition;
import tailspin.language.parser.composer.CompositionSpec.MultiplierComposition;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.RangeMatch;
import tailspin.language.parser.composer.Scope;

public class ParserParserTest {

  @Test
  void single_named_rule() {
    String parserDefinition = """
    rule a: <b>
    """;
    assertEquals(Map.of("a", List.of(new NamedComposition("b"))),
        ParserParser.createSyntaxRules(parserDefinition));
  }

  @Test
  void two_named_rules() {
    String parserDefinition = """
    rule a: <b>
    rule b: <INT>
    """;
    assertEquals(Map.of("a", List.of(new NamedComposition("b")),
            "b", List.of(new NamedComposition("INT"))),
        ParserParser.createSyntaxRules(parserDefinition));
  }

  @ParameterizedTest
  @MethodSource
  void multiplier(String multiplier, RangeMatch matcher) {
    String parserDefinition = """
    rule a: <b>%s
    """;
    assertEquals(Map.of("a", List.of(new MultiplierComposition(
        new NamedComposition("b"), matcher))),
        ParserParser.createSyntaxRules(String.format(parserDefinition, multiplier)));
  }

  static Stream<Arguments> multiplier() {
    return Stream.of(
        Arguments.of("?", RangeMatch.AT_MOST_ONE),
        Arguments.of("+", RangeMatch.AT_LEAST_ONE),
        Arguments.of("*", RangeMatch.ANY_AMOUNT)
    );
  }

  @Test
  void custom_int_multiplier() {
    String parserDefinition = """
    rule a: <b>=5
    """;
    Map<String, List<CompositionSpec>> syntaxRules = ParserParser.createSyntaxRules(
        parserDefinition);
    assertEquals(Set.of("a"), syntaxRules.keySet());
    List<CompositionSpec> rule = syntaxRules.get("a");
    assertEquals(1, rule.size());
    if (rule.getFirst() instanceof MultiplierComposition(NamedComposition(String name), RangeMatch matcher)) {
      assertEquals("b", name);
      Scope scope = new Scope(null);
      assertFalse(matcher.matches(4, scope));
      assertTrue(matcher.matches(5, scope));
      assertFalse(matcher.matches(6, scope));
    } else {
      fail();
    }
  }

  @Test
  void custom_reference_multiplier() {
    String parserDefinition = """
    rule a: <b>=$ref
    """;
    Map<String, List<CompositionSpec>> syntaxRules = ParserParser.createSyntaxRules(
        parserDefinition);
    assertEquals(Set.of("a"), syntaxRules.keySet());
    List<CompositionSpec> rule = syntaxRules.get("a");
    assertEquals(1, rule.size());
    if (rule.getFirst() instanceof MultiplierComposition(NamedComposition(String name), RangeMatch matcher)) {
      assertEquals("b", name);
      Scope scope = new Scope(null);
      scope.defineValue("ref", 5L);
      assertFalse(matcher.matches(4, scope));
      assertTrue(matcher.matches(5, scope));
      assertFalse(matcher.matches(6, scope));
    } else {
      fail();
    }
  }

  @Test
  void inverse() {
    String parserDefinition = """
    rule a: <~b>
    """;
    assertEquals(Map.of("a", List.of(new InverseComposition(new NamedComposition("b")))),
        ParserParser.createSyntaxRules(parserDefinition));
  }

  @Test
  void choice() {
    String parserDefinition = """
    rule a: <a|b>
    """;
    assertEquals(Map.of("a", List.of(
        new ChoiceComposition(List.of(
            new NamedComposition("a"),
            new NamedComposition("b"))))),
        ParserParser.createSyntaxRules(parserDefinition));
  }

  @Test
  void choices() {
    String parserDefinition = """
    rule a: <a|b|c>
    """;
    assertEquals(Map.of("a", List.of(
        new ChoiceComposition(List.of(
            new NamedComposition("a"),
            new NamedComposition("b"),
            new NamedComposition("c"))))),
        ParserParser.createSyntaxRules(parserDefinition));
  }

}
