// CheckStyle: start generated
package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link ChainStageNode#doLong}
 *     Activation probability: 0,38500
 *     With/without class size: 8/0 bytes
 *   Specialization {@link ChainStageNode#doNull}
 *     Activation probability: 0,29500
 *     With/without class size: 7/0 bytes
 *   Specialization {@link ChainStageNode#doValueStream}
 *     Activation probability: 0,20500
 *     With/without class size: 6/0 bytes
 *   Specialization {@link ChainStageNode#doSingle}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(ChainStageNode.class)
@SuppressWarnings("javadoc")
public final class ChainStageNodeGen extends ChainStageNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ChainStageNode#doLong}
     *   1: SpecializationActive {@link ChainStageNode#doNull}
     *   2: SpecializationActive {@link ChainStageNode#doValueStream}
     *   3: SpecializationActive {@link ChainStageNode#doSingle}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link ChainStageNode#doLong}
     *   Parameter: {@link SetChainCvNode} setCv</pre>
     */
    @Child private SetChainCvNode cvSetters;

    private ChainStageNodeGen(int valuesSlot, int cvSlot, TransformNode stage, SourceSection sourceSection) {
        super(valuesSlot, cvSlot, stage, sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object valuesValue) {
        if (!((state_0 & 0b1) != 0 /* is SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] */) && valuesValue instanceof Long) {
            return false;
        }
        if (!((state_0 & 0b10) != 0 /* is SpecializationActive[ChainStageNode.doNull(VirtualFrame, Object)] */) && (valuesValue == null)) {
            return false;
        }
        if (valuesValue instanceof ArrayList<?>) {
            ArrayList<?> valuesValue_ = (ArrayList<?>) valuesValue;
            if ((valuesValue_ != null)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1110) == 0 /* only-active SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] */ && (state_0 != 0  /* is-not SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] && SpecializationActive[ChainStageNode.doNull(VirtualFrame, Object)] && SpecializationActive[ChainStageNode.doValueStream(VirtualFrame, ArrayList<>)] && SpecializationActive[ChainStageNode.doSingle(VirtualFrame, Object, SetChainCvNode)] */)) {
            executeTransform_long0(state_0, frameValue);
            return;
        } else {
            executeTransform_generic1(state_0, frameValue);
            return;
        }
    }

    private void executeTransform_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long valuesValue_;
        try {
            valuesValue_ = super.values.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(frameValue, ex.getResult());
            return;
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] */;
        {
            SetChainCvNode cvSetters_ = this.cvSetters;
            if (cvSetters_ != null) {
                doLong(frameValue, valuesValue_, cvSetters_);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, valuesValue_);
        return;
    }

    private void executeTransform_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object valuesValue_ = super.values.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] || SpecializationActive[ChainStageNode.doNull(VirtualFrame, Object)] || SpecializationActive[ChainStageNode.doValueStream(VirtualFrame, ArrayList<>)] || SpecializationActive[ChainStageNode.doSingle(VirtualFrame, Object, SetChainCvNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] */ && valuesValue_ instanceof Long) {
                long valuesValue__ = (long) valuesValue_;
                {
                    SetChainCvNode cvSetters_ = this.cvSetters;
                    if (cvSetters_ != null) {
                        doLong(frameValue, valuesValue__, cvSetters_);
                        return;
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ChainStageNode.doNull(VirtualFrame, Object)] */) {
                if ((valuesValue_ == null)) {
                    doNull(frameValue, valuesValue_);
                    return;
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ChainStageNode.doValueStream(VirtualFrame, ArrayList<>)] */ && valuesValue_ instanceof ArrayList<?>) {
                ArrayList<?> valuesValue__ = (ArrayList<?>) valuesValue_;
                if ((valuesValue__ != null)) {
                    doValueStream(frameValue, valuesValue__);
                    return;
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ChainStageNode.doSingle(VirtualFrame, Object, SetChainCvNode)] */) {
                {
                    SetChainCvNode cvSetters_1 = this.cvSetters;
                    if (cvSetters_1 != null) {
                        if (fallbackGuard_(state_0, valuesValue_)) {
                            doSingle(frameValue, valuesValue_, cvSetters_1);
                            return;
                        }
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, valuesValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object valuesValue) {
        int state_0 = this.state_0_;
        if (valuesValue instanceof Long) {
            long valuesValue_ = (long) valuesValue;
            SetChainCvNode cvSetters_;
            SetChainCvNode cvSetters__shared = this.cvSetters;
            if (cvSetters__shared != null) {
                cvSetters_ = cvSetters__shared;
            } else {
                cvSetters_ = this.insert((SetChainCvNode.create(cvSlot)));
                if (cvSetters_ == null) {
                    throw new IllegalStateException("Specialization 'doLong(VirtualFrame, long, SetChainCvNode)' contains a shared cache with name 'setCv' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.cvSetters == null) {
                VarHandle.storeStoreFence();
                this.cvSetters = cvSetters_;
            }
            state_0 = state_0 | 0b1 /* add SpecializationActive[ChainStageNode.doLong(VirtualFrame, long, SetChainCvNode)] */;
            this.state_0_ = state_0;
            doLong(frameValue, valuesValue_, cvSetters_);
            return;
        }
        if ((valuesValue == null)) {
            state_0 = state_0 | 0b10 /* add SpecializationActive[ChainStageNode.doNull(VirtualFrame, Object)] */;
            this.state_0_ = state_0;
            doNull(frameValue, valuesValue);
            return;
        }
        if (valuesValue instanceof ArrayList<?>) {
            ArrayList<?> valuesValue_ = (ArrayList<?>) valuesValue;
            if ((valuesValue_ != null)) {
                state_0 = state_0 | 0b100 /* add SpecializationActive[ChainStageNode.doValueStream(VirtualFrame, ArrayList<>)] */;
                this.state_0_ = state_0;
                doValueStream(frameValue, valuesValue_);
                return;
            }
        }
        SetChainCvNode cvSetters_1;
        SetChainCvNode cvSetters_1_shared = this.cvSetters;
        if (cvSetters_1_shared != null) {
            cvSetters_1 = cvSetters_1_shared;
        } else {
            cvSetters_1 = this.insert((SetChainCvNode.create(cvSlot)));
            if (cvSetters_1 == null) {
                throw new IllegalStateException("Specialization 'doSingle(VirtualFrame, Object, SetChainCvNode)' contains a shared cache with name 'setCv' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
            }
        }
        if (this.cvSetters == null) {
            VarHandle.storeStoreFence();
            this.cvSetters = cvSetters_1;
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[ChainStageNode.doSingle(VirtualFrame, Object, SetChainCvNode)] */;
        this.state_0_ = state_0;
        doSingle(frameValue, valuesValue, cvSetters_1);
        return;
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if ((state_0 & (state_0 - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static ChainStageNode create(int valuesSlot, int cvSlot, TransformNode stage, SourceSection sourceSection) {
        return new ChainStageNodeGen(valuesSlot, cvSlot, stage, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link SetChainCvNode#setObject}
     *     Activation probability: 1,00000
     *     With/without class size: 16/0 bytes
     * </pre>
     */
    @GeneratedBy(SetChainCvNode.class)
    @SuppressWarnings("javadoc")
    public static final class SetChainCvNodeGen extends SetChainCvNode {

        private final int slot;

        private SetChainCvNodeGen(int slot) {
            this.slot = slot;
        }

        @Override
        protected int getSlot() {
            return this.slot;
        }

        @Override
        public void execute(VirtualFrame frameValue, Object arg0Value) {
            setObject(frameValue, arg0Value);
            return;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @NeverDefault
        public static SetChainCvNode create(int slot) {
            return new SetChainCvNodeGen(slot);
        }

    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link GetNextStreamValueNode#doStream}
     *     Activation probability: 1,00000
     *     With/without class size: 16/0 bytes
     * </pre>
     */
    @GeneratedBy(GetNextStreamValueNode.class)
    @SuppressWarnings("javadoc")
    public static final class GetNextStreamValueNodeGen extends GetNextStreamValueNode {

        @Child private StaticReferenceNode valueStream_;
        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link GetNextStreamValueNode#doStream}
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private GetNextStreamValueNodeGen(StaticReferenceNode valueStream) {
            this.valueStream_ = valueStream;
        }

        @Override
        public Object executeStream(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object valueStreamValue_ = this.valueStream_.executeGeneric(frameValue);
            if (state_0 != 0 /* is SpecializationActive[ChainStageNode.GetNextStreamValueNode.doStream(ArrayList<>)] */ && valueStreamValue_ instanceof ArrayList<?>) {
                ArrayList<?> valueStreamValue__ = (ArrayList<?>) valueStreamValue_;
                if ((valueStreamValue__ != null)) {
                    return doStream(valueStreamValue__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(valueStreamValue_);
        }

        private Object executeAndSpecialize(Object valueStreamValue) {
            int state_0 = this.state_0_;
            if (valueStreamValue instanceof ArrayList<?>) {
                ArrayList<?> valueStreamValue_ = (ArrayList<?>) valueStreamValue;
                if ((valueStreamValue_ != null)) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[ChainStageNode.GetNextStreamValueNode.doStream(ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doStream(valueStreamValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.valueStream_}, valueStreamValue);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                return NodeCost.MONOMORPHIC;
            }
        }

        @NeverDefault
        public static GetNextStreamValueNode create(StaticReferenceNode valueStream) {
            return new GetNextStreamValueNodeGen(valueStream);
        }

    }
}
