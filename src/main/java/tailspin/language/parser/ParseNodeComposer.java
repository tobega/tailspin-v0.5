package tailspin.language.parser;

import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.SubComposer;

public class ParseNodeComposer implements SubComposer {
  private final CompositionSpec spec;
  private final SubComposer wrapped;
  private int start;
  private int end;

  public ParseNodeComposer(CompositionSpec spec, SubComposer wrapped) {
    this.spec = spec;
    this.wrapped = wrapped;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    start = memo.pos;
    Memo nibbled = wrapped.nibble(s, memo);
    end = memo.pos;
    return nibbled;
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    Memo backtracked = wrapped.backtrack(s, memo);
    end = backtracked.pos;
    return backtracked;
  }

  @Override
  public Object getValues() {
    Object values = wrapped.getValues();
    values = ParseNode.normalizeValues(values);
    if (values == null) {
      return null;
    }
    if (spec instanceof NamedComposition nc) {
      return new ParseNode(nc.namedPattern(), values, start, end);
    } else {
      return values;
    }
  }

  @Override
  public boolean isSatisfied() {
    return wrapped.isSatisfied();
  }
}
