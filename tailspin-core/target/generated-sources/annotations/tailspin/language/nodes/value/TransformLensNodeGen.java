// CheckStyle: start generated
package tailspin.language.nodes.value;

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
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.AppendResultNode.MergeResultNode;
import tailspin.language.nodes.transform.AppendResultNodeFactory.MergeResultNodeGen;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;
import tailspin.language.runtime.IndexedArrayValue;

/**
 * Debug Info: <pre>
 *   Specialization {@link TransformLensNode#doTransformMany}
 *     Activation probability: 0,48333
 *     With/without class size: 13/1 bytes
 *   Specialization {@link TransformLensNode#doTransformIndexed}
 *     Activation probability: 0,33333
 *     With/without class size: 10/1 bytes
 *   Specialization {@link TransformLensNode#doTransformOne}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(TransformLensNode.class)
@SuppressWarnings("javadoc")
public final class TransformLensNodeGen extends TransformLensNode {

    private static final StateField STATE_0_TransformLensNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link TransformLensNode#doTransformMany}
     *   Parameter: {@link MergeResultNode} mergeResultNode
     *   Inline method: {@link MergeResultNodeGen#inline}</pre>
     */
    private static final MergeResultNode INLINED_TRANSFORM_MANY_MERGE_RESULT_NODE_ = MergeResultNodeGen.inline(InlineTarget.create(MergeResultNode.class, STATE_0_TransformLensNode_UPDATER.subUpdater(3, 6)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link TransformLensNode#doTransformIndexed}
     *   Parameter: {@link GetContextFrameNode} getFrame
     *   Inline method: {@link GetContextFrameNodeGen#inline}</pre>
     */
    private static final GetContextFrameNode INLINED_TRANSFORM_INDEXED_GET_FRAME_ = GetContextFrameNodeGen.inline(InlineTarget.create(GetContextFrameNode.class, STATE_0_TransformLensNode_UPDATER.subUpdater(9, 3)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link TransformLensNode#doTransformIndexed}
     *   Parameter: {@link WriteLocalValueNode} writeIndex
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_TRANSFORM_INDEXED_WRITE_INDEX_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));
    /**
     * Source Info: <pre>
     *   Specialization: {@link TransformLensNode#doTransformOne}
     *   Parameter: {@link WriteLocalValueNode} writeLocalValueNode
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_TRANSFORM_ONE_WRITE_LOCAL_VALUE_NODE_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    @Child private ValueNode child0_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link TransformLensNode#doTransformMany}
     *   1: SpecializationActive {@link TransformLensNode#doTransformIndexed}
     *   2: SpecializationActive {@link TransformLensNode#doTransformOne}
     *   3-8: InlinedCache
     *        Specialization: {@link TransformLensNode#doTransformMany}
     *        Parameter: {@link MergeResultNode} mergeResultNode
     *        Inline method: {@link MergeResultNodeGen#inline}
     *   9-11: InlinedCache
     *        Specialization: {@link TransformLensNode#doTransformIndexed}
     *        Parameter: {@link GetContextFrameNode} getFrame
     *        Inline method: {@link GetContextFrameNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private TransformLensNodeGen(int cvSlot, TransformNode transformNode, int resultSlot, SourceSection sourceSection, ValueNode child0) {
        super(cvSlot, transformNode, resultSlot, sourceSection);
        this.child0_ = child0;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object child0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111) != 0 /* is SpecializationActive[TransformLensNode.doTransformMany(VirtualFrame, ArrayList<>, MergeResultNode)] || SpecializationActive[TransformLensNode.doTransformIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[TransformLensNode.doTransformOne(VirtualFrame, Object, WriteLocalValueNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[TransformLensNode.doTransformMany(VirtualFrame, ArrayList<>, MergeResultNode)] */ && child0Value instanceof ArrayList<?>) {
                ArrayList<?> child0Value_ = (ArrayList<?>) child0Value;
                return doTransformMany(frameValue, child0Value_, INLINED_TRANSFORM_MANY_MERGE_RESULT_NODE_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[TransformLensNode.doTransformIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && child0Value instanceof IndexedArrayValue) {
                IndexedArrayValue child0Value_ = (IndexedArrayValue) child0Value;
                return doTransformIndexed(frameValue, child0Value_, INLINED_TRANSFORM_INDEXED_GET_FRAME_, INLINED_TRANSFORM_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[TransformLensNode.doTransformOne(VirtualFrame, Object, WriteLocalValueNode)] */) {
                return doTransformOne(frameValue, child0Value, INLINED_TRANSFORM_ONE_WRITE_LOCAL_VALUE_NODE_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, child0Value);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object child0Value_ = this.child0_.executeGeneric(frameValue);
        if ((state_0 & 0b111) != 0 /* is SpecializationActive[TransformLensNode.doTransformMany(VirtualFrame, ArrayList<>, MergeResultNode)] || SpecializationActive[TransformLensNode.doTransformIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] || SpecializationActive[TransformLensNode.doTransformOne(VirtualFrame, Object, WriteLocalValueNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[TransformLensNode.doTransformMany(VirtualFrame, ArrayList<>, MergeResultNode)] */ && child0Value_ instanceof ArrayList<?>) {
                ArrayList<?> child0Value__ = (ArrayList<?>) child0Value_;
                return doTransformMany(frameValue, child0Value__, INLINED_TRANSFORM_MANY_MERGE_RESULT_NODE_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[TransformLensNode.doTransformIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */ && child0Value_ instanceof IndexedArrayValue) {
                IndexedArrayValue child0Value__ = (IndexedArrayValue) child0Value_;
                return doTransformIndexed(frameValue, child0Value__, INLINED_TRANSFORM_INDEXED_GET_FRAME_, INLINED_TRANSFORM_INDEXED_WRITE_INDEX_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[TransformLensNode.doTransformOne(VirtualFrame, Object, WriteLocalValueNode)] */) {
                return doTransformOne(frameValue, child0Value_, INLINED_TRANSFORM_ONE_WRITE_LOCAL_VALUE_NODE_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, child0Value_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object child0Value) {
        int state_0 = this.state_0_;
        if (child0Value instanceof ArrayList<?>) {
            ArrayList<?> child0Value_ = (ArrayList<?>) child0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[TransformLensNode.doTransformMany(VirtualFrame, ArrayList<>, MergeResultNode)] */;
            this.state_0_ = state_0;
            return doTransformMany(frameValue, child0Value_, INLINED_TRANSFORM_MANY_MERGE_RESULT_NODE_);
        }
        if (child0Value instanceof IndexedArrayValue) {
            IndexedArrayValue child0Value_ = (IndexedArrayValue) child0Value;
            state_0 = state_0 | 0b10 /* add SpecializationActive[TransformLensNode.doTransformIndexed(VirtualFrame, IndexedArrayValue, GetContextFrameNode, WriteLocalValueNode)] */;
            this.state_0_ = state_0;
            return doTransformIndexed(frameValue, child0Value_, INLINED_TRANSFORM_INDEXED_GET_FRAME_, INLINED_TRANSFORM_INDEXED_WRITE_INDEX_);
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[TransformLensNode.doTransformOne(VirtualFrame, Object, WriteLocalValueNode)] */;
        this.state_0_ = state_0;
        return doTransformOne(frameValue, child0Value, INLINED_TRANSFORM_ONE_WRITE_LOCAL_VALUE_NODE_);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b111) & ((state_0 & 0b111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static TransformLensNode create(int cvSlot, TransformNode transformNode, int resultSlot, SourceSection sourceSection, ValueNode child0) {
        return new TransformLensNodeGen(cvSlot, transformNode, resultSlot, sourceSection, child0);
    }

}
