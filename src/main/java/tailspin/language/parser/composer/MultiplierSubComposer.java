package tailspin.language.parser.composer;

import java.util.ArrayList;
import java.util.List;
import tailspin.language.parser.composer.CompositionSpec.Resolver;

public class MultiplierSubComposer implements SubComposer {

  private final CompositionSpec compositionSpec;
  private final RangeMatch multiplier;
  private final Scope scope;
  private final Resolver resolver;
  private List<SubComposer> values;

  public MultiplierSubComposer(CompositionSpec compositionSpec, RangeMatch multiplier, Scope scope,
      Resolver resolver) {
    this.compositionSpec = compositionSpec;
    this.multiplier = multiplier;
    this.scope = scope;
    this.resolver = resolver;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    values = new ArrayList<>();
    return addRepetitions(s, memo);
  }

  private Memo addRepetitions(String s, Memo memo) {
    while (memo.pos < s.length() &&
        (!multiplier.matches((long) values.size(), null, scope)
            || multiplier.matches((long) values.size()+1, null, scope))) {
      SubComposer subComposer = resolver.resolveSpec(compositionSpec, scope, resolver);
      memo = subComposer.nibble(s, memo);
      if (subComposer.isSatisfied()) {
        values.add(subComposer);
      } else {
        break;
      }
    }
    if (!isSatisfied()) {
      memo = rewind(s, memo);
    }
    return memo;
  }

  private Memo rewind(String s, Memo memo) {
    while (!values.isEmpty()) {
      SubComposer last = values.removeLast();
      do {
        memo = last.backtrack(s, memo);
      } while (last.isSatisfied());
    }
    values = null;
    return memo;
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    if (values.isEmpty()) {
      values = null;
      return memo;
    }
    SubComposer last = values.removeLast();
    memo = last.backtrack(s, memo);
    if (last.isSatisfied()) {
      values.add(last);
      return addRepetitions(s, memo);
    }
    // See if we can accept just one less repetition
    if (!isSatisfied()) {
      memo = rewind(s, memo);
    }
    return memo;
  }

  @Override
  public Object getValues() {
    List<Object> result =  new ArrayList<>();
    values.forEach((subComposer) -> {
      Object subValue = subComposer.getValues();
      if (subValue instanceof List<?> l) {
        result.addAll(l);
      } else {
        result.add(subValue);
      }
    });
    return result;
  }

  @Override
  public boolean isSatisfied() {
    return values != null && multiplier.matches((long) values.size(), null, scope);
  }
}
