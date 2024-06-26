package tailspin.language.parser.composer;

public interface SubComposer {

  Memo nibble(String s, Memo memo);

  Memo backtrack(String s, Memo memo);

  Object getValues();

  boolean isSatisfied();
}
