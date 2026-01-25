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
import tailspin.language.runtime.Reference;

/**
 * Debug Info: <pre>
 *   Specialization {@link ReadContextValueNode#readObject}
 *     Activation probability: 1,00000
 *     With/without class size: 24/1 bytes
 * </pre>
 */
@GeneratedBy(ReadContextValueNode.class)
@SuppressWarnings("javadoc")
public final class ReadContextValueNodeGen extends ReadContextValueNode {

    private static final StateField STATE_0_ReadContextValueNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadContextValueNode#readObject}
     *   Parameter: {@link GetContextFrameNode} getFrame
     *   Inline method: {@link GetContextFrameNodeGen#inline}</pre>
     */
    private static final GetContextFrameNode INLINED_GET_FRAME_ = GetContextFrameNodeGen.inline(InlineTarget.create(GetContextFrameNode.class, STATE_0_ReadContextValueNode_UPDATER.subUpdater(0, 3)));
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadContextValueNode#readObject}
     *   Parameter: {@link ReadLocalValueNode} readValue
     *   Inline method: {@link ReadLocalValueNodeGen#inline}</pre>
     */
    private static final ReadLocalValueNode INLINED_READ_VALUE_ = ReadLocalValueNodeGen.inline(InlineTarget.create(ReadLocalValueNode.class));

    /**
     * State Info: <pre>
     *   0-2: InlinedCache
     *        Specialization: {@link ReadContextValueNode#readObject}
     *        Parameter: {@link GetContextFrameNode} getFrame
     *        Inline method: {@link GetContextFrameNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private ReadContextValueNodeGen(Reference reference, SourceSection sourceSection) {
        super(reference, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        return readObject(frameValue, INLINED_GET_FRAME_, INLINED_READ_VALUE_);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static ReadContextValueNode create(Reference reference, SourceSection sourceSection) {
        return new ReadContextValueNodeGen(reference, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link ReadLocalValueNode#readObject}
     *     Activation probability: 1,00000
     *     With/without class size: 16/0 bytes
     * </pre>
     */
    @GeneratedBy(ReadLocalValueNode.class)
    @SuppressWarnings("javadoc")
    public static final class ReadLocalValueNodeGen extends ReadLocalValueNode {

        private ReadLocalValueNodeGen() {
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
            return readObject(frameValue, arg1Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @NeverDefault
        public static ReadLocalValueNode create() {
            return new ReadLocalValueNodeGen();
        }

        /**
         * Required Fields: <ul>
         * </ul>
         */
        @NeverDefault
        public static ReadLocalValueNode inline( InlineTarget target) {
            return new ReadLocalValueNodeGen.Inlined(target);
        }

        @GeneratedBy(ReadLocalValueNode.class)
        @DenyReplace
        private static final class Inlined extends ReadLocalValueNode {

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(ReadLocalValueNode.class);
            }

            @Override
            public Object executeGeneric(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
                return readObject(frameValue, arg1Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
