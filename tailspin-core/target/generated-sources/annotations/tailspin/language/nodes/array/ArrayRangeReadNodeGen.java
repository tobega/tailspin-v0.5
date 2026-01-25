// CheckStyle: start generated
package tailspin.language.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.GetContextFrameNodeGen;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link ArrayRangeReadNode#doIndexed}
 *     Activation probability: 0,38500
 *     With/without class size: 11/1 bytes
 *   Specialization {@link ArrayRangeReadNode#doArray}
 *     Activation probability: 0,29500
 *     With/without class size: 7/0 bytes
 *   Specialization {@link ArrayRangeReadNode#doMultiSelect}
 *     Activation probability: 0,20500
 *     With/without class size: 6/0 bytes
 *   Specialization {@link ArrayRangeReadNode#doIllegal}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(ArrayRangeReadNode.class)
@SuppressWarnings("javadoc")
public final class ArrayRangeReadNodeGen extends ArrayRangeReadNode {

    private static final StateField STATE_0_ArrayRangeReadNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link ArrayRangeReadNode#doIndexed}
     *   Parameter: {@link GetContextFrameNode} getFrame
     *   Inline method: {@link GetContextFrameNodeGen#inline}</pre>
     */
    private static final GetContextFrameNode INLINED_INDEXED_GET_FRAME_ = GetContextFrameNodeGen.inline(InlineTarget.create(GetContextFrameNode.class, STATE_0_ArrayRangeReadNode_UPDATER.subUpdater(4, 3)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link ArrayRangeReadNode#doIndexed}
     *   Parameter: {@link WriteLocalValueNode} writeIndex
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_INDEXED_WRITE_INDEX_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    @Child private ValueNode array_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ArrayRangeReadNode#doIndexed}
     *   1: SpecializationActive {@link ArrayRangeReadNode#doArray}
     *   2: SpecializationActive {@link ArrayRangeReadNode#doMultiSelect}
     *   3: SpecializationActive {@link ArrayRangeReadNode#doIllegal}
     *   4-6: InlinedCache
     *        Specialization: {@link ArrayRangeReadNode#doIndexed}
     *        Parameter: {@link GetContextFrameNode} getFrame
     *        Inline method: {@link GetContextFrameNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private ArrayRangeReadNodeGen(RangeIteration iterationNode, int resultSlot, SourceSection sourceSection, ValueNode array) {
        super(iterationNode, resultSlot, sourceSection);
        this.array_ = array;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object arrayValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[ArrayRangeReadNode.doArray(VirtualFrame, TailspinArray)] || SpecializationActive[ArrayRangeReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] || SpecializationActive[ArrayRangeReadNode.doIllegal(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && arrayValue instanceof IndexedArrayValue) {
                IndexedArrayValue arrayValue_ = (IndexedArrayValue) arrayValue;
                return doIndexed(frameValue, arrayValue_, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayRangeReadNode.doArray(VirtualFrame, TailspinArray)] */ && arrayValue instanceof TailspinArray) {
                TailspinArray arrayValue_ = (TailspinArray) arrayValue;
                return doArray(frameValue, arrayValue_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayRangeReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */ && arrayValue instanceof ArrayList<?>) {
                ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
                return doMultiSelect(frameValue, arrayValue_);
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIllegal(Object)] */) {
                return doIllegal(arrayValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arrayValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object arrayValue_ = this.array_.executeGeneric(frameValue);
        if ((state_0 & 0b1111) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[ArrayRangeReadNode.doArray(VirtualFrame, TailspinArray)] || SpecializationActive[ArrayRangeReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] || SpecializationActive[ArrayRangeReadNode.doIllegal(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && arrayValue_ instanceof IndexedArrayValue) {
                IndexedArrayValue arrayValue__ = (IndexedArrayValue) arrayValue_;
                return doIndexed(frameValue, arrayValue__, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayRangeReadNode.doArray(VirtualFrame, TailspinArray)] */ && arrayValue_ instanceof TailspinArray) {
                TailspinArray arrayValue__ = (TailspinArray) arrayValue_;
                return doArray(frameValue, arrayValue__);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ArrayRangeReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */ && arrayValue_ instanceof ArrayList<?>) {
                ArrayList<?> arrayValue__ = (ArrayList<?>) arrayValue_;
                return doMultiSelect(frameValue, arrayValue__);
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[ArrayRangeReadNode.doIllegal(Object)] */) {
                return doIllegal(arrayValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arrayValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object arrayValue) {
        int state_0 = this.state_0_;
        if (arrayValue instanceof IndexedArrayValue) {
            IndexedArrayValue arrayValue_ = (IndexedArrayValue) arrayValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayRangeReadNode.doIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */;
            this.state_0_ = state_0;
            return doIndexed(frameValue, arrayValue_, INLINED_INDEXED_GET_FRAME_, INLINED_INDEXED_WRITE_INDEX_);
        }
        if (arrayValue instanceof TailspinArray) {
            TailspinArray arrayValue_ = (TailspinArray) arrayValue;
            state_0 = state_0 | 0b10 /* add SpecializationActive[ArrayRangeReadNode.doArray(VirtualFrame, TailspinArray)] */;
            this.state_0_ = state_0;
            return doArray(frameValue, arrayValue_);
        }
        if (arrayValue instanceof ArrayList<?>) {
            ArrayList<?> arrayValue_ = (ArrayList<?>) arrayValue;
            state_0 = state_0 | 0b100 /* add SpecializationActive[ArrayRangeReadNode.doMultiSelect(VirtualFrame, ArrayList<>)] */;
            this.state_0_ = state_0;
            return doMultiSelect(frameValue, arrayValue_);
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[ArrayRangeReadNode.doIllegal(Object)] */;
        this.state_0_ = state_0;
        return doIllegal(arrayValue);
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
    public static ArrayRangeReadNode create(RangeIteration iterationNode, int resultSlot, SourceSection sourceSection, ValueNode array) {
        return new ArrayRangeReadNodeGen(iterationNode, resultSlot, sourceSection, array);
    }

}
