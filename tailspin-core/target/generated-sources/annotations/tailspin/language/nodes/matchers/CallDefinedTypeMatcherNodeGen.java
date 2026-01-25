// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.transform.CallDefinedTemplatesNode.DispatchNode;
import tailspin.language.nodes.transform.CallDefinedTemplatesNodeGen.DispatchNodeGen;
import tailspin.language.runtime.TemplatesInstance;

/**
 * Debug Info: <pre>
 *   Specialization {@link CallDefinedTypeMatcherNode#doDispatch}
 *     Activation probability: 1,00000
 *     With/without class size: 24/4 bytes
 * </pre>
 */
@GeneratedBy(CallDefinedTypeMatcherNode.class)
@SuppressWarnings("javadoc")
public final class CallDefinedTypeMatcherNodeGen extends CallDefinedTypeMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link CallDefinedTypeMatcherNode#doDispatch}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link CallDefinedTypeMatcherNode#doDispatch}
     *   Parameter: {@link DispatchNode} dispatchNode</pre>
     */
    @Child private DispatchNode dispatchNode_;

    private CallDefinedTypeMatcherNodeGen(TemplatesInstance templates, SourceSection sourceSection) {
        super(templates, sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[CallDefinedTypeMatcherNode.doDispatch(Object, DispatchNode)] */) {
            {
                DispatchNode dispatchNode__ = this.dispatchNode_;
                if (dispatchNode__ != null) {
                    return doDispatch(arg0Value, dispatchNode__);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[CallDefinedTypeMatcherNode.doDispatch(Object, DispatchNode)] */) {
            {
                DispatchNode dispatchNode__ = this.dispatchNode_;
                if (dispatchNode__ != null) {
                    return doDispatch(arg0Value, dispatchNode__);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        DispatchNode dispatchNode__ = this.insert((DispatchNodeGen.create()));
        Objects.requireNonNull(dispatchNode__, "Specialization 'doDispatch(Object, DispatchNode)' cache 'dispatchNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
        VarHandle.storeStoreFence();
        this.dispatchNode_ = dispatchNode__;
        state_0 = state_0 | 0b1 /* add SpecializationActive[CallDefinedTypeMatcherNode.doDispatch(Object, DispatchNode)] */;
        this.state_0_ = state_0;
        return doDispatch(arg0Value, dispatchNode__);
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
    public static CallDefinedTypeMatcherNode create(TemplatesInstance templates, SourceSection sourceSection) {
        return new CallDefinedTypeMatcherNodeGen(templates, sourceSection);
    }

}
