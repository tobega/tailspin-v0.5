package tailspin.language.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class SmallSciNumTest {
  static final Pattern FIRST_DIGIT = Pattern.compile("\\d");
  @Test
  void firstDigitAndPrecisionOnAdd() {
    for (long digit = 1; digit < 10; digit ++) {
      for (long addend = 1; addend < 10; addend ++) {
        for (int exponent = -4; exponent < 5; exponent++) {
          SmallSciNum positiveDigit = SmallSciNum.fromDigits(Long.toString(digit), exponent, 1);
          SmallSciNum positiveAddend = SmallSciNum.fromDigits(Long.toString(addend), exponent, 1);
          SmallSciNum negativeDigit = SmallSciNum.fromDigits(Long.toString(-digit), exponent, 1);
          SmallSciNum negativeAddend = SmallSciNum.fromDigits(Long.toString(-addend), exponent, 1);
          checkDigitAndPrecision(positiveDigit.add(positiveAddend), digit + addend, exponent);
          checkDigitAndPrecision(positiveDigit.add(negativeAddend), digit - addend, exponent);
          checkDigitAndPrecision(negativeDigit.add(positiveAddend), -digit + addend, exponent);
          checkDigitAndPrecision(negativeDigit.add(negativeAddend), -digit - addend, exponent);
        }
      }
    }
  }

  void checkDigitAndPrecision(SmallSciNum result, long control, int exponent) {
    Matcher firstDigit = FIRST_DIGIT.matcher(result.toString());
    firstDigit.find();
    Matcher expectedDigit = FIRST_DIGIT.matcher(Long.toString(control));
    expectedDigit.find();
    assertEquals(expectedDigit.group(), firstDigit.group(), String.format("digit %s %d %d", result, control, exponent));
    if (control == 0) {
      assertEquals(Math.max(1 - exponent, 1), result.getPrecision(), String.format("precision %s %d %d", result, control, exponent));
    } else if (Math.abs(control) > 9) {
      assertEquals(2, result.getPrecision(), String.format("precision %s %d %d", result, control, exponent));
    } else {
      assertEquals(1, result.getPrecision(), String.format("precision %s %d %d", result, control, exponent));
    }
  }
}
