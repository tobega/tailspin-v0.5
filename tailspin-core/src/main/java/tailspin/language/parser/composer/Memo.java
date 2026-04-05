package tailspin.language.parser.composer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Memo {
  public final int pos;
  private final Memo previous;
  public final List<String> namedRulesStack = new ArrayList<>();
  public String caughtLeftRecursion;
  public SubComposer leftRecursionResult;
  public Set<String> failedRules = new HashSet<>();
  public int maxParsed;

  private Memo(int pos, Memo previous, String caughtLeftRecursion, SubComposer leftRecursionResult) {
    this.pos = pos;
    this.previous = previous;
    this.caughtLeftRecursion = caughtLeftRecursion;
    this.leftRecursionResult = leftRecursionResult;
    this.maxParsed = previous == null ? pos : Math.max(previous.maxParsed, pos);
  }

  public static Memo root(int pos) {
    return new Memo(pos, null, null, null);
  }

  public Memo next(int pos) {
    namedRulesStack.clear();
    return new Memo(pos,this, caughtLeftRecursion, leftRecursionResult);
  }

  public Memo previous() {
    if (previous != null) {
      previous.maxParsed = Math.max(previous.maxParsed, maxParsed);
    }
    return previous;
  }

  public Memo withoutLeftRecursionResult() {
    return new Memo(pos, this, caughtLeftRecursion, null);
  }
}
