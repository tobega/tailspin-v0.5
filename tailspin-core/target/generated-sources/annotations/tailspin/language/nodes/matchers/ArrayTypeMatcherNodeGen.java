// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link ArrayTypeMatcherNode#isArray}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link ArrayTypeMatcherNode#notArray}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(ArrayTypeMatcherNode.class)
@SuppressWarnings("javadoc")
public final class ArrayTypeMatcherNodeGen extends ArrayTypeMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ArrayTypeMatcherNode#isArray}
     *   1: SpecializationActive {@link ArrayTypeMatcherNode#notArray}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private ArrayTypeMatcherNodeGen(SourceSection sourceSection) {
        super(sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object arg0Value) {
        if (!((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayTypeMatcherNode.isArray(TailspinArray)] */) && arg0Value instanceof TailspinArray) {
            return false;
        }
        return true;
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[ArrayTypeMatcherNode.isArray(TailspinArray)] || SpecializationActive[ArrayTypeMatcherNode.notArray(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ArrayTypeMatcherNode.isArray(TailspinArray)] */ && arg0Value instanceof TailspinArray) {
                TailspinArray arg0Value_ = (TailspinArray) arg0Value;
                return isArray(arg0Value_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayTypeMatcherNode.notArray(Object)] */) {
                if (fallbackGuard_(state_0, arg0Value)) {
                    return notArray(arg0Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) != 0 /* is SpecializationActive[ArrayTypeMatcherNode.notArray(Object)] */) {
            if (fallbackGuard_(state_0, arg0Value)) {
                return notArray(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof TailspinArray) {
            TailspinArray arg0Value_ = (TailspinArray) arg0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[ArrayTypeMatcherNode.isArray(TailspinArray)] */;
            this.state_0_ = state_0;
            return isArray(arg0Value_);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[ArrayTypeMatcherNode.notArray(Object)] */;
        this.state_0_ = state_0;
        return notArray(arg0Value);
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
    public static ArrayTypeMatcherNode create(SourceSection sourceSection) {
        return new ArrayTypeMatcherNodeGen(sourceSection);
    }

}
