package tailspin.language.runtime;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.util.Arrays;
import java.util.Optional;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.ArrayTypeMatcherNode;
import tailspin.language.nodes.matchers.MeasureTypeMatcher;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;
import tailspin.language.nodes.matchers.StructureTypeMatcherNode;
import tailspin.language.nodes.matchers.TagMatcherNode;

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
      constraint = autoType(value);
    }
    return constraint;
  }

  public static MatcherNode autoType(Object value) {
    return switch (value) {
      case Long ignored -> NumericTypeMatcherNode.create();
      case BigNumber ignored -> NumericTypeMatcherNode.create();
      case Rational ignored -> NumericTypeMatcherNode.create();
      case SciNum ignored -> NumericTypeMatcherNode.create();
      case Measure(Object ignored, Object unit) -> MeasureTypeMatcher.create(unit, INTERNAL_CODE_SOURCE);
      case TaggedValue(VocabularyType type, Object ignored) -> TagMatcherNode.create(type,
          INTERNAL_CODE_SOURCE);
      case TailspinArray ignored -> ArrayTypeMatcherNode.create(INTERNAL_CODE_SOURCE);
      case Structure s -> {
        Object[] keyArray = DynamicObjectLibrary.getUncached().getKeyArray(s);
        yield StructureTypeMatcherNode.create(
            Arrays.copyOf(keyArray, keyArray.length, VocabularyType[].class), false, new VocabularyType[0],
            INTERNAL_CODE_SOURCE);
      }
      default -> AlwaysTrueMatcherNode.create(INTERNAL_CODE_SOURCE);
    };
  }

  public void setConstraint(MatcherNode constraint) {
    if (this.constraint != null) throw new IllegalStateException("Cannot redefine type " + key);
    this.constraint = constraint;
  }

  public Optional<MatcherNode> getConstraint() {
    return Optional.ofNullable(constraint);
  }
}
