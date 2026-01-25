// CheckStyle: start generated
package tailspin.language.nodes.value;

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
import tailspin.language.nodes.TransformNode;
import tailspin.language.runtime.BigNumber;

/**
 * Debug Info: <pre>
 *   Specialization {@link SingleValueNode#doLong}
 *     Activation probability: 0,32000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link SingleValueNode#doBigNumber}
 *     Activation probability: 0,26000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link SingleValueNode#doResultIterator}
 *     Activation probability: 0,20000
 *     With/without class size: 6/0 bytes
 *   Specialization {@link SingleValueNode#doNull}
 *     Activation probability: 0,14000
 *     With/without class size: 5/0 bytes
 *   Specialization {@link SingleValueNode#doObject}
 *     Activation probability: 0,08000
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(SingleValueNode.class)
@SuppressWarnings("javadoc")
public final class SingleValueNodeGen extends SingleValueNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link SingleValueNode#doLong}
     *   1: SpecializationActive {@link SingleValueNode#doBigNumber}
     *   2: SpecializationActive {@link SingleValueNode#doResultIterator}
     *   3: SpecializationActive {@link SingleValueNode#doNull}
     *   4: SpecializationActive {@link SingleValueNode#doObject}
     *   5-6: ImplicitCast[type=BigNumber, index=0]
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private SingleValueNodeGen(TransformNode transformNode, SourceSection sourceSection) {
        super(transformNode, sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object transformResultValue) {
        if (TailspinTypesGen.isImplicitBigNumber(transformResultValue)) {
            return false;
        }
        if (!((state_0 & 0b100) != 0 /* is SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] */) && transformResultValue instanceof ArrayList<?>) {
            return false;
        }
        if (!((state_0 & 0b1000) != 0 /* is SpecializationActive[SingleValueNode.doNull(Object)] */) && (transformResultValue == null)) {
            return false;
        }
        return true;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11110) == 0 /* only-active SpecializationActive[SingleValueNode.doLong(long)] */ && ((state_0 & 0b11111) != 0  /* is-not SpecializationActive[SingleValueNode.doLong(long)] && SpecializationActive[SingleValueNode.doBigNumber(BigNumber)] && SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] && SpecializationActive[SingleValueNode.doNull(Object)] && SpecializationActive[SingleValueNode.doObject(Object)] */)) {
            return executeGeneric_long0(state_0, frameValue);
        } else {
            return executeGeneric_generic1(state_0, frameValue);
        }
    }

    private Object executeGeneric_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long transformResultValue_;
        try {
            transformResultValue_ = super.transformResult.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(ex.getResult());
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[SingleValueNode.doLong(long)] */;
        return doLong(transformResultValue_);
    }

    private Object executeGeneric_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object transformResultValue_ = super.transformResult.executeGeneric(frameValue);
        if ((state_0 & 0b11111) != 0 /* is SpecializationActive[SingleValueNode.doLong(long)] || SpecializationActive[SingleValueNode.doBigNumber(BigNumber)] || SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] || SpecializationActive[SingleValueNode.doNull(Object)] || SpecializationActive[SingleValueNode.doObject(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[SingleValueNode.doLong(long)] */ && transformResultValue_ instanceof Long) {
                long transformResultValue__ = (long) transformResultValue_;
                return doLong(transformResultValue__);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[SingleValueNode.doBigNumber(BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=0] */, transformResultValue_)) {
                BigNumber transformResultValue__ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b1100000) >>> 5 /* get-int ImplicitCast[type=BigNumber, index=0] */, transformResultValue_);
                return doBigNumber(transformResultValue__);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] */ && transformResultValue_ instanceof ArrayList<?>) {
                ArrayList<?> transformResultValue__ = (ArrayList<?>) transformResultValue_;
                return doResultIterator(transformResultValue__);
            }
            if ((state_0 & 0b11000) != 0 /* is SpecializationActive[SingleValueNode.doNull(Object)] || SpecializationActive[SingleValueNode.doObject(Object)] */) {
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[SingleValueNode.doNull(Object)] */) {
                    if ((transformResultValue_ == null)) {
                        return doNull(transformResultValue_);
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[SingleValueNode.doObject(Object)] */) {
                    if (fallbackGuard_(state_0, transformResultValue_)) {
                        return doObject(transformResultValue_);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(transformResultValue_);
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11100) != 0 /* is SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] || SpecializationActive[SingleValueNode.doNull(Object)] || SpecializationActive[SingleValueNode.doObject(Object)] */) {
            return TailspinTypesGen.expectLong(executeGeneric(frameValue));
        }
        long transformResultValue_;
        try {
            transformResultValue_ = super.transformResult.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TailspinTypesGen.expectLong(executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 0b1) != 0 /* is SpecializationActive[SingleValueNode.doLong(long)] */) {
            return doLong(transformResultValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TailspinTypesGen.expectLong(executeAndSpecialize(transformResultValue_));
    }

    private Object executeAndSpecialize(Object transformResultValue) {
        int state_0 = this.state_0_;
        if (transformResultValue instanceof Long) {
            long transformResultValue_ = (long) transformResultValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[SingleValueNode.doLong(long)] */;
            this.state_0_ = state_0;
            return doLong(transformResultValue_);
        }
        {
            int bigNumberCast0;
            if ((bigNumberCast0 = TailspinTypesGen.specializeImplicitBigNumber(transformResultValue)) != 0) {
                BigNumber transformResultValue_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast0, transformResultValue);
                state_0 = (state_0 | (bigNumberCast0 << 5) /* set-int ImplicitCast[type=BigNumber, index=0] */);
                state_0 = state_0 | 0b10 /* add SpecializationActive[SingleValueNode.doBigNumber(BigNumber)] */;
                this.state_0_ = state_0;
                return doBigNumber(transformResultValue_);
            }
        }
        if (transformResultValue instanceof ArrayList<?>) {
            ArrayList<?> transformResultValue_ = (ArrayList<?>) transformResultValue;
            state_0 = state_0 | 0b100 /* add SpecializationActive[SingleValueNode.doResultIterator(ArrayList<>)] */;
            this.state_0_ = state_0;
            return doResultIterator(transformResultValue_);
        }
        if ((transformResultValue == null)) {
            state_0 = state_0 | 0b1000 /* add SpecializationActive[SingleValueNode.doNull(Object)] */;
            this.state_0_ = state_0;
            return doNull(transformResultValue);
        }
        state_0 = state_0 | 0b10000 /* add SpecializationActive[SingleValueNode.doObject(Object)] */;
        this.state_0_ = state_0;
        return doObject(transformResultValue);
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
    public static SingleValueNode create(TransformNode transformNode, SourceSection sourceSection) {
        return new SingleValueNodeGen(transformNode, sourceSection);
    }

}
