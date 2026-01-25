// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.ArrayList;
import tailspin.language.nodes.transform.AppendResultNode.MergeResultNode;

@GeneratedBy(AppendResultNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class AppendResultNodeFactory {

    /**
     * Debug Info: <pre>
     *   Specialization {@link MergeResultNode#doPreviousNull}
     *     Activation probability: 0,27381
     *     With/without class size: 7/0 bytes
     *   Specialization {@link MergeResultNode#doResultNull}
     *     Activation probability: 0,23095
     *     With/without class size: 6/0 bytes
     *   Specialization {@link MergeResultNode#doMerge}
     *     Activation probability: 0,18810
     *     With/without class size: 6/0 bytes
     *   Specialization {@link MergeResultNode#doAppend}
     *     Activation probability: 0,14524
     *     With/without class size: 5/0 bytes
     *   Specialization {@link MergeResultNode#doPrepend}
     *     Activation probability: 0,10238
     *     With/without class size: 5/0 bytes
     *   Specialization {@link MergeResultNode#doNewList}
     *     Activation probability: 0,05952
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(MergeResultNode.class)
    @SuppressWarnings("javadoc")
    public static final class MergeResultNodeGen extends MergeResultNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link MergeResultNode#doPreviousNull}
         *   1: SpecializationActive {@link MergeResultNode#doResultNull}
         *   2: SpecializationActive {@link MergeResultNode#doMerge}
         *   3: SpecializationActive {@link MergeResultNode#doAppend}
         *   4: SpecializationActive {@link MergeResultNode#doPrepend}
         *   5: SpecializationActive {@link MergeResultNode#doNewList}
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private MergeResultNodeGen() {
        }

        @Override
        public Object execute(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                if ((state_0 & 0b11) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] */) {
                        if ((arg1Value == null)) {
                            return doPreviousNull(arg1Value, arg2Value);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */) {
                        if ((arg2Value == null)) {
                            return doResultNull(arg1Value, arg2Value);
                        }
                    }
                }
                if ((state_0 & 0b1100) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */ && arg1Value instanceof ArrayList<?>) {
                    ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] */ && arg2Value instanceof ArrayList<?>) {
                        ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                        return doMerge(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */) {
                        if ((arg2Value != null)) {
                            return doAppend(arg1Value_, arg2Value);
                        }
                    }
                }
                if ((state_0 & 0b110000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                    if ((state_0 & 0b10000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] */ && arg2Value instanceof ArrayList<?>) {
                        ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                        if ((arg1Value != null)) {
                            return doPrepend(arg1Value, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                        return doNewList(arg1Value, arg2Value);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((arg1Value == null)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] */;
                this.state_0_ = state_0;
                return doPreviousNull(arg1Value, arg2Value);
            }
            if ((arg2Value == null)) {
                state_0 = state_0 | 0b10 /* add SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */;
                this.state_0_ = state_0;
                return doResultNull(arg1Value, arg2Value);
            }
            if (arg1Value instanceof ArrayList<?>) {
                ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                if (arg2Value instanceof ArrayList<?>) {
                    ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                    state_0 = state_0 | 0b100 /* add SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doMerge(arg1Value_, arg2Value_);
                }
                if ((arg2Value != null)) {
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */;
                    this.state_0_ = state_0;
                    return doAppend(arg1Value_, arg2Value);
                }
            }
            if (arg2Value instanceof ArrayList<?>) {
                ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                if ((arg1Value != null)) {
                    state_0 = state_0 | 0b10000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doPrepend(arg1Value, arg2Value_);
                }
            }
            state_0 = state_0 | 0b100000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */;
            this.state_0_ = state_0;
            return doNewList(arg1Value, arg2Value);
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
        public static MergeResultNode create() {
            return new MergeResultNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * </ul>
         */
        @NeverDefault
        public static MergeResultNode inline(@RequiredField(bits = 6, value = StateField.class) InlineTarget target) {
            return new MergeResultNodeGen.Inlined(target);
        }

        @GeneratedBy(MergeResultNode.class)
        @DenyReplace
        private static final class Inlined extends MergeResultNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link MergeResultNode#doPreviousNull}
             *   1: SpecializationActive {@link MergeResultNode#doResultNull}
             *   2: SpecializationActive {@link MergeResultNode#doMerge}
             *   3: SpecializationActive {@link MergeResultNode#doAppend}
             *   4: SpecializationActive {@link MergeResultNode#doPrepend}
             *   5: SpecializationActive {@link MergeResultNode#doNewList}
             * </pre>
             */
            private final StateField state_0_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(MergeResultNode.class);
                this.state_0_ = target.getState(0, 6);
            }

            @Override
            public Object execute(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (state_0 != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                    if ((state_0 & 0b11) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] || SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */) {
                        if ((state_0 & 0b1) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] */) {
                            if ((arg1Value == null)) {
                                return doPreviousNull(arg1Value, arg2Value);
                            }
                        }
                        if ((state_0 & 0b10) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */) {
                            if ((arg2Value == null)) {
                                return doResultNull(arg1Value, arg2Value);
                            }
                        }
                    }
                    if ((state_0 & 0b1100) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */ && arg1Value instanceof ArrayList<?>) {
                        ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                        if ((state_0 & 0b100) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] */ && arg2Value instanceof ArrayList<?>) {
                            ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                            return doMerge(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b1000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */) {
                            if ((arg2Value != null)) {
                                return doAppend(arg1Value_, arg2Value);
                            }
                        }
                    }
                    if ((state_0 & 0b110000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] || SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                        if ((state_0 & 0b10000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] */ && arg2Value instanceof ArrayList<?>) {
                            ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                            if ((arg1Value != null)) {
                                return doPrepend(arg1Value, arg2Value_);
                            }
                        }
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */) {
                            return doNewList(arg1Value, arg2Value);
                        }
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private Object executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((arg1Value == null)) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[AppendResultNode.MergeResultNode.doPreviousNull(Object, Object)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return doPreviousNull(arg1Value, arg2Value);
                }
                if ((arg2Value == null)) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[AppendResultNode.MergeResultNode.doResultNull(Object, Object)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return doResultNull(arg1Value, arg2Value);
                }
                if (arg1Value instanceof ArrayList<?>) {
                    ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                    if (arg2Value instanceof ArrayList<?>) {
                        ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                        state_0 = state_0 | 0b100 /* add SpecializationActive[AppendResultNode.MergeResultNode.doMerge(ArrayList<>, ArrayList<>)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doMerge(arg1Value_, arg2Value_);
                    }
                    if ((arg2Value != null)) {
                        state_0 = state_0 | 0b1000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doAppend(ArrayList<>, Object)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doAppend(arg1Value_, arg2Value);
                    }
                }
                if (arg2Value instanceof ArrayList<?>) {
                    ArrayList<?> arg2Value_ = (ArrayList<?>) arg2Value;
                    if ((arg1Value != null)) {
                        state_0 = state_0 | 0b10000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doPrepend(Object, ArrayList<>)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doPrepend(arg1Value, arg2Value_);
                    }
                }
                state_0 = state_0 | 0b100000 /* add SpecializationActive[AppendResultNode.MergeResultNode.doNewList(Object, Object)] */;
                this.state_0_.set(arg0Value, state_0);
                return doNewList(arg1Value, arg2Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
