package tailspin.language.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;
import java.math.BigInteger;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

@TypeSystem({long.class, BigNumber.class, Rational.class, SciNum.class})
public abstract class TailspinTypes {
  @ImplicitCast
  @TruffleBoundary
  public static BigNumber castBigNumber(long value) {
    return new BigNumber(BigInteger.valueOf(value));
  }

  @ImplicitCast
  @TruffleBoundary
  public static Rational castLongToRational(long value) {
    return new Rational(BigInteger.valueOf(value), BigInteger.ONE);
  }

  @ImplicitCast
  @TruffleBoundary
  public static Rational castBigNumberToRational(BigNumber bigNumber) {
    return new Rational(bigNumber.asBigInteger(), BigInteger.ONE);
  }

  @ImplicitCast
  @TruffleBoundary
  public static SciNum castSciNum(BigNumber value) {
    return SciNum.fromBigNumber(value);
  }

  @ImplicitCast
  @TruffleBoundary
  public static SciNum castSciNum(long value) {
    return SciNum.fromLong(value);
  }
}
