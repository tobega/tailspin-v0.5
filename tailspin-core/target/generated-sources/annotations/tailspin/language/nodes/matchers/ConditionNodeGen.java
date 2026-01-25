// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;

/**
 * Debug Info: <pre>
 *   Specialization {@link ConditionNode#doGeneric}
 *     Activation probability: 1,00000
 *     With/without class size: 20/0 bytes
 * </pre>
 */
@GeneratedBy(ConditionNode.class)
@SuppressWarnings("javadoc")
public final class ConditionNodeGen extends ConditionNode {

    /**
     * Source Info: <pre>
     *   Specialization: {@link ConditionNode#doGeneric}
     *   Parameter: {@link WriteLocalValueNode} writeCv
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_WRITE_CV_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    private ConditionNodeGen(int cvSlot, ValueNode toMatchNode, MatcherNode matcherNode, SourceSection sourceSection) {
        super(cvSlot, toMatchNode, matcherNode, sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        return doGeneric(frameValue, arg0Value, INLINED_WRITE_CV_);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        return doGeneric(frameValue, arg0Value, INLINED_WRITE_CV_);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static ConditionNode create(int cvSlot, ValueNode toMatchNode, MatcherNode matcherNode, SourceSection sourceSection) {
        return new ConditionNodeGen(cvSlot, toMatchNode, matcherNode, sourceSection);
    }

}
