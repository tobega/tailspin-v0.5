package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public final class SmallSciNum implements TruffleObject {

  private static final long[] LONG_POWERS_OF_10 = {
      1L, 10L, 100L, 1000L, 10000L, 100000L,
      1000000L, 10000000L, 100000000L, 1000000000L,
      10000000000L, 100000000000L, 1000000000000L,
      10000000000000L, 100000000000000L, 1000000000000000L,
      10000000000000000L, 100000000000000000L, 1000000000000000000L
  };

  private static final long[] PADDED_POWERS_OF_10 = Arrays.stream(LONG_POWERS_OF_10).map(p -> p * 8).toArray();

  private final long unscaled; // The digits + 3 extra bits to pad against exponent shifting artefacts
  private final int exponent; // Power of 10 (e.g., -3 for 12.345)
  private final int precision; // Significant digits, generally corresponds to the magnitude of unscaled.

  private SmallSciNum(long unscaled, int exponent, int precision) {
    this.unscaled = unscaled;
    this.exponent = exponent;
    this.precision = precision;
  }

  public static SmallSciNum fromDigits(String digits, int exponent, int precision) {
    long mantissa = Long.parseLong(digits);
    return new SmallSciNum(Math.multiplyExact(mantissa, 8), exponent, precision);
  }

  public static SmallSciNum fromLong(long value) {
    return new SmallSciNum(Math.multiplyExact(value, 8), 0, 0);
  }

  public SmallSciNum add(SmallSciNum other) {
    long sum;
    int finalExponent;
    int newPrecision;
    if (exponent == other.exponent) {
      newPrecision = Math.max(precision, other.precision);
      finalExponent = exponent;
      sum = Math.addExact(unscaled, other.unscaled);
    } else if ((exponent > other.exponent && precision != 0) || other.precision == 0) {
      newPrecision = Math.max(precision, other.precision + other.exponent - exponent);
      finalExponent = exponent;
      sum = Math.addExact(unscaled, reScale(other.unscaled, other.exponent, exponent));
    } else {
      newPrecision = Math.max(other.precision, precision + exponent - other.exponent);
      finalExponent = other.exponent;
      sum = Math.addExact(other.unscaled, reScale(unscaled, exponent, other.exponent));
    }
    int finalPrecision;
    if (sum == 0) {
      finalPrecision = Math.max(1, newPrecision);
    } else {
      // Check if the sum carried over into a new digit column
      int sumPrecision = getPaddedMagnitude(sum) + 1;
      finalPrecision = Math.max(sumPrecision, newPrecision);
    }
    return new SmallSciNum(sum, finalExponent, finalPrecision);
  }

  long reScale(long toScale, int fromExponent, int toExponent) {
    int diff = fromExponent - toExponent;
    if (diff >= 0) {
      long multiplier = LONG_POWERS_OF_10[diff];
      return toScale * multiplier;
    } else {
      long divisor = LONG_POWERS_OF_10[-diff];
      return toScale / divisor;
    }
  }

  private long alignExponent(SmallSciNum n, int target) {
    int diff = n.exponent - target;
    if (diff <= 0) {
      return n.unscaled;
    }
    if (diff >= 19) {
      throw new ArithmeticException("Shift too large");
    }

    // MultiplyExact catches if the shift itself overflows the long
    return Math.multiplyExact(n.unscaled / 8, LONG_POWERS_OF_10[diff]) * 8;
  }

  private int getMagnitude(long n) {
    long absN = Math.abs(n);
    if (absN == 0) {
      return 0;
    }
    // Use a simple branch or log10-based lookup
    for (int i = 18; i >= 0; i--) {
      if (absN >= LONG_POWERS_OF_10[i]) {
        return i;
      }
    }
    return 0;
  }

  private int getPaddedMagnitude(long n) {
    // Snabb absolutvärde som hanterar Long.MIN_VALUE utan krasch
    long absN = (n < 0) ? -n : n;

    // Om det skalade talet är mindre än 8 är det logiska värdet 0
    if (absN < 8) return 0;

    // 61 = 64 bitar - 3 bitar (skalning)
    int bits = 61 - Long.numberOfLeadingZeros(absN);

    // Approximation av log10 (digits - 1)
    // Vi använder (bits - 1) för att ligga på den säkra, lägre sidan
    int guess = ((bits - 1) * 1233) >> 12;

    // Eftersom PADDED_POWERS_OF_10 redan är * 8, jämför vi direkt
    if (guess < 18 && absN >= PADDED_POWERS_OF_10[guess + 1]) {
      return guess + 1;
    }
    return guess;
  }

  public SmallSciNum subtract(SmallSciNum other) {
    long a, b, diff;
    int finalExponent, newPrecision;

    // Berätta för Graal att vi oftast har samma exponent
    if (CompilerDirectives.injectBranchProbability(CompilerDirectives.LIKELY_PROBABILITY,
        this.exponent == other.exponent)) {

      a = this.unscaled;
      b = other.unscaled;
      newPrecision = Math.max(this.precision, other.precision);
      finalExponent = this.exponent;
    } else if ((this.exponent > other.exponent && this.precision != 0) || other.precision == 0) {
      a = this.unscaled;
      b = reScale(other.unscaled, other.exponent, this.exponent);
      newPrecision = Math.max(this.precision, other.precision + other.exponent - this.exponent);
      finalExponent = this.exponent;
    } else {
      a = reScale(this.unscaled, this.exponent, other.exponent);
      b = other.unscaled;
      newPrecision = Math.max(other.precision, this.precision + this.exponent - other.exponent);
      finalExponent = other.exponent;
    }

    diff = a - b;

    // Manuell overflow-kontroll (Tecken-analys)
    if (((a ^ b) & (a ^ diff)) < 0L) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleOverflow(a, b, finalExponent); // Implementera efter behov
    }

    int finalPrecision = (diff == 0) ? newPrecision : 1 + getPaddedMagnitude(diff);

    // Utan @TruffleBoundary kan Graal optimera bort denna allokering helt (Escape Analysis)
    return new SmallSciNum(diff, finalExponent, finalPrecision);
  }

  @TruffleBoundary // Flytta den tunga felhanteringen utanför den heta vägen
  private SmallSciNum handleOverflow(long a, long b, int exp) {
    // Här kan du t.ex. konvertera till en större representation
    // eller kasta ett kontrollerat fel.
    throw new ArithmeticException("Long overflow i SmallSciNum");
  }

  public SmallSciNum multiply(SmallSciNum other) {
    int targetExp = this.exponent + other.exponent;
    long product = Math.multiplyExact(this.unscaled / 8, other.unscaled / 8);
    int resultPrecision;
      if (this.precision == 0) {
        resultPrecision = other.precision;
      } else if (other.precision == 0) {
        resultPrecision = this.precision;
      } else {
        resultPrecision = Math.min(this.precision, other.precision);
      }

      if (product == 0) {
        return new SmallSciNum(0, 1 - resultPrecision, resultPrecision);
      }

      int reduction = 1 + getMagnitude(product) - resultPrecision;
      long result = (reduction == 0) ? product : roundBy(product, LONG_POWERS_OF_10[reduction]);
      int newExp = targetExp + reduction;
      return new SmallSciNum(result * 8, newExp, resultPrecision);
  }

  private SmallSciNum normalizeForMultiplication(long product, int exp, int precision) {
    if (product == 0) {
      return new SmallSciNum(0, 1 - precision, precision);
    }
    if (precision == 0) {
      return new SmallSciNum(product, exp, 0);
    }

    // 1. Find the current digit count of the product
    int currentDigits = getPaddedMagnitude(product) + 1;

    // 2. If we have more digits than our precision allows, we must round
    if (currentDigits > precision) {
      int diff = currentDigits - precision;
      long divisor = LONG_POWERS_OF_10[diff];
      long half = divisor / 2;

      long remainder = Math.abs(product % divisor);
      product /= divisor;
      exp += diff; // Moving the decimal point right as we trim digits

      if (remainder >= half) {
        product += (product >= 0) ? 1 : -1;

        // Edge case: rounding 99 to 100 changes digit count
        if (getPaddedMagnitude(product) + 1 > precision) {
          // This happens rarely (e.g., 9.9 rounded to 10)
          // We keep the extra digit or round again depending on your strictness
        }
      }
    }

    return new SmallSciNum(product, exp, precision);
  }

  public Object divide(SmallSciNum other) {
    if (other.unscaled == 0) {
      throw new ArithmeticException("Division by zero");
    }
    if (this.unscaled == 0) {
      return new SmallSciNum(0, 0, this.precision);
    }

    // 1. Determine precision
    int resultPrecision;
    if (this.precision == 0 && other.precision == 0) {
      // For exact division, we'll aim for maximum long precision (18 digits)
      // or you can promote to BigDecimal immediately if it's not a clean division.
      resultPrecision = 18;
    } else if (this.precision == 0) {
      resultPrecision = other.precision;
    } else if (other.precision == 0) {
      resultPrecision = this.precision;
    } else {
      resultPrecision = Math.min(this.precision, other.precision);
    }

    try {
      // 2. Prepare for shifting
      // We want the result to have 'resultPrecision' digits.
      // Current digits in (this.s / other.s) is approx: mag(this) - mag(other)
      int thisMag = getPaddedMagnitude(this.unscaled);
      int otherMag = getPaddedMagnitude(other.unscaled);

      // We need to shift 'this' so that the result of the division has roughly resultPrecision + 1 digits
      // (The +1 is for rounding accuracy)
      int neededShift = resultPrecision - (thisMag - otherMag) + 1;

      if (neededShift < 0) {
        // No shift needed, or we need to shift the divisor instead
        // To keep it simple, we'll just handle positive shifts for now
        neededShift = 0;
      }

      long shiftedDividend = Math.multiplyExact(this.unscaled, LONG_POWERS_OF_10[neededShift]);
      long quotient = shiftedDividend / other.unscaled;
      long remainder = Math.abs(shiftedDividend % other.unscaled);

      // 3. Rounding
      if (remainder * 2 >= Math.abs(other.unscaled)) {
        quotient += (quotient >= 0) ? 1 : -1;
      }

      int finalExp = (this.exponent - other.exponent) - neededShift;

      // 4. Normalize to the target precision
      return normalizeForMultiplication(quotient * 8, finalExp, resultPrecision);

    } catch (ArithmeticException e) {
      // Overflows during shift or division go to BigDecimal
      return SciNum.fromSmallSciNum(this).divide(SciNum.fromSmallSciNum(other));
    }
  }

  public SmallSciNum negate() {
    return new SmallSciNum(-unscaled, exponent, precision);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    long rounded = getUnscaled();
    StringBuilder sb = new StringBuilder();
    if (rounded == 0) {
      // Significant Zero: 0.00e2
      sb.append("0");
      if (precision > 1) {
        sb.append(".");
        for (int i = 0; i < precision - 1; i++) {
          sb.append('0');
        }
        sb.append("e0");
      }
      return sb.toString();
    }
    if (precision == 0) {
      // Exact number representation
      return sb.append(rounded).repeat("0", exponent).toString();
    }

    String digits = Long.toString(Math.abs(rounded));
    if (rounded < 0) {
      sb.append('-');
    }

    // 1. The first digit
    sb.append(digits.charAt(0));

    // 2. The dot and fractional part (respecting precision)
    if (precision > 1) {
      sb.append('.');
      for (int i = 1; i < precision; i++) {
        if (i < digits.length()) {
          sb.append(digits.charAt(i));
        } else {
          sb.append('0'); // Trailing zeros
        }
      }
    }

    // 3. The Scientific Exponent
    // The power of the first digit is exponent + (length - 1)
    long totalExp = (long) exponent + (digits.length() - 1);
    sb.append('e').append(totalExp);

    return sb.toString();
  }

  public int compareTo(SmallSciNum other) {
    // 1. Easy out: Signums
    int s1 = Long.signum(this.unscaled);
    int s2 = Long.signum(other.unscaled);
    if (s1 != s2) {
      return Integer.compare(s1, s2);
    }
    if (s1 == 0) {
      return 0; // Both are zero
    }

    // 2. Magnitude Check (The "Exponent + Magnitude" trick)
    // We calculate the power of 10 of the most significant digit.
    int mag1 = getPaddedMagnitude(this.unscaled) + this.exponent;
    int mag2 = getPaddedMagnitude(other.unscaled) + other.exponent;

    if (mag1 != mag2) {
      // If they are different magnitudes, the higher one is larger
      // (Adjust for negative numbers: -100 is smaller than -10)
      return s1 > 0 ? Integer.compare(mag1, mag2) : Integer.compare(mag2, mag1);
    }

    // 3. Same Magnitude: Alignment Tie-Breaker
    int targetExp = Math.min(this.exponent, other.exponent);
    try {
      // We use our existing alignment logic
      long v1 = reScale(getUnscaled(), exponent, targetExp);
      long v2 = reScale(other.getUnscaled(), other.exponent, targetExp);
      return Long.compare(v1, v2);
    } catch (ArithmeticException e) {
      // This only happens if they have a huge precision gap but identical top-end magnitudes.
      // Extremely rare, but we fall back to BigDecimal for safety.
      return SciNum.fromSmallSciNum(this).compareTo(SciNum.fromSmallSciNum(other));
    }
  }

  public Object mod(SmallSciNum modulus) {
    // 1. Align to the finer scale for the raw calculation
    int targetExp = Math.min(this.exponent, modulus.exponent);

    try {
      long term1 = alignExponent(this, targetExp);
      long term2 = alignExponent(modulus, targetExp);

      long rem = term1 % term2;
      if (rem < 0) {
        rem += Math.abs(term2);
      }

      // 2. Determine Sig-Fig Count
      int resultPrecision;
      if (this.precision == 0 && modulus.precision == 0) {
        resultPrecision = 0; // Keep it exact
      } else if (this.precision == 0) {
        resultPrecision = modulus.precision;
      } else if (modulus.precision == 0) {
        resultPrecision = this.precision;
      } else {
        // Sig-fig rule: Result is limited by the least precise input
        resultPrecision = Math.min(this.precision, modulus.precision);
      }

      // 3. Normalize based on SIGNIFICANT FIGURES
      // We use the multiplication normalizer here!
      return normalizeForMultiplication(rem, targetExp, resultPrecision);
    } catch (ArithmeticException e) {
      return SciNum.fromSmallSciNum(this).mod(SciNum.fromSmallSciNum(modulus));
    }
  }

  public Object truncateDivide(SmallSciNum divisor) {
    if (divisor.unscaled == 0) {
      throw new ArithmeticException("Division by zero");
    }

    // 1. Align to the finer scale so we are dividing "like with like"
    // e.g., 10.5 / 0.5 becomes 105 / 5
    int targetExp = Math.min(this.exponent, divisor.exponent);

    try {
      long term1 = alignExponent(this, targetExp);
      long term2 = alignExponent(divisor, targetExp);

      // 2. Perform truncating division
      long quotient = term1 / term2;

      // 3. Return as an Exact Integer
      // Truncate divide usually implies an integer result at scale 10^0
      return quotient;
    } catch (ArithmeticException e) {
      // Promote to BigDecimal-backed SciNum
      return SciNum.fromSmallSciNum(this).truncateDivide(SciNum.fromSmallSciNum(divisor));
    }
  }

  public Object squareRoot() {
    if (this.unscaled < 0) {
      throw new ArithmeticException("Square root of negative number");
    }
    if (this.unscaled == 0) {
      // Zero is special; we keep the precision of the input
      return new SmallSciNum(0, 0, this.precision == 0 ? 6 : this.precision);
    }

    // 1. Determine Target Precision
    // Exact inputs get 6 digits by default.
    // Measured inputs maintain their existing precision.
    int targetP = (this.precision == 0) ? 6 : this.precision;

    // 2. Perform the Calculation via Double
    double valAsDouble = this.unscaled / 8.0d * Math.pow(10, this.exponent);
    double root = Math.sqrt(valAsDouble);

    // 3. Safety Check for Promotion
    if (Double.isInfinite(root) || Double.isNaN(root)) {
      return SciNum.fromSmallSciNum(this).squareRoot();
    }

    // 4. Return as a Measured SmallSciNum
    return fromDouble(root, targetP);
  }

  public static SmallSciNum fromDouble(double val, int precision) {
    if (val == 0) {
      return new SmallSciNum(0, 1-precision, precision);
    }

    // 1. Get the "Magnitude" of the double.
    // log10(123.45) is ~2.09, so floor is 2.
    int mag = (int) Math.floor(Math.log10(Math.abs(val)));

    // 2. Calculate the shift needed to bring the 'precision' digits
    // into the integer part of a long.
    // If val = 1.234 and precision = 3, we want '123'
    int exponent = mag - (precision - 1);

    // 3. Scale the double and round to long
    // We use a power of 10 to shift the decimal point
    double scaled = val * Math.pow(10, -exponent);
    long unscaled = Math.round(scaled);

    // 4. Edge case: If rounding pushed us up a digit (e.g., 9.99 -> 10.0)
    // we might need to adjust the exponent.
    if (Math.abs(unscaled) >= LONG_POWERS_OF_10[precision]) {
      unscaled /= 10;
      exponent += 1;
    }

    return new SmallSciNum(unscaled * 8, exponent, precision);
  }

  private long roundBy(long value, long divisor) {
    long remainder = Math.abs(value % divisor);
    long rounded = value / divisor;
    if (remainder * 2 >= divisor) {
      rounded += (rounded >= 0 ? 1 : -1);
    }
    return rounded;
  }

  public long getUnscaled() {
    return roundBy(unscaled, 8);
  }

  public int getExponent() {
    return exponent;
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
    return switch (member) {
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
    return precision <= Double.PRECISION;
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
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public short asShort() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public int asInt() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public long asLong() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public float asFloat() throws UnsupportedMessageException {
    return (float) (unscaled * Math.pow(10, exponent));
  }

  @ExportMessage
  @TruffleBoundary
  public double asDouble() throws UnsupportedMessageException {
    return unscaled * Math.pow(10, exponent);
  }

  @ExportMessage
  public BigInteger asBigInteger() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }
}