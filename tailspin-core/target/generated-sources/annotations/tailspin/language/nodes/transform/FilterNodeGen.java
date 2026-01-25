// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link FilterNode#doFilter}
 *     Activation probability: 1,00000
 *     With/without class size: 16/0 bytes
 * </pre>
 */
@GeneratedBy(FilterNode.class)
@SuppressWarnings("javadoc")
public final class FilterNodeGen extends FilterNode {

    @Child private ValueNode value_;
    @Child private MatcherNode matcher_;

    private FilterNodeGen(SourceSection sourceSection, ValueNode value, MatcherNode matcher) {
        super(sourceSection);
        this.value_ = value;
        this.matcher_ = matcher;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        boolean matcherValue_ = this.matcher_.executeMatcherGeneric(frameValue, valueValue_);
        doFilter(frameValue, valueValue_, matcherValue_);
        return;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static FilterNode create(SourceSection sourceSection, ValueNode value, MatcherNode matcher) {
        return new FilterNodeGen(sourceSection, value, matcher);
    }

}
