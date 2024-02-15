package tailspin.language;

import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(id = "tt", name = "Tailspin")
public class TailspinLanguage extends TruffleLanguage<TailspinLanguageContext> {

  @Override
  protected TailspinLanguageContext createContext(Env env) {
    return new TailspinLanguageContext();
  }
}
