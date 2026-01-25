module tailspin.language {
  requires org.graalvm.polyglot;
  requires org.graalvm.truffle;

  exports tailspin.language;

  provides com.oracle.truffle.api.provider.TruffleLanguageProvider
      with tailspin.language.TailspinLanguageProvider;

  opens tailspin.language to org.graalvm.truffle;
  opens tailspin.language.nodes to org.graalvm.truffle;
}
