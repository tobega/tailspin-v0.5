// CheckStyle: start generated
package tailspin.language.nodes.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.FromCodePointNode;
import java.lang.invoke.VarHandle;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

/**
 * Debug Info: <pre>
 *   Specialization {@link CodepointNode#doLong}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link CodepointNode#doBigNumber}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(CodepointNode.class)
@SuppressWarnings("javadoc")
public final class CodepointNodeGen extends CodepointNode {

    @Child private ValueNode child0_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link CodepointNode#doLong}
     *   1: SpecializationActive {@link CodepointNode#doBigNumber}
     *   2-3: ImplicitCast[type=BigNumber, index=0]
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link CodepointNode#doLong}
     *   Parameter: {@link FromCodePointNode} fromCodePointNode</pre>
     */
    @Child private FromCodePointNode fromCodePointNode;

    private CodepointNodeGen(SourceSection sourceSection, ValueNode child0) {
        super(sourceSection);
        this.child0_ = child0;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) == 0 /* only-active SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] */ && ((state_0 & 0b11) != 0  /* is-not SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] && SpecializationActive[CodepointNode.doBigNumber(BigNumber, FromCodePointNode)] */)) {
            return executeGeneric_long0(state_0, frameValue);
        } else {
            return executeGeneric_generic1(state_0, frameValue);
        }
    }

    private Object executeGeneric_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long child0Value_;
        try {
            child0Value_ = this.child0_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(ex.getResult());
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] */;
        {
            FromCodePointNode fromCodePointNode_ = this.fromCodePointNode;
            if (fromCodePointNode_ != null) {
                return doLong(child0Value_, fromCodePointNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(child0Value_);
    }

    private Object executeGeneric_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object child0Value_ = this.child0_.executeGeneric(frameValue);
        if ((state_0 & 0b11) != 0 /* is SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] || SpecializationActive[CodepointNode.doBigNumber(BigNumber, FromCodePointNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] */ && child0Value_ instanceof Long) {
                long child0Value__ = (long) child0Value_;
                {
                    FromCodePointNode fromCodePointNode_ = this.fromCodePointNode;
                    if (fromCodePointNode_ != null) {
                        return doLong(child0Value__, fromCodePointNode_);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[CodepointNode.doBigNumber(BigNumber, FromCodePointNode)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b1100) >>> 2 /* get-int ImplicitCast[type=BigNumber, index=0] */, child0Value_)) {
                BigNumber child0Value__ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b1100) >>> 2 /* get-int ImplicitCast[type=BigNumber, index=0] */, child0Value_);
                {
                    FromCodePointNode fromCodePointNode_1 = this.fromCodePointNode;
                    if (fromCodePointNode_1 != null) {
                        return doBigNumber(child0Value__, fromCodePointNode_1);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(child0Value_);
    }

    private TruffleString executeAndSpecialize(Object child0Value) {
        int state_0 = this.state_0_;
        if (child0Value instanceof Long) {
            long child0Value_ = (long) child0Value;
            FromCodePointNode fromCodePointNode_;
            FromCodePointNode fromCodePointNode__shared = this.fromCodePointNode;
            if (fromCodePointNode__shared != null) {
                fromCodePointNode_ = fromCodePointNode__shared;
            } else {
                fromCodePointNode_ = this.insert((FromCodePointNode.create()));
                if (fromCodePointNode_ == null) {
                    throw new IllegalStateException("Specialization 'doLong(long, FromCodePointNode)' contains a shared cache with name 'fromCodePointNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.fromCodePointNode == null) {
                VarHandle.storeStoreFence();
                this.fromCodePointNode = fromCodePointNode_;
            }
            state_0 = state_0 | 0b1 /* add SpecializationActive[CodepointNode.doLong(long, FromCodePointNode)] */;
            this.state_0_ = state_0;
            return doLong(child0Value_, fromCodePointNode_);
        }
        {
            int bigNumberCast0;
            if ((bigNumberCast0 = TailspinTypesGen.specializeImplicitBigNumber(child0Value)) != 0) {
                BigNumber child0Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast0, child0Value);
                FromCodePointNode fromCodePointNode_1;
                FromCodePointNode fromCodePointNode_1_shared = this.fromCodePointNode;
                if (fromCodePointNode_1_shared != null) {
                    fromCodePointNode_1 = fromCodePointNode_1_shared;
                } else {
                    fromCodePointNode_1 = this.insert((FromCodePointNode.create()));
                    if (fromCodePointNode_1 == null) {
                        throw new IllegalStateException("Specialization 'doBigNumber(BigNumber, FromCodePointNode)' contains a shared cache with name 'fromCodePointNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.fromCodePointNode == null) {
                    VarHandle.storeStoreFence();
                    this.fromCodePointNode = fromCodePointNode_1;
                }
                state_0 = (state_0 | (bigNumberCast0 << 2) /* set-int ImplicitCast[type=BigNumber, index=0] */);
                state_0 = state_0 | 0b10 /* add SpecializationActive[CodepointNode.doBigNumber(BigNumber, FromCodePointNode)] */;
                this.state_0_ = state_0;
                return doBigNumber(child0Value_, fromCodePointNode_1);
            }
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.child0_}, child0Value);
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
    public static CodepointNode create(SourceSection sourceSection, ValueNode child0) {
        return new CodepointNodeGen(sourceSection, child0);
    }

}
