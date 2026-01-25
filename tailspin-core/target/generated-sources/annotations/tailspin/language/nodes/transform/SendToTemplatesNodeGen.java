// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.DSLSupport;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.ReferenceField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.state.FreezeNode;
import tailspin.language.nodes.state.FreezeNodeGen;
import tailspin.language.nodes.state.GetDefiningScopeNode;
import tailspin.language.nodes.state.GetDefiningScopeNodeGen;
import tailspin.language.runtime.DefiningScope;
import tailspin.language.runtime.Templates;

/**
 * Debug Info: <pre>
 *   Specialization {@link SendToTemplatesNode#doAuxiliaryDispatch}
 *     Activation probability: 0,48333
 *     With/without class size: 11/0 bytes
 *   Specialization {@link SendToTemplatesNode#doTransactionDispatch}
 *     Activation probability: 0,33333
 *     With/without class size: 12/8 bytes
 *   Specialization {@link SendToTemplatesNode#doFree}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(SendToTemplatesNode.class)
@SuppressWarnings("javadoc")
public final class SendToTemplatesNodeGen extends SendToTemplatesNode {

    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    /**
     * Source Info: <pre>
     *   Specialization: {@link SendToTemplatesNode#doAuxiliaryDispatch}
     *   Parameter: {@link GetDefiningScopeNode} getScope
     *   Inline method: {@link GetDefiningScopeNodeGen#inline}</pre>
     */
    private static final GetDefiningScopeNode INLINED_GET_SCOPE = GetDefiningScopeNodeGen.inline(InlineTarget.create(GetDefiningScopeNode.class, STATE_0_UPDATER.subUpdater(3, 2)));

    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link SendToTemplatesNode#doAuxiliaryDispatch}
     *   1: SpecializationActive {@link SendToTemplatesNode#doTransactionDispatch}
     *   2: SpecializationActive {@link SendToTemplatesNode#doFree}
     *   3-4: InlinedCache
     *        Specialization: {@link SendToTemplatesNode#doAuxiliaryDispatch}
     *        Parameter: {@link GetDefiningScopeNode} getScope
     *        Inline method: {@link GetDefiningScopeNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link SendToTemplatesNode#doAuxiliaryDispatch}
     *   Parameter: {@link DispatchNode} dispatchNode</pre>
     */
    @Child private DispatchNode dispatchNode;
    /**
     * Source Info: <pre>
     *   Specialization: {@link SendToTemplatesNode#doTransactionDispatch}
     *   Parameter: {@link NewTransactionNode} createTransaction</pre>
     */
    @Child private NewTransactionNode transactionDispatch_createTransaction_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link SendToTemplatesNode#doTransactionDispatch}
     *   Parameter: {@link CommitTransactionNode} commitTransaction</pre>
     */
    @Child private CommitTransactionNode transactionDispatch_commitTransaction_;

    private SendToTemplatesNodeGen(boolean noTransaction, Templates templates, int callLevel, SourceSection sourceSection, ValueNode value) {
        super(noTransaction, templates, callLevel, sourceSection);
        this.value_ = value;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if ((state_0 & 0b111) != 0 /* is SpecializationActive[SendToTemplatesNode.doAuxiliaryDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode)] || SpecializationActive[SendToTemplatesNode.doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)] || SpecializationActive[SendToTemplatesNode.doFree(VirtualFrame, Object, DispatchNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[SendToTemplatesNode.doAuxiliaryDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode)] */) {
                {
                    DispatchNode dispatchNode_ = this.dispatchNode;
                    if (dispatchNode_ != null) {
                        assert DSLSupport.assertIdempotence((contextFrameLevel() >= 0));
                        assert DSLSupport.assertIdempotence((noTransaction));
                        doAuxiliaryDispatch(frameValue, valueValue_, INLINED_GET_SCOPE, dispatchNode_);
                        return;
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[SendToTemplatesNode.doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)] */) {
                {
                    DispatchNode dispatchNode_1 = this.dispatchNode;
                    if (dispatchNode_1 != null) {
                        NewTransactionNode createTransaction__ = this.transactionDispatch_createTransaction_;
                        if (createTransaction__ != null) {
                            CommitTransactionNode commitTransaction__ = this.transactionDispatch_commitTransaction_;
                            if (commitTransaction__ != null) {
                                assert DSLSupport.assertIdempotence((contextFrameLevel() >= 0));
                                assert DSLSupport.assertIdempotence((!(noTransaction)));
                                doTransactionDispatch(frameValue, valueValue_, INLINED_GET_SCOPE, dispatchNode_1, createTransaction__, commitTransaction__);
                                return;
                            }
                        }
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[SendToTemplatesNode.doFree(VirtualFrame, Object, DispatchNode)] */) {
                {
                    DispatchNode dispatchNode_2 = this.dispatchNode;
                    if (dispatchNode_2 != null) {
                        assert DSLSupport.assertIdempotence((contextFrameLevel() < 0));
                        doFree(frameValue, valueValue_, dispatchNode_2);
                        return;
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, valueValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object valueValue) {
        int state_0 = this.state_0_;
        if ((contextFrameLevel() >= 0) && (noTransaction)) {
            DispatchNode dispatchNode_;
            DispatchNode dispatchNode__shared = this.dispatchNode;
            if (dispatchNode__shared != null) {
                dispatchNode_ = dispatchNode__shared;
            } else {
                dispatchNode_ = this.insert((DispatchNodeGen.create()));
                if (dispatchNode_ == null) {
                    throw new IllegalStateException("Specialization 'doAuxiliaryDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode)' contains a shared cache with name 'dispatchNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.dispatchNode == null) {
                VarHandle.storeStoreFence();
                this.dispatchNode = dispatchNode_;
            }
            state_0 = state_0 | 0b1 /* add SpecializationActive[SendToTemplatesNode.doAuxiliaryDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode)] */;
            this.state_0_ = state_0;
            doAuxiliaryDispatch(frameValue, valueValue, INLINED_GET_SCOPE, dispatchNode_);
            return;
        }
        if ((contextFrameLevel() >= 0) && (!(noTransaction))) {
            DispatchNode dispatchNode_1;
            DispatchNode dispatchNode_1_shared = this.dispatchNode;
            if (dispatchNode_1_shared != null) {
                dispatchNode_1 = dispatchNode_1_shared;
            } else {
                dispatchNode_1 = this.insert((DispatchNodeGen.create()));
                if (dispatchNode_1 == null) {
                    throw new IllegalStateException("Specialization 'doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)' contains a shared cache with name 'dispatchNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.dispatchNode == null) {
                VarHandle.storeStoreFence();
                this.dispatchNode = dispatchNode_1;
            }
            NewTransactionNode createTransaction__ = this.insert((NewTransactionNodeGen.create()));
            Objects.requireNonNull(createTransaction__, "Specialization 'doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)' cache 'createTransaction' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.transactionDispatch_createTransaction_ = createTransaction__;
            CommitTransactionNode commitTransaction__ = this.insert((CommitTransactionNodeGen.create()));
            Objects.requireNonNull(commitTransaction__, "Specialization 'doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)' cache 'commitTransaction' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.transactionDispatch_commitTransaction_ = commitTransaction__;
            state_0 = state_0 | 0b10 /* add SpecializationActive[SendToTemplatesNode.doTransactionDispatch(VirtualFrame, Object, GetDefiningScopeNode, DispatchNode, NewTransactionNode, CommitTransactionNode)] */;
            this.state_0_ = state_0;
            doTransactionDispatch(frameValue, valueValue, INLINED_GET_SCOPE, dispatchNode_1, createTransaction__, commitTransaction__);
            return;
        }
        if ((contextFrameLevel() < 0)) {
            DispatchNode dispatchNode_2;
            DispatchNode dispatchNode_2_shared = this.dispatchNode;
            if (dispatchNode_2_shared != null) {
                dispatchNode_2 = dispatchNode_2_shared;
            } else {
                dispatchNode_2 = this.insert((DispatchNodeGen.create()));
                if (dispatchNode_2 == null) {
                    throw new IllegalStateException("Specialization 'doFree(VirtualFrame, Object, DispatchNode)' contains a shared cache with name 'dispatchNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.dispatchNode == null) {
                VarHandle.storeStoreFence();
                this.dispatchNode = dispatchNode_2;
            }
            state_0 = state_0 | 0b100 /* add SpecializationActive[SendToTemplatesNode.doFree(VirtualFrame, Object, DispatchNode)] */;
            this.state_0_ = state_0;
            doFree(frameValue, valueValue, dispatchNode_2);
            return;
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.value_}, valueValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b111) & ((state_0 & 0b111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static SendToTemplatesNode create(boolean noTransaction, Templates templates, int callLevel, SourceSection sourceSection, ValueNode value) {
        return new SendToTemplatesNodeGen(noTransaction, templates, callLevel, sourceSection, value);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link DispatchNode#dispatchDirectly}
     *     Activation probability: 1,00000
     *     With/without class size: 24/4 bytes
     * </pre>
     */
    @GeneratedBy(DispatchNode.class)
    @SuppressWarnings("javadoc")
    public static final class DispatchNodeGen extends DispatchNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DispatchNode#dispatchDirectly}
         * </pre>
         */
        @CompilationFinal private int state_0_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link DispatchNode#dispatchDirectly}
         *   Parameter: {@link DirectCallNode} directCallNode</pre>
         */
        @Child private DirectCallNode directCallNode_;

        private DispatchNodeGen() {
        }

        @Override
        public Object executeDispatch(Templates arg0Value, Object arg1Value, DefiningScope arg2Value, Object arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[SendToTemplatesNode.DispatchNode.dispatchDirectly(Templates, Object, DefiningScope, Object, DirectCallNode)] */) {
                {
                    DirectCallNode directCallNode__ = this.directCallNode_;
                    if (directCallNode__ != null) {
                        return DispatchNode.dispatchDirectly(arg0Value, arg1Value, arg2Value, arg3Value, directCallNode__);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        private Object executeAndSpecialize(Templates arg0Value, Object arg1Value, DefiningScope arg2Value, Object arg3Value) {
            int state_0 = this.state_0_;
            VarHandle.storeStoreFence();
            this.directCallNode_ = this.insert((DirectCallNode.create(arg0Value.getCallTarget())));
            state_0 = state_0 | 0b1 /* add SpecializationActive[SendToTemplatesNode.DispatchNode.dispatchDirectly(Templates, Object, DefiningScope, Object, DirectCallNode)] */;
            this.state_0_ = state_0;
            return DispatchNode.dispatchDirectly(arg0Value, arg1Value, arg2Value, arg3Value, this.directCallNode_);
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
        public static DispatchNode create() {
            return new DispatchNodeGen();
        }

    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link NewTransactionNode#doNull}
     *     Activation probability: 0,65000
     *     With/without class size: 11/0 bytes
     *   Specialization {@link NewTransactionNode#createTransactionScope}
     *     Activation probability: 0,35000
     *     With/without class size: 15/13 bytes
     * </pre>
     */
    @GeneratedBy(NewTransactionNode.class)
    @SuppressWarnings("javadoc")
    public static final class NewTransactionNodeGen extends NewTransactionNode {

        private static final StateField STATE_0_NewTransactionNode_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
        /**
         * Source Info: <pre>
         *   Specialization: {@link NewTransactionNode#createTransactionScope}
         *   Parameter: {@link FreezeNode} freezer
         *   Inline method: {@link FreezeNodeGen#inline}</pre>
         */
        private static final FreezeNode INLINED_CREATE_TRANSACTION_SCOPE_FREEZER_ = FreezeNodeGen.inline(InlineTarget.create(FreezeNode.class, STATE_0_NewTransactionNode_UPDATER.subUpdater(2, 4), ReferenceField.create(MethodHandles.lookup(), "createTransactionScope_freezer__field1_", Node.class), ReferenceField.create(MethodHandles.lookup(), "createTransactionScope_freezer__field2_", Node.class), ReferenceField.create(MethodHandles.lookup(), "createTransactionScope_freezer__field3_", Node.class)));

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link NewTransactionNode#doNull}
         *   1: SpecializationActive {@link NewTransactionNode#createTransactionScope}
         *   2-5: InlinedCache
         *        Specialization: {@link NewTransactionNode#createTransactionScope}
         *        Parameter: {@link FreezeNode} freezer
         *        Inline method: {@link FreezeNodeGen#inline}
         * </pre>
         */
        @CompilationFinal @UnsafeAccessedField private int state_0_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link NewTransactionNode#createTransactionScope}
         *   Parameter: {@link FreezeNode} freezer
         *   Inline method: {@link FreezeNodeGen#inline}
         *   Inline field: {@link Node} field1</pre>
         */
        @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node createTransactionScope_freezer__field1_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link NewTransactionNode#createTransactionScope}
         *   Parameter: {@link FreezeNode} freezer
         *   Inline method: {@link FreezeNodeGen#inline}
         *   Inline field: {@link Node} field2</pre>
         */
        @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node createTransactionScope_freezer__field2_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link NewTransactionNode#createTransactionScope}
         *   Parameter: {@link FreezeNode} freezer
         *   Inline method: {@link FreezeNodeGen#inline}
         *   Inline field: {@link Node} field3</pre>
         */
        @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node createTransactionScope_freezer__field3_;

        private NewTransactionNodeGen() {
        }

        @Override
        public DefiningScope execute(DefiningScope arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0b11) != 0 /* is SpecializationActive[SendToTemplatesNode.NewTransactionNode.doNull(DefiningScope)] || SpecializationActive[SendToTemplatesNode.NewTransactionNode.createTransactionScope(DefiningScope, FreezeNode)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[SendToTemplatesNode.NewTransactionNode.doNull(DefiningScope)] */) {
                    if ((arg0Value == null)) {
                        return doNull(arg0Value);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[SendToTemplatesNode.NewTransactionNode.createTransactionScope(DefiningScope, FreezeNode)] */) {
                    if ((arg0Value != null)) {
                        return createTransactionScope(arg0Value, INLINED_CREATE_TRANSACTION_SCOPE_FREEZER_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value);
        }

        private DefiningScope executeAndSpecialize(DefiningScope arg0Value) {
            int state_0 = this.state_0_;
            if ((arg0Value == null)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[SendToTemplatesNode.NewTransactionNode.doNull(DefiningScope)] */;
                this.state_0_ = state_0;
                return doNull(arg0Value);
            }
            if ((arg0Value != null)) {
                state_0 = state_0 | 0b10 /* add SpecializationActive[SendToTemplatesNode.NewTransactionNode.createTransactionScope(DefiningScope, FreezeNode)] */;
                this.state_0_ = state_0;
                return createTransactionScope(arg0Value, INLINED_CREATE_TRANSACTION_SCOPE_FREEZER_);
            }
            throw new UnsupportedSpecializationException(this, new Node[] {null}, arg0Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0b11) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                if (((state_0 & 0b11) & ((state_0 & 0b11) - 1)) == 0 /* is-single  */) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static NewTransactionNode create() {
            return new NewTransactionNodeGen();
        }

    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link CommitTransactionNode#doNull}
     *     Activation probability: 0,65000
     *     With/without class size: 11/0 bytes
     *   Specialization {@link CommitTransactionNode#commitTransactionScope}
     *     Activation probability: 0,35000
     *     With/without class size: 8/0 bytes
     * </pre>
     */
    @GeneratedBy(CommitTransactionNode.class)
    @SuppressWarnings("javadoc")
    public static final class CommitTransactionNodeGen extends CommitTransactionNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link CommitTransactionNode#doNull}
         *   1: SpecializationActive {@link CommitTransactionNode#commitTransactionScope}
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private CommitTransactionNodeGen() {
        }

        @Override
        public void execute(DefiningScope arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[SendToTemplatesNode.CommitTransactionNode.doNull(DefiningScope)] || SpecializationActive[SendToTemplatesNode.CommitTransactionNode.commitTransactionScope(DefiningScope)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[SendToTemplatesNode.CommitTransactionNode.doNull(DefiningScope)] */) {
                    if ((arg0Value == null)) {
                        doNull(arg0Value);
                        return;
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[SendToTemplatesNode.CommitTransactionNode.commitTransactionScope(DefiningScope)] */) {
                    if ((arg0Value != null)) {
                        commitTransactionScope(arg0Value);
                        return;
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(arg0Value);
            return;
        }

        private void executeAndSpecialize(DefiningScope arg0Value) {
            int state_0 = this.state_0_;
            if ((arg0Value == null)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[SendToTemplatesNode.CommitTransactionNode.doNull(DefiningScope)] */;
                this.state_0_ = state_0;
                doNull(arg0Value);
                return;
            }
            if ((arg0Value != null)) {
                state_0 = state_0 | 0b10 /* add SpecializationActive[SendToTemplatesNode.CommitTransactionNode.commitTransactionScope(DefiningScope)] */;
                this.state_0_ = state_0;
                commitTransactionScope(arg0Value);
                return;
            }
            throw new UnsupportedSpecializationException(this, new Node[] {null}, arg0Value);
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
        public static CommitTransactionNode create() {
            return new CommitTransactionNodeGen();
        }

    }
}
