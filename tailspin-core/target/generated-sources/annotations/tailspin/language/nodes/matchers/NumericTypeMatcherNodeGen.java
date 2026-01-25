// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;

/**
 * Debug Info: <pre>
 *   Specialization {@link NumericTypeMatcherNode#isLong}
 *     Activation probability: 0,27381
 *     With/without class size: 7/0 bytes
 *   Specialization {@link NumericTypeMatcherNode#isBigNumber}
 *     Activation probability: 0,23095
 *     With/without class size: 6/0 bytes
 *   Specialization {@link NumericTypeMatcherNode#isRational}
 *     Activation probability: 0,18810
 *     With/without class size: 6/0 bytes
 *   Specialization {@link NumericTypeMatcherNode#isSmallSciNum}
 *     Activation probability: 0,14524
 *     With/without class size: 5/0 bytes
 *   Specialization {@link NumericTypeMatcherNode#isSciNum}
 *     Activation probability: 0,10238
 *     With/without class size: 5/0 bytes
 *   Specialization {@link NumericTypeMatcherNode#notNumber}
 *     Activation probability: 0,05952
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(NumericTypeMatcherNode.class)
@SuppressWarnings("javadoc")
public final class NumericTypeMatcherNodeGen extends NumericTypeMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link NumericTypeMatcherNode#isLong}
     *   1: SpecializationActive {@link NumericTypeMatcherNode#isBigNumber}
     *   2: SpecializationActive {@link NumericTypeMatcherNode#isRational}
     *   3: SpecializationActive {@link NumericTypeMatcherNode#isSmallSciNum}
     *   4: SpecializationActive {@link NumericTypeMatcherNode#isSciNum}
     *   5: SpecializationActive {@link NumericTypeMatcherNode#notNumber}
     *   6-7: ImplicitCast[type=BigNumber, index=0]
     *   8-9: ImplicitCast[type=Rational, index=0]
     *   10-11: ImplicitCast[type=SciNum, index=0]
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private NumericTypeMatcherNodeGen(SourceSection sourceSection) {
        super(sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(Object arg0Value) {
        if (TailspinTypesGen.isImplicitBigNumber(arg0Value)) {
            return false;
        }
        if (TailspinTypesGen.isImplicitRational(arg0Value)) {
            return false;
        }
        if (TailspinTypesGen.isImplicitSciNum(arg0Value)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111111) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isLong(long)] || SpecializationActive[NumericTypeMatcherNode.isBigNumber(BigNumber)] || SpecializationActive[NumericTypeMatcherNode.isRational(Rational)] || SpecializationActive[NumericTypeMatcherNode.isSmallSciNum(SmallSciNum)] || SpecializationActive[NumericTypeMatcherNode.isSciNum(SciNum)] || SpecializationActive[NumericTypeMatcherNode.notNumber(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isLong(long)] */ && arg0Value instanceof Long) {
                long arg0Value_ = (long) arg0Value;
                return isLong(arg0Value_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b11000000) >>> 6 /* get-int ImplicitCast[type=BigNumber, index=0] */, arg0Value)) {
                BigNumber arg0Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b11000000) >>> 6 /* get-int ImplicitCast[type=BigNumber, index=0] */, arg0Value);
                return isBigNumber(arg0Value_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isRational(Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b1100000000) >>> 8 /* get-int ImplicitCast[type=Rational, index=0] */, arg0Value)) {
                Rational arg0Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0b1100000000) >>> 8 /* get-int ImplicitCast[type=Rational, index=0] */, arg0Value);
                return isRational(arg0Value_);
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isSmallSciNum(SmallSciNum)] */ && arg0Value instanceof SmallSciNum) {
                SmallSciNum arg0Value_ = (SmallSciNum) arg0Value;
                return isSmallSciNum(arg0Value_);
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isSciNum(SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=SciNum, index=0] */, arg0Value)) {
                SciNum arg0Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b110000000000) >>> 10 /* get-int ImplicitCast[type=SciNum, index=0] */, arg0Value);
                return isSciNum(arg0Value_);
            }
            if ((state_0 & 0b100000) != 0 /* is SpecializationActive[NumericTypeMatcherNode.notNumber(Object)] */) {
                if (fallbackGuard_(arg0Value)) {
                    return notNumber(arg0Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b100011) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isLong(long)] || SpecializationActive[NumericTypeMatcherNode.isBigNumber(BigNumber)] || SpecializationActive[NumericTypeMatcherNode.notNumber(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isLong(long)] */) {
                return isLong(arg0Value);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[NumericTypeMatcherNode.isBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b11000000) >>> 6 /* get-int ImplicitCast[type=BigNumber, index=0] */, arg0Value)) {
                BigNumber arg0Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b11000000) >>> 6 /* get-int ImplicitCast[type=BigNumber, index=0] */, arg0Value);
                return isBigNumber(arg0Value_);
            }
            if ((state_0 & 0b100000) != 0 /* is SpecializationActive[NumericTypeMatcherNode.notNumber(Object)] */) {
                if (fallbackGuard_(arg0Value)) {
                    return notNumber(arg0Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof Long) {
            long arg0Value_ = (long) arg0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[NumericTypeMatcherNode.isLong(long)] */;
            this.state_0_ = state_0;
            return isLong(arg0Value_);
        }
        {
            int bigNumberCast0;
            if ((bigNumberCast0 = TailspinTypesGen.specializeImplicitBigNumber(arg0Value)) != 0) {
                BigNumber arg0Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast0, arg0Value);
                state_0 = (state_0 | (bigNumberCast0 << 6) /* set-int ImplicitCast[type=BigNumber, index=0] */);
                state_0 = state_0 | 0b10 /* add SpecializationActive[NumericTypeMatcherNode.isBigNumber(BigNumber)] */;
                this.state_0_ = state_0;
                return isBigNumber(arg0Value_);
            }
        }
        {
            int rationalCast0;
            if ((rationalCast0 = TailspinTypesGen.specializeImplicitRational(arg0Value)) != 0) {
                Rational arg0Value_ = TailspinTypesGen.asImplicitRational(rationalCast0, arg0Value);
                state_0 = (state_0 | (rationalCast0 << 8) /* set-int ImplicitCast[type=Rational, index=0] */);
                state_0 = state_0 | 0b100 /* add SpecializationActive[NumericTypeMatcherNode.isRational(Rational)] */;
                this.state_0_ = state_0;
                return isRational(arg0Value_);
            }
        }
        if (arg0Value instanceof SmallSciNum) {
            SmallSciNum arg0Value_ = (SmallSciNum) arg0Value;
            state_0 = state_0 | 0b1000 /* add SpecializationActive[NumericTypeMatcherNode.isSmallSciNum(SmallSciNum)] */;
            this.state_0_ = state_0;
            return isSmallSciNum(arg0Value_);
        }
        {
            int sciNumCast0;
            if ((sciNumCast0 = TailspinTypesGen.specializeImplicitSciNum(arg0Value)) != 0) {
                SciNum arg0Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast0, arg0Value);
                state_0 = (state_0 | (sciNumCast0 << 10) /* set-int ImplicitCast[type=SciNum, index=0] */);
                state_0 = state_0 | 0b10000 /* add SpecializationActive[NumericTypeMatcherNode.isSciNum(SciNum)] */;
                this.state_0_ = state_0;
                return isSciNum(arg0Value_);
            }
        }
        state_0 = state_0 | 0b100000 /* add SpecializationActive[NumericTypeMatcherNode.notNumber(Object)] */;
        this.state_0_ = state_0;
        return notNumber(arg0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b111111) & ((state_0 & 0b111111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static NumericTypeMatcherNode create(SourceSection sourceSection) {
        return new NumericTypeMatcherNodeGen(sourceSection);
    }

}
