// CheckStyle: start generated
package tailspin.language.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link ArrayMutateNode#doLong}
 *     Activation probability: 0,32000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link ArrayMutateNode#doBigNumber}
 *     Activation probability: 0,26000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link ArrayMutateNode#doArray}
 *     Activation probability: 0,20000
 *     With/without class size: 6/0 bytes
 *   Specialization {@link ArrayMutateNode#doMany}
 *     Activation probability: 0,14000
 *     With/without class size: 5/0 bytes
 *   Specialization {@link ArrayMutateNode#doIllegal}
 *     Activation probability: 0,08000
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(ArrayMutateNode.class)
@SuppressWarnings("javadoc")
public final class ArrayMutateNodeGen extends ArrayMutateNode {

    @Child private ValueNode array_;
    @Child private ValueNode lens_;
    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ArrayMutateNode#doLong}
     *   1: SpecializationActive {@link ArrayMutateNode#doBigNumber}
     *   2: SpecializationActive {@link ArrayMutateNode#doArray}
     *   3: SpecializationActive {@link ArrayMutateNode#doMany}
     *   4: SpecializationActive {@link ArrayMutateNode#doIllegal}
     *   5-6: ImplicitCast[type=BigNumber, index=1]
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private ArrayMutateNodeGen(SourceSection sourceSection, ValueNode array, ValueNode lens, ValueNode value) {
        super(sourceSection);
        this.array_ = array;
        this.lens_ = lens;
        this.value_ = value;
    }

    @Override
    public Object executeDirect(Object arrayValue, Object lensValue, Object valueValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] || SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] || SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] */ && arrayValue instanceof TailspinArray) {
                TailspinArray arrayValue_ = (TailspinArray) arrayValue;
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] */ && lensValue instanceof Long) {
                        long lensValue_ = (long) lensValue;
                        return doLong(arrayValue_, lensValue_, valueValue);
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=1] */, lensValue)) {
                        BigNumber lensValue_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=1] */, lensValue);
                        return doBigNumber(arrayValue_, lensValue_, valueValue);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] */ && lensValue instanceof TailspinArray) {
                    TailspinArray lensValue_ = (TailspinArray) lensValue;
                    if (valueValue instanceof ArrayList<?>) {
                        ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                        if ((lensValue_.getArraySize() == valueValue_.size())) {
                            return doArray(arrayValue_, lensValue_, valueValue_);
                        }
                    }
                }
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] */ && arrayValue instanceof ArrayList<?>) {
                    ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
                    if (valueValue instanceof ArrayList<?>) {
                        ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                        if ((arrayValue_.size() == valueValue_.size())) {
                            return doMany(arrayValue_, lensValue, valueValue_);
                        }
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
                    return doIllegal(arrayValue, lensValue, valueValue);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arrayValue, lensValue, valueValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11110) == 0 /* only-active SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] */ && ((state_0 & 0b11111) != 0  /* is-not SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] && SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] && SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] && SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] && SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */)) {
            return executeGeneric_long0(state_0, frameValue);
        } else {
            return executeGeneric_generic1(state_0, frameValue);
        }
    }

    private Object executeGeneric_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object arrayValue_ = this.array_.executeGeneric(frameValue);
        long lensValue_;
        try {
            lensValue_ = this.lens_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object valueValue = this.value_.executeGeneric(frameValue);
            return executeAndSpecialize(arrayValue_, ex.getResult(), valueValue);
        }
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] */;
        if (arrayValue_ instanceof TailspinArray) {
            TailspinArray arrayValue__ = (TailspinArray) arrayValue_;
            return doLong(arrayValue__, lensValue_, valueValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arrayValue_, lensValue_, valueValue_);
    }

    private Object executeGeneric_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object arrayValue_ = this.array_.executeGeneric(frameValue);
        Object lensValue_ = this.lens_.executeGeneric(frameValue);
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] || SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] || SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] */ && arrayValue_ instanceof TailspinArray) {
                TailspinArray arrayValue__ = (TailspinArray) arrayValue_;
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] || SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] */ && lensValue_ instanceof Long) {
                        long lensValue__ = (long) lensValue_;
                        return doLong(arrayValue__, lensValue__, valueValue_);
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=1] */, lensValue_)) {
                        BigNumber lensValue__ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=1] */, lensValue_);
                        return doBigNumber(arrayValue__, lensValue__, valueValue_);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] */ && lensValue_ instanceof TailspinArray) {
                    TailspinArray lensValue__ = (TailspinArray) lensValue_;
                    if (valueValue_ instanceof ArrayList<?>) {
                        ArrayList<?> valueValue__ = (ArrayList<?>) valueValue_;
                        if ((lensValue__.getArraySize() == valueValue__.size())) {
                            return doArray(arrayValue__, lensValue__, valueValue__);
                        }
                    }
                }
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] || SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] */ && arrayValue_ instanceof ArrayList<?>) {
                    ArrayList<?> arrayValue__ = (ArrayList<?>) arrayValue_;
                    if (valueValue_ instanceof ArrayList<?>) {
                        ArrayList<?> valueValue__ = (ArrayList<?>) valueValue_;
                        if ((arrayValue__.size() == valueValue__.size())) {
                            return doMany(arrayValue__, lensValue_, valueValue__);
                        }
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */) {
                    return doIllegal(arrayValue_, lensValue_, valueValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arrayValue_, lensValue_, valueValue_);
    }

    private Object executeAndSpecialize(Object arrayValue, Object lensValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (arrayValue instanceof TailspinArray) {
            TailspinArray arrayValue_ = (TailspinArray) arrayValue;
            if (lensValue instanceof Long) {
                long lensValue_ = (long) lensValue;
                state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayMutateNode.doLong(TailspinArray, long, Object)] */;
                this.state_0_ = state_0;
                return doLong(arrayValue_, lensValue_, valueValue);
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(lensValue)) != 0) {
                    BigNumber lensValue_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, lensValue);
                    state_0 = (state_0 | (bigNumberCast1 << 5) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                    state_0 = state_0 | 0b10 /* add SpecializationActive[ArrayMutateNode.doBigNumber(TailspinArray, BigNumber, Object)] */;
                    this.state_0_ = state_0;
                    return doBigNumber(arrayValue_, lensValue_, valueValue);
                }
            }
            if (lensValue instanceof TailspinArray) {
                TailspinArray lensValue_ = (TailspinArray) lensValue;
                if (valueValue instanceof ArrayList<?>) {
                    ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                    if ((lensValue_.getArraySize() == valueValue_.size())) {
                        state_0 = state_0 | 0b100 /* add SpecializationActive[ArrayMutateNode.doArray(TailspinArray, TailspinArray, ArrayList<>)] */;
                        this.state_0_ = state_0;
                        return doArray(arrayValue_, lensValue_, valueValue_);
                    }
                }
            }
        }
        if (arrayValue instanceof ArrayList<?>) {
            ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
            if (valueValue instanceof ArrayList<?>) {
                ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                if ((arrayValue_.size() == valueValue_.size())) {
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[ArrayMutateNode.doMany(ArrayList<>, Object, ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doMany(arrayValue_, lensValue, valueValue_);
                }
            }
        }
        state_0 = state_0 | 0b10000 /* add SpecializationActive[ArrayMutateNode.doIllegal(Object, Object, Object)] */;
        this.state_0_ = state_0;
        return doIllegal(arrayValue, lensValue, valueValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b11111) & ((state_0 & 0b11111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static ArrayMutateNode create(SourceSection sourceSection, ValueNode array, ValueNode lens, ValueNode value) {
        return new ArrayMutateNodeGen(sourceSection, array, lens, value);
    }

}
