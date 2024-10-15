package tailspin.language.parser;

import static tailspin.language.parser.ParseNode.normalizeValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.CaptureComposition;
import tailspin.language.parser.composer.CompositionSpec.ChoiceComposition;
import tailspin.language.parser.composer.CompositionSpec.DereferenceComposition;
import tailspin.language.parser.composer.CompositionSpec.InverseComposition;
import tailspin.language.parser.composer.CompositionSpec.LiteralComposition;
import tailspin.language.parser.composer.CompositionSpec.MultiplierComposition;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.CompositionSpec.RegexComposition;
import tailspin.language.parser.composer.CompositionSpec.Resolver;
import tailspin.language.parser.composer.CompositionSpec.SkipComposition;
import tailspin.language.parser.composer.CompositionSpec.StringUnescapeComposition;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.RangeMatch;
import tailspin.language.parser.composer.SequenceSubComposer;
import tailspin.language.parser.composer.SubComposerFactory;
import tailspin.language.parser.composer.Value;
import tailspin.language.parser.composer.Value.Constant;
import tailspin.language.parser.composer.Value.Reference;

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
            new StringUnescapeComposition(new RegexComposition("([^']|'')*")),
            new SkipComposition(List.of(new LiteralComposition((s) -> "'")))
        )
    ));
    syntaxRules.putAll(Map.of(
        // rule definedCompositionSequence: <='rule'> <localIdentifier> (<=':'> <WS>?) <compositionComponent>+
        "definedCompositionSequence", List.of(
            new NamedComposition("localIdentifier"),
            new SkipComposition(List.of(new NamedComposition("WS"), new LiteralComposition((s) -> "rule"), new NamedComposition("WS"))),
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
        // tokenMatcher rule ?<|multiplier> (<|='<'>) ?<|='~'> (?<|WS>) +<|compositionToken> (?<|WS> <|='>'>) (?<|WS>)
        "tokenMatcher", List.of(
            new MultiplierComposition(new NamedComposition("multiplier"), RangeMatch.AT_MOST_ONE),
            new SkipComposition(List.of(new LiteralComposition((s) -> "<"))),
            new MultiplierComposition(new LiteralComposition((s) -> "~"), RangeMatch.AT_MOST_ONE),
            new NamedComposition("optionalWhitespace"),
            new MultiplierComposition(new NamedComposition("compositionToken"), RangeMatch.AT_LEAST_ONE),
            new NamedComposition("optionalWhitespace"),
            new SkipComposition(List.of(new LiteralComposition((s) -> ">"))),
            new NamedComposition("optionalWhitespace")
        ),
        // compositionToken rule (<|='|'> <|WS>?) <|literalComposition|localIdentifier|stringLiteral> (<|WS>?)
        "compositionToken", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "|"))),
            new NamedComposition("optionalWhitespace"),
            new ChoiceComposition(List.of(
                new NamedComposition("literalComposition"),
                new NamedComposition("localIdentifier"),
                new NamedComposition("stringLiteral")
            )),
            new NamedComposition("optionalWhitespace")
        ),
        "literalComposition", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "="))),
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
            new SkipComposition(List.of(new LiteralComposition((s) -> "="))),
            new ChoiceComposition(List.of(
                new NamedComposition("INT"),
                new NamedComposition("sourceReference")
            ))
        )
    ));
    syntaxRules.putAll(Map.of(
        // rule compositionSkipRule: (<='('> <WS>?) <skipComposition>+ (<WS>? <=')'> <WS>?);
        "compositionSkipRule", List.of(
            new SkipComposition(List.of(new LiteralComposition((s) -> "("),
              new NamedComposition("optionalWhitespace"))),
            new MultiplierComposition(new NamedComposition("skipComposition"), RangeMatch.AT_LEAST_ONE),
            new SkipComposition(List.of(new NamedComposition("optionalWhitespace"),
              new LiteralComposition((s) -> ")"),
              new NamedComposition("optionalWhitespace")))
        ),
        "skipComposition", List.of(new ChoiceComposition(List.of(
            new NamedComposition("tokenCapture"),
            new NamedComposition("tokenMatcher")
        ))),
        // rule tokenCapture: localIdentifier (<WS> <='is'> <WS>) <compositionMatcher> (<WS>? <=';'> <WS>?)
        "tokenCapture", List.of(
            new NamedComposition("localIdentifier"),
            new SkipComposition(List.of(new NamedComposition("WS"))),
            new SkipComposition(List.of(new LiteralComposition((s) -> "is"))),
            new SkipComposition(List.of(new NamedComposition("WS"))),
            new NamedComposition("compositionMatcher"),
            new NamedComposition("optionalWhitespace"),
            new SkipComposition(List.of(new LiteralComposition((s) -> ";"))),
            new NamedComposition("optionalWhitespace")
        )
    ));
  }

  public static Map<String, List<CompositionSpec>> createSyntaxRules(String parserDefinition) {
    Resolver resolver = new ParseComposerFactory(new SubComposerFactory(syntaxRules));
    SequenceSubComposer composer = new SequenceSubComposer(List.of(
        new NamedComposition("optionalWhitespace"),
        new MultiplierComposition(new NamedComposition("definedCompositionSequence"), RangeMatch.AT_LEAST_ONE)), new ParseNodeScope(null), resolver);
    Memo end = composer.nibble(parserDefinition, Memo.root(0));
    if (end.pos != parserDefinition.length()) throw new AssertionError("Parse failed at \n" + parserDefinition.substring(end.pos));
    @SuppressWarnings("unchecked")
    List<ParseNode> parseNodes = (List<ParseNode>) composer.getValues();
    @SuppressWarnings("unchecked")
    Entry<String, List<CompositionSpec>>[] rules = parseNodes.stream().map(ParserParser::toRule).toList().toArray(new Entry[0]);
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
      case ParseNode(String name, ParseNode cm) when name.equals("compositionMatcher") -> visitCompositionMatcher(cm);
      case ParseNode(String name, Object skipped) when name.equals("compositionSkipRule") -> new SkipComposition(visitSkipped(skipped));
      default -> throw new IllegalStateException("Unexpected value: " + compositionComponent.content());
    };
  }

  private static CompositionSpec visitCompositionMatcher(ParseNode compositionMatcher) {
    return switch (compositionMatcher) {
      case ParseNode(String type, Object tm) when type.equals("tokenMatcher") -> visitTokenMatcher(tm);
      case ParseNode(String type, ParseNode localIdentifier) when type.equals("sourceReference") -> new DereferenceComposition(localIdentifier.content().toString());
      default -> throw new IllegalStateException("Unexpected value: " + compositionMatcher);
    };
  }

  private static List<CompositionSpec> visitSkipped(Object skipped) {
    if (skipped instanceof ParseNode(String name, ParseNode p) && name.equals("skipComposition")) {
      return List.of(visitSkipComposition(p));
    }
    if (skipped instanceof List<?> list) {
      return list.stream().map(p -> visitSkipComposition((ParseNode) ((ParseNode) p).content())).toList();
    }
    throw new IllegalStateException("Unexpected value " + skipped);
  }

  private static CompositionSpec visitSkipComposition(ParseNode p) {
    return switch (p) {
      case ParseNode(String name, Object c) when name.equals("tokenMatcher") -> visitTokenMatcher(c);
      case ParseNode(String name, Object c) when name.equals("tokenCapture")
          -> visitTokenCapture((List<?>) c);
      default -> throw new IllegalStateException("Unexpected value: " + p);
    };
  }

  private static CompositionSpec visitTokenMatcher(Object tm) {
    if (tm instanceof ParseNode(String name, ParseNode c) && name.equals("compositionToken")) {
      return visitCompositionToken(c);
    }
    if (tm instanceof List<?> l) {
      if (l.getFirst() instanceof ParseNode(String name, String c) && name.equals("multiplier")) {
        RangeMatch multiplier = switch (c) {
          case "?" -> RangeMatch.AT_MOST_ONE;
          case "+" -> RangeMatch.AT_LEAST_ONE;
          case "*" -> RangeMatch.ANY_AMOUNT;
          default -> throw new IllegalStateException("Unexpected value: " + c);
        };
        return new MultiplierComposition(visitTokenMatcher(normalizeValues(l.subList(1, l.size()))), multiplier);
      }
      if (l.getFirst() instanceof ParseNode(String name, ParseNode(String cm, ParseNode(
          String type, Long value))) && name.equals("multiplier") && cm.equals("customMultiplier") && type.equals("INT")) {
        return new MultiplierComposition(visitTokenMatcher(normalizeValues(l.subList(1, l.size()))), RangeMatch.exactly(new Constant<>(value)));
      }
      if (l.getFirst() instanceof ParseNode(String name, ParseNode(String cm, ParseNode(
          String type, ParseNode(String li, String ref)))) && name.equals("multiplier") && cm.equals("customMultiplier")
              && type.equals("sourceReference") && li.equals("localIdentifier")) {
        return new MultiplierComposition(visitTokenMatcher(normalizeValues(l.subList(1, l.size()))), RangeMatch.exactly(new Reference<>(ref)));
      }
      if (l.getFirst() instanceof String c && c.equals("~")) {
        return new InverseComposition(visitTokenMatcher(normalizeValues(l.subList(1, l.size()))));
      }
      return new ChoiceComposition(
          l.stream().map(ParserParser::visitTokenMatcher).toList());
    }
    throw new IllegalStateException("Unexpected value " + tm);
  }

  private static CompositionSpec visitCompositionToken(ParseNode c) {
    return switch (c.name()) {
      case "literalComposition" -> new LiteralComposition(visitLiteralValue(c.content()));
      case "localIdentifier" -> new NamedComposition(c.content().toString());
      case "stringLiteral" -> new RegexComposition(c.content().toString());
      default -> throw new IllegalStateException("Unexpected value: " + c.name());
    };
  }

  private static Value<String> visitLiteralValue(Object v) {
    return switch (v) {
      case ParseNode(String type, String value) when type.equals("stringLiteral") -> new Constant<>(value);
      case ParseNode(String type, ParseNode(String ignored, String identifier)) when type.equals("sourceReference")
          -> new Reference<>(identifier);
      default -> throw new IllegalStateException("Unexpected value: " + v);
    };
  }

  private static CompositionSpec visitTokenCapture(List<?> capture) {
    return new CaptureComposition(
        ((ParseNode) capture.getFirst()).content().toString(),
        visitCompositionMatcher((ParseNode) ((ParseNode) capture.getLast()).content())
    );
  }

}
