// CheckStyle: start generated
package tailspin.language.nodes.state;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link AppendStateNode#appendArrayMany}
 *     Activation probability: 0,48333
 *     With/without class size: 9/0 bytes
 *   Specialization {@link AppendStateNode#appendArray}
 *     Activation probability: 0,33333
 *     With/without class size: 8/0 bytes
 *   Specialization {@link AppendStateNode#cannotAppend}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(AppendStateNode.class)
@SuppressWarnings("javadoc")
public final class AppendStateNodeGen extends AppendStateNode {

    @Child private ValueNode target_;
    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link AppendStateNode#appendArrayMany}
     *   1: SpecializationActive {@link AppendStateNode#appendArray}
     *   2: SpecializationActive {@link AppendStateNode#cannotAppend}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private AppendStateNodeGen(SourceSection sourceSection, ValueNode target, ValueNode value) {
        super(sourceSection);
        this.target_ = target;
        this.value_ = value;
    }

    @Override
    public Object executeDirect(Object targetValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] || SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] || SpecializationActive[AppendStateNode.cannotAppend(Object, Object)] */) {
            if ((state_0 & 0b11) != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] || SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] */ && targetValue instanceof TailspinArray) {
                TailspinArray targetValue_ = (TailspinArray) targetValue;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] */ && valueValue instanceof ArrayList<?>) {
                    ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                    return appendArrayMany(targetValue_, valueValue_);
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] */) {
                    return appendArray(targetValue_, valueValue);
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[AppendStateNode.cannotAppend(Object, Object)] */) {
                return cannotAppend(targetValue, valueValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(targetValue, valueValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetValue_ = this.target_.executeGeneric(frameValue);
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] || SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] || SpecializationActive[AppendStateNode.cannotAppend(Object, Object)] */) {
            if ((state_0 & 0b11) != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] || SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] */ && targetValue_ instanceof TailspinArray) {
                TailspinArray targetValue__ = (TailspinArray) targetValue_;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] */ && valueValue_ instanceof ArrayList<?>) {
                    ArrayList<?> valueValue__ = (ArrayList<?>) valueValue_;
                    return appendArrayMany(targetValue__, valueValue__);
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] */) {
                    return appendArray(targetValue__, valueValue_);
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[AppendStateNode.cannotAppend(Object, Object)] */) {
                return cannotAppend(targetValue_, valueValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(targetValue_, valueValue_);
    }

    private Object executeAndSpecialize(Object targetValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (targetValue instanceof TailspinArray) {
            TailspinArray targetValue_ = (TailspinArray) targetValue;
            if (valueValue instanceof ArrayList<?>) {
                ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                state_0 = state_0 | 0b1 /* add SpecializationActive[AppendStateNode.appendArrayMany(TailspinArray, ArrayList<>)] */;
                this.state_0_ = state_0;
                return appendArrayMany(targetValue_, valueValue_);
            }
            state_0 = state_0 | 0b10 /* add SpecializationActive[AppendStateNode.appendArray(TailspinArray, Object)] */;
            this.state_0_ = state_0;
            return appendArray(targetValue_, valueValue);
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[AppendStateNode.cannotAppend(Object, Object)] */;
        this.state_0_ = state_0;
        return cannotAppend(targetValue, valueValue);
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
    public static AppendStateNode create(SourceSection sourceSection, ValueNode target, ValueNode value) {
        return new AppendStateNodeGen(sourceSection, target, value);
    }

}
