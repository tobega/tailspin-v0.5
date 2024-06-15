package tailspin.language.parser.composer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import tailspin.language.parser.composer.CompositionSpec.CaptureComposition;
import tailspin.language.parser.composer.CompositionSpec.ChoiceComposition;
import tailspin.language.parser.composer.CompositionSpec.Constant;
import tailspin.language.parser.composer.CompositionSpec.DereferenceComposition;
import tailspin.language.parser.composer.CompositionSpec.InverseComposition;
import tailspin.language.parser.composer.CompositionSpec.LiteralComposition;
import tailspin.language.parser.composer.CompositionSpec.MultiplierComposition;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.CompositionSpec.RegexComposition;
import tailspin.language.parser.composer.CompositionSpec.SkipComposition;

public class SubComposerFactory implements CompositionSpec.Resolver {

  static final HashMap<String, Pattern> namedPatterns = new HashMap<>();
  static final HashMap<String, Function<? super String, Object>> namedValueCreators = new HashMap<>();
  final Map<String, List<CompositionSpec>> definedSequences;

  static {
    namedPatterns.put("INT", Pattern.compile("[+-]?(0|[1-9][0-9]*)"));
    namedValueCreators.put("INT", s -> {
      try {
        return Long.valueOf(s);
      } catch (NumberFormatException e) {
        return new BigInteger(s);
      }
    });
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
    return switch (spec) {
      case NamedComposition namedSpec -> {
        String name = namedSpec.namedPattern();
        if (definedSequences.containsKey(name)) {
          yield new RuleSubComposer(name, definedSequences.get(name), scope, resolver);
        }
        if (!namedPatterns.containsKey(name)) {
          throw new IllegalArgumentException("Unknown composition rule " + name);
        }
        yield new RegexpSubComposer(namedPatterns.get(name), namedValueCreators.get(name));
      }
      case RegexComposition regexSpec -> new RegexpSubComposer(
          Pattern.compile(regexSpec.pattern()), Function.identity());
      case SkipComposition skip ->
          new SkipSubComposer(new SequenceSubComposer(skip.skipSpecs(), scope, resolver));
      case ChoiceComposition choiceSpec ->
          new ChoiceSubComposer(resolveSpecs(choiceSpec.choices(), scope, resolver));
      case MultiplierComposition multiplier -> new MultiplierSubComposer(
          multiplier.compositionSpec(),
          multiplier.multiplier(), scope, resolver);
      case DereferenceComposition deref -> new DereferenceSubComposer(deref.identifier(), scope);
      case CaptureComposition captureComposition ->
          new CaptureSubComposer(captureComposition.identifier(), scope,
              resolver.resolveSpec(captureComposition.compositionSpec(), scope, resolver));
      case Constant constant -> new ConstantSubComposer(constant.value());
      case InverseComposition inverse -> new InvertSubComposer(
          resolver.resolveSpec(inverse.compositionSpec(), scope, resolver));
      case LiteralComposition lc -> new LiteralSubComposer(lc.literal().resolve(scope));
    };
  }

  List<SubComposer> resolveSpecs(List<CompositionSpec> specs, Scope scope, CompositionSpec.Resolver resolver) {
    return specs.stream().map(spec -> resolver.resolveSpec(spec, scope, resolver)).collect(
        Collectors.toList());
  }
}