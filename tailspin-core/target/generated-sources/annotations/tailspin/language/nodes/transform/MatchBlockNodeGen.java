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
import java.util.List;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link MatchBlockNode#doLong}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link MatchBlockNode#doObject}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(MatchBlockNode.class)
@SuppressWarnings("javadoc")
public final class MatchBlockNodeGen extends MatchBlockNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MatchBlockNode#doLong}
     *   1: SpecializationActive {@link MatchBlockNode#doObject}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private MatchBlockNodeGen(ValueNode toMatchNode, List<MatchTemplateNode> matchTemplates, SourceSection sourceSection) {
        super(toMatchNode, matchTemplates, sourceSection);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) == 0 /* only-active SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] */ && (state_0 != 0  /* is-not SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] && SpecializationActive[MatchBlockNode.doObject(VirtualFrame, Object)] */)) {
            executeVoid_long0(state_0, frameValue);
            return;
        } else {
            executeVoid_generic1(state_0, frameValue);
            return;
        }
    }

    private void executeVoid_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long toMatchNodeValue_;
        try {
            toMatchNodeValue_ = super.toMatchNode.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(frameValue, ex.getResult());
            return;
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] */;
        doLong(frameValue, toMatchNodeValue_);
        return;
    }

    private void executeVoid_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object toMatchNodeValue_ = super.toMatchNode.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] || SpecializationActive[MatchBlockNode.doObject(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] */ && toMatchNodeValue_ instanceof Long) {
                long toMatchNodeValue__ = (long) toMatchNodeValue_;
                doLong(frameValue, toMatchNodeValue__);
                return;
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[MatchBlockNode.doObject(VirtualFrame, Object)] */) {
                doObject(frameValue, toMatchNodeValue_);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, toMatchNodeValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object toMatchNodeValue) {
        int state_0 = this.state_0_;
        if (toMatchNodeValue instanceof Long) {
            long toMatchNodeValue_ = (long) toMatchNodeValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[MatchBlockNode.doLong(VirtualFrame, long)] */;
            this.state_0_ = state_0;
            doLong(frameValue, toMatchNodeValue_);
            return;
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[MatchBlockNode.doObject(VirtualFrame, Object)] */;
        this.state_0_ = state_0;
        doObject(frameValue, toMatchNodeValue);
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
    public static MatchBlockNode create(ValueNode toMatchNode, List<MatchTemplateNode> matchTemplates, SourceSection sourceSection) {
        return new MatchBlockNodeGen(toMatchNode, matchTemplates, sourceSection);
    }

}
