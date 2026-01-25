package tailspin;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class Tailspin {

  public static void main(String[] args) {
    Path programPath = Paths.get(args[0]);
    try (Context truffleContext = Context.create(); Reader programReader = Files.newBufferedReader(programPath)) {
      Source source = Source.newBuilder("tt", programReader, programPath.getFileName().toString()).build();
      System.out.println(truffleContext.eval(source));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
