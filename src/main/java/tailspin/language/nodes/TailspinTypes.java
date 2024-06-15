package tailspin.language.nodes;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;
import java.math.BigInteger;
import tailspin.language.runtime.BigNumber;

@TypeSystem({long.class, BigNumber.class})
public abstract class TailspinTypes {
  @ImplicitCast
  @TruffleBoundary
  public static BigNumber castBigNumber(long value) {
    return new BigNumber(BigInteger.valueOf(value));
  }
}
