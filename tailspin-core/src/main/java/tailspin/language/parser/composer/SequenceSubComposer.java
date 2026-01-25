package tailspin.language.parser.composer;

import java.util.ArrayList;
import java.util.List;
import tailspin.language.parser.ParseNodeScope;
import tailspin.language.parser.composer.CompositionSpec.Resolver;

public class SequenceSubComposer implements SubComposer {
  private final List<CompositionSpec> sequence;
  private final ParseNodeScope scope;
  private final Resolver resolver;
  private List<SubComposer> value;

  public SequenceSubComposer(List<CompositionSpec> sequence, ParseNodeScope scope, CompositionSpec.Resolver resolver) {
    this.sequence = sequence;
    this.scope = scope;
    this.resolver = resolver;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    value = new ArrayList<>();
    return resolveRemaining(s, memo);
  }

  private Memo resolveRemaining(String s, Memo memo) {
    for (CompositionSpec spec : sequence.subList(value.size(), sequence.size())) {
      SubComposer subComposer = resolver.resolveSpec(spec, scope, resolver);
      memo = subComposer.nibble(s, memo);
      if (subComposer.isSatisfied()) {
        value.add(subComposer);
      } else {
        return backtrack(s, memo);
      }
    }
    return memo;
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    while (!value.isEmpty()) {
      SubComposer subComposer = value.removeLast();
      memo = subComposer.backtrack(s, memo);
      if (subComposer.isSatisfied()) {
        value.add(subComposer);
        return resolveRemaining(s, memo);
      }
    }
    value = null;
    return memo;
  }

  @Override
  public Object getValues() {
    List<Object> result =  new ArrayList<>();
    value.forEach((subComposer) -> {
      Object subValue = subComposer.getValues();
      if (subValue != null) {
        if (subValue instanceof List<?> l) {
          result.addAll(l);
        } else {
          result.add(subValue);
        }
      }
    });
    return result;
  }

  @Override
  public boolean isSatisfied() {
    if (sequence.isEmpty()) return true;
    return  value != null && !value.isEmpty() && value.stream().allMatch(SubComposer::isSatisfied);
  }
}
