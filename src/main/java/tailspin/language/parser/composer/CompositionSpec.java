package tailspin.language.parser.composer;

public interface CompositionSpec {
  interface Resolver {
    // Pass in a resolver instance to allow wrapping
    SubComposer resolveSpec(CompositionSpec spec, Scope scope, CompositionSpec.Resolver resolver);
  }
}
