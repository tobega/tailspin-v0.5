package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public class SmallRational implements TruffleObject, Comparable<SmallRational> {
  private final long numerator;
  private final long denominator;

  private SmallRational(long numerator, long denominator) {
    if (denominator < 0) {
      this.numerator = -numerator;
      this.denominator = -denominator;
    } else {
      this.numerator = numerator;
      this.denominator = denominator;
    }
  }

  public static SmallRational of(long numerator, long denominator) {
    long gcd = gcd(numerator, denominator);
    numerator = numerator / gcd;
    denominator = denominator / gcd;
    return new SmallRational(numerator, denominator);
  }

  static long gcd(long a, long b) {
    long x = Math.abs(a);
    long y = Math.abs(b);
    while (y != 0) {
      long temp = y;
      y = x % y;
      x = temp;
    }
    return x;
  }

  public static long binaryGcd(long a, long b) {
    if (a == 0) return Math.abs(b);
    if (b == 0) return Math.abs(a);

    a = Math.abs(a);
    b = Math.abs(b);
    int shift = Long.numberOfTrailingZeros(a | b);
    a >>= Long.numberOfTrailingZeros(a);

    do {
      b >>= Long.numberOfTrailingZeros(b);
      if (a > b) {
        long temp = a;
        a = b;
        b = temp;
      }
      b = b - a;
    } while (b != 0);

    return a << shift;
  }

  static long lcm(long a, long b) {
    if (a == 0 || b == 0) return 0;
    long gcd = gcd(a, b);
    // Divide first to reduce the size of the intermediate product
    return Math.abs(a / gcd * b);
  }

  @Override
  public String toString() {
    return numerator + (denominator == 1L ? "" : ("/" + denominator));
  }

  public SmallRational divide(SmallRational right) {
    long nGcd = gcd(numerator, right.numerator);
    long dGcd = gcd(denominator, right.denominator);
    return new SmallRational(Math.multiplyExact(numerator / nGcd, right.denominator / dGcd),
        Math.multiplyExact(denominator / dGcd, right.numerator / nGcd));
  }

  public SmallRational multiply(SmallRational right) {
    long aGcd = gcd(numerator, right.denominator);
    long bGcd = gcd(denominator, right.numerator);
    return new SmallRational(Math.multiplyExact(numerator / aGcd, right.numerator / bGcd),
        Math.multiplyExact(denominator / bGcd, right.denominator / aGcd));
  }

  public SmallRational add(SmallRational right) {
    long gcd = gcd(denominator, right.denominator);

    long sum;
    long leftDenominator;
    if (gcd == 1) {
      leftDenominator = denominator;
      long addend = Math.multiplyExact(numerator, right.denominator);
      long augend = Math.multiplyExact(denominator, right.numerator);
      sum = Math.addExact(addend, augend);
    } else {
      leftDenominator = denominator / gcd;
      long addend = Math.multiplyExact(numerator, right.denominator / gcd);
      long augend = Math.multiplyExact(right.numerator, denominator / gcd);
      sum = Math.addExact(addend, augend);
    }

    long lGcd = gcd(sum, leftDenominator);
    if (lGcd > 1) {
      sum /= lGcd;
      leftDenominator /= lGcd;
    }
    long rGcd = gcd(sum, right.denominator);
    return new SmallRational(sum / rGcd, Math.multiplyExact(leftDenominator, right.denominator / rGcd));
  }

  public SmallRational subtract(SmallRational right) {
    long gcd = gcd(denominator, right.denominator);

    long difference;
    long leftDenominator;
    if (gcd == 1) {
      leftDenominator = denominator;
      long minuend = Math.multiplyExact(numerator, right.denominator);
      long subtrahend = Math.multiplyExact(denominator, right.numerator);
      difference = Math.subtractExact(minuend, subtrahend);
    } else {
      leftDenominator = denominator / gcd;
      long minuend = Math.multiplyExact(numerator, right.denominator / gcd);
      long subtrahend = Math.multiplyExact(right.numerator, denominator / gcd);
      difference = Math.subtractExact(minuend, subtrahend);
    }

    long lGcd = gcd(difference, leftDenominator);
    if (lGcd > 1) {
      difference /= lGcd;
      leftDenominator /= lGcd;
    }
    long rGcd = gcd(difference, right.denominator);
    return new SmallRational(difference / rGcd, Math.multiplyExact(leftDenominator, right.denominator / rGcd));
  }

  public SmallRational mod(SmallRational modulus) {
    long gcd = gcd(denominator, modulus.denominator);

    long leftNumerator = Math.multiplyExact(numerator, modulus.denominator / gcd);
    long leftDenominator = denominator / gcd;
    long rightNumerator = Math.multiplyExact(modulus.numerator, leftDenominator);

    long resN = leftNumerator % rightNumerator;
    if (resN < 0) {
      resN += Math.abs(rightNumerator);
    }

    long lGcd = gcd(resN, leftDenominator);
    if (lGcd > 1) {
      resN /= lGcd;
      leftDenominator /= lGcd;
    }
    long rGcd = gcd(resN, modulus.denominator);
    return new SmallRational(resN / rGcd, Math.multiplyExact(leftDenominator, modulus.denominator / rGcd));
  }

  public SmallRational negate() {
    return new SmallRational(Math.negateExact(numerator), denominator);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof SmallRational r) {
      return numerator == r.numerator && denominator == r.denominator;
    }
    return false;
  }

  @Override
  public int compareTo(SmallRational other) {
    long left = Math.multiplyExact(numerator, other.denominator);
    long right = Math.multiplyExact(other.numerator, denominator);
    return Long.compare(left, right);
  }

  public long numerator() {
    return numerator;
  }

  public long denominator() {
    return denominator;
  }

  public long truncateDivide(SmallRational other) {
    long left = Math.multiplyExact(numerator, other.denominator / gcd(numerator, other.denominator));
    long right = Math.multiplyExact(other.numerator, denominator / gcd(other.numerator, denominator));
    return left / right;
  }

  @ExportMessage
  public boolean hasMembers() {
    return true;
  }

  static final Set<String> supportedMessages = Set.of("raw");

  @ExportMessage
  public boolean isMemberReadable(String member) {
    return supportedMessages.contains(member);
  }

  @ExportMessage
  public Object readMember(String member) throws UnknownIdentifierException {
    return switch(member) {
      case "raw" -> this;
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"raw"});
  }
}
