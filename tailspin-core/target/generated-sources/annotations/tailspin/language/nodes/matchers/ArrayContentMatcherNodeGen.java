// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link ArrayContentMatcherNode#findElement}
 *     Activation probability: 1,00000
 *     With/without class size: 16/0 bytes
 * </pre>
 */
@GeneratedBy(ArrayContentMatcherNode.class)
@SuppressWarnings("javadoc")
public final class ArrayContentMatcherNodeGen extends ArrayContentMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ArrayContentMatcherNode#findElement}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private ArrayContentMatcherNodeGen(MatcherNode contentMatcher, SourceSection sourceSection) {
        super(contentMatcher, sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[ArrayContentMatcherNode.findElement(VirtualFrame, TailspinArray)] */ && arg0Value instanceof TailspinArray) {
            TailspinArray arg0Value_ = (TailspinArray) arg0Value;
            return findElement(frameValue, arg0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        throw CompilerDirectives.shouldNotReachHere("Delegation failed.");
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof TailspinArray) {
            TailspinArray arg0Value_ = (TailspinArray) arg0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayContentMatcherNode.findElement(VirtualFrame, TailspinArray)] */;
            this.state_0_ = state_0;
            return findElement(frameValue, arg0Value_);
        }
        throw new UnsupportedSpecializationException(this, new Node[] {null}, arg0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            return NodeCost.MONOMORPHIC;
        }
    }

    @NeverDefault
    public static ArrayContentMatcherNode create(MatcherNode contentMatcher, SourceSection sourceSection) {
        return new ArrayContentMatcherNodeGen(contentMatcher, sourceSection);
    }

}
