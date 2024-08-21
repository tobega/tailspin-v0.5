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
  PI is 3.14159265358979;
  SOLAR_MASS is 4.00000000000000 * $PI * $PI;
  DAYS_PER_YEAR is 365.240000000000;

  jupiter is {
    x: 4.84143144246472e+00,
    y: -1.16032004402743e+00,
    z: -1.03622044471123e-01,
    vx: 1.66007664274404e-03 * $DAYS_PER_YEAR,
    vy: 7.69901118419740e-03 * $DAYS_PER_YEAR,
    vz: -6.90460016972063e-05 * $DAYS_PER_YEAR,
    mass: 9.54791938424327e-04 * $SOLAR_MASS
  };

  saturn is {
    x: 8.34336671824458e+00,
    y: 4.12479856412430e+00,
    z: -4.03523417114321e-01,
    vx: -2.76742510726862e-03 * $DAYS_PER_YEAR,
    vy: 4.99852801234917e-03 * $DAYS_PER_YEAR,
    vz: 2.30417297573764e-05 * $DAYS_PER_YEAR,
    mass:2.85885980666131e-04 * $SOLAR_MASS
  };

  uranus is {
    x: 1.28943695621391e+01,
    y: -1.51111514016986e+01,
    z: -2.23307578892656e-01,
    vx: 2.96460137564762e-03 * $DAYS_PER_YEAR,
    vy: 2.37847173959481e-03 * $DAYS_PER_YEAR,
    vz: -2.96589568540238e-05 * $DAYS_PER_YEAR,
    mass: 4.36624404335156e-05 * $SOLAR_MASS
  };

  neptune is {
    x: 1.53796971148509e+01,
    y: -2.59193146099880e+01,
    z: 1.79258772950371e-01,
    vx: 2.68067772490389e-03 * $DAYS_PER_YEAR,
    vy: 1.62824170038242e-03 * $DAYS_PER_YEAR,
    vz: -9.51592254519716e-05 * $DAYS_PER_YEAR,
    mass: 5.15138902046611e-05 * $SOLAR_MASS
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
        @energy set $@energy + 0.500000000000000 * $iBody(mass:) * ($iBody(vx:) * $iBody(vx:) +
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
    SciNum expected = SciNum.fromDigits("-16901847485766", -14);
    if (energy.compareTo(expected) != 0) throw new AssertionError("Wrong result " + energy + " vs " + expected);
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
