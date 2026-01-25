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
 *   Specialization {@link DivideNode#doMeasure}
 *     Activation probability: 0,27381
 *     With/without class size: 8/0 bytes
 *   Specialization {@link DivideNode#doSameMeasure}
 *     Activation probability: 0,23095
 *     With/without class size: 7/0 bytes
 *   Specialization {@link DivideNode#doUntypedMeasures}
 *     Activation probability: 0,18810
 *     With/without class size: 7/0 bytes
 *   Specialization {@link DivideNode#doUntypedMeasureRight}
 *     Activation probability: 0,14524
 *     With/without class size: 6/0 bytes
 *   Specialization {@link DivideNode#doUntypedMeasureLeft}
 *     Activation probability: 0,10238
 *     With/without class size: 5/0 bytes
 *   Specialization {@link DivideNode#doOther}
 *     Activation probability: 0,05952
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(DivideNode.class)
@SuppressWarnings("javadoc")
public final class DivideNodeGen extends DivideNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link DivideNode#doMeasure}
     *   Parameter: {@link DoDivideNode} doDivideNode
     *   Inline method: {@link DoDivideNodeGen#inline}</pre>
     */
    private static final DoDivideNode INLINED_DO_DIVIDE_NODE = DoDivideNodeGen.inline(InlineTarget.create(DoDivideNode.class, STATE_1_UPDATER.subUpdater(0, 31), STATE_0_UPDATER.subUpdater(6, 8)));

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link DivideNode#doMeasure}
     *   1: SpecializationActive {@link DivideNode#doSameMeasure}
     *   2: SpecializationActive {@link DivideNode#doUntypedMeasures}
     *   3: SpecializationActive {@link DivideNode#doUntypedMeasureRight}
     *   4: SpecializationActive {@link DivideNode#doUntypedMeasureLeft}
     *   5: SpecializationActive {@link DivideNode#doOther}
     *   6-13: InlinedCache
     *        Specialization: {@link DivideNode#doMeasure}
     *        Parameter: {@link DoDivideNode} doDivideNode
     *        Inline method: {@link DoDivideNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * State Info: <pre>
     *   0-30: InlinedCache
     *        Specialization: {@link DivideNode#doMeasure}
     *        Parameter: {@link DoDivideNode} doDivideNode
     *        Inline method: {@link DoDivideNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;

    private DivideNodeGen(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        super(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = super.leftNode.executeGeneric(frameValue);
        Object rightNodeValue_ = super.rightNode.executeGeneric(frameValue);
        if ((state_0 & 0b111111) != 0 /* is SpecializationActive[DivideNode.doMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doSameMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoDivideNode)] || SpecializationActive[DivideNode.doOther(VirtualFrame, Object, Object, DoDivideNode)] */) {
            if ((state_0 & 0b1111) != 0 /* is SpecializationActive[DivideNode.doMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doSameMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoDivideNode)] */ && rightNodeValue_ instanceof Measure) {
                Measure rightNodeValue__ = (Measure) rightNodeValue_;
                if ((state_0 & 0b111) != 0 /* is SpecializationActive[DivideNode.doMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doSameMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] || SpecializationActive[DivideNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoDivideNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[DivideNode.doMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] */) {
                        if ((isScalar(rightNodeValue__.unit()))) {
                            return doMeasure(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_DIVIDE_NODE);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[DivideNode.doSameMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] */) {
                        if ((leftNodeValue__.unit() == rightNodeValue__.unit())) {
                            return doSameMeasure(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_DIVIDE_NODE);
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[DivideNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoDivideNode)] */) {
                        assert DSLSupport.assertIdempotence((isUntypedRegion));
                        return doUntypedMeasures(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_DIVIDE_NODE);
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[DivideNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoDivideNode)] */) {
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureRight(frameValue, leftNodeValue_, rightNodeValue__, INLINED_DO_DIVIDE_NODE);
                }
            }
            if ((state_0 & 0b110000) != 0 /* is SpecializationActive[DivideNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoDivideNode)] || SpecializationActive[DivideNode.doOther(VirtualFrame, Object, Object, DoDivideNode)] */) {
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[DivideNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoDivideNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureLeft(frameValue, leftNodeValue__, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
                }
                if ((state_0 & 0b100000) != 0 /* is SpecializationActive[DivideNode.doOther(VirtualFrame, Object, Object, DoDivideNode)] */) {
                    return doOther(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
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
                    state_0 = state_0 | 0b1 /* add SpecializationActive[DivideNode.doMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] */;
                    this.state_0_ = state_0;
                    return doMeasure(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
                }
                if ((leftNodeValue_.unit() == rightNodeValue_.unit())) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[DivideNode.doSameMeasure(VirtualFrame, Measure, Measure, DoDivideNode)] */;
                    this.state_0_ = state_0;
                    return doSameMeasure(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
                }
                if ((isUntypedRegion)) {
                    state_0 = state_0 | 0b100 /* add SpecializationActive[DivideNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoDivideNode)] */;
                    this.state_0_ = state_0;
                    return doUntypedMeasures(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
                }
            }
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b1000 /* add SpecializationActive[DivideNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoDivideNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureRight(frameValue, leftNodeValue, rightNodeValue_, INLINED_DO_DIVIDE_NODE);
            }
        }
        if (leftNodeValue instanceof Measure) {
            Measure leftNodeValue_ = (Measure) leftNodeValue;
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b10000 /* add SpecializationActive[DivideNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoDivideNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureLeft(frameValue, leftNodeValue_, rightNodeValue, INLINED_DO_DIVIDE_NODE);
            }
        }
        state_0 = state_0 | 0b100000 /* add SpecializationActive[DivideNode.doOther(VirtualFrame, Object, Object, DoDivideNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, leftNodeValue, rightNodeValue, INLINED_DO_DIVIDE_NODE);
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
    public static DivideNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        return new DivideNodeGen(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoDivideNode#doCreateRational}
     *     Activation probability: 0,09526
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoDivideNode#doCreateBigRational}
     *     Activation probability: 0,09053
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoDivideNode#doSmallRational}
     *     Activation probability: 0,08579
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoDivideNode#smallRationalLong}
     *     Activation probability: 0,08105
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#longSmallRational}
     *     Activation probability: 0,07632
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doRational}
     *     Activation probability: 0,07158
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#rationalBigNumber}
     *     Activation probability: 0,06684
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#bigNumberRational}
     *     Activation probability: 0,06211
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSmallSciNum}
     *     Activation probability: 0,05737
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSmallSciNumLong}
     *     Activation probability: 0,05263
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doLongSmallSciNum}
     *     Activation probability: 0,04789
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSmallRationalSmallSciNum}
     *     Activation probability: 0,04316
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSmallSciNumSmallRational}
     *     Activation probability: 0,03842
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSciNum}
     *     Activation probability: 0,03368
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doBigNumSciNum}
     *     Activation probability: 0,02895
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSciNumBigNum}
     *     Activation probability: 0,02421
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doRationalSciNum}
     *     Activation probability: 0,01947
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#doSciNumRational}
     *     Activation probability: 0,01474
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoDivideNode#typeError}
     *     Activation probability: 0,01000
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoDivideNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoDivideNodeGen extends DoDivideNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoDivideNode#doCreateRational}
         *   1: SpecializationActive {@link DoDivideNode#doCreateBigRational}
         *   2: SpecializationActive {@link DoDivideNode#doSmallRational}
         *   3: SpecializationExcluded {@link DoDivideNode#doSmallRational}
         *   4: SpecializationActive {@link DoDivideNode#doRational}
         *   5: SpecializationActive {@link DoDivideNode#smallRationalLong}
         *   6: SpecializationExcluded {@link DoDivideNode#smallRationalLong}
         *   7: SpecializationActive {@link DoDivideNode#rationalBigNumber}
         *   8: SpecializationActive {@link DoDivideNode#longSmallRational}
         *   9: SpecializationExcluded {@link DoDivideNode#longSmallRational}
         *   10: SpecializationActive {@link DoDivideNode#bigNumberRational}
         *   11: SpecializationActive {@link DoDivideNode#doSmallSciNum}
         *   12: SpecializationExcluded {@link DoDivideNode#doSmallSciNum}
         *   13: SpecializationActive {@link DoDivideNode#doSciNum}
         *   14: SpecializationActive {@link DoDivideNode#doSmallSciNumLong}
         *   15: SpecializationExcluded {@link DoDivideNode#doSmallSciNumLong}
         *   16: SpecializationActive {@link DoDivideNode#doSciNumBigNum}
         *   17: SpecializationActive {@link DoDivideNode#doLongSmallSciNum}
         *   18: SpecializationExcluded {@link DoDivideNode#doLongSmallSciNum}
         *   19: SpecializationActive {@link DoDivideNode#doBigNumSciNum}
         *   20: SpecializationActive {@link DoDivideNode#doSmallRationalSmallSciNum}
         *   21: SpecializationExcluded {@link DoDivideNode#doSmallRationalSmallSciNum}
         *   22: SpecializationActive {@link DoDivideNode#doRationalSciNum}
         *   23: SpecializationActive {@link DoDivideNode#doSmallSciNumSmallRational}
         *   24: SpecializationExcluded {@link DoDivideNode#doSmallSciNumSmallRational}
         *   25: SpecializationActive {@link DoDivideNode#doSciNumRational}
         *   26: SpecializationActive {@link DoDivideNode#typeError}
         *   27-28: ImplicitCast[type=BigNumber, index=1]
         *   29-30: ImplicitCast[type=BigNumber, index=2]
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

        private DoDivideNodeGen() {
        }

        @Override
        public Object executeDivide(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x6db6db7) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] || SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] || SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] || SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        return doCreateRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doCreateBigRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100100) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b10010000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] || SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b10000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doRational(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100100000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b1000000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0b1000000000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x120000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x40000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x100000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x200000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x800000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x1000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x82000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0x80000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x10000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x400000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x2000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */) {
                    return typeError(arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] */;
                    this.state_0_ = state_0;
                    return doCreateRational(arg1Value_, arg2Value_);
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b10 /* add SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] */;
                        this.state_0_ = state_0;
                        return doCreateBigRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (((state_0 & 0b10000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */ && ((state_0 & 0b1000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b100 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                        state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0b10000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b1000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b100000 /* add SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                        state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0b10000000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b1000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b100000000 /* add SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return longSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
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
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b10000 /* add SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doRational(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10000000 /* add SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */;
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
                        state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b10000000000 /* add SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b1000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b100000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        state_0 = state_0 | 0b1000000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x10000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0b1000000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    Long arg2Value_ = (Long) arg2Value;
                    state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_0 = state_0 | 0b1000000000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg2Value instanceof SmallSciNum) {
                SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                if (((state_0 & 0x80000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x40000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    state_0 = state_0 | 0x20000 /* add SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        state_0 = state_0 | 0x40000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x400000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x200000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0x100000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_0 = state_0 | 0x200000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0x2000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x1000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0x800000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_0 = state_0 | 0x1000000 /* add SpecializationExcluded  */;
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
                            state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x80000 /* add SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */;
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
                        state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0x10000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */;
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
                        state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                        state_0 = state_0 | 0x400000 /* add SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */;
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
                        state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0x2000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
            }
            state_0 = state_0 | 0x4000000 /* add SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */;
            this.state_0_ = state_0;
            return typeError(arg1Value, arg2Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0x6db6db7) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                int counter = 0;
                counter += Integer.bitCount((state_0 & 0x6db6db7));
                if (counter == 1) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static DoDivideNode create() {
            return new DoDivideNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * <li>{@link Inlined#state_1_}
         * </ul>
         */
        @NeverDefault
        public static DoDivideNode inline(@RequiredField(bits = 31, value = StateField.class)@RequiredField(bits = 8, value = StateField.class) InlineTarget target) {
            return new DoDivideNodeGen.Inlined(target);
        }

        @GeneratedBy(DoDivideNode.class)
        @DenyReplace
        private static final class Inlined extends DoDivideNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoDivideNode#doCreateRational}
             *   1: SpecializationActive {@link DoDivideNode#doCreateBigRational}
             *   2: SpecializationActive {@link DoDivideNode#doSmallRational}
             *   3: SpecializationExcluded {@link DoDivideNode#doSmallRational}
             *   4: SpecializationActive {@link DoDivideNode#doRational}
             *   5: SpecializationActive {@link DoDivideNode#smallRationalLong}
             *   6: SpecializationExcluded {@link DoDivideNode#smallRationalLong}
             *   7: SpecializationActive {@link DoDivideNode#rationalBigNumber}
             *   8: SpecializationActive {@link DoDivideNode#longSmallRational}
             *   9: SpecializationExcluded {@link DoDivideNode#longSmallRational}
             *   10: SpecializationActive {@link DoDivideNode#bigNumberRational}
             *   11: SpecializationActive {@link DoDivideNode#doSmallSciNum}
             *   12: SpecializationExcluded {@link DoDivideNode#doSmallSciNum}
             *   13: SpecializationActive {@link DoDivideNode#doSciNum}
             *   14: SpecializationActive {@link DoDivideNode#doSmallSciNumLong}
             *   15: SpecializationExcluded {@link DoDivideNode#doSmallSciNumLong}
             *   16: SpecializationActive {@link DoDivideNode#doSciNumBigNum}
             *   17: SpecializationActive {@link DoDivideNode#doLongSmallSciNum}
             *   18: SpecializationExcluded {@link DoDivideNode#doLongSmallSciNum}
             *   19: SpecializationActive {@link DoDivideNode#doBigNumSciNum}
             *   20: SpecializationActive {@link DoDivideNode#doSmallRationalSmallSciNum}
             *   21: SpecializationExcluded {@link DoDivideNode#doSmallRationalSmallSciNum}
             *   22: SpecializationActive {@link DoDivideNode#doRationalSciNum}
             *   23: SpecializationActive {@link DoDivideNode#doSmallSciNumSmallRational}
             *   24: SpecializationExcluded {@link DoDivideNode#doSmallSciNumSmallRational}
             *   25: SpecializationActive {@link DoDivideNode#doSciNumRational}
             *   26: SpecializationActive {@link DoDivideNode#typeError}
             *   27-28: ImplicitCast[type=BigNumber, index=1]
             *   29-30: ImplicitCast[type=BigNumber, index=2]
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
                assert target.getTargetClass().isAssignableFrom(DoDivideNode.class);
                this.state_0_ = target.getState(0, 31);
                this.state_1_ = target.getState(1, 8);
            }

            @Override
            public Object executeDivide(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0x6db6db7) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] || SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] || SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] || SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            return doCreateRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doCreateBigRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100100) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b100) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return smallRationalLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                                state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return longSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                                state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b10010000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] || SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b10000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doRational(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100100000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            try {
                                return doSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_0 = state_0 | 0b1000000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                            Long arg2Value_ = (Long) arg2Value;
                            try {
                                return doSmallSciNumLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                                state_0 = state_0 | 0b1000000000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x120000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        if ((state_0 & 0x20000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                            Long arg1Value_ = (Long) arg1Value;
                            try {
                                return doLongSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = state_0 | 0x40000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0x100000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                            SmallRational arg1Value_ = (SmallRational) arg1Value;
                            try {
                                return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                                state_0 = state_0 | 0x200000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x800000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                                state_0 = state_0 | 0x1000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x82000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] || SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0x80000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x18000000) >>> 27 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x10000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x60000000) >>> 29 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x400000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x2000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */) {
                        return typeError(arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                int state_1 = this.state_1_.get(arg0Value);
                if (arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[DivideNode.DoDivideNode.doCreateRational(long, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doCreateRational(arg1Value_, arg2Value_);
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10 /* add SpecializationActive[DivideNode.DoDivideNode.doCreateBigRational(BigNumber, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doCreateBigRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (((state_0 & 0b10000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */ && ((state_0 & 0b1000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b100 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0b10000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b1000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b100000 /* add SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0b10000000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b1000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b100000000 /* add SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
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
                                state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b10000 /* add SpecializationActive[DivideNode.DoDivideNode.doRational(Rational, Rational)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doRational(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[DivideNode.DoDivideNode.smallRationalLong(SmallRational, long)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b10000000 /* add SpecializationActive[DivideNode.DoDivideNode.rationalBigNumber(Rational, BigNumber)] */;
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
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[DivideNode.DoDivideNode.longSmallRational(long, SmallRational)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b10000000000 /* add SpecializationActive[DivideNode.DoDivideNode.bigNumberRational(BigNumber, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b1000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b100000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b1000000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x10000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0b1000000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0b1000000000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if (((state_0 & 0x80000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x40000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        state_0 = state_0 | 0x20000 /* add SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x40000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x400000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x200000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        state_0 = state_0 | 0x100000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x200000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0x2000000)) == 0 /* is-not SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x1000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0x800000 /* add SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x1000000 /* add SpecializationExcluded  */;
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
                                state_0 = state_0 & 0xfffff7ff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNum(SciNum, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = state_0 & 0xfffdffff /* remove SpecializationActive[DivideNode.DoDivideNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = (state_0 | (bigNumberCast1 << 27) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0x80000 /* add SpecializationActive[DivideNode.DoDivideNode.doBigNumSciNum(BigNumber, SciNum)] */;
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
                            state_0 = state_0 & 0xffffbfff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 29) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0x10000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNumBigNum(SciNum, BigNumber)] */;
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
                            state_0 = state_0 & 0xffefffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x400000 /* add SpecializationActive[DivideNode.DoDivideNode.doRationalSciNum(Rational, SciNum)] */;
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
                            state_0 = state_0 & 0xff7fffff /* remove SpecializationActive[DivideNode.DoDivideNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0x2000000 /* add SpecializationActive[DivideNode.DoDivideNode.doSciNumRational(SciNum, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                state_0 = state_0 | 0x4000000 /* add SpecializationActive[DivideNode.DoDivideNode.typeError(Object, Object)] */;
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
