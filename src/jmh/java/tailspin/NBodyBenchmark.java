package tailspin;

import org.openjdk.jmh.annotations.Benchmark;
import tailspin.impl.nbody.NBodySystem;
import tailspin.language.runtime.SciNum;

/**
 * This benchmark does a lot of state mutations as well as exercising SciNums
 */
@SuppressWarnings("unused")
public class NBodyBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
  PI is 3.141592653589793;
  SOLAR_MASS is 4 * $PI * $PI;
  DAYS_PER_YEAR is 365.2400000000000;

  jupiter is {
    x: 4.84143144246472090e+00,
    y: -1.16032004402742839e+00,
    z: -1.03622044471123109e-01,
    vx: 1.66007664274403694e-03 * $DAYS_PER_YEAR,
    vy: 7.69901118419740425e-03 * $DAYS_PER_YEAR,
    vz: -6.90460016972063023e-05 * $DAYS_PER_YEAR,
    mass: 9.54791938424326609e-04 * $SOLAR_MASS
  };

  saturn is {
    x: 8.34336671824457987e+00,
    y: 4.12479856412430479e+00,
    z: -4.03523417114321381e-01,
    vx: -2.76742510726862411e-03 * $DAYS_PER_YEAR,
    vy: 4.99852801234917238e-03 * $DAYS_PER_YEAR,
    vz: 2.30417297573763929e-05 * $DAYS_PER_YEAR,
    mass:2.85885980666130812e-04 * $SOLAR_MASS
  };

  uranus is {
    x: 1.28943695621391310e+01,
    y: -1.51111514016986312e+01,
    z: -2.23307578892655734e-01,
    vx: 2.96460137564761618e-03 * $DAYS_PER_YEAR,
    vy: 2.37847173959480950e-03 * $DAYS_PER_YEAR,
    vz: -2.96589568540237556e-05 * $DAYS_PER_YEAR,
    mass: 4.36624404335156298e-05 * $SOLAR_MASS
  };

  neptune is {
    x: 1.53796971148509165e+01,
    y: -2.59193146099879641e+01,
    z: 1.79258772950371181e-01,
    vx: 2.68067772490389322e-03 * $DAYS_PER_YEAR,
    vy: 1.62824170038242295e-03 * $DAYS_PER_YEAR,
    vz: -9.51592254519715870e-05 * $DAYS_PER_YEAR,
    mass: 5.15138902046611451e-05 * $SOLAR_MASS
  };

  sun is {
    x: 0,
    y: 0,
    z: 0,
    vx: 0,
    vy: 0,
    vz: 0,
    mass: $SOLAR_MASS
  };
  
  n-body-system templates
    @ set [$sun, $jupiter, $saturn, $uranus, $neptune];
    $@ -> templates
      @ set {px: 0, py: 0, pz: 0};
      $... -> @ set {
        px: $@(px:) + $(vx:) * $(mass:),
        py: $@(py:) + $(vy:) * $(mass:),
        pz: $@(pz:) + $(vz:) * $(mass:)
      };
      @n-body-system(1; vx:) set 0 - ($@(px:) / $SOLAR_MASS);
      @n-body-system(1; vy:) set 0 - ($@(py:) / $SOLAR_MASS);
      @n-body-system(1; vz:) set 0 - ($@(pz:) / $SOLAR_MASS);
    end -> !VOID

    advance sink
      dt is $;
      1..$@n-body-system::length -> templates
        i is $;
        iBody is $@n-body-system($);
        $~..$@n-body-system::length -> templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          dSquared is $dx * $dx + $dy * $dy + $dz * $dz;
          distance is √$dSquared;
          mag is $dt / ($dSquared * $distance);

          @n-body-system($i; vx:) set $@n-body-system($i; vx:) - $dx * $@n-body-system($j; mass:) * $mag;
          @n-body-system($i; vy:) set $@n-body-system($i; vy:) - $dy * $@n-body-system($j; mass:) * $mag;
          @n-body-system($i; vz:) set $@n-body-system($i; vz:) - $dz * $@n-body-system($j; mass:) * $mag;

          @n-body-system($j; vx:) set $@n-body-system($j; vx:) + $dx * $@n-body-system($i; mass:) * $mag;
          @n-body-system($j; vy:) set $@n-body-system($j; vy:) + $dy * $@n-body-system($i; mass:) * $mag;
          @n-body-system($j; vz:) set $@n-body-system($j; vz:) + $dz * $@n-body-system($i; mass:) * $mag;
        end -> !VOID
      end -> !VOID

      1..$@n-body-system::length -> templates
          @n-body-system($; x:) set $@n-body-system($; x:) + $dt * $@n-body-system($; vx:);
          @n-body-system($; y:) set $@n-body-system($; y:) + $dt * $@n-body-system($; vy:);
          @n-body-system($; z:) set $@n-body-system($; z:) + $dt * $@n-body-system($; vz:);
      end -> !VOID
    end advance

    energy source
      @ set 0;

      1..$@n-body-system::length -> templates
        i is $;
        iBody is $@n-body-system($);
        @energy set $@energy + 0.5000000000000000 * $iBody(mass:) * ($iBody(vx:) * $iBody(vx:) +
          $iBody(vy:) * $iBody(vy:) +
          $iBody(vz:) * $iBody(vz:));

        $~..$@n-body-system::length -> templates
          j is $;
          jBody is $@n-body-system($);
          dx is $iBody(x:) - $jBody(x:);
          dy is $iBody(y:) - $jBody(y:);
          dz is $iBody(z:) - $jBody(z:);

          distance is √($dx * $dx + $dy * $dy + $dz * $dz);
          @energy set $@energy - ($iBody(mass:) * $jBody(mass:)) / $distance;
        end -> !VOID
      end -> !VOID
  
      $@ !
    end energy

    1..$ -> 0.01000000000000000 -> !advance
    $energy !
  end n-body-system
  
  250 -> n-body-system !
  """;

  @Benchmark
  public void nbody_tailspin() {
    SciNum energy = truffleContext.eval("tt", tailspinProgram).as(SciNum.class);
    if (energy.compareTo(SciNum.fromDigits("-1690184748576635", -16)) != 0) throw new AssertionError("Wrong result " + energy);
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
