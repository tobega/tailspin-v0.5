// CheckStyle: start generated
package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Measure;

/**
 * Debug Info: <pre>
 *   Specialization {@link MeasureLiteral#doCast}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link MeasureLiteral#doMeasure}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(MeasureLiteral.class)
@SuppressWarnings("javadoc")
public final class MeasureLiteralNodeGen extends MeasureLiteral {

    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MeasureLiteral#doCast}
     *   1: SpecializationActive {@link MeasureLiteral#doMeasure}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private MeasureLiteralNodeGen(TruffleString unit, SourceSection sourceSection, ValueNode value) {
        super(unit, sourceSection);
        this.value_ = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[MeasureLiteral.doCast(Measure)] || SpecializationActive[MeasureLiteral.doMeasure(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[MeasureLiteral.doCast(Measure)] */ && valueValue_ instanceof Measure) {
                Measure valueValue__ = (Measure) valueValue_;
                return doCast(valueValue__);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[MeasureLiteral.doMeasure(Object)] */) {
                return doMeasure(valueValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(valueValue_);
    }

    private Object executeAndSpecialize(Object valueValue) {
        int state_0 = this.state_0_;
        if (valueValue instanceof Measure) {
            Measure valueValue_ = (Measure) valueValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[MeasureLiteral.doCast(Measure)] */;
            this.state_0_ = state_0;
            return doCast(valueValue_);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[MeasureLiteral.doMeasure(Object)] */;
        this.state_0_ = state_0;
        return doMeasure(valueValue);
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
    public static MeasureLiteral create(TruffleString unit, SourceSection sourceSection, ValueNode value) {
        return new MeasureLiteralNodeGen(unit, sourceSection, value);
    }

}
