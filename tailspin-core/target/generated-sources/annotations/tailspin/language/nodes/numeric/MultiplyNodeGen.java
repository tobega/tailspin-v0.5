// CheckStyle: start generated
package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.DSLSupport;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;

/**
 * Debug Info: <pre>
 *   Specialization {@link MultiplyNode#doMeasureLeft}
 *     Activation probability: 0,27381
 *     With/without class size: 8/0 bytes
 *   Specialization {@link MultiplyNode#doMeasureRight}
 *     Activation probability: 0,23095
 *     With/without class size: 7/0 bytes
 *   Specialization {@link MultiplyNode#doUntypedMeasures}
 *     Activation probability: 0,18810
 *     With/without class size: 7/0 bytes
 *   Specialization {@link MultiplyNode#doUntypedMeasureRight}
 *     Activation probability: 0,14524
 *     With/without class size: 6/0 bytes
 *   Specialization {@link MultiplyNode#doUntypedMeasureLeft}
 *     Activation probability: 0,10238
 *     With/without class size: 5/0 bytes
 *   Specialization {@link MultiplyNode#doOther}
 *     Activation probability: 0,05952
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(MultiplyNode.class)
@SuppressWarnings("javadoc")
public final class MultiplyNodeGen extends MultiplyNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link MultiplyNode#doMeasureLeft}
     *   Parameter: {@link DoMultiplyNode} doMultiplyNode
     *   Inline method: {@link DoMultiplyNodeGen#inline}</pre>
     */
    private static final DoMultiplyNode INLINED_DO_MULTIPLY_NODE = DoMultiplyNodeGen.inline(InlineTarget.create(DoMultiplyNode.class, STATE_1_UPDATER.subUpdater(0, 32), STATE_0_UPDATER.subUpdater(6, 8)));

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MultiplyNode#doMeasureLeft}
     *   1: SpecializationActive {@link MultiplyNode#doMeasureRight}
     *   2: SpecializationActive {@link MultiplyNode#doUntypedMeasures}
     *   3: SpecializationActive {@link MultiplyNode#doUntypedMeasureRight}
     *   4: SpecializationActive {@link MultiplyNode#doUntypedMeasureLeft}
     *   5: SpecializationActive {@link MultiplyNode#doOther}
     *   6-13: InlinedCache
     *        Specialization: {@link MultiplyNode#doMeasureLeft}
     *        Parameter: {@link DoMultiplyNode} doMultiplyNode
     *        Inline method: {@link DoMultiplyNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * State Info: <pre>
     *   0-31: InlinedCache
     *        Specialization: {@link MultiplyNode#doMeasureLeft}
     *        Parameter: {@link DoMultiplyNode} doMultiplyNode
     *        Inline method: {@link DoMultiplyNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;

    private MultiplyNodeGen(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        super(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = super.leftNode.executeGeneric(frameValue);
        Object rightNodeValue_ = super.rightNode.executeGeneric(frameValue);
        if ((state_0 & 0b111111) != 0 /* is SpecializationActive[MultiplyNode.doMeasureLeft(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doMeasureRight(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doOther(VirtualFrame, Object, Object, DoMultiplyNode)] */) {
            if ((state_0 & 0b1111) != 0 /* is SpecializationActive[MultiplyNode.doMeasureLeft(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doMeasureRight(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMultiplyNode)] */ && rightNodeValue_ instanceof Measure) {
                Measure rightNodeValue__ = (Measure) rightNodeValue_;
                if ((state_0 & 0b111) != 0 /* is SpecializationActive[MultiplyNode.doMeasureLeft(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doMeasureRight(VirtualFrame, Measure, Measure, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMultiplyNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[MultiplyNode.doMeasureLeft(VirtualFrame, Measure, Measure, DoMultiplyNode)] */) {
                        if ((isScalar(rightNodeValue__.unit()))) {
                            return doMeasureLeft(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_MULTIPLY_NODE);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[MultiplyNode.doMeasureRight(VirtualFrame, Measure, Measure, DoMultiplyNode)] */) {
                        if ((isScalar(leftNodeValue__.unit()))) {
                            return doMeasureRight(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_MULTIPLY_NODE);
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[MultiplyNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMultiplyNode)] */) {
                        assert DSLSupport.assertIdempotence((isUntypedRegion));
                        return doUntypedMeasures(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_MULTIPLY_NODE);
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MultiplyNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMultiplyNode)] */) {
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureRight(frameValue, leftNodeValue_, rightNodeValue__, INLINED_DO_MULTIPLY_NODE);
                }
            }
            if ((state_0 & 0b110000) != 0 /* is SpecializationActive[MultiplyNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMultiplyNode)] || SpecializationActive[MultiplyNode.doOther(VirtualFrame, Object, Object, DoMultiplyNode)] */) {
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[MultiplyNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMultiplyNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureLeft(frameValue, leftNodeValue__, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
                }
                if ((state_0 & 0b100000) != 0 /* is SpecializationActive[MultiplyNode.doOther(VirtualFrame, Object, Object, DoMultiplyNode)] */) {
                    return doOther(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, leftNodeValue_, rightNodeValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if (rightNodeValue instanceof Measure) {
            Measure rightNodeValue_ = (Measure) rightNodeValue;
            if (leftNodeValue instanceof Measure) {
                Measure leftNodeValue_ = (Measure) leftNodeValue;
                if ((isScalar(rightNodeValue_.unit()))) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[MultiplyNode.doMeasureLeft(VirtualFrame, Measure, Measure, DoMultiplyNode)] */;
                    this.state_0_ = state_0;
                    return doMeasureLeft(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
                }
                if ((isScalar(leftNodeValue_.unit()))) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[MultiplyNode.doMeasureRight(VirtualFrame, Measure, Measure, DoMultiplyNode)] */;
                    this.state_0_ = state_0;
                    return doMeasureRight(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
                }
                if ((isUntypedRegion)) {
                    state_0 = state_0 | 0b100 /* add SpecializationActive[MultiplyNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMultiplyNode)] */;
                    this.state_0_ = state_0;
                    return doUntypedMeasures(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
                }
            }
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b1000 /* add SpecializationActive[MultiplyNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMultiplyNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureRight(frameValue, leftNodeValue, rightNodeValue_, INLINED_DO_MULTIPLY_NODE);
            }
        }
        if (leftNodeValue instanceof Measure) {
            Measure leftNodeValue_ = (Measure) leftNodeValue;
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b10000 /* add SpecializationActive[MultiplyNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMultiplyNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureLeft(frameValue, leftNodeValue_, rightNodeValue, INLINED_DO_MULTIPLY_NODE);
            }
        }
        state_0 = state_0 | 0b100000 /* add SpecializationActive[MultiplyNode.doOther(VirtualFrame, Object, Object, DoMultiplyNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, leftNodeValue, rightNodeValue, INLINED_DO_MULTIPLY_NODE);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            int counter = 0;
            counter += Integer.bitCount((state_0 & 0b111111));
            if (counter == 1) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static MultiplyNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        return new MultiplyNodeGen(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoMultiplyNode#doLong}
     *     Activation probability: 0,09526
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMultiplyNode#doBigNumber}
     *     Activation probability: 0,09053
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMultiplyNode#doSmallRational}
     *     Activation probability: 0,08579
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMultiplyNode#smallRationalLong}
     *     Activation probability: 0,08105
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#longSmallRational}
     *     Activation probability: 0,07632
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doRational}
     *     Activation probability: 0,07158
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#rationalBigNumber}
     *     Activation probability: 0,06684
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#bigNumberRational}
     *     Activation probability: 0,06211
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSmallSciNum}
     *     Activation probability: 0,05737
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSmallSciNumLong}
     *     Activation probability: 0,05263
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doLongSmallSciNum}
     *     Activation probability: 0,04789
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSmallRationalSmallSciNum}
     *     Activation probability: 0,04316
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSmallSciNumSmallRational}
     *     Activation probability: 0,03842
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSciNum}
     *     Activation probability: 0,03368
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doBigNumSciNum}
     *     Activation probability: 0,02895
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSciNumBigNum}
     *     Activation probability: 0,02421
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doRationalSciNum}
     *     Activation probability: 0,01947
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#doSciNumRational}
     *     Activation probability: 0,01474
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMultiplyNode#typeError}
     *     Activation probability: 0,01000
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoMultiplyNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoMultiplyNodeGen extends DoMultiplyNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoMultiplyNode#doLong}
         *   1: SpecializationExcluded {@link DoMultiplyNode#doLong}
         *   2: SpecializationActive {@link DoMultiplyNode#doBigNumber}
         *   3: SpecializationActive {@link DoMultiplyNode#doSmallRational}
         *   4: SpecializationExcluded {@link DoMultiplyNode#doSmallRational}
         *   5: SpecializationActive {@link DoMultiplyNode#doRational}
         *   6: SpecializationActive {@link DoMultiplyNode#smallRationalLong}
         *   7: SpecializationExcluded {@link DoMultiplyNode#smallRationalLong}
         *   8: SpecializationActive {@link DoMultiplyNode#rationalBigNumber}
         *   9: SpecializationActive {@link DoMultiplyNode#longSmallRational}
         *   10: SpecializationExcluded {@link DoMultiplyNode#longSmallRational}
         *   11: SpecializationActive {@link DoMultiplyNode#bigNumberRational}
         *   12: SpecializationActive {@link DoMultiplyNode#doSmallSciNum}
         *   13: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNum}
         *   14: SpecializationActive {@link DoMultiplyNode#doSciNum}
         *   15: SpecializationActive {@link DoMultiplyNode#doSmallSciNumLong}
         *   16: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNumLong}
         *   17: SpecializationActive {@link DoMultiplyNode#doSciNumBigNum}
         *   18: SpecializationActive {@link DoMultiplyNode#doLongSmallSciNum}
         *   19: SpecializationExcluded {@link DoMultiplyNode#doLongSmallSciNum}
         *   20: SpecializationActive {@link DoMultiplyNode#doBigNumSciNum}
         *   21: SpecializationActive {@link DoMultiplyNode#doSmallRationalSmallSciNum}
         *   22: SpecializationExcluded {@link DoMultiplyNode#doSmallRationalSmallSciNum}
         *   23: SpecializationActive {@link DoMultiplyNode#doRationalSciNum}
         *   24: SpecializationActive {@link DoMultiplyNode#doSmallSciNumSmallRational}
         *   25: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNumSmallRational}
         *   26: SpecializationActive {@link DoMultiplyNode#doSciNumRational}
         *   27: SpecializationActive {@link DoMultiplyNode#typeError}
         *   28-29: ImplicitCast[type=BigNumber, index=1]
         *   30-31: ImplicitCast[type=BigNumber, index=2]
         * </pre>
         */
        @CompilationFinal private int state_0_;
        /**
         * State Info: <pre>
         *   0-1: ImplicitCast[type=Rational, index=1]
         *   2-3: ImplicitCast[type=Rational, index=2]
         *   4-5: ImplicitCast[type=SciNum, index=1]
         *   6-7: ImplicitCast[type=SciNum, index=2]
         * </pre>
         */
        @CompilationFinal private int state_1_;

        private DoMultiplyNodeGen() {
        }

        @Override
        public Object executeMultiply(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doRational(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x240000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if ((state_0 & 0x40000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x200000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x104000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0x100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x20000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x800000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */) {
                    return typeError(arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b100 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */;
                        this.state_0_ = state_0;
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return longSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                        state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    {
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doRational(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int rationalCast2;
                    if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b100000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    Long arg2Value_ = (Long) arg2Value;
                    state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg2Value instanceof SmallSciNum) {
                SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    state_0 = state_0 | 0x40000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0x200000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0x1000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int sciNumCast2;
                if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                    {
                        int sciNumCast1;
                        if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x100000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0x20000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    int sciNumCast2;
                    if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                        state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                        state_0 = state_0 | 0x800000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    int rationalCast2;
                    if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0x4000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
            }
            state_0 = state_0 | 0x8000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */;
            this.state_0_ = state_0;
            return typeError(arg1Value, arg2Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0xdb6db6d) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                int counter = 0;
                counter += Integer.bitCount((state_0 & 0xdb6db6d));
                if (counter == 1) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static DoMultiplyNode create() {
            return new DoMultiplyNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * <li>{@link Inlined#state_1_}
         * </ul>
         */
        @NeverDefault
        public static DoMultiplyNode inline(@RequiredField(bits = 32, value = StateField.class)@RequiredField(bits = 8, value = StateField.class) InlineTarget target) {
            return new DoMultiplyNodeGen.Inlined(target);
        }

        @GeneratedBy(DoMultiplyNode.class)
        @DenyReplace
        private static final class Inlined extends DoMultiplyNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoMultiplyNode#doLong}
             *   1: SpecializationExcluded {@link DoMultiplyNode#doLong}
             *   2: SpecializationActive {@link DoMultiplyNode#doBigNumber}
             *   3: SpecializationActive {@link DoMultiplyNode#doSmallRational}
             *   4: SpecializationExcluded {@link DoMultiplyNode#doSmallRational}
             *   5: SpecializationActive {@link DoMultiplyNode#doRational}
             *   6: SpecializationActive {@link DoMultiplyNode#smallRationalLong}
             *   7: SpecializationExcluded {@link DoMultiplyNode#smallRationalLong}
             *   8: SpecializationActive {@link DoMultiplyNode#rationalBigNumber}
             *   9: SpecializationActive {@link DoMultiplyNode#longSmallRational}
             *   10: SpecializationExcluded {@link DoMultiplyNode#longSmallRational}
             *   11: SpecializationActive {@link DoMultiplyNode#bigNumberRational}
             *   12: SpecializationActive {@link DoMultiplyNode#doSmallSciNum}
             *   13: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNum}
             *   14: SpecializationActive {@link DoMultiplyNode#doSciNum}
             *   15: SpecializationActive {@link DoMultiplyNode#doSmallSciNumLong}
             *   16: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNumLong}
             *   17: SpecializationActive {@link DoMultiplyNode#doSciNumBigNum}
             *   18: SpecializationActive {@link DoMultiplyNode#doLongSmallSciNum}
             *   19: SpecializationExcluded {@link DoMultiplyNode#doLongSmallSciNum}
             *   20: SpecializationActive {@link DoMultiplyNode#doBigNumSciNum}
             *   21: SpecializationActive {@link DoMultiplyNode#doSmallRationalSmallSciNum}
             *   22: SpecializationExcluded {@link DoMultiplyNode#doSmallRationalSmallSciNum}
             *   23: SpecializationActive {@link DoMultiplyNode#doRationalSciNum}
             *   24: SpecializationActive {@link DoMultiplyNode#doSmallSciNumSmallRational}
             *   25: SpecializationExcluded {@link DoMultiplyNode#doSmallSciNumSmallRational}
             *   26: SpecializationActive {@link DoMultiplyNode#doSciNumRational}
             *   27: SpecializationActive {@link DoMultiplyNode#typeError}
             *   28-29: ImplicitCast[type=BigNumber, index=1]
             *   30-31: ImplicitCast[type=BigNumber, index=2]
             * </pre>
             */
            private final StateField state_0_;
            /**
             * State Info: <pre>
             *   0-1: ImplicitCast[type=Rational, index=1]
             *   2-3: ImplicitCast[type=Rational, index=2]
             *   4-5: ImplicitCast[type=SciNum, index=1]
             *   6-7: ImplicitCast[type=SciNum, index=2]
             * </pre>
             */
            private final StateField state_1_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(DoMultiplyNode.class);
                this.state_0_ = target.getState(0, 32);
                this.state_1_ = target.getState(1, 8);
            }

            @Override
            public Object executeMultiply(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return doLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                                state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return smallRationalLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                                state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return longSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                                state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] || SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doRational(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            try {
                                return doSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                            Long arg2Value_ = (Long) arg2Value;
                            try {
                                return doSmallSciNumLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                                state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x240000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        if ((state_0 & 0x40000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                            Long arg1Value_ = (Long) arg1Value;
                            try {
                                return doLongSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0x200000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                            SmallRational arg1Value_ = (SmallRational) arg1Value;
                            try {
                                return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                                state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                                state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x104000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0x100000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x800000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */) {
                        return typeError(arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                int state_1 = this.state_1_.get(arg0Value);
                if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLong(long, long)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumber(BigNumber, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        {
                            int rationalCast2;
                            if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                                Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b100000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doRational(Rational, Rational)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doRational(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.smallRationalLong(SmallRational, long)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b100000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.rationalBigNumber(Rational, BigNumber)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return rationalBigNumber(arg1Value_, arg2Value_);
                            }
                        }
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.longSmallRational(long, SmallRational)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.bigNumberRational(BigNumber, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        state_0 = state_0 | 0x40000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        state_0 = state_0 | 0x200000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0x1000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int sciNumCast2;
                    if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                        {
                            int sciNumCast1;
                            if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                                SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNum(SciNum, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0x100000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doBigNumSciNum(BigNumber, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doBigNumSciNum(arg1Value_, arg2Value_);
                            }
                        }
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0x20000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumBigNum(SciNum, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        int sciNumCast2;
                        if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x800000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doRationalSciNum(Rational, SciNum)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MultiplyNode.DoMultiplyNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0x4000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.doSciNumRational(SciNum, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                state_0 = state_0 | 0x8000000 /* add SpecializationActive[MultiplyNode.DoMultiplyNode.typeError(Object, Object)] */;
                this.state_0_.set(arg0Value, state_0);
                return typeError(arg1Value, arg2Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
