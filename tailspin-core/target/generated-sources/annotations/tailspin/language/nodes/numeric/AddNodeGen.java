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
 *   Specialization {@link AddNode#doMeasure}
 *     Activation probability: 0,32000
 *     With/without class size: 9/0 bytes
 *   Specialization {@link AddNode#doUntypedMeasures}
 *     Activation probability: 0,26000
 *     With/without class size: 8/0 bytes
 *   Specialization {@link AddNode#doUntypedMeasureRight}
 *     Activation probability: 0,20000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link AddNode#doUntypedMeasureLeft}
 *     Activation probability: 0,14000
 *     With/without class size: 6/0 bytes
 *   Specialization {@link AddNode#doOther}
 *     Activation probability: 0,08000
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(AddNode.class)
@SuppressWarnings("javadoc")
public final class AddNodeGen extends AddNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link AddNode#doMeasure}
     *   Parameter: {@link DoAddNode} doAddNode
     *   Inline method: {@link DoAddNodeGen#inline}</pre>
     */
    private static final DoAddNode INLINED_DO_ADD_NODE = DoAddNodeGen.inline(InlineTarget.create(DoAddNode.class, STATE_1_UPDATER.subUpdater(0, 32), STATE_0_UPDATER.subUpdater(5, 8)));

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link AddNode#doMeasure}
     *   1: SpecializationActive {@link AddNode#doUntypedMeasures}
     *   2: SpecializationActive {@link AddNode#doUntypedMeasureRight}
     *   3: SpecializationActive {@link AddNode#doUntypedMeasureLeft}
     *   4: SpecializationActive {@link AddNode#doOther}
     *   5-12: InlinedCache
     *        Specialization: {@link AddNode#doMeasure}
     *        Parameter: {@link DoAddNode} doAddNode
     *        Inline method: {@link DoAddNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * State Info: <pre>
     *   0-31: InlinedCache
     *        Specialization: {@link AddNode#doMeasure}
     *        Parameter: {@link DoAddNode} doAddNode
     *        Inline method: {@link DoAddNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;

    private AddNodeGen(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        super(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    @Override
    public Object executeAdd(VirtualFrame frameValue, Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] || SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] */ && rightNodeValue instanceof Measure) {
                Measure rightNodeValue_ = (Measure) rightNodeValue;
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] */ && leftNodeValue instanceof Measure) {
                    Measure leftNodeValue_ = (Measure) leftNodeValue;
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] */) {
                        if ((leftNodeValue_.unit() == rightNodeValue_.unit())) {
                            return doMeasure(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_ADD_NODE);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] */) {
                        assert DSLSupport.assertIdempotence((isUntypedRegion));
                        return doUntypedMeasures(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_ADD_NODE);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] */) {
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureRight(frameValue, leftNodeValue, rightNodeValue_, INLINED_DO_ADD_NODE);
                }
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] || SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] */ && leftNodeValue instanceof Measure) {
                    Measure leftNodeValue_ = (Measure) leftNodeValue;
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureLeft(frameValue, leftNodeValue_, rightNodeValue, INLINED_DO_ADD_NODE);
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
                    return doOther(frameValue, leftNodeValue, rightNodeValue, INLINED_DO_ADD_NODE);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, leftNodeValue, rightNodeValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = super.leftNode.executeGeneric(frameValue);
        Object rightNodeValue_ = super.rightNode.executeGeneric(frameValue);
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] || SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] */ && rightNodeValue_ instanceof Measure) {
                Measure rightNodeValue__ = (Measure) rightNodeValue_;
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] || SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] */) {
                        if ((leftNodeValue__.unit() == rightNodeValue__.unit())) {
                            return doMeasure(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_ADD_NODE);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] */) {
                        assert DSLSupport.assertIdempotence((isUntypedRegion));
                        return doUntypedMeasures(frameValue, leftNodeValue__, rightNodeValue__, INLINED_DO_ADD_NODE);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] */) {
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureRight(frameValue, leftNodeValue_, rightNodeValue__, INLINED_DO_ADD_NODE);
                }
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] || SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] */ && leftNodeValue_ instanceof Measure) {
                    Measure leftNodeValue__ = (Measure) leftNodeValue_;
                    assert DSLSupport.assertIdempotence((isUntypedRegion));
                    return doUntypedMeasureLeft(frameValue, leftNodeValue__, rightNodeValue_, INLINED_DO_ADD_NODE);
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */) {
                    return doOther(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_ADD_NODE);
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
                    state_0 = state_0 | 0b1 /* add SpecializationActive[AddNode.doMeasure(VirtualFrame, Measure, Measure, DoAddNode)] */;
                    this.state_0_ = state_0;
                    return doMeasure(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_ADD_NODE);
                }
                if ((isUntypedRegion)) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[AddNode.doUntypedMeasures(VirtualFrame, Measure, Measure, DoAddNode)] */;
                    this.state_0_ = state_0;
                    return doUntypedMeasures(frameValue, leftNodeValue_, rightNodeValue_, INLINED_DO_ADD_NODE);
                }
            }
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b100 /* add SpecializationActive[AddNode.doUntypedMeasureRight(VirtualFrame, Object, Measure, DoAddNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureRight(frameValue, leftNodeValue, rightNodeValue_, INLINED_DO_ADD_NODE);
            }
        }
        if (leftNodeValue instanceof Measure) {
            Measure leftNodeValue_ = (Measure) leftNodeValue;
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b1000 /* add SpecializationActive[AddNode.doUntypedMeasureLeft(VirtualFrame, Measure, Object, DoAddNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasureLeft(frameValue, leftNodeValue_, rightNodeValue, INLINED_DO_ADD_NODE);
            }
        }
        state_0 = state_0 | 0b10000 /* add SpecializationActive[AddNode.doOther(VirtualFrame, Object, Object, DoAddNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, leftNodeValue, rightNodeValue, INLINED_DO_ADD_NODE);
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
    public static AddNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
        return new AddNodeGen(leftNode, rightNode, isUntypedRegion, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoAddNode#doLong}
     *     Activation probability: 0,09526
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoAddNode#doBigNumber}
     *     Activation probability: 0,09053
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoAddNode#doSmallRational}
     *     Activation probability: 0,08579
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoAddNode#smallRationalLong}
     *     Activation probability: 0,08105
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#longSmallRational}
     *     Activation probability: 0,07632
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doRational}
     *     Activation probability: 0,07158
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#rationalBigNumber}
     *     Activation probability: 0,06684
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#bigNumberRational}
     *     Activation probability: 0,06211
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSmallSciNum}
     *     Activation probability: 0,05737
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSmallSciNumLong}
     *     Activation probability: 0,05263
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doLongSmallSciNum}
     *     Activation probability: 0,04789
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSmallRationalSmallSciNum}
     *     Activation probability: 0,04316
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSmallSciNumSmallRational}
     *     Activation probability: 0,03842
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSciNum}
     *     Activation probability: 0,03368
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doBigNumSciNum}
     *     Activation probability: 0,02895
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSciNumBigNum}
     *     Activation probability: 0,02421
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doRationalSciNum}
     *     Activation probability: 0,01947
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#doSciNumRational}
     *     Activation probability: 0,01474
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoAddNode#typeError}
     *     Activation probability: 0,01000
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoAddNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoAddNodeGen extends DoAddNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoAddNode#doLong}
         *   1: SpecializationExcluded {@link DoAddNode#doLong}
         *   2: SpecializationActive {@link DoAddNode#doBigNumber}
         *   3: SpecializationActive {@link DoAddNode#doSmallRational}
         *   4: SpecializationExcluded {@link DoAddNode#doSmallRational}
         *   5: SpecializationActive {@link DoAddNode#doRational}
         *   6: SpecializationActive {@link DoAddNode#smallRationalLong}
         *   7: SpecializationExcluded {@link DoAddNode#smallRationalLong}
         *   8: SpecializationActive {@link DoAddNode#rationalBigNumber}
         *   9: SpecializationActive {@link DoAddNode#longSmallRational}
         *   10: SpecializationExcluded {@link DoAddNode#longSmallRational}
         *   11: SpecializationActive {@link DoAddNode#bigNumberRational}
         *   12: SpecializationActive {@link DoAddNode#doSmallSciNum}
         *   13: SpecializationExcluded {@link DoAddNode#doSmallSciNum}
         *   14: SpecializationActive {@link DoAddNode#doSciNum}
         *   15: SpecializationActive {@link DoAddNode#doSmallSciNumLong}
         *   16: SpecializationExcluded {@link DoAddNode#doSmallSciNumLong}
         *   17: SpecializationActive {@link DoAddNode#doSciNumBigNum}
         *   18: SpecializationActive {@link DoAddNode#doLongSmallSciNum}
         *   19: SpecializationExcluded {@link DoAddNode#doLongSmallSciNum}
         *   20: SpecializationActive {@link DoAddNode#doBigNumSciNum}
         *   21: SpecializationActive {@link DoAddNode#doSmallRationalSmallSciNum}
         *   22: SpecializationExcluded {@link DoAddNode#doSmallRationalSmallSciNum}
         *   23: SpecializationActive {@link DoAddNode#doRationalSciNum}
         *   24: SpecializationActive {@link DoAddNode#doSmallSciNumSmallRational}
         *   25: SpecializationExcluded {@link DoAddNode#doSmallSciNumSmallRational}
         *   26: SpecializationActive {@link DoAddNode#doSciNumRational}
         *   27: SpecializationActive {@link DoAddNode#typeError}
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

        private DoAddNodeGen() {
        }

        @Override
        public Object executeAdd(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLong(long, long)] || SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] || SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] || SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] || SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] || SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                            state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] || SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doRational(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x240000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if ((state_0 & 0x40000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x200000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if ((state_0 & 0x104000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] || SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0x100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x20000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x800000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_ & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_ & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */) {
                    return typeError(arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
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
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b100 /* add SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */;
                        this.state_0_ = state_0;
                        return doBigNumber(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b1000000000 /* add SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return longSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
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
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000 /* add SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doRational(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100000000 /* add SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */;
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
                        state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b100000000000 /* add SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    Long arg2Value_ = (Long) arg2Value;
                    state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg2Value instanceof SmallSciNum) {
                SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    state_0 = state_0 | 0x40000 /* add SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
                if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0x200000 /* add SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                    }
                }
            }
            if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0x1000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                    this.state_0_ = state_0;
                    try {
                        return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
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
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x100000 /* add SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */;
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
                        state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0x20000 /* add SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */;
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
                        state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                        state_0 = state_0 | 0x800000 /* add SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */;
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
                        state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0x4000000 /* add SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
            }
            state_0 = state_0 | 0x8000000 /* add SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */;
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
        public static DoAddNode create() {
            return new DoAddNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * <li>{@link Inlined#state_1_}
         * </ul>
         */
        @NeverDefault
        public static DoAddNode inline(@RequiredField(bits = 32, value = StateField.class)@RequiredField(bits = 8, value = StateField.class) InlineTarget target) {
            return new DoAddNodeGen.Inlined(target);
        }

        @GeneratedBy(DoAddNode.class)
        @DenyReplace
        private static final class Inlined extends DoAddNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoAddNode#doLong}
             *   1: SpecializationExcluded {@link DoAddNode#doLong}
             *   2: SpecializationActive {@link DoAddNode#doBigNumber}
             *   3: SpecializationActive {@link DoAddNode#doSmallRational}
             *   4: SpecializationExcluded {@link DoAddNode#doSmallRational}
             *   5: SpecializationActive {@link DoAddNode#doRational}
             *   6: SpecializationActive {@link DoAddNode#smallRationalLong}
             *   7: SpecializationExcluded {@link DoAddNode#smallRationalLong}
             *   8: SpecializationActive {@link DoAddNode#rationalBigNumber}
             *   9: SpecializationActive {@link DoAddNode#longSmallRational}
             *   10: SpecializationExcluded {@link DoAddNode#longSmallRational}
             *   11: SpecializationActive {@link DoAddNode#bigNumberRational}
             *   12: SpecializationActive {@link DoAddNode#doSmallSciNum}
             *   13: SpecializationExcluded {@link DoAddNode#doSmallSciNum}
             *   14: SpecializationActive {@link DoAddNode#doSciNum}
             *   15: SpecializationActive {@link DoAddNode#doSmallSciNumLong}
             *   16: SpecializationExcluded {@link DoAddNode#doSmallSciNumLong}
             *   17: SpecializationActive {@link DoAddNode#doSciNumBigNum}
             *   18: SpecializationActive {@link DoAddNode#doLongSmallSciNum}
             *   19: SpecializationExcluded {@link DoAddNode#doLongSmallSciNum}
             *   20: SpecializationActive {@link DoAddNode#doBigNumSciNum}
             *   21: SpecializationActive {@link DoAddNode#doSmallRationalSmallSciNum}
             *   22: SpecializationExcluded {@link DoAddNode#doSmallRationalSmallSciNum}
             *   23: SpecializationActive {@link DoAddNode#doRationalSciNum}
             *   24: SpecializationActive {@link DoAddNode#doSmallSciNumSmallRational}
             *   25: SpecializationExcluded {@link DoAddNode#doSmallSciNumSmallRational}
             *   26: SpecializationActive {@link DoAddNode#doSciNumRational}
             *   27: SpecializationActive {@link DoAddNode#typeError}
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
                assert target.getTargetClass().isAssignableFrom(DoAddNode.class);
                this.state_0_ = target.getState(0, 32);
                this.state_1_ = target.getState(1, 8);
            }

            @Override
            public Object executeAdd(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0xdb6db6d) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLong(long, long)] || SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] || SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] || SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] || SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] || SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] || SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] || SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return doLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                                state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] || SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return smallRationalLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                                state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return longSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                                state_0 = state_0 | 0b10000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0b100100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] || SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doRational(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1001000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            try {
                                return doSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                            Long arg2Value_ = (Long) arg2Value;
                            try {
                                return doSmallSciNumLong(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                                state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x240000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        if ((state_0 & 0x40000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                            Long arg1Value_ = (Long) arg1Value;
                            try {
                                return doLongSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                        if ((state_0 & 0x200000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */ && arg1Value instanceof SmallRational) {
                            SmallRational arg1Value_ = (SmallRational) arg1Value;
                            try {
                                return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                                state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x1000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                                state_0 = state_0 | 0x2000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                            }
                        }
                    }
                    if ((state_0 & 0x104000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] || SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0x100000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x800000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b11) >>> 0 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b11000000) >>> 6 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x4000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_1_.get(arg0Value) & 0b110000) >>> 4 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_1_.get(arg0Value) & 0b1100) >>> 2 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x8000000) != 0 /* is SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */) {
                        return typeError(arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                int state_1 = this.state_1_.get(arg0Value);
                if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
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
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[AddNode.DoAddNode.doLong(long, long)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b100 /* add SpecializationActive[AddNode.DoAddNode.doBigNumber(BigNumber, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000 /* add SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1000000 /* add SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0b100000000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */ && ((state_0 & 0b10000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return longSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
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
                                state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[AddNode.DoAddNode.doSmallRational(SmallRational, SmallRational)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b100000 /* add SpecializationActive[AddNode.DoAddNode.doRational(Rational, Rational)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doRational(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[AddNode.DoAddNode.smallRationalLong(SmallRational, long)] */;
                                state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b100000000 /* add SpecializationActive[AddNode.DoAddNode.rationalBigNumber(Rational, BigNumber)] */;
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
                            state_0 = state_0 & 0xfffffdff /* remove SpecializationActive[AddNode.DoAddNode.longSmallRational(long, SmallRational)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000000000 /* add SpecializationActive[AddNode.DoAddNode.bigNumberRational(BigNumber, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (((state_0 & 0b100000000000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */ && ((state_0 & 0b10000000000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x20000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */ && ((state_0 & 0x10000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_0 = state_0 | 0x10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    if (((state_0 & 0x100000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */ && ((state_0 & 0x80000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        state_0 = state_0 | 0x40000 /* add SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                            state_0 = state_0 | 0x80000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                    if (((state_0 & 0x800000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */ && ((state_0 & 0x400000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        state_0 = state_0 | 0x200000 /* add SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallRationalSmallSciNum(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_0 = state_0 | 0x400000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_);
                        }
                    }
                }
                if (((state_0 & 0x4000000)) == 0 /* is-not SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */ && ((state_0 & 0x2000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0x1000000 /* add SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return doSmallSciNumSmallRational(arg1Value_, arg2Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
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
                                state_0 = state_0 & 0xffffefff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                                state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[AddNode.DoAddNode.doSciNum(SciNum, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = state_0 & 0xfffbffff /* remove SpecializationActive[AddNode.DoAddNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                                state_0 = (state_0 | (bigNumberCast1 << 28) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0x100000 /* add SpecializationActive[AddNode.DoAddNode.doBigNumSciNum(BigNumber, SciNum)] */;
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
                            state_0 = state_0 & 0xffff7fff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 30) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0x20000 /* add SpecializationActive[AddNode.DoAddNode.doSciNumBigNum(SciNum, BigNumber)] */;
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
                            state_0 = state_0 & 0xffdfffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallRationalSmallSciNum(SmallRational, SmallSciNum)] */;
                            state_1 = (state_1 | (rationalCast1 << 0) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 6) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x800000 /* add SpecializationActive[AddNode.DoAddNode.doRationalSciNum(Rational, SciNum)] */;
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
                            state_0 = state_0 & 0xfeffffff /* remove SpecializationActive[AddNode.DoAddNode.doSmallSciNumSmallRational(SmallSciNum, SmallRational)] */;
                            state_1 = (state_1 | (sciNumCast1 << 4) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (rationalCast2 << 2) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0x4000000 /* add SpecializationActive[AddNode.DoAddNode.doSciNumRational(SciNum, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                state_0 = state_0 | 0x8000000 /* add SpecializationActive[AddNode.DoAddNode.typeError(Object, Object)] */;
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
