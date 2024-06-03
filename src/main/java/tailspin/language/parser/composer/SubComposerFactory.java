package tailspin.language.parser.composer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SubComposerFactory {

  static final HashMap<String, Pattern> namedPatterns = new HashMap<>();
  static final HashMap<String, Function<? super String, Object>> namedValueCreators = new HashMap<>();
  final Map<String, List<CompositionSpec>> definedSequences;

  static {
    namedPatterns.put("INT", Pattern.compile("[+-]?(0|[1-9][0-9]*)"));
    namedValueCreators.put("INT", Long::valueOf);
    namedPatterns.put("WS", Pattern.compile("\\s+"));
    namedValueCreators.put("WS", Function.identity());
    namedPatterns.put("HEX", Pattern.compile(("[0-9a-fA-F]+")));
    namedValueCreators.put("HEX", SubComposerFactory::fromString);
  }

  public static byte[] fromString(String hex) {
    if (hex.length() % 2 != 0) hex = "0" + hex;
    byte[] bytes = new byte[hex.length()/2];
    for (int i = 0; i < hex.length(); i += 2) {
      bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
          + Character.digit(hex.charAt(i+1), 16));
    }
    return bytes;
  }

  public SubComposerFactory(Map<String, List<CompositionSpec>> definedSequences) {
    this.definedSequences = definedSequences;
  }

  public SubComposer resolveSpec(CompositionSpec spec, Scope scope, CompositionSpec.Resolver resolver) {
    if (spec instanceof NamedComposition namedSpec) {
      String name = namedSpec.namedPattern;
      if (definedSequences.containsKey(name)) {
        return new RuleSubComposer(name, definedSequences.get(name), scope, resolver);
      }
      if (!namedPatterns.containsKey(name)) {
        throw new IllegalArgumentException("Unknown composition rule " + name);
      }
      return new RegexpSubComposer(namedPatterns.get(name), namedValueCreators.get(name));
    }
    if (spec instanceof RegexComposition regexSpec) {
      // Note that we do not allow regex interpolations to reference $. What would that even mean?
      return new RegexpSubComposer(
          Pattern.compile(regexSpec.pattern), Function.identity());
    }
    if (spec instanceof SkipComposition) {
      return new SkipSubComposer(new SequenceSubComposer(((SkipComposition) spec).skipSpecs, scope, resolver));
    }
    if (spec instanceof ChoiceComposition choiceSpec) {
      return new ChoiceSubComposer(resolveSpecs(choiceSpec.choices, scope, resolver));
    }
    if (spec instanceof MultiplierComposition) {
      return new MultiplierSubComposer(
          ((MultiplierComposition) spec).compositionSpec,
          ((MultiplierComposition) spec).multiplier, scope, resolver);
    }
    if (spec instanceof DereferenceComposition) {
      return new DereferenceSubComposer(((DereferenceComposition) spec).identifier, scope);
    }
    if (spec instanceof CaptureComposition captureComposition) {
      return new CaptureSubComposer(captureComposition.identifier, scope,
          resolveSpec(captureComposition.compositionSpec, scope, resolver));
    }
    if (spec instanceof Constant) {
      return new ConstantSubComposer(((Constant) spec).value);
    }
    if (spec instanceof InverseComposition) {
      return new InvertSubComposer(
          resolveSpec(((InverseComposition) spec).compositionSpec, scope, resolver));
    }
    if (spec instanceof LiteralComposition lc) {
      return new LiteralSubComposer(lc.literal);
    }
    throw new UnsupportedOperationException(
        "Unknown composition spec " + spec.getClass().getSimpleName());
  }

  List<SubComposer> resolveSpecs(List<CompositionSpec> specs, Scope scope, CompositionSpec.Resolver resolver) {
    return specs.stream().map(spec -> resolver.resolveSpec(spec, scope, resolver)).collect(
        Collectors.toList());
  }

  public static class NamedComposition implements CompositionSpec {

    private final String namedPattern;

    public NamedComposition(String namedPattern) {
      this.namedPattern = namedPattern;
    }
  }

  public static class RegexComposition implements CompositionSpec {

    private final String pattern;

    public RegexComposition(String pattern) {
      this.pattern = pattern;
    }
  }

  public static class SkipComposition implements CompositionSpec {

    private final List<CompositionSpec> skipSpecs;

    public SkipComposition(List<CompositionSpec> skipSpecs) {
      this.skipSpecs = skipSpecs;
    }
  }

  public static class Constant implements CompositionSpec {

    private final String value;

    public Constant(String value) {
      this.value = value;
    }
  }

  public static class DereferenceComposition implements CompositionSpec {

    private final String identifier;

    public DereferenceComposition(String identifier) {
      this.identifier = identifier;
    }
  }

  public static class CaptureComposition implements CompositionSpec {

    private final String identifier;
    private final CompositionSpec compositionSpec;

    public CaptureComposition(String identifier, CompositionSpec compositionSpec) {
      this.identifier = identifier;
      this.compositionSpec = compositionSpec;
    }
  }

  public static class MultiplierComposition implements CompositionSpec {

    private final CompositionSpec compositionSpec;
    private final RangeMatch multiplier;

    public MultiplierComposition(CompositionSpec compositionSpec, RangeMatch multiplier) {
      this.compositionSpec = compositionSpec;
      this.multiplier = multiplier;
    }
  }

  public static class ChoiceComposition implements CompositionSpec {

    private final List<CompositionSpec> choices;

    public ChoiceComposition(List<CompositionSpec> choices) {
      this.choices = choices;
    }
  }

  public static class InverseComposition implements CompositionSpec {

    private final CompositionSpec compositionSpec;

    public InverseComposition(CompositionSpec compositionSpec) {
      this.compositionSpec = compositionSpec;
    }
  }

  public static class LiteralComposition implements CompositionSpec {

    private final String literal;

    public LiteralComposition(String literal) {
      this.literal = literal;
    }
  }
}