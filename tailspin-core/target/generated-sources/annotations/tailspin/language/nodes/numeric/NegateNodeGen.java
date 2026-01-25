// CheckStyle: start generated
package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
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
 *   Specialization {@link NegateNode#doMeasure}
 *     Activation probability: 0,65000
 *     With/without class size: 14/0 bytes
 *   Specialization {@link NegateNode#doUntyped}
 *     Activation probability: 0,35000
 *     With/without class size: 9/0 bytes
 * </pre>
 */
@GeneratedBy(NegateNode.class)
@SuppressWarnings("javadoc")
public final class NegateNodeGen extends NegateNode {

    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link NegateNode#doMeasure}
     *   Parameter: {@link DoNegateNode} doNegateNode
     *   Inline method: {@link DoNegateNodeGen#inline}</pre>
     */
    private static final DoNegateNode INLINED_DO_NEGATE_NODE = DoNegateNodeGen.inline(InlineTarget.create(DoNegateNode.class, STATE_0_UPDATER.subUpdater(2, 16)));

    @Child private ValueNode child0_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link NegateNode#doMeasure}
     *   1: SpecializationActive {@link NegateNode#doUntyped}
     *   2-17: InlinedCache
     *        Specialization: {@link NegateNode#doMeasure}
     *        Parameter: {@link DoNegateNode} doNegateNode
     *        Inline method: {@link DoNegateNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private NegateNodeGen(SourceSection sourceSection, ValueNode child0) {
        super(sourceSection);
        this.child0_ = child0;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object child0Value_ = this.child0_.executeGeneric(frameValue);
        if ((state_0 & 0b11) != 0 /* is SpecializationActive[NegateNode.doMeasure(VirtualFrame, Measure, DoNegateNode)] || SpecializationActive[NegateNode.doUntyped(VirtualFrame, Object, DoNegateNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[NegateNode.doMeasure(VirtualFrame, Measure, DoNegateNode)] */ && child0Value_ instanceof Measure) {
                Measure child0Value__ = (Measure) child0Value_;
                return doMeasure(frameValue, child0Value__, INLINED_DO_NEGATE_NODE);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[NegateNode.doUntyped(VirtualFrame, Object, DoNegateNode)] */) {
                return doUntyped(frameValue, child0Value_, INLINED_DO_NEGATE_NODE);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, child0Value_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object child0Value) {
        int state_0 = this.state_0_;
        if (child0Value instanceof Measure) {
            Measure child0Value_ = (Measure) child0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[NegateNode.doMeasure(VirtualFrame, Measure, DoNegateNode)] */;
            this.state_0_ = state_0;
            return doMeasure(frameValue, child0Value_, INLINED_DO_NEGATE_NODE);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[NegateNode.doUntyped(VirtualFrame, Object, DoNegateNode)] */;
        this.state_0_ = state_0;
        return doUntyped(frameValue, child0Value, INLINED_DO_NEGATE_NODE);
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
    public static NegateNode create(SourceSection sourceSection, ValueNode child0) {
        return new NegateNodeGen(sourceSection, child0);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoNegateNode#doLong}
     *     Activation probability: 0,23929
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoNegateNode#doBigNumber}
     *     Activation probability: 0,20714
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoNegateNode#doSmallRational}
     *     Activation probability: 0,17500
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoNegateNode#doRational}
     *     Activation probability: 0,14286
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoNegateNode#doSmallSciNum}
     *     Activation probability: 0,11071
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoNegateNode#doSciNum}
     *     Activation probability: 0,07857
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoNegateNode#typeError}
     *     Activation probability: 0,04643
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoNegateNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoNegateNodeGen extends DoNegateNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoNegateNode#doLong}
         *   1: SpecializationExcluded {@link DoNegateNode#doLong}
         *   2: SpecializationActive {@link DoNegateNode#doBigNumber}
         *   3: SpecializationActive {@link DoNegateNode#doSmallRational}
         *   4: SpecializationExcluded {@link DoNegateNode#doSmallRational}
         *   5: SpecializationActive {@link DoNegateNode#doRational}
         *   6: SpecializationActive {@link DoNegateNode#doSmallSciNum}
         *   7: SpecializationExcluded {@link DoNegateNode#doSmallSciNum}
         *   8: SpecializationActive {@link DoNegateNode#doSciNum}
         *   9: SpecializationActive {@link DoNegateNode#typeError}
         *   10-11: ImplicitCast[type=BigNumber, index=1]
         *   12-13: ImplicitCast[type=Rational, index=1]
         *   14-15: ImplicitCast[type=SciNum, index=1]
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private DoNegateNodeGen() {
        }

        @Override
        public Object executeNegate(VirtualFrame frameValue, Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0b1101101101) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doLong(long)] || SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] || SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] || SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] || SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] || SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] || SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    try {
                        return doLong(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    return doBigNumber(arg1Value_);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    try {
                        return doSmallRational(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    return doRational(arg1Value_);
                }
                if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    try {
                        return doSmallSciNum(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    return doSciNum(arg1Value_);
                }
                if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */) {
                    return typeError(arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                state_0 = state_0 | 0b1 /* add SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                this.state_0_ = state_0;
                try {
                    return doLong(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                    state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                    state_0 = (state_0 | (bigNumberCast1 << 10) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                    state_0 = state_0 | 0b100 /* add SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */;
                    this.state_0_ = state_0;
                    return doBigNumber(arg1Value_);
                }
            }
            if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                state_0 = state_0 | 0b1000 /* add SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                this.state_0_ = state_0;
                try {
                    return doSmallRational(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                    state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                    state_0 = (state_0 | (rationalCast1 << 12) /* set-int ImplicitCast[type=Rational, index=1] */);
                    state_0 = state_0 | 0b100000 /* add SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */;
                    this.state_0_ = state_0;
                    return doRational(arg1Value_);
                }
            }
            if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                state_0 = state_0 | 0b1000000 /* add SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                this.state_0_ = state_0;
                try {
                    return doSmallSciNum(arg1Value_);
                } catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                    state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return executeAndSpecialize(arg0Value, arg1Value_);
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                    state_0 = (state_0 | (sciNumCast1 << 14) /* set-int ImplicitCast[type=SciNum, index=1] */);
                    state_0 = state_0 | 0b100000000 /* add SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */;
                    this.state_0_ = state_0;
                    return doSciNum(arg1Value_);
                }
            }
            state_0 = state_0 | 0b1000000000 /* add SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */;
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
        public static DoNegateNode create() {
            return new DoNegateNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * </ul>
         */
        @NeverDefault
        public static DoNegateNode inline(@RequiredField(bits = 16, value = StateField.class) InlineTarget target) {
            return new DoNegateNodeGen.Inlined(target);
        }

        @GeneratedBy(DoNegateNode.class)
        @DenyReplace
        private static final class Inlined extends DoNegateNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoNegateNode#doLong}
             *   1: SpecializationExcluded {@link DoNegateNode#doLong}
             *   2: SpecializationActive {@link DoNegateNode#doBigNumber}
             *   3: SpecializationActive {@link DoNegateNode#doSmallRational}
             *   4: SpecializationExcluded {@link DoNegateNode#doSmallRational}
             *   5: SpecializationActive {@link DoNegateNode#doRational}
             *   6: SpecializationActive {@link DoNegateNode#doSmallSciNum}
             *   7: SpecializationExcluded {@link DoNegateNode#doSmallSciNum}
             *   8: SpecializationActive {@link DoNegateNode#doSciNum}
             *   9: SpecializationActive {@link DoNegateNode#typeError}
             *   10-11: ImplicitCast[type=BigNumber, index=1]
             *   12-13: ImplicitCast[type=Rational, index=1]
             *   14-15: ImplicitCast[type=SciNum, index=1]
             * </pre>
             */
            private final StateField state_0_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(DoNegateNode.class);
                this.state_0_ = target.getState(0, 16);
            }

            @Override
            public Object executeNegate(VirtualFrame frameValue, Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0b1101101101) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doLong(long)] || SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] || SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] || SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] || SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] || SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] || SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        try {
                            return doLong(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                            state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumber(arg1Value_);
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        try {
                            return doSmallRational(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                            state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000000) >>> 12 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        return doRational(arg1Value_);
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        try {
                            return doSmallSciNum(arg1Value_);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                            state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_);
                        }
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000000) >>> 14 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_);
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */) {
                        return typeError(arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */ && ((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doLong(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[NegateNode.DoNegateNode.doLong(long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 10) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = state_0 | 0b100 /* add SpecializationActive[NegateNode.DoNegateNode.doBigNumber(BigNumber)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doBigNumber(arg1Value_);
                    }
                }
                if (((state_0 & 0b100000)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */ && ((state_0 & 0b10000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doSmallRational(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                        state_0 = state_0 | 0b10000 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        state_0 = state_0 & 0xfffffff7 /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallRational(SmallRational)] */;
                        state_0 = (state_0 | (rationalCast1 << 12) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_0 = state_0 | 0b100000 /* add SpecializationActive[NegateNode.DoNegateNode.doRational(Rational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doRational(arg1Value_);
                    }
                }
                if (((state_0 & 0b100000000)) == 0 /* is-not SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */ && ((state_0 & 0b10000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                    this.state_0_.set(arg0Value, state_0);
                    try {
                        return doSmallSciNum(arg1Value_);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_.get(arg0Value);
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = state_0 | 0b10000000 /* add SpecializationExcluded  */;
                        this.state_0_.set(arg0Value, state_0);
                        return executeAndSpecialize(arg0Value, arg1Value_);
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        state_0 = state_0 & 0xffffffbf /* remove SpecializationActive[NegateNode.DoNegateNode.doSmallSciNum(SmallSciNum)] */;
                        state_0 = (state_0 | (sciNumCast1 << 14) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = state_0 | 0b100000000 /* add SpecializationActive[NegateNode.DoNegateNode.doSciNum(SciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doSciNum(arg1Value_);
                    }
                }
                state_0 = state_0 | 0b1000000000 /* add SpecializationActive[NegateNode.DoNegateNode.typeError(Object)] */;
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
