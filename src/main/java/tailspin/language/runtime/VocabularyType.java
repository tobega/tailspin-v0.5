package tailspin.language.runtime;

import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;

public class VocabularyType implements Comparable<VocabularyType> {
  private final String key;
  private MatcherNode constraint;

  public VocabularyType(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }

  @Override
  public int compareTo(VocabularyType o) {
    return key.compareTo(o.key);
  }

  public MatcherNode getConstraint(Object value) {
    if (constraint == null) {
      autoType(value);
    }
    return constraint;
  }

  private void autoType(Object value) {
    constraint = switch (value) {
      case Long ignored -> NumericTypeMatcherNode.create();
      case BigNumber ignored -> NumericTypeMatcherNode.create();
      default -> AlwaysTrueMatcherNode.create();
    };
  }
}
