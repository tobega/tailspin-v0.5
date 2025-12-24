package tailspin.language.runtime;

import static java.lang.Math.abs;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public class SmallSciNum implements TruffleObject {
  public static final long MAX_MANTISSA = (long) Math.pow(10, 15) - 1;
  private static final double BITS_PER_DIGIT = 3.322;

  private double s;
  private double c;
  private final int precision;
  private final int bitsToKeep;

  private SmallSciNum(double s, double c, int precision, int bitsToKeep) {
    this.s = s;
    this.c = c;
    this.precision = precision;
    this.bitsToKeep = bitsToKeep;
  }

  private SmallSciNum(double value, int precision) {
    this(value, 0, precision, (int)Math.ceil(precision * BITS_PER_DIGIT) + 1);
  }

  public static SmallSciNum fromDigits(String digits, int precision, int exponent) {
    long mantissa = Long.valueOf(digits);
    if (abs(mantissa) > MAX_MANTISSA) throw new ArithmeticException("Too large " + digits);
    return new SmallSciNum(mantissa * Math.pow(10, exponent), precision);
  }

  public static SmallSciNum fromLong(long value) {
    return new SmallSciNum(value, 14);
  }

  private static SmallSciNum newNormalized(double inS, double inC, int precision, int bitsToKeep) {
    double s = inS + inC;
    if (s == 0) return new SmallSciNum(0, 0, precision, bitsToKeep);

    // 1. Calculate the 'Weight' of the last decimal digit
    // For 11.1, the magnitude is 10^1. 3 digits of precision means 1-3+1 = -1.
    double mag = Math.floor(Math.log10(Math.abs(s)) + 1e-9);
    double decimalStep = Math.pow(10, mag - (precision - 1));

    // 2. Round the sum to the nearest multiple of that decimal step
    double roundedS = Math.round(s / decimalStep) * decimalStep;

    // 3. The carry is now just the 'discarded' part
    double c = (inS - roundedS) + inC;

    return new SmallSciNum(roundedS, c, precision, bitsToKeep);
  }

  public SmallSciNum multiply(SmallSciNum other) {
    int newPrecision = Math.min(this.precision, other.precision);
    int newBits = Math.min(this.bitsToKeep, other.bitsToKeep);

    double newS = this.s * other.s;
    double newC = Math.fma(this.s, other.s, -newS) + (this.c * other.s) + (other.c * this.s);

    return newNormalized(newS, newC, newPrecision, newBits);
  }

  public SmallSciNum divide(SmallSciNum other) {
    int newPrecision = Math.min(this.precision, other.precision);
    int newBits = Math.min(this.bitsToKeep, other.bitsToKeep);

    // 1. Estimate the quotient
    double newS = this.s / other.s;

    // 2. Use FMA to find the remainder: (dividend - quotient * divisor)
    // This calculates exactly how much 's' was not covered by 'newS'
    double remainder = Math.fma(-newS, other.s, this.s);

    // 3. The new correction includes the remainder and the old corrections
    double newC = (remainder + this.c - (newS * other.c)) / other.s;

    return newNormalized(newS, newC, newPrecision, newBits);
  }

  public SmallSciNum add(SmallSciNum other) {
    // 1. Neumaier addition for high-precision sum (t) and carry (newC)
    double t = this.s + other.s;
    double newC;
    if (Math.abs(this.s) >= Math.abs(other.s)) {
      newC = (this.s - t) + other.s;
    } else {
      newC = (other.s - t) + this.s;
    }
    newC += (this.c + other.c);

    int finalDigits = additionAccuracyDigits(other, t + newC);
    // 6. Derive bit budget from precision
    int finalBits = (int) Math.ceil(finalDigits * BITS_PER_DIGIT) + 2;

    // Use the Neumaier results (t, newC) for the normalized result
    return newNormalized(t, newC, finalDigits, finalBits);
  }

  private int additionAccuracyDigits(SmallSciNum other, double total) {
    // 3. Calculate the decimal anchor (coarsest floor)
    int floorThis = getAbsoluteFloor(this);
    int floorOther = getAbsoluteFloor(other);
    int targetDecimalFloor = Math.max(floorThis, floorOther);

    // 2. Handle the zero case
    if (total == 0) {
      int zeroPrecision = (0 - targetDecimalFloor + 1);
      return  Math.max(1, zeroPrecision);
    }

    // 4. Calculate magnitude of the RESULT (use t + newC for best estimate)
    double resMag = Math.floor(Math.log10(Math.abs(total)) + 1e-9);

    // 5. Secure the Decimal Precision
    int finalDigits = (int) (resMag - targetDecimalFloor + 1);
    return Math.max(1, finalDigits);
  }

  private int getAbsoluteFloor(SmallSciNum n) {
    // Returns the power of 10 of the last significant digit
    double magnitude = (n.s == 0) ? 0 : Math.floor(Math.log10(Math.abs(n.s)) + 1e-9);
    return (int) (magnitude - (n.precision - 1));
  }

  public SmallSciNum subtract(SmallSciNum other) {
    // 1. Neumaier subtraction (Addition of a negative)
    double negS = -other.s;
    double negC = -other.c;

    double t = this.s + negS;
    double newC;
    if (abs(this.s) >= abs(negS)) {
      newC = (this.s - t) + negS;
    } else {
      newC = (negS - t) + this.s;
    }
    newC += (this.c + negC);

    int finalDigits = additionAccuracyDigits(other, t + newC);
    // 6. Derive bit budget from precision
    int finalBits = (int) Math.ceil(finalDigits * BITS_PER_DIGIT) + 2;

    return newNormalized(t, newC, finalDigits, finalBits);
  }

  public SmallSciNum negate() {
    return new SmallSciNum(-s, -c, precision, bitsToKeep);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    BigDecimal value = new BigDecimal(s, new MathContext(precision));
    builder.append(value.unscaledValue());
    int offset = builder.charAt(0) == '-' ? 2 : 1;
    int extras = precision - builder.length() + offset - 1;
    builder.repeat("0", extras);
    int exponent = builder.length() - extras - offset - value.scale();
    if (builder.length() > 1) {
      builder.insert(offset, '.');
    }
    builder.append('e').append(exponent);
    return builder.toString();
  }

  public int compareTo(SmallSciNum other) {
    // 1. Calculate the 'floor' (uncertainty) for both
    double floorThis = Math.pow(2, Math.getExponent(this.s) - this.bitsToKeep);
    double floorOther = Math.pow(2, Math.getExponent(other.s) - other.bitsToKeep);

    // 2. Use the 'coarsest' uncertainty as our epsilon
    double epsilon = Math.max(floorThis, floorOther);

    double diff = (this.s + this.c) - (other.s + other.c);

    // 3. Check if the difference is smaller than our 'noise' threshold
    if (abs(diff) <= epsilon) {
      return 0; // Effectively equal
    }

    return diff > 0 ? 1 : -1;
  }

  @TruffleBoundary
  public SmallSciNum mod(SmallSciNum modulus) {
    int newPrecision = Math.min(this.precision, modulus.precision);
    int newBits = Math.min(this.bitsToKeep, modulus.bitsToKeep);
    // 1. Calculate the standard remainder
    // Java's % operator for -7 % 4 returns -3.
    double r = this.s % modulus.s;

    // 2. Force it to be positive relative to the ABSOLUTE value of the modulus
    // This handles the -7 mod 4 case: -3 + abs(4) = 1
    double absDiv = Math.abs(modulus.s);
    double result = (r < 0) ? (r + absDiv) : r;

    return newNormalized(result, 0.0, newPrecision, newBits);
  }

  @TruffleBoundary
  public Object truncateDivide(SmallSciNum divisor) {
    if (divisor.s == 0) throw new ArithmeticException("Division by zero");

    // 1. Calculate the raw quotient using the full precision
    double quotient = (this.s + this.c) / (divisor.s + divisor.c);

    if (Double.isNaN(quotient) || Double.isInfinite(quotient)) {
      throw new ArithmeticException("Result is not a finite number");
    }

    // 2. Check the bounds of a Long
    // We use a slightly smaller bound to be safe from rounding errors during the check
    double absQ = Math.abs(quotient);
    if (absQ <= 9e18) {
      return (long) quotient;
    }

    // 3. Fallback to BigInteger for huge numbers
    // We convert to a string or use BigDecimal to ensure an exact integral conversion
    return new java.math.BigDecimal(quotient).toBigInteger();
  }

  public SmallSciNum power(double exponent) {
    // 1. Calculate the result using the identity e^(y * ln(x))
    double valS = Math.pow(this.s, exponent);

    // 2. Precision Governing:
    // The relative error is scaled by the magnitude of the exponent.
    // We lose log2(|exponent|) bits of precision.
    double bitLoss = (exponent == 0) ? 0 : Math.log(abs(exponent)) / Math.log(2);
    int newBits = Math.max(0, (int) Math.floor(this.bitsToKeep - bitLoss));

    // 3. Compensation Refinement
    // For general powers, we can approximate the new compensation
    // using the derivative: d(x^y)/dx = y * x^(y-1)
    double derivative = exponent * Math.pow(this.s, exponent - 1);
    double newC = derivative * this.c;

    return newNormalized(valS, newC, (int) Math.floor(newBits / BITS_PER_DIGIT), newBits);
  }

  public SmallSciNum squareRoot() {
    // 1. Initial estimate
    double rootS = Math.sqrt(this.s);

    // 2. Refine the correction term
    // Since sqrt(x) is x^0.5, we maintain the same bitsToKeep.
    // In fact, sqrt slightly compresses relative error.
    int finalBits = this.bitsToKeep + 1;

    // Use FMA to find the residual: (this.s - rootS * rootS)
    double residual = Math.fma(-rootS, rootS, this.s);

    // Babylonian refinement for the compensation term
    double rootC = (residual + this.c) / (2.0 * rootS);

    return newNormalized(rootS, rootC, precision, finalBits);
  }

  public double getDouble() {
    return s;
  }

  public int getPrecision() {
    return precision;
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


  @ExportMessage
  public boolean isNumber() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInByte() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInShort() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInInt() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInLong() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInDouble() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInFloat() {
    return precision <= Float.PRECISION;
  }

  @ExportMessage
  public boolean fitsInBigInteger() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public byte asByte() throws UnsupportedMessageException {
    return (byte) s;
  }

  @ExportMessage
  @TruffleBoundary
  public short asShort() throws UnsupportedMessageException {
    return (short) s;
  }

  @ExportMessage
  @TruffleBoundary
  public int asInt() throws UnsupportedMessageException {
    return (int) s;
  }

  @ExportMessage
  @TruffleBoundary
  public long asLong() throws UnsupportedMessageException {
    return (long) s;
  }

  @ExportMessage
  @TruffleBoundary
  public float asFloat() throws UnsupportedMessageException {
    return (float) s;
  }

  @ExportMessage
  @TruffleBoundary
  public double asDouble() throws UnsupportedMessageException {
    return s;
  }

  @ExportMessage
  public BigInteger asBigInteger() throws UnsupportedMessageException {
    return BigInteger.valueOf((long) s);
  }

}