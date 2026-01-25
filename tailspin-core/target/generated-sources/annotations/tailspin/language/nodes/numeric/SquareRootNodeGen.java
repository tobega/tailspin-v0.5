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
 *   Specialization {@link SquareRootNode#doUntypedMeasure}
 *     Activation probability: 0,65000
 *     With/without class size: 14/0 bytes
 *   Specialization {@link SquareRootNode#doOther}
 *     Activation probability: 0,35000
 *     With/without class size: 9/0 bytes
 * </pre>
 */
@GeneratedBy(SquareRootNode.class)
@SuppressWarnings("javadoc")
public final class SquareRootNodeGen extends SquareRootNode {

    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link SquareRootNode#doUntypedMeasure}
     *   Parameter: {@link DoSquareRootNode} doSquareRootNode
     *   Inline method: {@link DoSquareRootNodeGen#inline}</pre>
     */
    private static final DoSquareRootNode INLINED_DO_SQUARE_ROOT_NODE = DoSquareRootNodeGen.inline(InlineTarget.create(DoSquareRootNode.class, STATE_0_UPDATER.subUpdater(2, 16)));

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link SquareRootNode#doUntypedMeasure}
     *   1: SpecializationActive {@link SquareRootNode#doOther}
     *   2-17: InlinedCache
     *        Specialization: {@link SquareRootNode#doUntypedMeasure}
     *        Parameter: {@link DoSquareRootNode} doSquareRootNode
     *        Inline method: {@link DoSquareRootNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private SquareRootNodeGen(ValueNode squareNode, boolean isUntypedRegion, SourceSection sourceSection) {
        super(squareNode, isUntypedRegion, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object squareNodeValue_ = super.squareNode.executeGeneric(frameValue);
        if ((state_0 & 0b11) != 0 /* is SpecializationActive[SquareRootNode.doUntypedMeasure(VirtualFrame, Measure, DoSquareRootNode)] || SpecializationActive[SquareRootNode.doOther(VirtualFrame, Object, DoSquareRootNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[SquareRootNode.doUntypedMeasure(VirtualFrame, Measure, DoSquareRootNode)] */ && squareNodeValue_ instanceof Measure) {
                Measure squareNodeValue__ = (Measure) squareNodeValue_;
                assert DSLSupport.assertIdempotence((isUntypedRegion));
                return doUntypedMeasure(frameValue, squareNodeValue__, INLINED_DO_SQUARE_ROOT_NODE);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[SquareRootNode.doOther(VirtualFrame, Object, DoSquareRootNode)] */) {
                return doOther(frameValue, squareNodeValue_, INLINED_DO_SQUARE_ROOT_NODE);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, squareNodeValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object squareNodeValue) {
        int state_0 = this.state_0_;
        if (squareNodeValue instanceof Measure) {
            Measure squareNodeValue_ = (Measure) squareNodeValue;
            if ((isUntypedRegion)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[SquareRootNode.doUntypedMeasure(VirtualFrame, Measure, DoSquareRootNode)] */;
                this.state_0_ = state_0;
                return doUntypedMeasure(frameValue, squareNodeValue_, INLINED_DO_SQUARE_ROOT_NODE);
            }
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[SquareRootNode.doOther(VirtualFrame, Object, DoSquareRootNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, squareNodeValue, INLINED_DO_SQUARE_ROOT_NODE);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b11) & ((state_0 & 0b11) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static SquareRootNode create(ValueNode squareNode, boolean isUntypedRegion, SourceSection sourceSection) {
        return new SquareRootNodeGen(squareNode, isUntypedRegion, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoSquareRootNode#doLong}
     *     Activation probability: 0,23929
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoSquareRootNode#doBigNumber}
     *     Activation probability: 0,20714
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoSquareRootNode#doSmallRational}
     *     Activation probability: 0,17500
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoSquareRootNode#doRational}
     *     Activation probability: 0,14286
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoSquareRootNode#doSmallSciNum}
     *     Activation probability: 0,11071
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoSquareRootNode#doSciNum}
     *     Activation probability: 0,07857
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoSquareRootNode#typeError}
     *     Activation probability: 0,04643
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoSquareRootNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoSquareRootNodeGen extends DoSquareRootNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoSquareRootNode#doLong}
         *   1: SpecializationExcluded {@link DoSquareRootNode#doLong}
         *   2: SpecializationActive {@link DoSquareRootNode#doBigNumber}
         *   3: SpecializationActive {@link DoSquareRootNode#doSmallRational}
         *   4: SpecializationExcluded {@link DoSquareRootNode#doSmallRational}
         *   5: SpecializationActive {@link DoSquareRootNode#doRational}
         *   6: SpecializationActive {@link DoSquareRootNode#doSmallSciNum}
         *   7: SpecializationExcluded {@link DoSquareRootNode#doSmallSciNum}
         *   8: SpecializationActive {@link DoSquareRootNode#doSciNum}
         *   9: SpecializationActive {@link DoSquareRootNode#typeError}
         *   10-11: ImplicitCast[type=BigNumber, index=1]
         *   12-13: ImplicitCast[type=Rational, index=1]
         *   14-15: ImplicitCast[type=SciNum, index=1]
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private DoSquareRootNodeGen() {
        }

        @Override
        public Object executeSquareRoot(VirtualFrame frameValue, Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0b1101101101) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] || SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    try {
                        return doLong(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    return doBigNumber(arg1Value_);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    try {
                        return doSmallRational(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    return doRational(arg1Value_);
                }
                if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    try {
                        return doSmallSciNum(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    return doSciNum(arg1Value_);
                }
                if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */) {
                    return typeError(arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                state_0 = state_0 | 0b1 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                this.state_0_ = state_0;
                try {
                    return doLong(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                    state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                    state_0 = (state_0 | (bigNumberCast1 << 10) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                    state_0 = state_0 | 0b100 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */;
                    this.state_0_ = state_0;
                    return doBigNumber(arg1Value_);
                }
            }
            if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                state_0 = state_0 | 0b1000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                this.state_0_ = state_0;
                try {
                    return doSmallRational(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                    state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                    state_0 = (state_0 | (rationalCast1 << 12) /* set-int ImplicitCast[type=Rational, index=1] */);
                    state_0 = state_0 | 0b100000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */;
                    this.state_0_ = state_0;
                    return doRational(arg1Value_);
                }
            }
            if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                state_0 = state_0 | 0b1000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                this.state_0_ = state_0;
                try {
                    return doSmallSciNum(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                    state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                    state_0 = (state_0 | (sciNumCast1 << 14) /* set-int ImplicitCast[type=SciNum, index=1] */);
                    state_0 = state_0 | 0b100000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */;
                    this.state_0_ = state_0;
                    return doSciNum(arg1Value_);
                }
            }
            state_0 = state_0 | 0b1000000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */;
            this.state_0_ = state_0;
            return typeError(arg1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0b1101101101) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                if (((state_0 & 0b1101101101) & ((state_0 & 0b1101101101) - 1)) == 0 /* is-single  */) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static DoSquareRootNode create() {
            return new DoSquareRootNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * </ul>
         */
        @NeverDefault
        public static DoSquareRootNode inline(@RequiredField(bits = 16, value = StateField.class) InlineTarget target) {
            return new DoSquareRootNodeGen.Inlined(target);
        }

        @GeneratedBy(DoSquareRootNode.class)
        @DenyReplace
        private static final class Inlined extends DoSquareRootNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoSquareRootNode#doLong}
             *   1: SpecializationExcluded {@link DoSquareRootNode#doLong}
             *   2: SpecializationActive {@link DoSquareRootNode#doBigNumber}
             *   3: SpecializationActive {@link DoSquareRootNode#doSmallRational}
             *   4: SpecializationExcluded {@link DoSquareRootNode#doSmallRational}
             *   5: SpecializationActive {@link DoSquareRootNode#doRational}
             *   6: SpecializationActive {@link DoSquareRootNode#doSmallSciNum}
             *   7: SpecializationExcluded {@link DoSquareRootNode#doSmallSciNum}
             *   8: SpecializationActive {@link DoSquareRootNode#doSciNum}
             *   9: SpecializationActive {@link DoSquareRootNode#typeError}
             *   10-11: ImplicitCast[type=BigNumber, index=1]
             *   12-13: ImplicitCast[type=Rational, index=1]
             *   14-15: ImplicitCast[type=SciNum, index=1]
             * </pre>
             */
            private final StateField state_0_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(DoSquareRootNode.class);
                this.state_0_ = target.getState(0, 16);
            }

            @Override
            public Object executeSquareRoot(VirtualFrame frameValue, Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0b1101101101) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] || SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] || SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        try {
                            return doLong(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumber(arg1Value_);
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRational(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        return doRational(arg1Value_);
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        try {
                            return doSmallSciNum(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_);
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */) {
                        return typeError(arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doLong(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doLong(long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 10) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = state_0 | 0b100 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doBigNumber(BigNumber)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doBigNumber(arg1Value_);
                    }
                }
                if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doSmallRational(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallRational(SmallRational)] */;
                        state_0 = (state_0 | (rationalCast1 << 12) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_0 = state_0 | 0b100000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doRational(Rational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doRational(arg1Value_);
                    }
                }
                if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doSmallSciNum(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[SquareRootNode.DoSquareRootNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = (state_0 | (sciNumCast1 << 14) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = state_0 | 0b100000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.doSciNum(SciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doSciNum(arg1Value_);
                    }
                }
                state_0 = state_0 | 0b1000000000 /* add SpecializationActive[SquareRootNode.DoSquareRootNode.typeError(Object)] */;
                this.state_0_.set(arg0Value, state_0);
                return typeError(arg1Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
