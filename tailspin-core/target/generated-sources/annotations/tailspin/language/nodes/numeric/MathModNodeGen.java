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
 *   Specialization {@link MathModNode#doMeasure}
 *     Activation probability: 0,32000
 *     With/without class size: 9/0 bytes
 *   Specialization {@link MathModNode#doUntypedMeasures}
 *     Activation probability: 0,26000
 *     With/without class size: 8/0 bytes
 *   Specialization {@link MathModNode#doUntypedMeasureRight}
 *     Activation probability: 0,20000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link MathModNode#doUntypedMeasureLeft}
 *     Activation probability: 0,14000
 *     With/without class size: 6/0 bytes
 *   Specialization {@link MathModNode#doOther}
 *     Activation probability: 0,08000
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(MathModNode.class)
@SuppressWarnings("javadoc")
public final class MathModNodeGen extends MathModNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link MathModNode#doMeasure}
     *   Parameter: {@link DoMathModNode} doMathModNode
     *   Inline method: {@link DoMathModNodeGen#inline}</pre>
     */
    private static final DoMathModNode INLINED_DO_MATH_MOD_NODE = DoMathModNodeGen.inline(InlineTarget.create(DoMathModNode.class, STATE_1_UPDATER.subUpdater(0, 32), STATE_0_UPDATER.subUpdater(5, 8)));

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MathModNode#doMeasure}
     *   1: SpecializationActive {@link MathModNode#doUntypedMeasures}
     *   2: SpecializationActive {@link MathModNode#doUntypedMeasureRight}
     *   3: SpecializationActive {@link MathModNode#doUntypedMeasureLeft}
     *   4: SpecializationActive {@link MathModNode#doOther}
     *   5-12: InlinedCache
     *        Specialization: {@link MathModNode#doMeasure}
     *        Parameter: {@link DoMathModNode} doMathModNode
     *        Inline method: {@link DoMathModNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * State Info: <pre>
     *   0-31: InlinedCache
     *        Specialization: {@link MathModNode#doMeasure}
     *        Parameter: {@link DoMathModNode} doMathModNode
     *        Inline method: {@link DoMathModNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;

    private MathModNodeGen(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        super(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = super.leftNode.executeGeneric(frameValue);
        Object rightNodeValue_ = super.rightNode.executeGeneric(frameValue);
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[MathModNode.doMeasure(VirtualFrame, Measure, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMathModNode)] || SpecializationActive[MathModNode.doOther(VirtualFrame, Object, Object, DoMathModNode)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[MathModNode.doMeasure(VirtualFrame, Measure, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMathModNode)] */ && rightNodeValue_ instanceof Measure) {
                Measure rightNodeValue__ = (Measure) rightNodeValue_;
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[MathModNode.doMeasure(VirtualFrame, Measure, Measure, DoMathModNode)] || SpecializationActive[MathModNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMathModNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[MathModNode.doMeasure(VirtualFrame, Measure, Measure, DoMathModNode)] */) {
                        if ((leftNodeValue__.unit() == rightNodeValue__.unit())) {
                            return doMeasure(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_MATH_MOD_NODE);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[MathModNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMathModNode)] */) {
                        assert DSLSupport.assertIdempotence((isUntypedRegion));
                        return doUntypedMeasures(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_MATH_MOD_NODE);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[MathModNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMathModNode)] */) {
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureRight(frameValue, leftNodeValue_, rightNodeValue__, INLINED_DO_MATH_MOD_NODE);
                }
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[MathModNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMathModNode)] || SpecializationActive[MathModNode.doOther(VirtualFrame, Object, Object, DoMathModNode)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MathModNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMathModNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureLeft(frameValue, leftNodeValue__, rightNodeValue_, INLINED_DO_MATH_MOD_NODE);
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[MathModNode.doOther(VirtualFrame, Object, Object, DoMathModNode)] */) {
                    return doOther(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MATH_MOD_NODE);
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
                if ((leftNodeValue_.unit() == rightNodeValue_.unit())) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[MathModNode.doMeasure(VirtualFrame, Measure, Measure, DoMathModNode)] */;
                    this.state_0_ = state_0;
                    return doMeasure(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MATH_MOD_NODE);
                }
                if ((isUntypedRegion)) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[MathModNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoMathModNode)] */;
                    this.state_0_ = state_0;
                    return doUntypedMeasures(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_MATH_MOD_NODE);
                }
            }
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b100 /* add SpecializationActive[MathModNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoMathModNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureRight(frameValue, leftNodeValue, rightNodeValue_, INLINED_DO_MATH_MOD_NODE);
            }
        }
        if (leftNodeValue instanceof Measure) {
            Measure leftNodeValue_ = (Measure) leftNodeValue;
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b1000 /* add SpecializationActive[MathModNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoMathModNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureLeft(frameValue, leftNodeValue_, rightNodeValue, INLINED_DO_MATH_MOD_NODE);
            }
        }
        state_0 = state_0 | 0b10000 /* add SpecializationActive[MathModNode.doOther(VirtualFrame, Object, Object, DoMathModNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, leftNodeValue, rightNodeValue, INLINED_DO_MATH_MOD_NODE);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            int counter = 0;
            counter += Integer.bitCount((state_0 & 0b11111));
            if (counter == 1) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static MathModNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        return new MathModNodeGen(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoMathModNode#doLong}
     *     Activation probability: 0,09526
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMathModNode#doBigNumber}
     *     Activation probability: 0,09053
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMathModNode#doSmallRational}
     *     Activation probability: 0,08579
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoMathModNode#smallRationalLong}
     *     Activation probability: 0,08105
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#longSmallRational}
     *     Activation probability: 0,07632
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doRational}
     *     Activation probability: 0,07158
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#rationalBigNumber}
     *     Activation probability: 0,06684
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#bigNumberRational}
     *     Activation probability: 0,06211
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSmallSciNum}
     *     Activation probability: 0,05737
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSmallSciNumLong}
     *     Activation probability: 0,05263
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doLongSmallSciNum}
     *     Activation probability: 0,04789
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSmallRationalSmallSciNum}
     *     Activation probability: 0,04316
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSmallSciNumSmallRational}
     *     Activation probability: 0,03842
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSciNum}
     *     Activation probability: 0,03368
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSciNumBigNum}
     *     Activation probability: 0,02895
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doBigNumSciNum}
     *     Activation probability: 0,02421
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doRationalSciNum}
     *     Activation probability: 0,01947
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#doSciNumRational}
     *     Activation probability: 0,01474
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoMathModNode#typeError}
     *     Activation probability: 0,01000
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoMathModNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoMathModNodeGen extends DoMathModNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoMathModNode#doLong}
         *   1: SpecializationExcluded {@link DoMathModNode#doLong}
         *   2: SpecializationActive {@link DoMathModNode#doBigNumber}
         *   3: SpecializationActive {@link DoMathModNode#doSmallRational}
         *   4: SpecializationExcluded {@link DoMathModNode#doSmallRational}
         *   5: SpecializationActive {@link DoMathModNode#doRational}
         *   6: SpecializationActive {@link DoMathModNode#smallRationalLong}
         *   7: SpecializationExcluded {@link DoMathModNode#smallRationalLong}
         *   8: SpecializationActive {@link DoMathModNode#rationalBigNumber}
         *   9: SpecializationActive {@link DoMathModNode#longSmallRational}
         *   10: SpecializationExcluded {@link DoMathModNode#longSmallRational}
         *   11: SpecializationActive {@link DoMathModNode#bigNumberRational}
         *   12: SpecializationActive {@link DoMathModNode#doSmallSciNum}
         *   13: SpecializationExcluded {@link DoMathModNode#doSmallSciNum}
         *   14: SpecializationActive {@link DoMathModNode#doSciNum}
         *   15: SpecializationActive {@link DoMathModNode#doSmallSciNumLong}
         *   16: SpecializationExcluded {@link DoMathModNode#doSmallSciNumLong}
         *   17: SpecializationActive {@link DoMathModNode#doSciNumBigNum}
         *   18: SpecializationActive {@link DoMathModNode#doLongSmallSciNum}
         *   19: SpecializationExcluded {@link DoMathModNode#doLongSmallSciNum}
         *   20: SpecializationActive {@link DoMathModNode#doBigNumSciNum}
         *   21: SpecializationActive {@link DoMathModNode#doSmallRationalSmallSciNum}
         *   22: SpecializationExcluded {@link DoMathModNode#doSmallRationalSmallSciNum}
         *   23: SpecializationActive {@link DoMathModNode#doRationalSciNum}
         *   24: SpecializationActive {@link DoMathModNode#doSmallSciNumSmallRational}
         *   25: SpecializationExcluded {@link DoMathModNode#doSmallSciNumSmallRational}
         *   26: SpecializationActive {@link DoMathModNode#doSciNumRational}
         *   27: SpecializationActive {@link DoMathModNode#typeError}
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

        private DoMathModNodeGen() {
        }

        @Override
        public Object executeMathMod(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] || SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] || SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] || SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] || SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doRational(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x240000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if ((state_0 & 0x40000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x200000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x24000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x900000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0x100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0x800000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */) {
                    return typeError(arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
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
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b100 /* add SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */;
                        this.state_0_ = state_0;
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000000000 /* add SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return longSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
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
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000 /* add SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doRational(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100000000 /* add SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */;
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
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b100000000000 /* add SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    Long arg2Value_ = (Long) arg2Value;
                    state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg2Value instanceof SmallSciNum) {
                SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    state_0 = state_0 | 0x40000 /* add SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0x200000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0x1000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    {
                        int sciNumCast2;
                        if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0x20000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                }
            }
            {
                int sciNumCast2;
                if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x100000 /* add SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int rationalCast1;
                        if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                            Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x800000 /* add SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
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
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0x4000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
            }
            state_0 = state_0 | 0x8000000 /* add SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */;
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
        public static DoMathModNode create() {
            return new DoMathModNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * <li>{@link Inlined#state_1_}
         * </ul>
         */
        @NeverDefault
        public static DoMathModNode inline(@RequiredField(bits = 32, value = StateField.class)@RequiredField(bits = 8, value = StateField.class) InlineTarget target) {
            return new DoMathModNodeGen.Inlined(target);
        }

        @GeneratedBy(DoMathModNode.class)
        @DenyReplace
        private static final class Inlined extends DoMathModNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoMathModNode#doLong}
             *   1: SpecializationExcluded {@link DoMathModNode#doLong}
             *   2: SpecializationActive {@link DoMathModNode#doBigNumber}
             *   3: SpecializationActive {@link DoMathModNode#doSmallRational}
             *   4: SpecializationExcluded {@link DoMathModNode#doSmallRational}
             *   5: SpecializationActive {@link DoMathModNode#doRational}
             *   6: SpecializationActive {@link DoMathModNode#smallRationalLong}
             *   7: SpecializationExcluded {@link DoMathModNode#smallRationalLong}
             *   8: SpecializationActive {@link DoMathModNode#rationalBigNumber}
             *   9: SpecializationActive {@link DoMathModNode#longSmallRational}
             *   10: SpecializationExcluded {@link DoMathModNode#longSmallRational}
             *   11: SpecializationActive {@link DoMathModNode#bigNumberRational}
             *   12: SpecializationActive {@link DoMathModNode#doSmallSciNum}
             *   13: SpecializationExcluded {@link DoMathModNode#doSmallSciNum}
             *   14: SpecializationActive {@link DoMathModNode#doSciNum}
             *   15: SpecializationActive {@link DoMathModNode#doSmallSciNumLong}
             *   16: SpecializationExcluded {@link DoMathModNode#doSmallSciNumLong}
             *   17: SpecializationActive {@link DoMathModNode#doSciNumBigNum}
             *   18: SpecializationActive {@link DoMathModNode#doLongSmallSciNum}
             *   19: SpecializationExcluded {@link DoMathModNode#doLongSmallSciNum}
             *   20: SpecializationActive {@link DoMathModNode#doBigNumSciNum}
             *   21: SpecializationActive {@link DoMathModNode#doSmallRationalSmallSciNum}
             *   22: SpecializationExcluded {@link DoMathModNode#doSmallRationalSmallSciNum}
             *   23: SpecializationActive {@link DoMathModNode#doRationalSciNum}
             *   24: SpecializationActive {@link DoMathModNode#doSmallSciNumSmallRational}
             *   25: SpecializationExcluded {@link DoMathModNode#doSmallSciNumSmallRational}
             *   26: SpecializationActive {@link DoMathModNode#doSciNumRational}
             *   27: SpecializationActive {@link DoMathModNode#typeError}
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
                assert target.getTargetClass().isAssignableFrom(DoMathModNode.class);
                this.state_0_ = target.getState(0, 32);
                this.state_1_ = target.getState(1, 8);
            }

            @Override
            public Object executeMathMod(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] || SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] || SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] || SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return doLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                                state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return smallRationalLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                                state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return longSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                                state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] || SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doRational(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            try {
                                return doSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                            Long arg2Value_ = (Long) arg2Value;
                            try {
                                return doSmallSciNumLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                                state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x240000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        if ((state_0 & 0x40000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                            Long arg1Value_ = (Long) arg1Value;
                            try {
                                return doLongSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0x200000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                            SmallRational arg1Value_ = (SmallRational) arg1Value;
                            try {
                                return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                                state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                                state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x24000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0x20000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x900000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0x100000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0x800000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                            Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */) {
                        return typeError(arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                int state_1 = this.state_1_.get(arg0Value);
                if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
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
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[MathModNode.DoMathModNode.doLong(long, long)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100 /* add SpecializationActive[MathModNode.DoMathModNode.doBigNumber(BigNumber, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1000000 /* add SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
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
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b100000 /* add SpecializationActive[MathModNode.DoMathModNode.doRational(Rational, Rational)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doRational(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[MathModNode.DoMathModNode.smallRationalLong(SmallRational, long)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b100000000 /* add SpecializationActive[MathModNode.DoMathModNode.rationalBigNumber(Rational, BigNumber)] */;
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
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[MathModNode.DoMathModNode.longSmallRational(long, SmallRational)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000000000 /* add SpecializationActive[MathModNode.DoMathModNode.bigNumberRational(BigNumber, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        state_0 = state_0 | 0x40000 /* add SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        state_0 = state_0 | 0x200000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0x1000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        {
                            int sciNumCast2;
                            if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                                SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNum(SciNum, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                                state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0x20000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNumBigNum(SciNum, BigNumber)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNumBigNum(arg1Value_, arg2Value_);
                            }
                        }
                    }
                }
                {
                    int sciNumCast2;
                    if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[MathModNode.DoMathModNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0x100000 /* add SpecializationActive[MathModNode.DoMathModNode.doBigNumSciNum(BigNumber, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doBigNumSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int rationalCast1;
                            if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                                Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                                state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0x800000 /* add SpecializationActive[MathModNode.DoMathModNode.doRationalSciNum(Rational, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doRationalSciNum(arg1Value_, arg2Value_);
                            }
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
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[MathModNode.DoMathModNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0x4000000 /* add SpecializationActive[MathModNode.DoMathModNode.doSciNumRational(SciNum, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                state_0 = state_0 | 0x8000000 /* add SpecializationActive[MathModNode.DoMathModNode.typeError(Object, Object)] */;
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
