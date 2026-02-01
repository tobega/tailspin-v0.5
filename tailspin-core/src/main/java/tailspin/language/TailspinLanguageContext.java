package tailspin.language;

import com.oracle.truffle.api.TruffleLanguage.Env;

public class TailspinLanguageContext {
  private final Env env;

  public TailspinLanguageContext(Env env) {
    this.env = env;
  }

  public Env getEnv() {
    return env;
  }
}
