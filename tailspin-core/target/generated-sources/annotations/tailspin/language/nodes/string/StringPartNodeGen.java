// CheckStyle: start generated
package tailspin.language.nodes.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.FromJavaStringNode;
import java.lang.invoke.VarHandle;
import java.util.Objects;

/**
 * Debug Info: <pre>
 *   Specialization {@link StringPart#doString}
 *     Activation probability: 1,00000
 *     With/without class size: 24/4 bytes
 * </pre>
 */
@GeneratedBy(StringPart.class)
@SuppressWarnings("javadoc")
public final class StringPartNodeGen extends StringPart {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StringPart#doString}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link StringPart#doString}
     *   Parameter: {@link FromJavaStringNode} fromJavaStringNode</pre>
     */
    @Child private FromJavaStringNode fromJavaStringNode_;

    private StringPartNodeGen(String value, SourceSection sourceSection) {
        super(value, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[StringPart.doString(FromJavaStringNode)] */) {
            {
                FromJavaStringNode fromJavaStringNode__ = this.fromJavaStringNode_;
                if (fromJavaStringNode__ != null) {
                    return doString(fromJavaStringNode__);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    private TruffleString executeAndSpecialize() {
        int state_0 = this.state_0_;
        FromJavaStringNode fromJavaStringNode__ = this.insert((FromJavaStringNode.create()));
        Objects.requireNonNull(fromJavaStringNode__, "Specialization 'doString(FromJavaStringNode)' cache 'fromJavaStringNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
        VarHandle.storeStoreFence();
        this.fromJavaStringNode_ = fromJavaStringNode__;
        state_0 = state_0 | 0b1 /* add SpecializationActive[StringPart.doString(FromJavaStringNode)] */;
        this.state_0_ = state_0;
        return doString(fromJavaStringNode__);
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
    public static StringPart create(String value, SourceSection sourceSection) {
        return new StringPartNodeGen(value, sourceSection);
    }

}
