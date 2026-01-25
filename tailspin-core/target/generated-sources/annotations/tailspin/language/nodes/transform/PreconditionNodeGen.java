// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link PreconditionNode#doLong}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link PreconditionNode#doGeneric}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(PreconditionNode.class)
@SuppressWarnings("javadoc")
public final class PreconditionNodeGen extends PreconditionNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link PreconditionNode#doLong}
     *   1: SpecializationActive {@link PreconditionNode#doGeneric}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private PreconditionNodeGen(ValueNode cvNode, MatcherNode precondition, SourceSection sourceSection) {
        super(cvNode, precondition, sourceSection);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) == 0 /* only-active SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] */ && (state_0 != 0  /* is-not SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] && SpecializationActive[PreconditionNode.doGeneric(VirtualFrame, Object)] */)) {
            executeVoid_long0(state_0, frameValue);
            return;
        } else {
            executeVoid_generic1(state_0, frameValue);
            return;
        }
    }

    private void executeVoid_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long cvNodeValue_;
        try {
            cvNodeValue_ = super.cvNode.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(frameValue, ex.getResult());
            return;
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] */;
        doLong(frameValue, cvNodeValue_);
        return;
    }

    private void executeVoid_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object cvNodeValue_ = super.cvNode.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] || SpecializationActive[PreconditionNode.doGeneric(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] */ && cvNodeValue_ instanceof Long) {
                long cvNodeValue__ = (long) cvNodeValue_;
                doLong(frameValue, cvNodeValue__);
                return;
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[PreconditionNode.doGeneric(VirtualFrame, Object)] */) {
                doGeneric(frameValue, cvNodeValue_);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, cvNodeValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object cvNodeValue) {
        int state_0 = this.state_0_;
        if (cvNodeValue instanceof Long) {
            long cvNodeValue_ = (long) cvNodeValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[PreconditionNode.doLong(VirtualFrame, long)] */;
            this.state_0_ = state_0;
            doLong(frameValue, cvNodeValue_);
            return;
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[PreconditionNode.doGeneric(VirtualFrame, Object)] */;
        this.state_0_ = state_0;
        doGeneric(frameValue, cvNodeValue);
        return;
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if ((state_0 & (state_0 - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static PreconditionNode create(ValueNode cvNode, MatcherNode precondition, SourceSection sourceSection) {
        return new PreconditionNodeGen(cvNode, precondition, sourceSection);
    }

}
