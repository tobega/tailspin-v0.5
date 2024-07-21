package tailspin.language.parser.composer;

import java.util.List;
import tailspin.language.parser.ParseNodeScope;

public class CaptureSubComposer implements SubComposer {
  private final String identifier;
  private final ParseNodeScope scope;
  private final SubComposer subComposer;
  private boolean satisfied = false;

  CaptureSubComposer(String identifier, ParseNodeScope scope, SubComposer subComposer) {
    this.identifier = identifier;
    this.scope = scope;
    this.subComposer = subComposer;
  }

  @Override
  public Memo nibble(String s, Memo memo) {
    memo = subComposer.nibble(s, memo);
    checkSatisfaction();
    return memo;
  }

  private void checkSatisfaction() {
    satisfied = subComposer.isSatisfied();
    if (subComposer.isSatisfied()) {
      Object result = oneValue(subComposer.getValues());
      scope.defineValue(identifier, result);
    }
  }

  static Object oneValue(Object values) {
    if (values instanceof List<?> l) {
      if (l.size() == 1) {
        return l.getFirst();
      } else {
        throw new AssertionError();
      }
    }
    return values;
  }

  @Override
  public Memo backtrack(String s, Memo memo) {
    scope.undefineValue(identifier);
    memo = subComposer.backtrack(s, memo);
    checkSatisfaction();
    return memo;
  }

  @Override
  public Object getValues() {
    return null;
  }

  @Override
  public boolean isSatisfied() {
    return  satisfied;
  }
}
