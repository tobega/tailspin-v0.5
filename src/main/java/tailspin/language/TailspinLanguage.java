package tailspin.language;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;

@TruffleLanguage.Registration(id = "tt", name = "Tailspin")
public class TailspinLanguage extends TruffleLanguage<TailspinLanguageContext> {
  private static final LanguageReference<TailspinLanguage> REF =
      LanguageReference.create(TailspinLanguage.class);

  /** Retrieve the current language instance for the given {@link Node}. */
  public static TailspinLanguage get(Node node) {
    return REF.get(node);
  }

  @Override
  protected TailspinLanguageContext createContext(Env env) {
    return new TailspinLanguageContext();
  }
}
