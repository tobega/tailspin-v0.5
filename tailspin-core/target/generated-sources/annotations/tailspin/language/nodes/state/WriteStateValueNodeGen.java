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
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link WriteStateValueNode#writeObject}
 *     Activation probability: 1,00000
 *     With/without class size: 24/1 bytes
 * </pre>
 */
@GeneratedBy(WriteStateValueNode.class)
@SuppressWarnings("javadoc")
public final class WriteStateValueNodeGen extends WriteStateValueNode {

    private static final StateField STATE_0_WriteStateValueNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteStateValueNode#writeObject}
     *   Parameter: {@link GetDefiningScopeNode} getScope
     *   Inline method: {@link GetDefiningScopeNodeGen#inline}</pre>
     */
    private static final GetDefiningScopeNode INLINED_GET_SCOPE_ = GetDefiningScopeNodeGen.inline(InlineTarget.create(GetDefiningScopeNode.class, STATE_0_WriteStateValueNode_UPDATER.subUpdater(0, 2)));

    @Child private ValueNode valueExpr_;
    /**
     * State Info: <pre>
     *   0-1: InlinedCache
     *        Specialization: {@link WriteStateValueNode#writeObject}
     *        Parameter: {@link GetDefiningScopeNode} getScope
     *        Inline method: {@link GetDefiningScopeNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;

    private WriteStateValueNodeGen(int level, SourceSection sourceSection, ValueNode valueExpr) {
        super(level, sourceSection);
        this.valueExpr_ = valueExpr;
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        Object valueExprValue_ = this.valueExpr_.executeGeneric(frameValue);
        writeObject(frameValue, valueExprValue_, INLINED_GET_SCOPE_);
        return;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static WriteStateValueNode create(int level, SourceSection sourceSection, ValueNode valueExpr) {
        return new WriteStateValueNodeGen(level, sourceSection, valueExpr);
    }

}
