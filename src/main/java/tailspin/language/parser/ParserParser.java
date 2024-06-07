package tailspin.language.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.ChoiceComposition;
import tailspin.language.parser.composer.CompositionSpec.DereferenceComposition;
import tailspin.language.parser.composer.CompositionSpec.LiteralComposition;
import tailspin.language.parser.composer.CompositionSpec.MultiplierComposition;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.CompositionSpec.RegexComposition;
import tailspin.language.parser.composer.CompositionSpec.Resolver;
import tailspin.language.parser.composer.CompositionSpec.SkipComposition;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.RangeMatch;
import tailspin.language.parser.composer.Scope;
import tailspin.language.parser.composer.SequenceSubComposer;
import tailspin.language.parser.composer.SubComposerFactory;

public class ParserParser {
  static Map<String, List<CompositionSpec>> syntaxRules;
  static {
    syntaxRules = new HashMap<>();
    syntaxRules.putAll(Map.of(
        // rule optionalWhitespace: (<WS>?)
        "optionalWhitespace", List.of(
            new SkipComposition(List.of(new MultiplierComposition(new NamedComposition("WS"), RangeMatch.AT_MOST_ONE)))
        ),
        // rule localIdentifier: <[a-zA-Z][_\-0-9a-zA-Z]*>
        "localIdentifier", List.of(new RegexComposition("[a-zA-Z][_\\-0-9a-zA-Z]*")),
        // rule sourceReference: <='$$'>
        "sourceReference", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "$"))), new NamedComposition("localIdentifier")),
        // rule stringLiteral: (<=''''>) <'[^']|'')*'> (<=''''>)
        "stringLiteral", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "'"))),
            new RegexComposition("([^']|'')*"),
            new SkipComposition(List.of(new LiteralComposition((s) -> "'")))
        )
    ));
    syntaxRules.putAll(Map.of(
        // rule definedCompositionSequence: <='rule'> <localIdentifier> (<=':'> <WS>?) <compositionComponent>+
        "definedCompositionSequence", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "rule"), new NamedComposition("WS"))),
            new NamedComposition("localIdentifier"),
            new SkipComposition(List.of(new LiteralComposition((s) -> ":"),
                new NamedComposition("optionalWhitespace"))),
            new MultiplierComposition(new NamedComposition("compositionComponent"), RangeMatch.AT_LEAST_ONE)
        ),
        // compositionComponent: compositionMatcher | compositionSkipRule
        "compositionComponent", List.of(
            new ChoiceComposition(List.of(
              new NamedComposition("compositionMatcher"),
              new NamedComposition("compositionSkipRule")
            )),
            new NamedComposition("optionalWhitespace")
        ),
        // compositionMatcher: tokenMatcher | source
        "compositionMatcher", List.of(new ChoiceComposition(List.of(
            new NamedComposition("tokenMatcher"),
            new NamedComposition("sourceReference")
        ))),
        // rule tokenMatcher: (<='<'>) <='~'>? (<WS>?) <compositionToken> <alternativeCompositionToken>* (<WS>? <='>'>) <multiplier>? (<WS>?)
        "tokenMatcher", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "<"))),
            new MultiplierComposition(new LiteralComposition((s) -> "~"), RangeMatch.AT_MOST_ONE),
            new NamedComposition("optionalWhitespace"),
            new NamedComposition("compositionToken"),
            new NamedComposition("optionalWhitespace"),
            new MultiplierComposition(new NamedComposition("alternativeCompositionToken"), RangeMatch.ANY_AMOUNT),
            new NamedComposition("optionalWhitespace"),
            new SkipComposition(List.of(new LiteralComposition((s) -> ">"))),
            new MultiplierComposition(new NamedComposition("multiplier"), RangeMatch.AT_MOST_ONE),
            new NamedComposition("optionalWhitespace")
        ),
        "alternativeCompositionToken", List.of(
            new LiteralComposition((s) -> "|"),
            new NamedComposition("optionalWhitespace"),
            new NamedComposition("compositionToken"),
            new NamedComposition("optionalWhitespace")
        ),
        // compositionToken: (Equal tag? (sourceReference|stringLiteral)|tag? localIdentifier|tag? stringLiteral) unit?;
        "compositionToken", List.of(
            new ChoiceComposition(List.of(
                new NamedComposition("literalComposition"),
                new NamedComposition("localIdentifier"),
                new NamedComposition("stringLiteral")
            )),
            new NamedComposition("optionalWhitespace")
        ),
        "literalComposition", List.of(
            new LiteralComposition((s) -> "="),
            new ChoiceComposition(List.of(
                new NamedComposition("sourceReference"),
                new NamedComposition("stringLiteral")
            ))
        ),
        //multiplier: Plus | Star | Question | Equal (PositiveInteger|sourceReference)
        "multiplier", List.of(new ChoiceComposition(List.of(
            new LiteralComposition((s) -> "+"),
            new LiteralComposition((s) -> "*"),
            new LiteralComposition((s) -> "?"),
            new NamedComposition("customMultiplier")
        )), new NamedComposition("optionalWhitespace")),
        "customMultiplier", List.of(
            new LiteralComposition((s) -> "="),
            new ChoiceComposition(List.of(
                new NamedComposition("INT"),
                new NamedComposition("sourceReference")
            ))
        )
    ));
    syntaxRules.putAll(Map.of(
        // rule compositionSkipRule: (<='('> <WS>?) <compositionCapture>+ (<WS>? <=')'> <WS>?);
        "compositionSkipRule", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "("),
              new NamedComposition("optionalWhitespace"))),
            new MultiplierComposition(new NamedComposition("skipComposition"), RangeMatch.AT_LEAST_ONE),
            new SkipComposition(List.of(new NamedComposition("optionalWhitespace"),
              new LiteralComposition((s) -> ")"),
              new NamedComposition("optionalWhitespace")))
        ),
        "skipComposition", List.of(new ChoiceComposition(List.of(
            new NamedComposition("compositionCapture"),
            new NamedComposition("compositionMatcher")
        ))),
        // compositionCapture: (Def localIdentifier Colon compositionMatcher transform? SemiColon)|(compositionMatcher (transform? To stateSink)?)|stateAssignment;
        "compositionCapture", List.of(
            new LiteralComposition((s) -> "def"),
            new SkipComposition(List.of(new NamedComposition("WS"))),
            new NamedComposition("localIdentifier"),
            new NamedComposition("optionalWhitespace"),
            new LiteralComposition((s) -> ":"),
            new NamedComposition("optionalWhitespace"),
            new NamedComposition("compositionMatcher"),
            new NamedComposition("optionalWhitespace"),
            new LiteralComposition((s) -> ";"),
            new NamedComposition("optionalWhitespace")
        )
    ));
  }

  public static Map<String, List<CompositionSpec>> createSyntaxRules(String parserDefinition) {
    Resolver resolver = new ParseComposerFactory(new SubComposerFactory(syntaxRules));
    SequenceSubComposer composer = new SequenceSubComposer(List.of(
        new NamedComposition("optionalWhitespace"),
        new MultiplierComposition(new NamedComposition("definedCompositionSequence"), RangeMatch.AT_LEAST_ONE)), new Scope(null), resolver);
    Memo end = composer.nibble(parserDefinition, Memo.root(0));
    if (end.pos != parserDefinition.length()) throw new AssertionError("Parse failed at \n" + parserDefinition.substring(end.pos));
    @SuppressWarnings("unchecked")
    List<ParseNode> parseNodes = (List<ParseNode>) composer.getValues();
    @SuppressWarnings("unchecked")
    Entry<String, List<CompositionSpec>>[] rules = parseNodes.stream().filter(Objects::nonNull).map(ParserParser::toRule).toList().toArray(new Entry[0]);
    return Map.ofEntries(
        rules
    );
  }

  private static Entry<String, List<CompositionSpec>> toRule(ParseNode ruleNode) {
    if (!ruleNode.name().equals("definedCompositionSequence")) throw new AssertionError("Got unexpected " + ruleNode);
    @SuppressWarnings("unchecked")
    List<ParseNode> content = (List<ParseNode>) ruleNode.content();
    return Map.entry(
        content.getFirst().content().toString(),
        content.stream().skip(1).map(ParserParser::visitCompositionComponent).toList()
    );
  }

  private static CompositionSpec visitCompositionComponent(ParseNode compositionComponent) {
    if (!compositionComponent.name().equals("compositionComponent")) throw new AssertionError("Got unexpected " + compositionComponent);
    return switch ((ParseNode) compositionComponent.content()) {
      case ParseNode(String name, ParseNode(String type, Object tm)) when name.equals("compositionMatcher") && type.equals("tokenMatcher") -> visitTokenMatcher(tm);
      case ParseNode(String name, ParseNode(String type, ParseNode localIdentifier)) when name.equals("compositionMatcher") && type.equals("sourceReference") -> new DereferenceComposition(localIdentifier.content().toString());
      case ParseNode(String name, Object c) when name.equals("compositionSkipRule") -> null;
      default -> throw new IllegalStateException("Unexpected value: " + (ParseNode) compositionComponent.content());
    };
  }

  private static CompositionSpec visitTokenMatcher(Object tm) {
    if (tm instanceof ParseNode(String name, ParseNode c) && name.equals("compositionToken")) {
      return visitCompositionToken(c);
    }
    return null;
  }

  private static CompositionSpec visitCompositionToken(ParseNode c) {
    return switch (c.name()) {
      case "literalComposition" -> new LiteralComposition(null);
      case "localIdentifier" -> new NamedComposition(c.content().toString());
      case "stringLiteral" -> new RegexComposition(c.content().toString());
      default -> throw new IllegalStateException("Unexpected value: " + c.name());
    };
  }

  public static void main(String[] args) {
    System.out.println(createSyntaxRules("""
        rule arithmeticExpression: <addition|multiplication|term>
        rule addition: <arithmeticExpression> (<WS>?) <'[+-]'> (<WS>?) <multiplication|term>
        rule multiplication: <multiplication|term> (<WS>?) <'\\*|~/|mod'> (<WS>?) <term>
        rule term: <INT|parentheses>
        rule parentheses: (<='('> <WS>?) <addition|multiplication|term> (<WS>? <=')'>)
     """));
  }

}
