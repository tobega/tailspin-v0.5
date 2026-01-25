// CheckStyle: start generated
package tailspin.language.nodes.state;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import tailspin.language.runtime.DefiningScope;

/**
 * Debug Info: <pre>
 *   Specialization {@link GetDefiningScopeNode#doUplevel}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link GetDefiningScopeNode#doNone}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(GetDefiningScopeNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class GetDefiningScopeNodeGen extends GetDefiningScopeNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link GetDefiningScopeNode#doUplevel}
     *   1: SpecializationActive {@link GetDefiningScopeNode#doNone}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private GetDefiningScopeNodeGen() {
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Node arg0Value, int arg1Value) {
        if (!((state_0 & 0b1) != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */) && (arg1Value >= 0)) {
            return false;
        }
        return true;
    }

    @Override
    public DefiningScope execute(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] || SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */) {
                if ((arg1Value >= 0)) {
                    return doUplevel(frameValue, arg1Value);
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */) {
                if (fallbackGuard_(state_0, arg0Value, arg1Value)) {
                    return doNone(frameValue, arg1Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value, arg1Value);
    }

    private DefiningScope executeAndSpecialize(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
        int state_0 = this.state_0_;
        if ((arg1Value >= 0)) {
            state_0 = state_0 | 0b1 /* add SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */;
            this.state_0_ = state_0;
            return doUplevel(frameValue, arg1Value);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */;
        this.state_0_ = state_0;
        return doNone(frameValue, arg1Value);
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
    public static GetDefiningScopeNode create() {
        return new GetDefiningScopeNodeGen();
    }

    /**
     * Required Fields: <ul>
     * <li>{@link Inlined#state_0_}
     * </ul>
     */
    @NeverDefault
    public static GetDefiningScopeNode inline(@RequiredField(bits = 2, value = StateField.class) InlineTarget target) {
        return new GetDefiningScopeNodeGen.Inlined(target);
    }

    @GeneratedBy(GetDefiningScopeNode.class)
    @DenyReplace
    private static final class Inlined extends GetDefiningScopeNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link GetDefiningScopeNode#doUplevel}
         *   1: SpecializationActive {@link GetDefiningScopeNode#doNone}
         * </pre>
         */
        private final StateField state_0_;

        private Inlined(InlineTarget target) {
            assert target.getTargetClass().isAssignableFrom(GetDefiningScopeNode.class);
            this.state_0_ = target.getState(0, 2);
        }

        @SuppressWarnings("static-method")
        private boolean fallbackGuard_(int state_0, Node arg0Value, int arg1Value) {
            if (!((state_0 & 0b1) != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */) && (arg1Value >= 0)) {
                return false;
            }
            return true;
        }

        @Override
        public DefiningScope execute(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if (state_0 != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] || SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */) {
                    if ((arg1Value >= 0)) {
                        return doUplevel(frameValue, arg1Value);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */) {
                    if (fallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return doNone(frameValue, arg1Value);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(frameValue, arg0Value, arg1Value);
        }

        private DefiningScope executeAndSpecialize(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if ((arg1Value >= 0)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[GetDefiningScopeNode.doUplevel(VirtualFrame, int)] */;
                this.state_0_.set(arg0Value, state_0);
                return doUplevel(frameValue, arg1Value);
            }
            state_0 = state_0 | 0b10 /* add SpecializationActive[GetDefiningScopeNode.doNone(VirtualFrame, int)] */;
            this.state_0_.set(arg0Value, state_0);
            return doNone(frameValue, arg1Value);
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }

    }
}
