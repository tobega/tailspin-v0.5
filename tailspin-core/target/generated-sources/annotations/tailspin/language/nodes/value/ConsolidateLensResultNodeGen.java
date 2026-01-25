// CheckStyle: start generated
package tailspin.language.nodes.value;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.IndexedArrayValue;

/**
 * Debug Info: <pre>
 *   Specialization {@link ConsolidateLensResultNode#doConsolidateMany}
 *     Activation probability: 0,48333
 *     With/without class size: 9/0 bytes
 *   Specialization {@link ConsolidateLensResultNode#doConsolidateIndexed}
 *     Activation probability: 0,33333
 *     With/without class size: 8/0 bytes
 *   Specialization {@link ConsolidateLensResultNode#doConsolidateOne}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(ConsolidateLensResultNode.class)
@SuppressWarnings("javadoc")
public final class ConsolidateLensResultNodeGen extends ConsolidateLensResultNode {

    @Child private ValueNode child0_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link ConsolidateLensResultNode#doConsolidateMany}
     *   1: SpecializationActive {@link ConsolidateLensResultNode#doConsolidateIndexed}
     *   2: SpecializationActive {@link ConsolidateLensResultNode#doConsolidateOne}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private ConsolidateLensResultNodeGen(SourceSection sourceSection, ValueNode child0) {
        super(sourceSection);
        this.child0_ = child0;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object child0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateMany(VirtualFrame, ArrayList<>)] || SpecializationActive[ConsolidateLensResultNode.doConsolidateIndexed(VirtualFrame, IndexedArrayValue)] || SpecializationActive[ConsolidateLensResultNode.doConsolidateOne(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateMany(VirtualFrame, ArrayList<>)] */ && child0Value instanceof ArrayList<?>) {
                ArrayList<?> child0Value_ = (ArrayList<?>) child0Value;
                return doConsolidateMany(frameValue, child0Value_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateIndexed(VirtualFrame, IndexedArrayValue)] */ && child0Value instanceof IndexedArrayValue) {
                IndexedArrayValue child0Value_ = (IndexedArrayValue) child0Value;
                return doConsolidateIndexed(frameValue, child0Value_);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateOne(VirtualFrame, Object)] */) {
                return doConsolidateOne(frameValue, child0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, child0Value);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object child0Value_ = this.child0_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateMany(VirtualFrame, ArrayList<>)] || SpecializationActive[ConsolidateLensResultNode.doConsolidateIndexed(VirtualFrame, IndexedArrayValue)] || SpecializationActive[ConsolidateLensResultNode.doConsolidateOne(VirtualFrame, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateMany(VirtualFrame, ArrayList<>)] */ && child0Value_ instanceof ArrayList<?>) {
                ArrayList<?> child0Value__ = (ArrayList<?>) child0Value_;
                return doConsolidateMany(frameValue, child0Value__);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateIndexed(VirtualFrame, IndexedArrayValue)] */ && child0Value_ instanceof IndexedArrayValue) {
                IndexedArrayValue child0Value__ = (IndexedArrayValue) child0Value_;
                return doConsolidateIndexed(frameValue, child0Value__);
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[ConsolidateLensResultNode.doConsolidateOne(VirtualFrame, Object)] */) {
                return doConsolidateOne(frameValue, child0Value_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, child0Value_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object child0Value) {
        int state_0 = this.state_0_;
        if (child0Value instanceof ArrayList<?>) {
            ArrayList<?> child0Value_ = (ArrayList<?>) child0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[ConsolidateLensResultNode.doConsolidateMany(VirtualFrame, ArrayList<>)] */;
            this.state_0_ = state_0;
            return doConsolidateMany(frameValue, child0Value_);
        }
        if (child0Value instanceof IndexedArrayValue) {
            IndexedArrayValue child0Value_ = (IndexedArrayValue) child0Value;
            state_0 = state_0 | 0b10 /* add SpecializationActive[ConsolidateLensResultNode.doConsolidateIndexed(VirtualFrame, IndexedArrayValue)] */;
            this.state_0_ = state_0;
            return doConsolidateIndexed(frameValue, child0Value_);
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[ConsolidateLensResultNode.doConsolidateOne(VirtualFrame, Object)] */;
        this.state_0_ = state_0;
        return doConsolidateOne(frameValue, child0Value);
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
    public static ConsolidateLensResultNode create(SourceSection sourceSection, ValueNode child0) {
        return new ConsolidateLensResultNodeGen(sourceSection, child0);
    }

}
