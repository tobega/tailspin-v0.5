package tailspin.language.parser.composer;

import java.util.List;
import tailspin.language.parser.ParseNodeScope;
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

public sealed interface CompositionSpec permits NamedComposition, RegexComposition, SkipComposition,
    Constant, DereferenceComposition, CaptureComposition, MultiplierComposition, ChoiceComposition,
    InverseComposition, LiteralComposition {
  interface Resolver {
    // Pass in a resolver instance to allow wrapping
    SubComposer resolveSpec(CompositionSpec spec, ParseNodeScope scope, CompositionSpec.Resolver resolver);
  }

  record NamedComposition(String namedPattern) implements CompositionSpec {
  }

  record RegexComposition(String pattern) implements CompositionSpec {
  }

  record SkipComposition(List<CompositionSpec> skipSpecs) implements CompositionSpec {
  }

  record Constant(String value) implements CompositionSpec {
  }

  record DereferenceComposition(String identifier) implements CompositionSpec {
  }

  record CaptureComposition(String identifier, CompositionSpec compositionSpec) implements CompositionSpec {
  }

  record MultiplierComposition(CompositionSpec compositionSpec, RangeMatch multiplier) implements CompositionSpec {
  }

  record ChoiceComposition(List<CompositionSpec> choices) implements CompositionSpec {
  }

  record InverseComposition(CompositionSpec compositionSpec) implements CompositionSpec {
  }

  record LiteralComposition(Value<String> literal) implements CompositionSpec {
  }
}
