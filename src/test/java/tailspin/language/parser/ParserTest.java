package tailspin.language.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

public class ParserTest {
  @Test
  void testSimpleArithmetic() {
    CallTarget callTarget = TailspinParser.parse("1 + 2 !");
    var result = callTarget.call(null, null, null);
    assertEquals(3, result);
  }
}
