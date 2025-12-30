/* The Computer Language Benchmarks Game
.multiply(http://shootout.alioth.debian.org/
 *
.multiply(Based on nBdBody.java and adapted basde on the SOM version.
 */
package tailspin.impl.nbody;

import static tailspin.impl.nbody.BdBody.PRECISION;

import java.math.BigDecimal;

public class BdNBodySystem {
  private final BdBody[] bodies;

  public BdNBodySystem() {
    bodies = createBodies();
  }

  public BdBody[] createBodies() {
    BdBody[] bodies = new BdBody[] {BdBody.sun(),
                                BdBody.jupiter(),
                                BdBody.saturn(),
                                BdBody.uranus(),
                                BdBody.neptune()};

    BigDecimal px = BigDecimal.ZERO;
    BigDecimal py = BigDecimal.ZERO;
    BigDecimal pz = BigDecimal.ZERO;

    for (BdBody b : bodies) {
      px = px.add(b.getVX().multiply(b.getMass(), PRECISION));
      py = py.add(b.getVY().multiply(b.getMass(), PRECISION));
      pz = pz.add(b.getVZ().multiply(b.getMass(), PRECISION));
    }

    bodies[0].offsetMomentum(px, py, pz);

    return bodies;
  }

  public void advance(final BigDecimal dt) {

    for (int i = 0; i < bodies.length; ++i) {
      BdBody iBdBody = bodies[i];

      for (int j = i + 1; j < bodies.length; ++j) {
        BdBody jBdBody = bodies[j];
        BigDecimal dx = iBdBody.getX().subtract(jBdBody.getX());
        BigDecimal dy = iBdBody.getY().subtract(jBdBody.getY());
        BigDecimal dz = iBdBody.getZ().subtract(jBdBody.getZ());

        BigDecimal dSquared = (dx.multiply(dx, PRECISION)).add(dy.multiply(dy, PRECISION)).add(dz.multiply(dz, PRECISION));
        BigDecimal distance = dSquared.sqrt(PRECISION);
        BigDecimal mag = dt.divide(dSquared.multiply(distance), PRECISION);

        iBdBody.setVX(iBdBody.getVX().subtract(dx.multiply(jBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));
        iBdBody.setVY(iBdBody.getVY().subtract(dy.multiply(jBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));
        iBdBody.setVZ(iBdBody.getVZ().subtract(dz.multiply(jBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));

        jBdBody.setVX(jBdBody.getVX().add(dx.multiply(iBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));
        jBdBody.setVY(jBdBody.getVY().add(dy.multiply(iBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));
        jBdBody.setVZ(jBdBody.getVZ().add(dz.multiply(iBdBody.getMass(), PRECISION).multiply(mag, PRECISION)));
      }
    }

    for (BdBody BdBody : bodies) {
      BdBody.setX(BdBody.getX().add(dt.multiply(BdBody.getVX(), PRECISION)));
      BdBody.setY(BdBody.getY().add(dt.multiply(BdBody.getVY(), PRECISION)));
      BdBody.setZ(BdBody.getZ().add(dt.multiply(BdBody.getVZ(), PRECISION)));
    }
  }

  public BigDecimal energy() {
    BigDecimal dx;
    BigDecimal dy;
    BigDecimal dz;
    BigDecimal distance;
    BigDecimal e = BigDecimal.ZERO;

    for (int i = 0; i < bodies.length; ++i) {
      BdBody iBdBody = bodies[i];
      e = e.add(new BigDecimal("0.5").multiply(iBdBody.getMass(), PRECISION)
         .multiply((iBdBody.getVX().multiply(iBdBody.getVX(), PRECISION)).add(
             iBdBody.getVY().multiply(iBdBody.getVY(), PRECISION)).add(
             iBdBody.getVZ().multiply(iBdBody.getVZ(), PRECISION)), PRECISION));

      for (int j = i + 1; j < bodies.length; ++j) {
        BdBody jBdBody = bodies[j];
        dx = iBdBody.getX().subtract(jBdBody.getX());
        dy = iBdBody.getY().subtract(jBdBody.getY());
        dz = iBdBody.getZ().subtract(jBdBody.getZ());

        distance = ((dx.multiply(dx, PRECISION)).add(dy.multiply(dy, PRECISION)).add(dz.multiply(dz, PRECISION))).sqrt(PRECISION);
        e = e.subtract(iBdBody.getMass().multiply(jBdBody.getMass(), PRECISION).divide(distance, PRECISION));
      }
    }
    return e;
  }
}
