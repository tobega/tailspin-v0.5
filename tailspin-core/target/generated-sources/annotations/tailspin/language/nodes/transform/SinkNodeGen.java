// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link SinkNode#doNull}
 *     Activation probability: 0,48333
 *     With/without class size: 9/0 bytes
 *   Specialization {@link SinkNode#doEmpty}
 *     Activation probability: 0,33333
 *     With/without class size: 8/0 bytes
 *   Specialization {@link SinkNode#unexpectedValues}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(SinkNode.class)
@SuppressWarnings("javadoc")
public final class SinkNodeGen extends SinkNode {

    @Child private ValueNode result_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link SinkNode#doNull}
     *   1: SpecializationActive {@link SinkNode#doEmpty}
     *   2: SpecializationActive {@link SinkNode#unexpectedValues}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private SinkNodeGen(SourceSection sourceSection, ValueNode result) {
        super(sourceSection);
        this.result_ = result;
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object resultValue_ = this.result_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[SinkNode.doNull(Object)] || SpecializationActive[SinkNode.doEmpty(ArrayList<>)] || SpecializationActive[SinkNode.unexpectedValues(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[SinkNode.doNull(Object)] */) {
                if ((resultValue_ == null)) {
                    doNull(resultValue_);
                    return;
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[SinkNode.doEmpty(ArrayList<>)] */ && resultValue_ instanceof ArrayList<?>) {
                ArrayList<?> resultValue__ = (ArrayList<?>) resultValue_;
                if ((resultValue__ != null)) {
                    doEmpty(resultValue__);
                    return;
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[SinkNode.unexpectedValues(Object)] */) {
                unexpectedValues(resultValue_);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(resultValue_);
        return;
    }

    private void executeAndSpecialize(Object resultValue) {
        int state_0 = this.state_0_;
        if ((resultValue == null)) {
            state_0 = state_0 | 0b1 /* add SpecializationActive[SinkNode.doNull(Object)] */;
            this.state_0_ = state_0;
            doNull(resultValue);
            return;
        }
        if (resultValue instanceof ArrayList<?>) {
            ArrayList<?> resultValue_ = (ArrayList<?>) resultValue;
            if ((resultValue_ != null)) {
                state_0 = state_0 | 0b10 /* add SpecializationActive[SinkNode.doEmpty(ArrayList<>)] */;
                this.state_0_ = state_0;
                doEmpty(resultValue_);
                return;
            }
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[SinkNode.unexpectedValues(Object)] */;
        this.state_0_ = state_0;
        unexpectedValues(resultValue);
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
    public static SinkNode create(SourceSection sourceSection, ValueNode result) {
        return new SinkNodeGen(sourceSection, result);
    }

}
