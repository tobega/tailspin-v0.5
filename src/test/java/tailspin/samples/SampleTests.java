package tailspin.samples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

public class SampleTests {
  private static final String[] SAMPLE_FILES = new String[] {
      "Numbers.tests"
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
            StringBuilder testProgram = new StringBuilder();
            while ((line = testReader.readLine()) != null) {
              if (line.startsWith("=")) {
                Value result = context.eval("tt", testProgram);
                assertEquals(line.substring(1), result.asString());
              } else if (line.startsWith("---")) {
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
