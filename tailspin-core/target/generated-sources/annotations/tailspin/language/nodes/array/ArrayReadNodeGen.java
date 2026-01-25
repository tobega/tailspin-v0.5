// CheckStyle: start generated
package tailspin.language.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.GetContextFrameNodeGen;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link ArrayReadNode#doIndexed}
 *     Activation probability: 0,38500
 *     With/without class size: 11/1 bytes
 *   Specialization {@link ArrayReadNode#doMultiSelect}
 *     Activation probability: 0,29500
 *     With/without class size: 7/0 bytes
 *   Specialization {@link ArrayReadNode#doSimpleRead}
 *     Activation probability: 0,20500
 *     With/without class size: 8/4 bytes
 *   Specialization {@link ArrayReadNode#doIllegal}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(ArrayReadNode.class)
@SuppressWarnings("javadoc")
public final class ArrayReadNodeGen extends ArrayReadNode {

    private static final StateField STATE_0_ArrayReadNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link ArrayReadNode#doIndexed}
     *   Parameter: {@link GetContextFrameNode} getFrame
     *   Inline method: {@link GetContextFrameNodeGen#inline}</pre>
     */
    private static final GetContextFrameNode INLINED_INDEXED_GET_FRAME_ = GetContextFrameNodeGen.inline(InlineTarget.create(GetContextFrameNode.class, STATE_0_ArrayReadNode_UPDATER.subUpdater(4, 3)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link ArrayReadNode#doIndexed}
     *   Parameter: {@link WriteLocalValueNode} writeIndex
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_INDEXED_WRITE_INDEX_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    @Child private ValueNode array_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ArrayReadNode#doIndexed}
     *   1: SpecializationActive {@link ArrayReadNode#doMultiSelect}
     *   2: SpecializationActive {@link ArrayReadNode#doSimpleRead}
     *   3: SpecializationActive {@link ArrayReadNode#doIllegal}
     *   4-6: InlinedCache
     *        Specialization: {@link ArrayReadNode#doIndexed}
     *        Parameter: {@link GetContextFrameNode} getFrame
     *        Inline method: {@link GetContextFrameNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link ArrayReadNode#doSimpleRead}
     *   Parameter: {@link DoArrayReadNode} arrayReadNode</pre>
     */
    @Child private DoArrayReadNode simpleRead_arrayReadNode_;

    private ArrayReadNodeGen(boolean noFail, Reference indexVar, ValueNode lensNode, SourceSection sourceSection, ValueNode array) {
        super(noFail, indexVar, lensNode, sourceSection);
        this.array_ = array;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object arrayValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111) != 0 /* is SpecializationActive[ArrayReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[ArrayReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] || SpecializationActive[ArrayReadNode.doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)] || SpecializationActive[ArrayReadNode.doIllegal(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && arrayValue instanceof IndexedArrayValue) {
                IndexedArrayValue arrayValue_ = (IndexedArrayValue) arrayValue;
                return doIndexed(frameValue, arrayValue_, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */ && arrayValue instanceof ArrayList<?>) {
                ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
                return doMultiSelect(frameValue, arrayValue_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayReadNode.doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)] */ && arrayValue instanceof TailspinArray) {
                TailspinArray arrayValue_ = (TailspinArray) arrayValue;
                {
                    DoArrayReadNode arrayReadNode__ = this.simpleRead_arrayReadNode_;
                    if (arrayReadNode__ != null) {
                        return doSimpleRead(frameValue, arrayValue_, arrayReadNode__);
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayReadNode.doIllegal(VirtualFrame, Object)] */) {
                return doIllegal(frameValue, arrayValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arrayValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object arrayValue_ = this.array_.executeGeneric(frameValue);
        if ((state_0 & 0b1111) != 0 /* is SpecializationActive[ArrayReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[ArrayReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] || SpecializationActive[ArrayReadNode.doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)] || SpecializationActive[ArrayReadNode.doIllegal(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && arrayValue_ instanceof IndexedArrayValue) {
                IndexedArrayValue arrayValue__ = (IndexedArrayValue) arrayValue_;
                return doIndexed(frameValue, arrayValue__, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */ && arrayValue_ instanceof ArrayList<?>) {
                ArrayList<?> arrayValue__ = (ArrayList<?>) arrayValue_;
                return doMultiSelect(frameValue, arrayValue__);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayReadNode.doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)] */ && arrayValue_ instanceof TailspinArray) {
                TailspinArray arrayValue__ = (TailspinArray) arrayValue_;
                {
                    DoArrayReadNode arrayReadNode__ = this.simpleRead_arrayReadNode_;
                    if (arrayReadNode__ != null) {
                        return doSimpleRead(frameValue, arrayValue__, arrayReadNode__);
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayReadNode.doIllegal(VirtualFrame, Object)] */) {
                return doIllegal(frameValue, arrayValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arrayValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object arrayValue) {
        int state_0 = this.state_0_;
        if (arrayValue instanceof IndexedArrayValue) {
            IndexedArrayValue arrayValue_ = (IndexedArrayValue) arrayValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */;
            this.state_0_ = state_0;
            return doIndexed(frameValue, arrayValue_, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
        }
        if (arrayValue instanceof ArrayList<?>) {
            ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
            state_0 = state_0 | 0b10 /* add SpecializationActive[ArrayReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */;
            this.state_0_ = state_0;
            return doMultiSelect(frameValue, arrayValue_);
        }
        if (arrayValue instanceof TailspinArray) {
            TailspinArray arrayValue_ = (TailspinArray) arrayValue;
            DoArrayReadNode arrayReadNode__ = this.insert((DoArrayReadNodeGen.create(noFail)));
            Objects.requireNonNull(arrayReadNode__, "Specialization 'doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)' cache 'arrayReadNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.simpleRead_arrayReadNode_ = arrayReadNode__;
            state_0 = state_0 | 0b100 /* add SpecializationActive[ArrayReadNode.doSimpleRead(VirtualFrame, TailspinArray, DoArrayReadNode)] */;
            this.state_0_ = state_0;
            return doSimpleRead(frameValue, arrayValue_, arrayReadNode__);
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[ArrayReadNode.doIllegal(VirtualFrame, Object)] */;
        this.state_0_ = state_0;
        return doIllegal(frameValue, arrayValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b1111) & ((state_0 & 0b1111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static ArrayReadNode create(boolean noFail, Reference indexVar, ValueNode lensNode, SourceSection sourceSection, ValueNode array) {
        return new ArrayReadNodeGen(noFail, indexVar, lensNode, sourceSection, array);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DoArrayReadNode#doLong}
     *     Activation probability: 0,38500
     *     With/without class size: 8/0 bytes
     *   Specialization {@link DoArrayReadNode#doBigNumber}
     *     Activation probability: 0,29500
     *     With/without class size: 7/0 bytes
     *   Specialization {@link DoArrayReadNode#doArray}
     *     Activation probability: 0,20500
     *     With/without class size: 6/0 bytes
     *   Specialization {@link DoArrayReadNode#doRange}
     *     Activation probability: 0,11500
     *     With/without class size: 5/0 bytes
     * </pre>
     */
    @GeneratedBy(DoArrayReadNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoArrayReadNodeGen extends DoArrayReadNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoArrayReadNode#doLong}
         *   1: SpecializationActive {@link DoArrayReadNode#doBigNumber}
         *   2: SpecializationActive {@link DoArrayReadNode#doArray}
         *   3: SpecializationActive {@link DoArrayReadNode#doRange}
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private DoArrayReadNodeGen(boolean noFail) {
            super(noFail);
        }

        @Override
        public Object executeArrayRead(TailspinArray arg0Value, Object arg1Value, Reference arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[ArrayReadNode.DoArrayReadNode.doLong(TailspinArray, long, Reference)] || SpecializationActive[ArrayReadNode.DoArrayReadNode.doBigNumber(TailspinArray, BigNumber, Reference)] || SpecializationActive[ArrayReadNode.DoArrayReadNode.doArray(TailspinArray, TailspinArray, Reference)] || SpecializationActive[ArrayReadNode.DoArrayReadNode.doRange(TailspinArray, ArrayList<>, Reference)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayReadNode.DoArrayReadNode.doLong(TailspinArray, long, Reference)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    return doLong(arg0Value, arg1Value_, arg2Value);
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayReadNode.DoArrayReadNode.doBigNumber(TailspinArray, BigNumber, Reference)] */ && arg1Value instanceof BigNumber) {
                    BigNumber arg1Value_ = (BigNumber) arg1Value;
                    return doBigNumber(arg0Value, arg1Value_, arg2Value);
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayReadNode.DoArrayReadNode.doArray(TailspinArray, TailspinArray, Reference)] */ && arg1Value instanceof TailspinArray) {
                    TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                    return doArray(arg0Value, arg1Value_, arg2Value);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayReadNode.DoArrayReadNode.doRange(TailspinArray, ArrayList<>, Reference)] */ && arg1Value instanceof ArrayList<?>) {
                    ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                    return doRange(arg0Value, arg1Value_, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(TailspinArray arg0Value, Object arg1Value, Reference arg2Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayReadNode.DoArrayReadNode.doLong(TailspinArray, long, Reference)] */;
                this.state_0_ = state_0;
                return doLong(arg0Value, arg1Value_, arg2Value);
            }
            if (arg1Value instanceof BigNumber) {
                BigNumber arg1Value_ = (BigNumber) arg1Value;
                state_0 = state_0 | 0b10 /* add SpecializationActive[ArrayReadNode.DoArrayReadNode.doBigNumber(TailspinArray, BigNumber, Reference)] */;
                this.state_0_ = state_0;
                return doBigNumber(arg0Value, arg1Value_, arg2Value);
            }
            if (arg1Value instanceof TailspinArray) {
                TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                state_0 = state_0 | 0b100 /* add SpecializationActive[ArrayReadNode.DoArrayReadNode.doArray(TailspinArray, TailspinArray, Reference)] */;
                this.state_0_ = state_0;
                return doArray(arg0Value, arg1Value_, arg2Value);
            }
            if (arg1Value instanceof ArrayList<?>) {
                ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                state_0 = state_0 | 0b1000 /* add SpecializationActive[ArrayReadNode.DoArrayReadNode.doRange(TailspinArray, ArrayList<>, Reference)] */;
                this.state_0_ = state_0;
                return doRange(arg0Value, arg1Value_, arg2Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[] {null, null, null}, arg0Value, arg1Value, arg2Value);
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
        public static DoArrayReadNode create(boolean noFail) {
            return new DoArrayReadNodeGen(noFail);
        }

    }
}
