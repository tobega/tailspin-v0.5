# tailspin-v0.5
Reworking syntax and reimplementing [Tailspin from v0](https://github.com/tobega/tailspin-v0/tree/master) to be better [based on concepts](CONCEPTS.md).
Also aiming for better performance and incremental parsing for creating a REPL and a language server.

See the [summary of changes](CHANGES.md) for an overview of what is different from v0.

Examples of programs with updated syntax are in the [samples](samples) folder.

Great thanks to Adam Ruka for his [Truffle tutorial](https://www.endoflineblog.com/graal-truffle-tutorial-part-0-what-is-truffle)

## Status update 2026-04-04
Improving interop by handling java lists

## Performance check
See the [performance tests](tailspin-benchmarks/README.md) for how Tailspin performs relative to java

## How to run your tailspin-v0.5 code
Tailspin is a graalvm polyglot language with language code "tt", download the tailspin-v0.5.jar to get the tailspin module.
Unfortunately Oracle doesn't make it entirely easy to figure out how to run this, but they have [docs here](https://www.graalvm.org/latest/reference-manual/embed-languages/)

### Setting up deps manually
The recommended way is to use a build system like gradle or maven, but since I wouldn't want to subject anyone to have to use gradle, here is how to set it up manually:

Using GraalVM CE 21.0.2+13.1, I got it working by downloading the following into a lib directory, with xx being 23.1.1 (change as appropriate for jdk version):
- tailspin-v0.5.jar
- polyglot-xx.jar (org.graalvm.polyglot)
- truffle-api-xx.jar (org.graalvm.truffle)

When running plain OpenJdk21, I also needed to add:
- collections-xx.jar (org.graalvm.sdk)
- nativeimage-xx.jar (org.graalvm.sdk)
- word-xx.jar (org.graalvm.sdk)

That will run in interpreted mode. To get truffle optimizations for graalVM, I needed to add:
- truffle-runtime-xx.jar (org.graalvm.truffle)
- jniutils-xx.jar (org.graalvm.sdk)

Running optimized in OpenJdk21 requires a bit more.
For a start, you need to add to the command-line `-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI --upgrade-module-path lib`
Then you also need to add the JVMCI and truffle compiler to lib:
- truffle-compiler-xx.jar (org.graalvm.truffle)
- compiler-xx.jar (org.graalvm.compiler)

For OpenJdk25, set xx=25.0.1. For reasons of silly games with version checks, you may have to go down to 25.0.0 of compiler-xx-jar.
You may get a warning about native access and also need to add the flag `--enable-native-access=org.graalvm.truffle`
because of how the jdk is evolving over versions and I am compiling on an older one.

### Commands to run
Running a Tailspin file directly (see [Tailspin.java](src/main/java/tailspin/Tailspin.java))
```shell
java --module-path lib --add-modules tailspin.language tailspin.Tailspin ./twelve_days_of_christmas.tt
```

Another option is to take more control of the call by creating your own java class to run tailspin code
```java
import org.graalvm.polyglot.Context;

public class HelloTailspin {
  public static void main(String[] args) {
    try (Context truffleContext = Context.create()) {
      System.out.println(truffleContext.eval("tt", "'Hello Tailspin!' !"));
    }
  }
}
```

Then you can run with
```shell
java --module-path lib --add-modules tailspin.language tailspin.HelloTailspin.java
```

### Using the returned values
If you want to do anything other than print the returned values from Tailspin code, there are methods on the [polyglot Value](https://www.graalvm.org/truffle/javadoc/org/graalvm/polyglot/Value.html) returned.

If you want to be more scrappy, it will be either a long, a TruffleString or one of the classes in the [runtime folder](src/main/java/tailspin/language/runtime)

### Passing values in
You may need to add `--add-opens=org.graalvm.truffle/com.oracle.truffle.polyglot=ALL-UNNAMED` on the command-line

When you create the Context, you can open up access to the polyglot bindings (and more).
Then you can add values to the polyglot bindings where `my_value` will be made accessible as `$BINDINGS::my_value` in Tailspin code.

Example:
```java
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotAccess;

public class HelloYou {
  public static void main(String[] args) {
    try (Context truffleContext = Context.newBuilder().allowPolyglotAccess(PolyglotAccess.ALL).build()) {
      truffleContext.getPolyglotBindings().putMember("input", "you");
      System.out.println(truffleContext.eval("tt", "'Hello $BINDINGS::input;!' !"));
    }
  }
}
```

Longs, strings or one of the classes in the [runtime folder](src/main/java/tailspin/language/runtime) should work, other things may work.

Interop iterables and arrays will be supported. When passing in java objects, you inexplicably need to allow HostAccess to make them work like interop objects.
```java
import java.util.List;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;

public class ListInput {
  public static void main() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L));
      System.out.println(truffleContext.eval("tt", "$BINDINGS::input ... -> $ + 1 !"));
    }
  }
}
```
