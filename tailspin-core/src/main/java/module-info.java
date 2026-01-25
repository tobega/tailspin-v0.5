module tailspin.language {
  // These are the modules your language needs to talk to
  requires org.graalvm.truffle;
  requires org.graalvm.polyglot;

  // This is the "Magic" line that tells GraalVM your language exists
  provides com.oracle.truffle.api.provider.TruffleLanguageProvider
      with tailspin.language.TailspinLanguageProvider;

  // Export your package so the Polyglot engine can access the classes
  exports tailspin.language;
}
