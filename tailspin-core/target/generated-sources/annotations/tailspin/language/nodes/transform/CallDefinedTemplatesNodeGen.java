// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TemplatesInstance;

/**
 * Debug Info: <pre>
 *   Specialization {@link CallDefinedTemplatesNode#doDispatch}
 *     Activation probability: 1,00000
 *     With/without class size: 24/4 bytes
 * </pre>
 */
@GeneratedBy(CallDefinedTemplatesNode.class)
@SuppressWarnings("javadoc")
public final class CallDefinedTemplatesNodeGen extends CallDefinedTemplatesNode {

    @Child private ValueNode value_;
    @Child private ValueNode templates_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link CallDefinedTemplatesNode#doDispatch}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link CallDefinedTemplatesNode#doDispatch}
     *   Parameter: {@link DispatchNode} dispatchNode</pre>
     */
    @Child private DispatchNode dispatchNode_;

    private CallDefinedTemplatesNodeGen(SourceSection sourceSection, ValueNode value, ValueNode templates) {
        super(sourceSection);
        this.value_ = value;
        this.templates_ = templates;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        Object templatesValue_ = this.templates_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[CallDefinedTemplatesNode.doDispatch(VirtualFrame, Object, TemplatesInstance, DispatchNode)] */ && templatesValue_ instanceof TemplatesInstance) {
            TemplatesInstance templatesValue__ = (TemplatesInstance) templatesValue_;
            {
                DispatchNode dispatchNode__ = this.dispatchNode_;
                if (dispatchNode__ != null) {
                    doDispatch(frameValue, valueValue_, templatesValue__, dispatchNode__);
                    return;
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, valueValue_, templatesValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object valueValue, Object templatesValue) {
        int state_0 = this.state_0_;
        if (templatesValue instanceof TemplatesInstance) {
            TemplatesInstance templatesValue_ = (TemplatesInstance) templatesValue;
            DispatchNode dispatchNode__ = this.insert((DispatchNodeGen.create()));
            Objects.requireNonNull(dispatchNode__, "Specialization 'doDispatch(VirtualFrame, Object, TemplatesInstance, DispatchNode)' cache 'dispatchNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.dispatchNode_ = dispatchNode__;
            state_0 = state_0 | 0b1 /* add SpecializationActive[CallDefinedTemplatesNode.doDispatch(VirtualFrame, Object, TemplatesInstance, DispatchNode)] */;
            this.state_0_ = state_0;
            doDispatch(frameValue, valueValue, templatesValue_, dispatchNode__);
            return;
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.value_, this.templates_}, valueValue, templatesValue);
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
    public static CallDefinedTemplatesNode create(SourceSection sourceSection, ValueNode value, ValueNode templates) {
        return new CallDefinedTemplatesNodeGen(sourceSection, value, templates);
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
        public Object executeDispatch(TemplatesInstance arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[CallDefinedTemplatesNode.DispatchNode.dispatchDirectly(TemplatesInstance, Object, Object, DirectCallNode)] */) {
                {
                    DirectCallNode directCallNode__ = this.directCallNode_;
                    if (directCallNode__ != null) {
                        return DispatchNode.dispatchDirectly(arg0Value, arg1Value, arg2Value, directCallNode__);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private Object executeAndSpecialize(TemplatesInstance arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            VarHandle.storeStoreFence();
            this.directCallNode_ = this.insert((DirectCallNode.create(arg0Value.callTarget())));
            state_0 = state_0 | 0b1 /* add SpecializationActive[CallDefinedTemplatesNode.DispatchNode.dispatchDirectly(TemplatesInstance, Object, Object, DirectCallNode)] */;
            this.state_0_ = state_0;
            return DispatchNode.dispatchDirectly(arg0Value, arg1Value, arg2Value, this.directCallNode_);
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
}
