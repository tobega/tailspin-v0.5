package tailspin.language.parser.composer;

class DereferenceSubComposer implements SubComposer {
  private final String identifier;
  private final Scope scope;
  private boolean satisfied;

  DereferenceSubComposer(String identifier, Scope scope) {
    this.identifier = identifier;
    this.scope = scope;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    satisfied = true;
    return memo;
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    satisfied = false; // Need to keep going back
    return memo;
  }

  @Override
  public Object getValues() {
    return scope.getValue(identifier);
  }

  @Override
  public boolean isSatisfied() {
    return satisfied;
  }
}
