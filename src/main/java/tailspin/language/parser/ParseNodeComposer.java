package tailspin.language.parser;

import java.util.List;
import java.util.Objects;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.SubComposer;

public class ParseNodeComposer implements SubComposer {
  private final CompositionSpec spec;
  private final SubComposer wrapped;

  public ParseNodeComposer(CompositionSpec spec, SubComposer wrapped) {
    this.spec = spec;
    this.wrapped = wrapped;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    return wrapped.nibble(s, memo);
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    return wrapped.backtrack(s, memo);
  }

  @Override
  public Object getValues() {
    Object values = wrapped.getValues();
    if (values == null) {
      return null;
    } else if (values instanceof List<?> list) {
      list = list.stream().filter(Objects::nonNull).toList();
      if (list.isEmpty()) return null;
      if (list.size() == 1) {
        values = list.getFirst();
      } else {
        values = list;
      }
    }
    if (spec instanceof NamedComposition nc) {
      return new ParseNode(nc.namedPattern(), values);
    } else {
      return values;
    }
  }

  @Override
  public boolean isSatisfied() {
    return wrapped.isSatisfied();
  }
}
