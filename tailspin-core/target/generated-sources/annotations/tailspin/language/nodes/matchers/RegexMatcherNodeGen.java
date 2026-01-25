// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.ToJavaStringNode;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link RegexMatcherNode#doRegex}
 *     Activation probability: 0,65000
 *     With/without class size: 17/4 bytes
 *   Specialization {@link RegexMatcherNode#doIllegal}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(RegexMatcherNode.class)
@SuppressWarnings("javadoc")
public final class RegexMatcherNodeGen extends RegexMatcherNode {

    @Child private ValueNode dummy_;
    @Child private ValueNode child1_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link RegexMatcherNode#doRegex}
     *   1: SpecializationActive {@link RegexMatcherNode#doIllegal}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link RegexMatcherNode#doRegex}
     *   Parameter: {@link ToJavaStringNode} toJavaStringNode</pre>
     */
    @Child private ToJavaStringNode regex_toJavaStringNode_;

    private RegexMatcherNodeGen(boolean isTypeChecked, SourceSection sourceSection, ValueNode dummy, ValueNode child1) {
        super(isTypeChecked, sourceSection);
        this.dummy_ = dummy;
        this.child1_ = child1;
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object dummyValue) {
        int state_0 = this.state_0_;
        Object child1Value_ = this.child1_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[RegexMatcherNode.doRegex(TruffleString, TruffleString, ToJavaStringNode)] || SpecializationActive[RegexMatcherNode.doIllegal(Object, TruffleString)] */ && child1Value_ instanceof TruffleString) {
            TruffleString child1Value__ = (TruffleString) child1Value_;
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[RegexMatcherNode.doRegex(TruffleString, TruffleString, ToJavaStringNode)] */ && dummyValue instanceof TruffleString) {
                TruffleString dummyValue_ = (TruffleString) dummyValue;
                {
                    ToJavaStringNode toJavaStringNode__ = this.regex_toJavaStringNode_;
                    if (toJavaStringNode__ != null) {
                        return doRegex(dummyValue_, child1Value__, toJavaStringNode__);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[RegexMatcherNode.doIllegal(Object, TruffleString)] */) {
                return doIllegal(dummyValue, child1Value__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(dummyValue, child1Value_);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long dummyValue) {
        int state_0 = this.state_0_;
        Object child1Value_ = this.child1_.executeGeneric(frameValue);
        if ((state_0 & 0b10) != 0 /* is SpecializationActive[RegexMatcherNode.doIllegal(Object, TruffleString)] */ && child1Value_ instanceof TruffleString) {
            TruffleString child1Value__ = (TruffleString) child1Value_;
            return doIllegal(dummyValue, child1Value__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(dummyValue, child1Value_);
    }

    private boolean executeAndSpecialize(Object dummyValue, Object child1Value) {
        int state_0 = this.state_0_;
        if (child1Value instanceof TruffleString) {
            TruffleString child1Value_ = (TruffleString) child1Value;
            if (dummyValue instanceof TruffleString) {
                TruffleString dummyValue_ = (TruffleString) dummyValue;
                ToJavaStringNode toJavaStringNode__ = this.insert((ToJavaStringNode.create()));
                Objects.requireNonNull(toJavaStringNode__, "Specialization 'doRegex(TruffleString, TruffleString, ToJavaStringNode)' cache 'toJavaStringNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.regex_toJavaStringNode_ = toJavaStringNode__;
                state_0 = state_0 | 0b1 /* add SpecializationActive[RegexMatcherNode.doRegex(TruffleString, TruffleString, ToJavaStringNode)] */;
                this.state_0_ = state_0;
                return doRegex(dummyValue_, child1Value_, toJavaStringNode__);
            }
            state_0 = state_0 | 0b10 /* add SpecializationActive[RegexMatcherNode.doIllegal(Object, TruffleString)] */;
            this.state_0_ = state_0;
            return doIllegal(dummyValue, child1Value_);
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.dummy_, this.child1_}, dummyValue, child1Value);
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
    public static RegexMatcherNode create(boolean isTypeChecked, SourceSection sourceSection, ValueNode dummy, ValueNode child1) {
        return new RegexMatcherNodeGen(isTypeChecked, sourceSection, dummy, child1);
    }

}
