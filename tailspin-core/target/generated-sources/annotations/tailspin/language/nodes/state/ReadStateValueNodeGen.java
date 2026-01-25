// CheckStyle: start generated
package tailspin.language.nodes.state;

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

/**
 * Debug Info: <pre>
 *   Specialization {@link ReadStateValueNode#readObject}
 *     Activation probability: 1,00000
 *     With/without class size: 24/1 bytes
 * </pre>
 */
@GeneratedBy(ReadStateValueNode.class)
@SuppressWarnings("javadoc")
public final class ReadStateValueNodeGen extends ReadStateValueNode {

    private static final StateField STATE_0_ReadStateValueNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadStateValueNode#readObject}
     *   Parameter: {@link GetDefiningScopeNode} getScope
     *   Inline method: {@link GetDefiningScopeNodeGen#inline}</pre>
     */
    private static final GetDefiningScopeNode INLINED_GET_SCOPE_ = GetDefiningScopeNodeGen.inline(InlineTarget.create(GetDefiningScopeNode.class, STATE_0_ReadStateValueNode_UPDATER.subUpdater(0, 2)));

    /**
     * State Info: <pre>
     *   0-1: InlinedCache
     *        Specialization: {@link ReadStateValueNode#readObject}
     *        Parameter: {@link GetDefiningScopeNode} getScope
     *        Inline method: {@link GetDefiningScopeNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private ReadStateValueNodeGen(int level, SourceSection sourceSection) {
        super(level, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        return readObject(frameValue, INLINED_GET_SCOPE_);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static ReadStateValueNode create(int level, SourceSection sourceSection) {
        return new ReadStateValueNodeGen(level, sourceSection);
    }

}
