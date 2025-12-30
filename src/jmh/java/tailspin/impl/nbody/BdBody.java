/* The Computer Language Benchmarks Game
 * http://shootout.alioth.debian.org/
 *
 * Based on nbody.java and adapted basde on the SOM version.
 */
package tailspin.impl.nbody;

import java.math.BigDecimal;
import java.math.MathContext;

public final class BdBody {
  public static final MathContext PRECISION = new MathContext(16);
  private static final BigDecimal PI = new BigDecimal("3.141592653589793");
  private static final BigDecimal SOLAR_MASS = new BigDecimal(4).multiply(PI, PRECISION).multiply(PI, PRECISION);
  private static final BigDecimal DAYS_PER_YEAR = new BigDecimal("365.24");

  private BigDecimal x;
  private BigDecimal y;
  private BigDecimal z;
  private BigDecimal vx;
  private BigDecimal vy;
  private BigDecimal vz;
  private final BigDecimal mass;

  public BigDecimal getX() { return x; }
  public BigDecimal getY() { return y; }
  public BigDecimal getZ() { return z; }

  public BigDecimal getVX() { return vx; }
  public BigDecimal getVY() { return vy; }
  public BigDecimal getVZ() { return vz; }

  public BigDecimal getMass() { return mass; }

  public void setX(final BigDecimal x) { this.x = x; }
  public void setY(final BigDecimal y) { this.y = y; }
  public void setZ(final BigDecimal z) { this.z = z; }

  public void setVX(final BigDecimal vx) { this.vx = vx; }
  public void setVY(final BigDecimal vy) { this.vy = vy; }
  public void setVZ(final BigDecimal vz) { this.vz = vz; }

  void offsetMomentum(final BigDecimal px, final BigDecimal py, final BigDecimal pz) {
    vx = BigDecimal.ZERO.subtract(px.divide(SOLAR_MASS, PRECISION));
    vy = BigDecimal.ZERO.subtract(py.divide(SOLAR_MASS, PRECISION));
    vz = BigDecimal.ZERO.subtract(pz.divide(SOLAR_MASS, PRECISION));
  }

  BdBody(final BigDecimal x, final BigDecimal y, final BigDecimal z,
      final BigDecimal vx, final BigDecimal vy, final BigDecimal vz, final BigDecimal mass) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.vx = vx.multiply(DAYS_PER_YEAR, PRECISION);
    this.vy = vy.multiply(DAYS_PER_YEAR, PRECISION);
    this.vz = vz.multiply(DAYS_PER_YEAR, PRECISION);
    this.mass = mass.multiply(SOLAR_MASS, PRECISION);
  }

  static BdBody jupiter() {
    return new BdBody(
         new BigDecimal("4.84143144246472090e+00"),
        new BigDecimal("-1.16032004402742839e+00"),
        new BigDecimal("-1.03622044471123109e-01"),
         new BigDecimal("1.66007664274403694e-03"),
         new BigDecimal("7.69901118419740425e-03"),
        new BigDecimal("-6.90460016972063023e-05"),
         new BigDecimal("9.54791938424326609e-04"));
  }

  static BdBody saturn() {
    return new BdBody(
         new BigDecimal("8.34336671824457987e+00"),
         new BigDecimal("4.12479856412430479e+00"),
        new BigDecimal("-4.03523417114321381e-01"),
        new BigDecimal("-2.76742510726862411e-03"),
         new BigDecimal("4.99852801234917238e-03"),
         new BigDecimal("2.30417297573763929e-05"),
         new BigDecimal("2.85885980666130812e-04"));
  }

  static BdBody uranus() {
    return new BdBody(
         new BigDecimal("1.28943695621391310e+01"),
        new BigDecimal("-1.51111514016986312e+01"),
        new BigDecimal("-2.23307578892655734e-01"),
         new BigDecimal("2.96460137564761618e-03"),
         new BigDecimal("2.37847173959480950e-03"),
        new BigDecimal("-2.96589568540237556e-05"),
         new BigDecimal("4.36624404335156298e-05"));
  }

  static BdBody neptune() {
    return new BdBody(
         new BigDecimal("1.53796971148509165e+01"),
        new BigDecimal("-2.59193146099879641e+01"),
         new BigDecimal("1.79258772950371181e-01"),
         new BigDecimal("2.68067772490389322e-03"),
         new BigDecimal("1.62824170038242295e-03"),
        new BigDecimal("-9.51592254519715870e-05"),
         new BigDecimal("5.15138902046611451e-05"));
  }

  static BdBody sun() {
    return new BdBody(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);
  }
}
