// CheckStyle: start generated
package tailspin.language.nodes.value;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Reference;

/**
 * Debug Info: <pre>
 *   Specialization {@link WriteContextValueNode#writeObject}
 *     Activation probability: 1,00000
 *     With/without class size: 24/1 bytes
 * </pre>
 */
@GeneratedBy(WriteContextValueNode.class)
@SuppressWarnings("javadoc")
public final class WriteContextValueNodeGen extends WriteContextValueNode {

    private static final StateField STATE_0_WriteContextValueNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteContextValueNode#writeObject}
     *   Parameter: {@link GetContextFrameNode} getFrame
     *   Inline method: {@link GetContextFrameNodeGen#inline}</pre>
     */
    private static final GetContextFrameNode INLINED_GET_FRAME_ = GetContextFrameNodeGen.inline(InlineTarget.create(GetContextFrameNode.class, STATE_0_WriteContextValueNode_UPDATER.subUpdater(0, 3)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteContextValueNode#writeObject}
     *   Parameter: {@link WriteLocalValueNode} writeValue
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_WRITE_VALUE_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    @Child private ValueNode valueExpr_;
    /**
     * State Info: <pre>
     *   0-2: InlinedCache
     *        Specialization: {@link WriteContextValueNode#writeObject}
     *        Parameter: {@link GetContextFrameNode} getFrame
     *        Inline method: {@link GetContextFrameNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private WriteContextValueNodeGen(Reference reference, SourceSection sourceSection, ValueNode valueExpr) {
        super(reference, sourceSection);
        this.valueExpr_ = valueExpr;
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        Object valueExprValue_ = this.valueExpr_.executeGeneric(frameValue);
        writeObject(frameValue, valueExprValue_, INLINED_GET_FRAME_, INLINED_WRITE_VALUE_);
        return;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static WriteContextValueNode create(Reference reference, SourceSection sourceSection, ValueNode valueExpr) {
        return new WriteContextValueNodeGen(reference, sourceSection, valueExpr);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link WriteLocalValueNode#writeObject}
     *     Activation probability: 1,00000
     *     With/without class size: 16/0 bytes
     * </pre>
     */
    @GeneratedBy(WriteLocalValueNode.class)
    @SuppressWarnings("javadoc")
    public static final class WriteLocalValueNodeGen extends WriteLocalValueNode {

        private WriteLocalValueNodeGen() {
        }

        @Override
        public void executeGeneric(VirtualFrame frameValue, Node arg0Value, int arg1Value, Object arg2Value) {
            writeObject(frameValue, arg1Value, arg2Value);
            return;
        }

        @Override
        public void executeLong(VirtualFrame frameValue, Node arg0Value, int arg1Value, long arg2Value) {
            writeObject(frameValue, arg1Value, arg2Value);
            return;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @NeverDefault
        public static WriteLocalValueNode create() {
            return new WriteLocalValueNodeGen();
        }

        /**
         * Required Fields: <ul>
         * </ul>
         */
        @NeverDefault
        public static WriteLocalValueNode inline( InlineTarget target) {
            return new WriteLocalValueNodeGen.Inlined(target);
        }

        @GeneratedBy(WriteLocalValueNode.class)
        @DenyReplace
        private static final class Inlined extends WriteLocalValueNode {

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(WriteLocalValueNode.class);
            }

            @Override
            public void executeGeneric(VirtualFrame frameValue, Node arg0Value, int arg1Value, Object arg2Value) {
                writeObject(frameValue, arg1Value, arg2Value);
                return;
            }

            @Override
            public void executeLong(VirtualFrame frameValue, Node arg0Value, int arg1Value, long arg2Value) {
                writeObject(frameValue, arg1Value, arg2Value);
                return;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
