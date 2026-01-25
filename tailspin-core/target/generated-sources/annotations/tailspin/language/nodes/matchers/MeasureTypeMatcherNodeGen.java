// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.runtime.Measure;

/**
 * Debug Info: <pre>
 *   Specialization {@link MeasureTypeMatcher#doMeasure}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link MeasureTypeMatcher#notMatcher}
 *     Activation probability: 0,35000
 *     With/without class size: 8/0 bytes
 * </pre>
 */
@GeneratedBy(MeasureTypeMatcher.class)
@SuppressWarnings("javadoc")
public final class MeasureTypeMatcherNodeGen extends MeasureTypeMatcher {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MeasureTypeMatcher#doMeasure}
     *   1: SpecializationActive {@link MeasureTypeMatcher#notMatcher}
     * </pre>
     */
    @CompilationFinal private int state_0_;

    private MeasureTypeMatcherNodeGen(Object unit, SourceSection sourceSection) {
        super(unit, sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[MeasureTypeMatcher.doMeasure(Measure)] || SpecializationActive[MeasureTypeMatcher.notMatcher(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[MeasureTypeMatcher.doMeasure(Measure)] */ && arg0Value instanceof Measure) {
                Measure arg0Value_ = (Measure) arg0Value;
                return doMeasure(arg0Value_);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[MeasureTypeMatcher.notMatcher(Object)] */) {
                return notMatcher(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) != 0 /* is SpecializationActive[MeasureTypeMatcher.notMatcher(Object)] */) {
            return notMatcher(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof Measure) {
            Measure arg0Value_ = (Measure) arg0Value;
            state_0 = state_0 | 0b1 /* add SpecializationActive[MeasureTypeMatcher.doMeasure(Measure)] */;
            this.state_0_ = state_0;
            return doMeasure(arg0Value_);
        }
        state_0 = state_0 | 0b10 /* add SpecializationActive[MeasureTypeMatcher.notMatcher(Object)] */;
        this.state_0_ = state_0;
        return notMatcher(arg0Value);
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
    public static MeasureTypeMatcher create(Object unit, SourceSection sourceSection) {
        return new MeasureTypeMatcherNodeGen(unit, sourceSection);
    }

}
