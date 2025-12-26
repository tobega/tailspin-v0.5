package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.math.BigInteger;
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

  private final long unscaled; // The digits (e.g., 12345)
  private final int exponent; // Power of 10 (e.g., -3 for 12.345)
  private final int precision; // Sig-figs (e.g., 5)

  private SmallSciNum(long unscaled, int exponent, int precision) {
    this.unscaled = unscaled;
    this.exponent = exponent;
    this.precision = precision;
  }

  public static SmallSciNum fromDigits(String digits, int exponent, int precision) {
    long mantissa = Long.parseLong(digits);
    return new SmallSciNum(mantissa, exponent, precision);
  }

  public static SmallSciNum fromLong(long value) {
    return new SmallSciNum(value, 0, 0);
  }

  public Object add(SmallSciNum other) {
    try {
      // 1. Identify which number is "coarser" (higher exponent)
      int targetExp = Math.min(this.exponent, other.exponent);

      // 2. Align this and other to the targetExp
      long term1 = alignExponent(this, targetExp);
      long term2 = alignExponent(other, targetExp);

      long sum = Math.addExact(term1, term2);
      int resultFloor;
      if (this.precision == 0 && other.precision == 0) {
        // Both exact: keep the finest scale of the two
        resultFloor = Math.min(this.exponent, other.exponent);
      } else if (this.precision == 0) {
        // This is exact, so 'other' is the only measurement
        resultFloor = other.exponent;
      } else if (other.precision == 0) {
        // Other is exact, so 'this' is the only measurement
        resultFloor = this.exponent;
      } else {
        // Both measured: the coarsest measurement wins (standard Sci-Figs)
        resultFloor = Math.max(this.exponent, other.exponent);
      }
      if (sum == 0) {
        // If we have a zero, the precision isn't just 1.
        // It's the number of digits between the "top" of the original
        // measurement and the "floor" where we stopped.
        // 2. Calculate Potential Precision
        // Find the "highest" point of our two numbers
        int top1 = this.exponent + getLongMagnitude(this.unscaled);
        int top2 = other.exponent + getLongMagnitude(other.unscaled);
        int maxTop = Math.max(top1, top2);

        // Potential precision is the distance from the highest digit
        // to the result floor.
        // e.g., 1.11 (top 0) to floor -2 = 3 digits.
        int potentialPrecision = maxTop - resultFloor + 1;
        return new SmallSciNum(0, resultFloor, Math.max(1, potentialPrecision));
      }
      return normalizeFromLong(sum, targetExp, resultFloor);
    } catch (ArithmeticException e) {
      return SciNum.fromSmallSciNum(this).add(SciNum.fromSmallSciNum(other));
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
    return Math.multiplyExact(n.unscaled, LONG_POWERS_OF_10[diff]);
  }

  private SmallSciNum normalizeFromLong(long sum, int currentExp, int anchorExp) {
    // 2. Scientific Rounding (The "Anchor" Logic)
    // If our sum is at 10^-3 but our anchor is 10^-2, we must round.
    if (currentExp < anchorExp) {
      int diff = anchorExp - currentExp;
      if (diff < 19) { // Safety check for power table
        long divisor = LONG_POWERS_OF_10[diff];
        long half = divisor / 2;

        // Manual rounding (Ties to even or Away from zero)
        long remainder = Math.abs(sum % divisor);
        sum /= divisor;

        // Basic "Round half away from zero"
        if (remainder >= half) {
          sum += (sum >= 0) ? 1 : -1;
        }
      } else {
        sum = 0; // The number was completely rounded away
      }
      currentExp = anchorExp;
    }

    // 3. Calculate Final Precision
    // We need to know where the "Top" digit is relative to our "Bottom" (currentExp)
    int magnitude = getLongMagnitude(sum) + currentExp;
    int finalPrecision = magnitude - currentExp + 1;

    return new SmallSciNum(sum, currentExp, Math.max(1, finalPrecision));
  }

  // Fast magnitude check for a long
  private int getLongMagnitude(long n) {
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

  public Object subtract(SmallSciNum other) {
    int targetExp = Math.min(this.exponent, other.exponent);

    try {
      long term1 = alignExponent(this, targetExp);
      long term2 = alignExponent(other, targetExp);

      // Directly subtract the longs without creating a negated object
      long diff = Math.subtractExact(term1, term2);

      int resultFloor;
      if (this.precision == 0 && other.precision == 0) {
        // Both exact: keep the finest scale of the two
        resultFloor = Math.min(this.exponent, other.exponent);
      } else if (this.precision == 0) {
        // This is exact, so 'other' is the only measurement
        resultFloor = other.exponent;
      } else if (other.precision == 0) {
        // Other is exact, so 'this' is the only measurement
        resultFloor = this.exponent;
      } else {
        // Both measured: the coarsest measurement wins (standard Sci-Figs)
        resultFloor = Math.max(this.exponent, other.exponent);
      }
      if (diff == 0) {
        // If we have a zero, the precision isn't just 1.
        // It's the number of digits between the "top" of the original
        // measurement and the "floor" where we stopped.
        // 2. Calculate Potential Precision
        // Find the "highest" point of our two numbers
        int top1 = this.exponent + getLongMagnitude(this.unscaled);
        int top2 = other.exponent + getLongMagnitude(other.unscaled);
        int maxTop = Math.max(top1, top2);

        // Potential precision is the distance from the highest digit
        // to the result floor.
        // e.g., 1.11 (top 0) to floor -2 = 3 digits.
        int potentialPrecision = maxTop - resultFloor + 1;
        return new SmallSciNum(0, resultFloor, Math.max(1, potentialPrecision));
      }
      return normalizeFromLong(diff, targetExp, resultFloor);
    } catch (ArithmeticException e) {
      // Fallback to BigDecimal
      return SciNum.fromSmallSciNum(this).subtract(SciNum.fromSmallSciNum(other));
    }
  }

  public Object multiply(SmallSciNum other) {
    // 1. New Exponent is the sum of exponents
    int targetExp = this.exponent + other.exponent;

    try {
      // 2. Multiply the unscaled longs
      long product = Math.multiplyExact(this.unscaled, other.unscaled);

      // 3. Determine precision
      // If both are exact (0), result is exact (0).
      // If one is measured, result is measured by that precision.
      int resultPrecision;
      if (this.precision == 0 && other.precision == 0) {
        resultPrecision = 0;
      } else if (this.precision == 0) {
        resultPrecision = other.precision;
      } else if (other.precision == 0) {
        resultPrecision = this.precision;
      } else {
        resultPrecision = Math.min(this.precision, other.precision);
      }

      // 4. Normalize based on the precision requirement
      return normalizeForMultiplication(product, targetExp, resultPrecision);
    } catch (ArithmeticException e) {
      // Promote to BigDecimal-backed SciNum
      return SciNum.fromSmallSciNum(this).multiply(SciNum.fromSmallSciNum(other));
    }
  }

  private SmallSciNum normalizeForMultiplication(long product, int exp, int precision) {
    if (product == 0) {
      return new SmallSciNum(0, 1 - precision, precision);
    }
    if (precision == 0) {
      return new SmallSciNum(product, exp, 0);
    }

    // 1. Find the current digit count of the product
    int currentDigits = getLongMagnitude(product) + 1;

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
        if (getLongMagnitude(product) + 1 > precision) {
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
      int thisMag = getLongMagnitude(this.unscaled);
      int otherMag = getLongMagnitude(other.unscaled);

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
      return normalizeForMultiplication(quotient, finalExp, resultPrecision);

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
    StringBuilder sb = new StringBuilder();
    if (unscaled == 0) {
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
      return sb.append(unscaled).repeat("0", exponent).toString();
    }

    String digits = Long.toString(Math.abs(unscaled));
    if (unscaled < 0) {
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
    int mag1 = getLongMagnitude(this.unscaled) + this.exponent;
    int mag2 = getLongMagnitude(other.unscaled) + other.exponent;

    if (mag1 != mag2) {
      // If they are different magnitudes, the higher one is larger
      // (Adjust for negative numbers: -100 is smaller than -10)
      return s1 > 0 ? Integer.compare(mag1, mag2) : Integer.compare(mag2, mag1);
    }

    // 3. Same Magnitude: Alignment Tie-Breaker
    int targetExp = Math.min(this.exponent, other.exponent);
    try {
      // We use our existing alignment logic
      long v1 = alignExponent(this, targetExp);
      long v2 = alignExponent(other, targetExp);
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
    double valAsDouble = this.unscaled * Math.pow(10, this.exponent);
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
      return new SmallSciNum(0, 0, precision);
    }

    // 1. Get the "Magnitude" of the double
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

    return new SmallSciNum(unscaled, exponent, precision);
  }

  public long getUnscaled() {
    return unscaled;
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