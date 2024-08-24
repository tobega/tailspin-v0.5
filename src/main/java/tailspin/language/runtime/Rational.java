package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import java.math.BigInteger;

@ValueType
public class Rational implements TruffleObject {
  private final BigInteger numerator;
  private final BigInteger denominator;

  public Rational(BigInteger numerator, BigInteger denominator) {
    if (denominator.signum() < 0) {
      numerator = numerator.negate();
      denominator = denominator.negate();
    }
    BigInteger gcd = numerator.gcd(denominator);
    this.numerator = numerator.divide(gcd);
    this.denominator = denominator.divide(gcd);
  }

  @Override
  public String toString() {
    return numerator.toString() + "/" + denominator.toString();
  }

  public Object simplestForm() {
    if (denominator.equals(BigInteger.ONE)) {
      try {
        return numerator.longValueExact();
      } catch (ArithmeticException e) {
        return new BigNumber(numerator);
      }
    }
    return this;
  }

  public Rational divide(Rational right) {
    return new Rational(numerator.multiply(right.denominator), denominator.multiply(right.numerator));
  }

  public Rational multiply(Rational right) {
    return new Rational(numerator.multiply(right.numerator), denominator.multiply(right.denominator));
  }

  public Rational add(Rational right) {
    return new Rational(numerator.multiply(right.denominator).add(right.numerator.multiply(denominator)),
        denominator.multiply(right.denominator));
  }

  public Rational subtract(Rational right) {
    return new Rational(numerator.multiply(right.denominator).subtract(right.numerator.multiply(denominator)),
        denominator.multiply(right.denominator));
  }
}
