package tailspin.language.parser.composer;

import tailspin.language.parser.composer.Value.Constant;

public class RangeMatch {

  public record Bound(Value<Long> value, boolean inclusive){}
  public static final RangeMatch AT_MOST_ONE = new RangeMatch(
      new Bound(new Constant<>(0L), true),
      new Bound(new Constant<>(1L), true));
  public static final RangeMatch AT_LEAST_ONE = new RangeMatch(
          new Bound(new Constant<>(1L), true),
          null);
  public static final RangeMatch ANY_AMOUNT = new RangeMatch(
              new Bound(new Constant<>(0L), true),
              null);

  public static RangeMatch exactly(Value<Long> target) {
    return new RangeMatch(
        new Bound(target, true),
        new Bound(target, true)
    );
  }

  private final Bound lowerBound;
  private final Bound upperBound;

  public RangeMatch(Bound lowerBound, Bound upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  public boolean matches(Object toMatch, Scope scope) {
    boolean result = false;
    if (lowerBound != null) {
      Object low = lowerBound.value.resolve(scope);
      result = compare(toMatch, lowerBound.inclusive ? Comparison.GREATER_OR_EQUAL : Comparison.GREATER, low);
      if (!result) return false;
    }
    if (upperBound != null) {
      Object high = upperBound.value.resolve(scope);
      result = compare(toMatch, upperBound.inclusive ? Comparison.LESS_OR_EQUAL : Comparison.LESS, high);
    }
    return result;
  }

  @Override
  public String toString() {
    return (lowerBound == null ? "" : lowerBound.value + (lowerBound.inclusive ? "" : "~")) + ".."
        + (upperBound == null ? "" : (upperBound.inclusive ? "" : "~") + upperBound.value);
  }

  public enum Comparison {
    LESS {
      @Override
      public boolean isValid(int comparison) {
        return comparison < 0;
      }
    }, EQUAL {
      @Override
      public boolean isValid(int comparison) {
        return comparison == 0;
      }
    }, GREATER {
      @Override
      public boolean isValid(int comparison) {
        return comparison > 0;
      }
    }, GREATER_OR_EQUAL {
      @Override
      public boolean isValid(int comparison) {
        return GREATER.isValid(comparison) || EQUAL.isValid(comparison);
      }
    }, LESS_OR_EQUAL {
      @Override
      public boolean isValid(int comparison) {
        return LESS.isValid(comparison) || EQUAL.isValid(comparison);
      }
    };

    public abstract boolean isValid(int comparison);
  }

  public static boolean compare(Object toMatch, Comparison comparison, Object rhs) {
    boolean matches = false;
    if ((toMatch instanceof String ls) && (rhs instanceof String rs)) {
      matches = comparison.isValid(ls.compareTo(rs));
    } else if ((toMatch instanceof Number ln) && (rhs instanceof Number rn)) {
      matches = comparison.isValid(Long.compare(ln.longValue(), rn.longValue()));
    }
    return matches;
  }
}
