package tailspin.language.parser.composer;

public class StringUnescape implements SubComposer {
  private final SubComposer producer;

  public StringUnescape(SubComposer producer) {
    this.producer = producer;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    return producer.nibble(s, memo);
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    return producer.backtrack(s, memo);
  }

  @Override
  public Object getValues() {
    return producer.getValues().toString().replace("''", "'");
  }

  @Override
  public boolean isSatisfied() {
    return producer.isSatisfied();
  }
}
