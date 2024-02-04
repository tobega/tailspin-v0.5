package tailspin.language.nodes;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;
import java.math.BigInteger;

@TypeSystem({long.class})
public abstract class TailspinTypes {
  @ImplicitCast
  @TruffleBoundary
  public static BigInteger castBigNumber(long value) {
    return BigInteger.valueOf(value);
  }
}
