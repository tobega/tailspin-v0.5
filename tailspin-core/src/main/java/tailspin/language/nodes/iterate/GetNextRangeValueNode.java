package tailspin.language.nodes.iterate;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.InlinedLoopConditionProfile;
import java.math.BigInteger;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.stream.RangeStream;

@GenerateInline
public abstract class GetNextRangeValueNode extends Node {
  public abstract Object execute(VirtualFrame frame, Node node, RangeStream range);

  @Specialization(
      guards = {
          "isPositiveIncrement == isPositiveIncrement(node, gtZeroNode, range)",
          "isInclusive == range.inclusiveEnd"
      },
      limit = "4"
  )
  static Object doRangePolymorphic(VirtualFrame frame,
      Node node,
      RangeStream range,
      @Cached GtZeroNode gtZeroNode,
      @Cached("isPositiveIncrement(node, gtZeroNode, range)") boolean isPositiveIncrement,
      @Cached("range.inclusiveEnd") boolean isInclusive,
      @Cached InlinedLoopConditionProfile loopProfile,
      @Cached(value = "createAddNode()", inline = false) AddNode addNode,
      @Cached(value = "createUpperBoundExceeded(true)", inline = false) GreaterThanMatcherNode upperBoundInclusive,
      @Cached(value = "createUpperBoundExceeded(false)", inline = false) GreaterThanMatcherNode upperBoundExclusive,
      @Cached(value = "createLowerBoundExceeded(true)", inline = false) LessThanMatcherNode lowerBoundInclusive,
      @Cached(value = "createLowerBoundExceeded(false)", inline = false) LessThanMatcherNode lowerBoundExclusive) {

    Object next = range.next;
    Object end = range.end;
    Object increment = range.increment;

    boolean isFinished;
    if (isPositiveIncrement) {
      GreaterThanMatcherNode matcher = isInclusive ? upperBoundInclusive : upperBoundExclusive;
      isFinished = matcher.executeComparison(frame, next, end);
    } else {
      LessThanMatcherNode matcher = isInclusive ? lowerBoundInclusive : lowerBoundExclusive;
      isFinished = matcher.executeComparison(frame, next, end);
    }

    if (loopProfile.profile(node, isFinished)) {
      throw new EndOfStreamException();
    }

    range.next = addNode.executeAdd(next, increment);
    return next;
  }

  GreaterThanMatcherNode createUpperBoundExceeded(boolean inclusive) {
    return GreaterThanMatcherNode.create(true, !inclusive, null, INTERNAL_CODE_SOURCE);
  }

  LessThanMatcherNode createLowerBoundExceeded(boolean inclusive) {
    return LessThanMatcherNode.create(true, !inclusive, null, INTERNAL_CODE_SOURCE);
  }

  static boolean isPositiveIncrement(Node node, GtZeroNode gtZeroNode, RangeStream range) {
    return gtZeroNode.executeTest(node, range.increment);
  }

  AddNode createAddNode() {
    return AddNode.create(null, null, false, INTERNAL_CODE_SOURCE);
  }

  public static GetNextRangeValueNode create() {
    return GetNextRangeValueNodeGen.create();
  }

  @GenerateInline
  static abstract class GtZeroNode extends Node {
    public abstract boolean executeTest(Node node, Object value);

    @Specialization
    boolean compareMeasure(Node node, Measure measure) {
      return executeTest(node, measure.value());
    }

    @Specialization
    boolean compareLong(long value) {
      return value > 0;
    }

    @Specialization
    boolean compareBigNumber(BigNumber value) {
      return value.asBigInteger().signum() > 0;
    }

    @Specialization
    boolean compareSmallRational(SmallRational value) {
      return value.compareTo(SmallRational.of(0, 1)) > 0;
    }

    @Specialization
    boolean compareRational(Rational value) {
      return value.compareTo(new Rational(BigInteger.ZERO, BigInteger.ONE)) > 0;
    }

    @Specialization
    boolean compareSmallSciNum(SmallSciNum value) {
      return value.compareTo(SmallSciNum.fromLong(0)) > 0;
    }

    @Specialization
    boolean compareSciNum(SciNum value) {
      return value.compareTo(SciNum.fromLong(0)) > 0;
    }
  }
}
