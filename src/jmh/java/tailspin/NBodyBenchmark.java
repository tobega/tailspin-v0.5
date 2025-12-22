package tailspin;

import org.openjdk.jmh.annotations.Benchmark;
import tailspin.impl.nbody.NBodySystem;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.SciNum;

/**
 * This benchmark does a lot of state mutations as well as exercising SciNums
 */
@SuppressWarnings("unused")
public class NBodyBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
  PI is 3.141592653589793;
  SOLAR_MASS is (4 * $PI * $PI)"u";
  DAYS_PER_YEAR is 365.2400000000000"1";

  jupiter is {
    x: 4.84143144246472090e+00"x",
    y: -1.16032004402742839e+00"y",
    z: -1.03622044471123109e-01"z",
    vx: 1.66007664274403694e-03"x/t" * $DAYS_PER_YEAR,
    vy: 7.69901118419740425e-03"y/t" * $DAYS_PER_YEAR,
    vz: -6.90460016972063023e-05"z/t" * $DAYS_PER_YEAR,
    mass: 9.54791938424326609e-04"1" * $SOLAR_MASS
  };

  saturn is {
    x: 8.34336671824457987e+00"x",
    y: 4.12479856412430479e+00"y",
    z: -4.03523417114321381e-01"z",
    vx: -2.76742510726862411e-03"x/t" * $DAYS_PER_YEAR,
    vy: 4.99852801234917238e-03"y/t" * $DAYS_PER_YEAR,
    vz: 2.30417297573763929e-05"z/t" * $DAYS_PER_YEAR,
    mass:2.85885980666130812e-04"1" * $SOLAR_MASS
  };

  uranus is {
    x: 1.28943695621391310e+01"x",
    y: -1.51111514016986312e+01"y",
    z: -2.23307578892655734e-01"z",
    vx: 2.96460137564761618e-03"x/t" * $DAYS_PER_YEAR,
    vy: 2.37847173959480950e-03"y/t" * $DAYS_PER_YEAR,
    vz: -2.96589568540237556e-05"z/t" * $DAYS_PER_YEAR,
    mass: 4.36624404335156298e-05"1" * $SOLAR_MASS
  };

  neptune is {
    x: 1.53796971148509165e+01"x",
    y: -2.59193146099879641e+01"y",
    z: 1.79258772950371181e-01"z",
    vx: 2.68067772490389322e-03"x/t" * $DAYS_PER_YEAR,
    vy: 1.62824170038242295e-03"y/t" * $DAYS_PER_YEAR,
    vz: -9.51592254519715870e-05"z/t" * $DAYS_PER_YEAR,
    mass: 5.15138902046611451e-05"1" * $SOLAR_MASS
  };

  sun is {
    x: 0"x",
    y: 0"y",
    z: 0"z",
    vx: 0"x/t",
    vy: 0"y/t",
    vz: 0"z/t",
    mass: $SOLAR_MASS
  };
  
  n-body-system templates
    @ set [$sun, $jupiter, $saturn, $uranus, $neptune];
    $@ -> auxiliary templates
      @ set {px: 0"u x/t", py: 0"u y/t", pz: 0"u z/t"};
      $... -> @ set {
        px: $@(px:) + ($(vx:) * $(mass:))"u x/t",
        py: $@(py:) + ($(vy:) * $(mass:))"u y/t",
        pz: $@(pz:) + ($(vz:) * $(mass:))"u z/t"
      };
      @n-body-system(1; vx:) set 0"x/t" - ($@(px:) / $SOLAR_MASS)"x/t";
      @n-body-system(1; vy:) set 0"y/t" - ($@(py:) / $SOLAR_MASS)"y/t";
      @n-body-system(1; vz:) set 0"z/t" - ($@(pz:) / $SOLAR_MASS)"z/t";
    end -> !VOID

    advance auxiliary sink
      dt is $;
      1..$@n-body-system::length -> auxiliary templates
        i is $;
        iBody is $@n-body-system($);
        $~..$@n-body-system::length -> auxiliary templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          dSquared is ($dx * $dx + $dy * $dy + $dz * $dz)"d2";
          distance is (√$dSquared)"d";
          mag is ($dt / ($dSquared * $distance))"1";

          @n-body-system($i; vx:) set $@n-body-system($i; vx:) - ($dx * $@n-body-system($j; mass:) * $mag)"x/t";
          @n-body-system($i; vy:) set $@n-body-system($i; vy:) - ($dy * $@n-body-system($j; mass:) * $mag)"y/t";
          @n-body-system($i; vz:) set $@n-body-system($i; vz:) - ($dz * $@n-body-system($j; mass:) * $mag)"z/t";

          @n-body-system($j; vx:) set $@n-body-system($j; vx:) + ($dx * $@n-body-system($i; mass:) * $mag)"x/t";
          @n-body-system($j; vy:) set $@n-body-system($j; vy:) + ($dy * $@n-body-system($i; mass:) * $mag)"y/t";
          @n-body-system($j; vz:) set $@n-body-system($j; vz:) + ($dz * $@n-body-system($i; mass:) * $mag)"z/t";
        end -> !VOID
      end -> !VOID

      1..$@n-body-system::length -> auxiliary templates
          @n-body-system($; x:) set $@n-body-system($; x:) + ($dt * $@n-body-system($; vx:))"x";
          @n-body-system($; y:) set $@n-body-system($; y:) + ($dt * $@n-body-system($; vy:))"y";
          @n-body-system($; z:) set $@n-body-system($; z:) + ($dt * $@n-body-system($; vz:))"z";
      end -> !VOID
    end advance

    energy auxiliary source
      @ set 0"e";

      1..$@n-body-system::length -> auxiliary templates
        i is $;
        iBody is $@n-body-system($);
        @energy set $@energy + (0.5000000000000000 * $iBody(mass:) * ($iBody(vx:) * $iBody(vx:) +
          $iBody(vy:) * $iBody(vy:) +
          $iBody(vz:) * $iBody(vz:)))"e";

        $~..$@n-body-system::length -> auxiliary templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          distance is (√($dx * $dx + $dy * $dy + $dz * $dz))"d";
          @energy set $@energy - (($iBody(mass:) * $jBody(mass:)) / $distance)"e";
        end -> !VOID
      end -> !VOID
  
      $@ !
    end energy

    1..$ -> 0.01000000000000000"t" -> !advance
    $energy !
  end n-body-system
  
  250 -> n-body-system !
  """;

  private static final String tailspinProgram6digits = """
  PI is 3.14159;
  SOLAR_MASS is (4 * $PI * $PI)"u";
  DAYS_PER_YEAR is 365.240"1";

  jupiter is {
    x: 4.84143e+00"x",
    y: -1.16032e+00"y",
    z: -1.03622e-01"z",
    vx: 1.66008e-03"x/t" * $DAYS_PER_YEAR,
    vy: 7.69901e-03"y/t" * $DAYS_PER_YEAR,
    vz: -6.90460e-05"z/t" * $DAYS_PER_YEAR,
    mass: 9.54792e-04"1" * $SOLAR_MASS
  };

  saturn is {
    x: 8.34337e+00"x",
    y: 4.12480e+00"y",
    z: -4.03523e-01"z",
    vx: -2.76742e-03"x/t" * $DAYS_PER_YEAR,
    vy: 4.99853e-03"y/t" * $DAYS_PER_YEAR,
    vz: 2.30417e-05"z/t" * $DAYS_PER_YEAR,
    mass:2.85886e-04"1" * $SOLAR_MASS
  };

  uranus is {
    x: 1.28944e+01"x",
    y: -1.51112e+01"y",
    z: -2.23308e-01"z",
    vx: 2.96460e-03"x/t" * $DAYS_PER_YEAR,
    vy: 2.37847e-03"y/t" * $DAYS_PER_YEAR,
    vz: -2.96590e-05"z/t" * $DAYS_PER_YEAR,
    mass: 4.366248e-05"1" * $SOLAR_MASS
  };

  neptune is {
    x: 1.53797e+01"x",
    y: -2.59193e+01"y",
    z: 1.79259e-01"z",
    vx: 2.68068e-03"x/t" * $DAYS_PER_YEAR,
    vy: 1.62824e-03"y/t" * $DAYS_PER_YEAR,
    vz: -9.51592e-05"z/t" * $DAYS_PER_YEAR,
    mass: 5.15139e-05"1" * $SOLAR_MASS
  };

  sun is {
    x: 0"x",
    y: 0"y",
    z: 0"z",
    vx: 0"x/t",
    vy: 0"y/t",
    vz: 0"z/t",
    mass: $SOLAR_MASS
  };
  
  n-body-system templates
    @ set [$sun, $jupiter, $saturn, $uranus, $neptune];
    $@ -> auxiliary templates
      @ set {px: 0"u x/t", py: 0"u y/t", pz: 0"u z/t"};
      $... -> @ set {
        px: $@(px:) + ($(vx:) * $(mass:))"u x/t",
        py: $@(py:) + ($(vy:) * $(mass:))"u y/t",
        pz: $@(pz:) + ($(vz:) * $(mass:))"u z/t"
      };
      @n-body-system(1; vx:) set 0"x/t" - ($@(px:) / $SOLAR_MASS)"x/t";
      @n-body-system(1; vy:) set 0"y/t" - ($@(py:) / $SOLAR_MASS)"y/t";
      @n-body-system(1; vz:) set 0"z/t" - ($@(pz:) / $SOLAR_MASS)"z/t";
    end -> !VOID

    advance auxiliary sink
      dt is $;
      1..$@n-body-system::length -> auxiliary templates
        i is $;
        iBody is $@n-body-system($);
        $~..$@n-body-system::length -> auxiliary templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          dSquared is ($dx * $dx + $dy * $dy + $dz * $dz)"d2";
          distance is (√$dSquared)"d";
          mag is ($dt / ($dSquared * $distance))"1";

          @n-body-system($i; vx:) set $@n-body-system($i; vx:) - ($dx * $@n-body-system($j; mass:) * $mag)"x/t";
          @n-body-system($i; vy:) set $@n-body-system($i; vy:) - ($dy * $@n-body-system($j; mass:) * $mag)"y/t";
          @n-body-system($i; vz:) set $@n-body-system($i; vz:) - ($dz * $@n-body-system($j; mass:) * $mag)"z/t";

          @n-body-system($j; vx:) set $@n-body-system($j; vx:) + ($dx * $@n-body-system($i; mass:) * $mag)"x/t";
          @n-body-system($j; vy:) set $@n-body-system($j; vy:) + ($dy * $@n-body-system($i; mass:) * $mag)"y/t";
          @n-body-system($j; vz:) set $@n-body-system($j; vz:) + ($dz * $@n-body-system($i; mass:) * $mag)"z/t";
        end -> !VOID
      end -> !VOID

      1..$@n-body-system::length -> auxiliary templates
          @n-body-system($; x:) set $@n-body-system($; x:) + ($dt * $@n-body-system($; vx:))"x";
          @n-body-system($; y:) set $@n-body-system($; y:) + ($dt * $@n-body-system($; vy:))"y";
          @n-body-system($; z:) set $@n-body-system($; z:) + ($dt * $@n-body-system($; vz:))"z";
      end -> !VOID
    end advance

    energy auxiliary source
      @ set 0"e";

      1..$@n-body-system::length -> auxiliary templates
        i is $;
        iBody is $@n-body-system($);
        @energy set $@energy + (0.500000 * $iBody(mass:) * ($iBody(vx:) * $iBody(vx:) +
          $iBody(vy:) * $iBody(vy:) +
          $iBody(vz:) * $iBody(vz:)))"e";

        $~..$@n-body-system::length -> auxiliary templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          distance is (√($dx * $dx + $dy * $dy + $dz * $dz))"d";
          @energy set $@energy - (($iBody(mass:) * $jBody(mass:)) / $distance)"e";
        end -> !VOID
      end -> !VOID
  
      $@ !
    end energy

    1..$ -> 0.010000"t" -> !advance
    $energy !
  end n-body-system
  
  250 -> n-body-system !
  """;

  @Benchmark
  public void nbody_tailspin_18digits() {
    SciNum energy = (SciNum) truffleContext.eval("tt", tailspinProgram).as(Measure.class).value();
    if (energy.compareTo(SciNum.fromDigits("-1690184748576635", 16, -16)) != 0) throw new AssertionError("Wrong result " + energy);
  }

  @Benchmark
  public void nbody_tailspin_6digits() {
    SciNum energy = (SciNum) truffleContext.eval("tt", tailspinProgram6digits).as(Measure.class).value();
    if (energy.compareTo(SciNum.fromDigits("-169054", 6,-6)) != 0) throw new AssertionError("Wrong result " + energy);
  }

  @Benchmark
  public void nbody_java() {
    NBodySystem system = new NBodySystem();
    for (int i = 0; i < 250; i++) {
      system.advance(0.01);
    }
    double energy = system.energy();
    if (energy != -0.16901847485766353) throw new AssertionError("Wrong result " + energy);
  }
}
