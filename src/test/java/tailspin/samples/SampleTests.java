package tailspin.samples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

public class SampleTests {
  private static final String[] SAMPLE_FILES = new String[] {
      "Numbers.tests", "Chain.tests", "Templates.tests", "Array.tests", "Range.tests", "Matchers.tests", "Structure.tests", "Types.tests", "State.tests"
  };

  @Test
  void samples() throws IOException {
    try (Context context = Context.create()) {
      for (String filename : SAMPLE_FILES) {
        try (InputStream testStream = SampleTests.class.getClassLoader()
            .getResourceAsStream(filename)) {
          assert testStream != null;
          try (InputStreamReader testStreamReader = new InputStreamReader(testStream);
              BufferedReader testReader = new BufferedReader(testStreamReader)) {
            String line;
            String testName = "";
            StringBuilder testProgram = new StringBuilder();
            while ((line = testReader.readLine()) != null) {
              if (line.startsWith("=")) {
                Value result;
                try {
                  result = context.eval("tt", testProgram);
                  assertEquals(line.substring(1), result.toString(), "Failed: " + testName);
                } catch (PolyglotException e) {
                  if (!line.startsWith("=*") || !e.getMessage().contains(line.substring(2))) throw new AssertionError("Failed: " + testName, e);
                } catch (Exception e) {
                  throw new AssertionError("Failed: " + testName, e);
                }
              } else if (line.startsWith("---")) {
                testName = line + " of " + filename;
                testProgram = new StringBuilder();
              } else {
                testProgram.append(line).append('\n');
              }
            }
          }
        }
      }
    }
  }

}
