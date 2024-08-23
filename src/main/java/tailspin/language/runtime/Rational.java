package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import java.math.BigInteger;

@ValueType
public class Rational implements TruffleObject {
  private final BigInteger numerator;
  private final BigInteger denominator;

  public Rational(BigInteger numerator, BigInteger denominator) {
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
}
