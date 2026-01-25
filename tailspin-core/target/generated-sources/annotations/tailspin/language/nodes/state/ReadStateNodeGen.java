// CheckStyle: start generated
package tailspin.language.nodes.state;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.ReferenceField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link ReadStateNode#doFreezeRead}
 *     Activation probability: 1,00000
 *     With/without class size: 36/13 bytes
 * </pre>
 */
@GeneratedBy(ReadStateNode.class)
@SuppressWarnings("javadoc")
public final class ReadStateNodeGen extends ReadStateNode {

    private static final StateField STATE_0_ReadStateNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadStateNode#doFreezeRead}
     *   Parameter: {@link FreezeNode} freezer
     *   Inline method: {@link FreezeNodeGen#inline}</pre>
     */
    private static final FreezeNode INLINED_FREEZER_ = FreezeNodeGen.inline(InlineTarget.create(FreezeNode.class, STATE_0_ReadStateNode_UPDATER.subUpdater(0, 4), ReferenceField.create(MethodHandles.lookup(), "freezer__field1_", Node.class), ReferenceField.create(MethodHandles.lookup(), "freezer__field2_", Node.class), ReferenceField.create(MethodHandles.lookup(), "freezer__field3_", Node.class)));

    @Child private ValueNode child0_;
    /**
     * State Info: <pre>
     *   0-3: InlinedCache
     *        Specialization: {@link ReadStateNode#doFreezeRead}
     *        Parameter: {@link FreezeNode} freezer
     *        Inline method: {@link FreezeNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadStateNode#doFreezeRead}
     *   Parameter: {@link FreezeNode} freezer
     *   Inline method: {@link FreezeNodeGen#inline}
     *   Inline field: {@link Node} field1</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node freezer__field1_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadStateNode#doFreezeRead}
     *   Parameter: {@link FreezeNode} freezer
     *   Inline method: {@link FreezeNodeGen#inline}
     *   Inline field: {@link Node} field2</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node freezer__field2_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link ReadStateNode#doFreezeRead}
     *   Parameter: {@link FreezeNode} freezer
     *   Inline method: {@link FreezeNodeGen#inline}
     *   Inline field: {@link Node} field3</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node freezer__field3_;

    private ReadStateNodeGen(SourceSection sourceSection, ValueNode child0) {
        super(sourceSection);
        this.child0_ = child0;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        Object child0Value_ = this.child0_.executeGeneric(frameValue);
        return doFreezeRead(child0Value_, INLINED_FREEZER_);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static ReadStateNode create(SourceSection sourceSection, ValueNode child0) {
        return new ReadStateNodeGen(sourceSection, child0);
    }

}
