// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;

/**
 * Debug Info: <pre>
 *   Specialization {@link StringTypeMatcherNode#doString}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link StringTypeMatcherNode#doOther}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(StringTypeMatcherNode.class)
@SuppressWarnings("javadoc")
public final class StringTypeMatcherNodeGen extends StringTypeMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StringTypeMatcherNode#doString}
     *   1: SpecializationActive {@link StringTypeMatcherNode#doOther}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private StringTypeMatcherNodeGen(SourceSection sourceSection) {
        super(sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[StringTypeMatcherNode.doString(TruffleString)] || SpecializationActive[StringTypeMatcherNode.doOther(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[StringTypeMatcherNode.doString(TruffleString)] */ && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString) arg0Value;
                return doString(arg0Value_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[StringTypeMatcherNode.doOther(Object)] */) {
                return doOther(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) != 0 /* is SpecializationActive[StringTypeMatcherNode.doOther(Object)] */) {
            return doOther(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString) arg0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[StringTypeMatcherNode.doString(TruffleString)] */;
            this.state_0_ = state_0;
            return doString(arg0Value_);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[StringTypeMatcherNode.doOther(Object)] */;
        this.state_0_ = state_0;
        return doOther(arg0Value);
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
    public static StringTypeMatcherNode create(SourceSection sourceSection) {
        return new StringTypeMatcherNodeGen(sourceSection);
    }

}
