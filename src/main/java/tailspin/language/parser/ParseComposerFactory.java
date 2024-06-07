package tailspin.language.parser;

import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.Resolver;
import tailspin.language.parser.composer.Scope;
import tailspin.language.parser.composer.SubComposer;

public class ParseComposerFactory implements CompositionSpec.Resolver {
  private final CompositionSpec.Resolver realResolver;

  public ParseComposerFactory(Resolver realResolver) {
    this.realResolver = realResolver;
  }

  @Override
  public SubComposer resolveSpec(CompositionSpec spec, Scope scope, Resolver resolver) {
    return new ParseNodeComposer(spec, realResolver.resolveSpec(spec, scope, resolver));
  }
}
