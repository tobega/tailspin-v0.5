// CheckStyle: start generated
package tailspin.language.nodes.value;

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

/**
 * Debug Info: <pre>
 *   Specialization {@link GetContextFrameNode#doLocal}
 *     Activation probability: 0,48333
 *     With/without class size: 9/0 bytes
 *   Specialization {@link GetContextFrameNode#doUplevel}
 *     Activation probability: 0,33333
 *     With/without class size: 8/0 bytes
 *   Specialization {@link GetContextFrameNode#doNone}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(GetContextFrameNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class GetContextFrameNodeGen extends GetContextFrameNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link GetContextFrameNode#doLocal}
     *   1: SpecializationActive {@link GetContextFrameNode#doUplevel}
     *   2: SpecializationActive {@link GetContextFrameNode#doNone}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private GetContextFrameNodeGen() {
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Node arg0Value, int arg1Value) {
        if (!((state_0 & 0b1) != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */) && (arg1Value < 0)) {
            return false;
        }
        if (!((state_0 & 0b10) != 0 /* is SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */) && (arg1Value >= 0)) {
            return false;
        }
        return true;
    }

    @Override
    public VirtualFrame execute(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] || SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] || SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */) {
                if ((arg1Value < 0)) {
                    return doLocal(frameValue, arg1Value);
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */) {
                if ((arg1Value >= 0)) {
                    return doUplevel(frameValue, arg1Value);
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */) {
                if (fallbackGuard_(state_0, arg0Value, arg1Value)) {
                    return doNone(frameValue, arg1Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value, arg1Value);
    }

    private VirtualFrame executeAndSpecialize(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
        int state_0 = this.state_0_;
        if ((arg1Value < 0)) {
            state_0 = state_0 | 0b1 /* add SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */;
            this.state_0_ = state_0;
            return doLocal(frameValue, arg1Value);
        }
        if ((arg1Value >= 0)) {
            state_0 = state_0 | 0b10 /* add SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */;
            this.state_0_ = state_0;
            return doUplevel(frameValue, arg1Value);
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */;
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
    public static GetContextFrameNode create() {
        return new GetContextFrameNodeGen();
    }

    /**
     * Required Fields: <ul>
     * <li>{@link Inlined#state_0_}
     * </ul>
     */
    @NeverDefault
    public static GetContextFrameNode inline(@RequiredField(bits = 3, value = StateField.class) InlineTarget target) {
        return new GetContextFrameNodeGen.Inlined(target);
    }

    @GeneratedBy(GetContextFrameNode.class)
    @DenyReplace
    private static final class Inlined extends GetContextFrameNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link GetContextFrameNode#doLocal}
         *   1: SpecializationActive {@link GetContextFrameNode#doUplevel}
         *   2: SpecializationActive {@link GetContextFrameNode#doNone}
         * </pre>
         */
        private final StateField state_0_;

        private Inlined(InlineTarget target) {
            assert target.getTargetClass().isAssignableFrom(GetContextFrameNode.class);
            this.state_0_ = target.getState(0, 3);
        }

        @SuppressWarnings("static-method")
        private boolean fallbackGuard_(int state_0, Node arg0Value, int arg1Value) {
            if (!((state_0 & 0b1) != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */) && (arg1Value < 0)) {
                return false;
            }
            if (!((state_0 & 0b10) != 0 /* is SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */) && (arg1Value >= 0)) {
                return false;
            }
            return true;
        }

        @Override
        public VirtualFrame execute(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if (state_0 != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] || SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] || SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */) {
                    if ((arg1Value < 0)) {
                        return doLocal(frameValue, arg1Value);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */) {
                    if ((arg1Value >= 0)) {
                        return doUplevel(frameValue, arg1Value);
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */) {
                    if (fallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return doNone(frameValue, arg1Value);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(frameValue, arg0Value, arg1Value);
        }

        private VirtualFrame executeAndSpecialize(VirtualFrame frameValue, Node arg0Value, int arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if ((arg1Value < 0)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[GetContextFrameNode.doLocal(VirtualFrame, int)] */;
                this.state_0_.set(arg0Value, state_0);
                return doLocal(frameValue, arg1Value);
            }
            if ((arg1Value >= 0)) {
                state_0 = state_0 | 0b10 /* add SpecializationActive[GetContextFrameNode.doUplevel(VirtualFrame, int)] */;
                this.state_0_.set(arg0Value, state_0);
                return doUplevel(frameValue, arg1Value);
            }
            state_0 = state_0 | 0b100 /* add SpecializationActive[GetContextFrameNode.doNone(VirtualFrame, int)] */;
            this.state_0_.set(arg0Value, state_0);
            return doNone(frameValue, arg1Value);
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }

    }
}
