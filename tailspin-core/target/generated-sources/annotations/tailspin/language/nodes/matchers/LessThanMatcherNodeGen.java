// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.DSLSupport;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.dsl.DSLSupport.SpecializationDataNode;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.ReferenceField;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.TaggedValue;

/**
 * Debug Info: <pre>
 *   Specialization {@link LessThanMatcherNode#doMeasure}
 *     Activation probability: 0,32000
 *     With/without class size: 9/0 bytes
 *   Specialization {@link LessThanMatcherNode#doTaggedValue}
 *     Activation probability: 0,26000
 *     With/without class size: 8/0 bytes
 *   Specialization {@link LessThanMatcherNode#doStaticBound}
 *     Activation probability: 0,20000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link LessThanMatcherNode#doDynamicCachedBound}
 *     Activation probability: 0,14000
 *     With/without class size: 7/4 bytes
 *   Specialization {@link LessThanMatcherNode#doDynamicBound}
 *     Activation probability: 0,08000
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(LessThanMatcherNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class LessThanMatcherNodeGen extends LessThanMatcherNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    static final ReferenceField<DynamicCachedBoundData> DYNAMIC_CACHED_BOUND_CACHE_UPDATER = ReferenceField.create(MethodHandles.lookup(), "dynamicCachedBound_cache", DynamicCachedBoundData.class);
    /**
     * Source Info: <pre>
     *   Specialization: {@link LessThanMatcherNode#doMeasure}
     *   Parameter: {@link DoLessThanNode} doLessThanNode
     *   Inline method: {@link DoLessThanNodeGen#inline}</pre>
     */
    private static final DoLessThanNode INLINED_DO_LESS_THAN_NODE = DoLessThanNodeGen.inline(InlineTarget.create(DoLessThanNode.class, STATE_1_UPDATER.subUpdater(0, 32)));

    @Child private ValueNode dummy_;
    @Child private ValueNode valueNode_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link LessThanMatcherNode#doMeasure}
     *   1: SpecializationActive {@link LessThanMatcherNode#doTaggedValue}
     *   2: SpecializationActive {@link LessThanMatcherNode#doStaticBound}
     *   3: SpecializationActive {@link LessThanMatcherNode#doDynamicCachedBound}
     *   4: SpecializationActive {@link LessThanMatcherNode#doDynamicBound}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * State Info: <pre>
     *   0-31: InlinedCache
     *        Specialization: {@link LessThanMatcherNode#doMeasure}
     *        Parameter: {@link DoLessThanNode} doLessThanNode
     *        Inline method: {@link DoLessThanNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;
    @UnsafeAccessedField @Child private DynamicCachedBoundData dynamicCachedBound_cache;

    private LessThanMatcherNodeGen(boolean isTypeChecked, boolean inclusive, SourceSection sourceSection, ValueNode dummy, ValueNode valueNode) {
        super(isTypeChecked, inclusive, sourceSection);
        this.dummy_ = dummy;
        this.valueNode_ = valueNode;
    }

    @ExplodeLoop
    @Override
    public boolean executeComparison(VirtualFrame frameValue, Object dummyValue, Object valueNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[LessThanMatcherNode.doMeasure(VirtualFrame, Measure, Measure, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doTaggedValue(VirtualFrame, TaggedValue, TaggedValue, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] || SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[LessThanMatcherNode.doMeasure(VirtualFrame, Measure, Measure, DoLessThanNode)] */ && dummyValue instanceof Measure) {
                Measure dummyValue_ = (Measure) dummyValue;
                if (valueNodeValue instanceof Measure) {
                    Measure valueNodeValue_ = (Measure) valueNodeValue;
                    if ((dummyValue_.unit() == valueNodeValue_.unit())) {
                        return doMeasure(frameValue, dummyValue_, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[LessThanMatcherNode.doTaggedValue(VirtualFrame, TaggedValue, TaggedValue, DoLessThanNode)] */ && dummyValue instanceof TaggedValue) {
                TaggedValue dummyValue_ = (TaggedValue) dummyValue;
                if (valueNodeValue instanceof TaggedValue) {
                    TaggedValue valueNodeValue_ = (TaggedValue) valueNodeValue;
                    if ((dummyValue_.type() == valueNodeValue_.type())) {
                        return doTaggedValue(frameValue, dummyValue_, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                    }
                }
            }
            if ((state_0 & 0b11100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] || SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                    assert DSLSupport.assertIdempotence((isTypeChecked));
                    return doStaticBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] */) {
                    assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                    DynamicCachedBoundData s3_ = this.dynamicCachedBound_cache;
                    while (s3_ != null) {
                        if ((s3_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue))) {
                            return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE, s3_.dynamicBound_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                    assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                    return doDynamicBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, dummyValue, valueNodeValue);
    }

    @ExplodeLoop
    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object dummyValue) {
        int state_0 = this.state_0_;
        Object valueNodeValue_ = this.valueNode_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[LessThanMatcherNode.doMeasure(VirtualFrame, Measure, Measure, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doTaggedValue(VirtualFrame, TaggedValue, TaggedValue, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] || SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[LessThanMatcherNode.doMeasure(VirtualFrame, Measure, Measure, DoLessThanNode)] */ && dummyValue instanceof Measure) {
                Measure dummyValue_ = (Measure) dummyValue;
                if (valueNodeValue_ instanceof Measure) {
                    Measure valueNodeValue__ = (Measure) valueNodeValue_;
                    if ((dummyValue_.unit() == valueNodeValue__.unit())) {
                        return doMeasure(frameValue, dummyValue_, valueNodeValue__, INLINED_DO_LESS_THAN_NODE);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[LessThanMatcherNode.doTaggedValue(VirtualFrame, TaggedValue, TaggedValue, DoLessThanNode)] */ && dummyValue instanceof TaggedValue) {
                TaggedValue dummyValue_ = (TaggedValue) dummyValue;
                if (valueNodeValue_ instanceof TaggedValue) {
                    TaggedValue valueNodeValue__ = (TaggedValue) valueNodeValue_;
                    if ((dummyValue_.type() == valueNodeValue__.type())) {
                        return doTaggedValue(frameValue, dummyValue_, valueNodeValue__, INLINED_DO_LESS_THAN_NODE);
                    }
                }
            }
            if ((state_0 & 0b11100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] || SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                    assert DSLSupport.assertIdempotence((isTypeChecked));
                    return doStaticBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] */) {
                    assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                    DynamicCachedBoundData s3_ = this.dynamicCachedBound_cache;
                    while (s3_ != null) {
                        if ((s3_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue_))) {
                            return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE, s3_.dynamicBound_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                    assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                    return doDynamicBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, dummyValue, valueNodeValue_);
    }

    @ExplodeLoop
    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long dummyValue) {
        int state_0 = this.state_0_;
        Object valueNodeValue_ = this.valueNode_.executeGeneric(frameValue);
        if ((state_0 & 0b11100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] || SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] || SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                assert DSLSupport.assertIdempotence((isTypeChecked));
                return doStaticBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                DynamicCachedBoundData s3_ = this.dynamicCachedBound_cache;
                while (s3_ != null) {
                    if ((s3_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue_))) {
                        return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE, s3_.dynamicBound_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                return doDynamicBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, dummyValue, valueNodeValue_);
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object dummyValue, Object valueNodeValue) {
        int state_0 = this.state_0_;
        if (dummyValue instanceof Measure) {
            Measure dummyValue_ = (Measure) dummyValue;
            if (valueNodeValue instanceof Measure) {
                Measure valueNodeValue_ = (Measure) valueNodeValue;
                if ((dummyValue_.unit() == valueNodeValue_.unit())) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[LessThanMatcherNode.doMeasure(VirtualFrame, Measure, Measure, DoLessThanNode)] */;
                    this.state_0_ = state_0;
                    return doMeasure(frameValue, dummyValue_, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                }
            }
        }
        if (dummyValue instanceof TaggedValue) {
            TaggedValue dummyValue_ = (TaggedValue) dummyValue;
            if (valueNodeValue instanceof TaggedValue) {
                TaggedValue valueNodeValue_ = (TaggedValue) valueNodeValue;
                if ((dummyValue_.type() == valueNodeValue_.type())) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[LessThanMatcherNode.doTaggedValue(VirtualFrame, TaggedValue, TaggedValue, DoLessThanNode)] */;
                    this.state_0_ = state_0;
                    return doTaggedValue(frameValue, dummyValue_, valueNodeValue_, INLINED_DO_LESS_THAN_NODE);
                }
            }
        }
        if ((isTypeChecked)) {
            state_0 = state_0 | 0b100 /* add SpecializationActive[LessThanMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoLessThanNode)] */;
            this.state_0_ = state_0;
            return doStaticBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE);
        }
        if ((!(isTypeChecked))) {
            while (true) {
                int count3_ = 0;
                DynamicCachedBoundData s3_ = DYNAMIC_CACHED_BOUND_CACHE_UPDATER.getVolatile(this);
                DynamicCachedBoundData s3_original = s3_;
                while (s3_ != null) {
                    if ((s3_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue))) {
                        break;
                    }
                    count3_++;
                    s3_ = s3_.next_;
                }
                if (s3_ == null) {
                    {
                        MatcherNode dynamicBound__ = this.insert((autoType(valueNodeValue)));
                        if ((dynamicBound__.executeMatcherGeneric(frameValue, valueNodeValue)) && count3_ < (3)) {
                            s3_ = this.insert(new DynamicCachedBoundData(s3_original));
                            s3_.dynamicBound_ = s3_.insert(dynamicBound__);
                            if (!DYNAMIC_CACHED_BOUND_CACHE_UPDATER.compareAndSet(this, s3_original, s3_)) {
                                continue;
                            }
                            state_0 = state_0 | 0b1000 /* add SpecializationActive[LessThanMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoLessThanNode, MatcherNode)] */;
                            this.state_0_ = state_0;
                        }
                    }
                }
                if (s3_ != null) {
                    return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE, s3_.dynamicBound_);
                }
                break;
            }
        }
        if ((!(isTypeChecked))) {
            state_0 = state_0 | 0b10000 /* add SpecializationActive[LessThanMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoLessThanNode)] */;
            this.state_0_ = state_0;
            return doDynamicBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_LESS_THAN_NODE);
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.dummy_, this.valueNode_}, dummyValue, valueNodeValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            int counter = 0;
            counter += Integer.bitCount(state_0);
            if (counter == 1) {
                DynamicCachedBoundData s3_ = this.dynamicCachedBound_cache;
                if ((s3_ == null || s3_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static LessThanMatcherNode create(boolean isTypeChecked, boolean inclusive, SourceSection sourceSection, ValueNode dummy, ValueNode valueNode) {
        return new LessThanMatcherNodeGen(isTypeChecked, inclusive, sourceSection, dummy, valueNode);
    }

    @GeneratedBy(LessThanMatcherNode.class)
    @DenyReplace
    private static final class DynamicCachedBoundData extends Node implements SpecializationDataNode {

        @Child DynamicCachedBoundData next_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link LessThanMatcherNode#doDynamicCachedBound}
         *   Parameter: {@link MatcherNode} dynamicBound</pre>
         */
        @Child MatcherNode dynamicBound_;

        DynamicCachedBoundData(DynamicCachedBoundData next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link DoLessThanNode#longLess}
     *     Activation probability: 0,10588
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoLessThanNode#bigNumberLess}
     *     Activation probability: 0,10000
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoLessThanNode#smallRationalLess}
     *     Activation probability: 0,09412
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoLessThanNode#smallRationalLong}
     *     Activation probability: 0,08824
     *     With/without class size: 5/0 bytes
     *   Specialization {@link DoLessThanNode#longSmallRational}
     *     Activation probability: 0,08235
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#rationalLess}
     *     Activation probability: 0,07647
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#rationalBigNumber}
     *     Activation probability: 0,07059
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#bigNumberRational}
     *     Activation probability: 0,06471
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#smallSciNumLess}
     *     Activation probability: 0,05882
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#smallSciNumLong}
     *     Activation probability: 0,05294
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#longSmallSciNum}
     *     Activation probability: 0,04706
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#sciNumLess}
     *     Activation probability: 0,04118
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#doBigNumSciNum}
     *     Activation probability: 0,03529
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#doSciNumBigNum}
     *     Activation probability: 0,02941
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#doRationalSciNum}
     *     Activation probability: 0,02353
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#doSciNumRational}
     *     Activation probability: 0,01765
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoLessThanNode#objectLess}
     *     Activation probability: 0,01176
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoLessThanNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoLessThanNodeGen extends DoLessThanNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoLessThanNode#longLess}
         *   1: SpecializationActive {@link DoLessThanNode#bigNumberLess}
         *   2: SpecializationActive {@link DoLessThanNode#smallRationalLess}
         *   3: SpecializationExcluded {@link DoLessThanNode#smallRationalLess}
         *   4: SpecializationActive {@link DoLessThanNode#rationalLess}
         *   5: SpecializationActive {@link DoLessThanNode#smallRationalLong}
         *   6: SpecializationExcluded {@link DoLessThanNode#smallRationalLong}
         *   7: SpecializationActive {@link DoLessThanNode#rationalBigNumber}
         *   8: SpecializationActive {@link DoLessThanNode#longSmallRational}
         *   9: SpecializationExcluded {@link DoLessThanNode#longSmallRational}
         *   10: SpecializationActive {@link DoLessThanNode#bigNumberRational}
         *   11: SpecializationActive {@link DoLessThanNode#smallSciNumLess}
         *   12: SpecializationActive {@link DoLessThanNode#smallSciNumLong}
         *   13: SpecializationActive {@link DoLessThanNode#longSmallSciNum}
         *   14: SpecializationActive {@link DoLessThanNode#sciNumLess}
         *   15: SpecializationActive {@link DoLessThanNode#doBigNumSciNum}
         *   16: SpecializationActive {@link DoLessThanNode#doSciNumBigNum}
         *   17: SpecializationActive {@link DoLessThanNode#doRationalSciNum}
         *   18: SpecializationActive {@link DoLessThanNode#doSciNumRational}
         *   19: SpecializationActive {@link DoLessThanNode#objectLess}
         *   20-21: ImplicitCast[type=BigNumber, index=1]
         *   22-23: ImplicitCast[type=BigNumber, index=2]
         *   24-25: ImplicitCast[type=Rational, index=1]
         *   26-27: ImplicitCast[type=Rational, index=2]
         *   28-29: ImplicitCast[type=SciNum, index=1]
         *   30-31: ImplicitCast[type=SciNum, index=2]
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private DoLessThanNodeGen() {
        }

        @SuppressWarnings("static-method")
        private boolean fallbackGuard_(Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
            if (TailspinTypesGen.isImplicitBigNumber(arg1Value) && TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                return false;
            }
            if (TailspinTypesGen.isImplicitRational(arg1Value)) {
                if (TailspinTypesGen.isImplicitRational(arg2Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                    return false;
                }
            }
            if (TailspinTypesGen.isImplicitBigNumber(arg1Value) && TailspinTypesGen.isImplicitRational(arg2Value)) {
                return false;
            }
            if (TailspinTypesGen.isImplicitSciNum(arg2Value)) {
                if (TailspinTypesGen.isImplicitSciNum(arg1Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitBigNumber(arg1Value)) {
                    return false;
                }
            }
            if (TailspinTypesGen.isImplicitSciNum(arg1Value) && TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                return false;
            }
            if (TailspinTypesGen.isImplicitRational(arg1Value) && TailspinTypesGen.isImplicitSciNum(arg2Value)) {
                return false;
            }
            if (TailspinTypesGen.isImplicitSciNum(arg1Value) && TailspinTypesGen.isImplicitRational(arg2Value)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean executeLessThan(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xffdb7) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        return longLess(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return bigNumberLess(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b100100) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return smallRationalLess(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                            state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                            state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        try {
                            return longSmallRational(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_;
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                            state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                            this.state_0_ = state_0;
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                if ((state_0 & 0b10010000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b10000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return rationalLess(arg1Value_, arg2Value_, arg3Value);
                    }
                    if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b1100000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        return smallSciNumLess(arg1Value_, arg2Value_, arg3Value);
                    }
                    if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        return smallSciNumLong(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        return longSmallSciNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0b1100000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return sciNumLess(arg1Value_, arg2Value_, arg3Value);
                    }
                    if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0x10000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0x20000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doRationalSciNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0x40000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if ((state_0 & 0x80000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */) {
                    if (fallbackGuard_(arg0Value, arg1Value, arg2Value, arg3Value)) {
                        return objectLess(arg1Value, arg2Value, arg3Value);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        private boolean executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] */;
                    this.state_0_ = state_0;
                    return longLess(arg1Value_, arg2Value_, arg3Value);
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b10 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] */;
                        this.state_0_ = state_0;
                        return bigNumberLess(arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (((state_0 & 0b10000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */ && ((state_0 & 0b1000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b100 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLess(arg1Value_, arg2Value_, arg3Value);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                        state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if (((state_0 & 0b10000000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && ((state_0 & 0b1000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b100000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                    this.state_0_ = state_0;
                    try {
                        return smallRationalLong(arg1Value_, arg2Value_, arg3Value);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                        state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            if (((state_0 & 0b10000000000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */ && ((state_0 & 0b1000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b100000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                    this.state_0_ = state_0;
                    try {
                        return longSmallRational(arg1Value_, arg2Value_, arg3Value);
                    } catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    {
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                            state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b10000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */;
                            this.state_0_ = state_0;
                            return rationalLess(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                            state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */;
                            this.state_0_ = state_0;
                            return rationalBigNumber(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int rationalCast2;
                    if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                        state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b10000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */;
                        this.state_0_ = state_0;
                        return bigNumberRational(arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b100000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] */;
                    this.state_0_ = state_0;
                    return smallSciNumLess(arg1Value_, arg2Value_, arg3Value);
                }
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */;
                    this.state_0_ = state_0;
                    return smallSciNumLong(arg1Value_, arg2Value_, arg3Value);
                }
            }
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] */;
                    this.state_0_ = state_0;
                    return longSmallSciNum(arg1Value_, arg2Value_, arg3Value);
                }
            }
            {
                int sciNumCast2;
                if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                    {
                        int sciNumCast1;
                        if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                            state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] */;
                            this.state_0_ = state_0;
                            return sciNumLess(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */;
                            this.state_0_ = state_0;
                            return doBigNumSciNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0x10000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] */;
                        this.state_0_ = state_0;
                        return doSciNumBigNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            {
                int rationalCast1;
                if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                    int sciNumCast2;
                    if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                        state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                        state_0 = state_0 | 0x20000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] */;
                        this.state_0_ = state_0;
                        return doRationalSciNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            {
                int sciNumCast1;
                if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                    int rationalCast2;
                    if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                        state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0x40000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] */;
                        this.state_0_ = state_0;
                        return doSciNumRational(arg1Value_, arg2Value_, arg3Value);
                    }
                }
            }
            state_0 = state_0 | 0x80000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */;
            this.state_0_ = state_0;
            return objectLess(arg1Value, arg2Value, arg3Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0xffdb7) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                if (((state_0 & 0xffdb7) & ((state_0 & 0xffdb7) - 1)) == 0 /* is-single  */) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static DoLessThanNode create() {
            return new DoLessThanNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * </ul>
         */
        @NeverDefault
        public static DoLessThanNode inline(@RequiredField(bits = 32, value = StateField.class) InlineTarget target) {
            return new DoLessThanNodeGen.Inlined(target);
        }

        @GeneratedBy(DoLessThanNode.class)
        @DenyReplace
        private static final class Inlined extends DoLessThanNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoLessThanNode#longLess}
             *   1: SpecializationActive {@link DoLessThanNode#bigNumberLess}
             *   2: SpecializationActive {@link DoLessThanNode#smallRationalLess}
             *   3: SpecializationExcluded {@link DoLessThanNode#smallRationalLess}
             *   4: SpecializationActive {@link DoLessThanNode#rationalLess}
             *   5: SpecializationActive {@link DoLessThanNode#smallRationalLong}
             *   6: SpecializationExcluded {@link DoLessThanNode#smallRationalLong}
             *   7: SpecializationActive {@link DoLessThanNode#rationalBigNumber}
             *   8: SpecializationActive {@link DoLessThanNode#longSmallRational}
             *   9: SpecializationExcluded {@link DoLessThanNode#longSmallRational}
             *   10: SpecializationActive {@link DoLessThanNode#bigNumberRational}
             *   11: SpecializationActive {@link DoLessThanNode#smallSciNumLess}
             *   12: SpecializationActive {@link DoLessThanNode#smallSciNumLong}
             *   13: SpecializationActive {@link DoLessThanNode#longSmallSciNum}
             *   14: SpecializationActive {@link DoLessThanNode#sciNumLess}
             *   15: SpecializationActive {@link DoLessThanNode#doBigNumSciNum}
             *   16: SpecializationActive {@link DoLessThanNode#doSciNumBigNum}
             *   17: SpecializationActive {@link DoLessThanNode#doRationalSciNum}
             *   18: SpecializationActive {@link DoLessThanNode#doSciNumRational}
             *   19: SpecializationActive {@link DoLessThanNode#objectLess}
             *   20-21: ImplicitCast[type=BigNumber, index=1]
             *   22-23: ImplicitCast[type=BigNumber, index=2]
             *   24-25: ImplicitCast[type=Rational, index=1]
             *   26-27: ImplicitCast[type=Rational, index=2]
             *   28-29: ImplicitCast[type=SciNum, index=1]
             *   30-31: ImplicitCast[type=SciNum, index=2]
             * </pre>
             */
            private final StateField state_0_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(DoLessThanNode.class);
                this.state_0_ = target.getState(0, 32);
            }

            @SuppressWarnings("static-method")
            private boolean fallbackGuard_(Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
                if (TailspinTypesGen.isImplicitBigNumber(arg1Value) && TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitRational(arg1Value)) {
                    if (TailspinTypesGen.isImplicitRational(arg2Value)) {
                        return false;
                    }
                    if (TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                        return false;
                    }
                }
                if (TailspinTypesGen.isImplicitBigNumber(arg1Value) && TailspinTypesGen.isImplicitRational(arg2Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitSciNum(arg2Value)) {
                    if (TailspinTypesGen.isImplicitSciNum(arg1Value)) {
                        return false;
                    }
                    if (TailspinTypesGen.isImplicitBigNumber(arg1Value)) {
                        return false;
                    }
                }
                if (TailspinTypesGen.isImplicitSciNum(arg1Value) && TailspinTypesGen.isImplicitBigNumber(arg2Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitRational(arg1Value) && TailspinTypesGen.isImplicitSciNum(arg2Value)) {
                    return false;
                }
                if (TailspinTypesGen.isImplicitSciNum(arg1Value) && TailspinTypesGen.isImplicitRational(arg2Value)) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean executeLessThan(VirtualFrame frameValue, Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0xffdb7) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            return longLess(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return bigNumberLess(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b100100) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b100) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return smallRationalLess(arg1Value_, arg2Value_, arg3Value);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                                state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            try {
                                return smallRationalLong(arg1Value_, arg2Value_, arg3Value);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                                state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                    }
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            try {
                                return longSmallRational(arg1Value_, arg2Value_, arg3Value);
                            } catch (ArithmeticException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_.get(arg0Value);
                                state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                                state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                                this.state_0_.set(arg0Value, state_0);
                                return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                    }
                    if ((state_0 & 0b10010000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b10000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return rationalLess(arg1Value_, arg2Value_, arg3Value);
                        }
                        if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b1100000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            return smallSciNumLess(arg1Value_, arg2Value_, arg3Value);
                        }
                        if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            return smallSciNumLong(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            return longSmallSciNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0b1100000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] || SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                            return sciNumLess(arg1Value_, arg2Value_, arg3Value);
                        }
                        if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x300000) >>> 20 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0x10000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doRationalSciNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0x40000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if ((state_0 & 0x80000) != 0 /* is SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */) {
                        if (fallbackGuard_(arg0Value, arg1Value, arg2Value, arg3Value)) {
                            return objectLess(arg1Value, arg2Value, arg3Value);
                        }
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            private boolean executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value, boolean arg3Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longLess(long, long, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return longLess(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberLess(BigNumber, BigNumber, boolean)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return bigNumberLess(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (((state_0 & 0b10000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */ && ((state_0 & 0b1000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b100 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLess(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                            state_0 = state_0 | 0b1000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                    if (((state_0 & 0b10000000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */ && ((state_0 & 0b1000000)) == 0 /* is-not SpecializationExcluded  */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b100000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return smallRationalLong(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                            state_0 = state_0 | 0b1000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                if (((state_0 & 0b10000000000)) == 0 /* is-not SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */ && ((state_0 & 0b1000000000)) == 0 /* is-not SpecializationExcluded  */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b100000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        try {
                            return longSmallRational(arg1Value_, arg2Value_, arg3Value);
                        } catch (ArithmeticException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            state_0 = this.state_0_.get(arg0Value);
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                            state_0 = state_0 | 0b1000000000 /* add SpecializationExcluded  */;
                            this.state_0_.set(arg0Value, state_0);
                            return executeAndSpecialize(arg0Value, arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        {
                            int rationalCast2;
                            if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                                Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                                state_0 = state_0 & 0xfffffffb /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLess(SmallRational, SmallRational, boolean)] */;
                                state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b10000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalLess(Rational, Rational, boolean)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return rationalLess(arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = state_0 & 0xffffffdf /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallRationalLong(SmallRational, long, boolean)] */;
                                state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b10000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.rationalBigNumber(Rational, BigNumber, boolean)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return rationalBigNumber(arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = state_0 & 0xfffffeff /* remove SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallRational(long, SmallRational, boolean)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b10000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.bigNumberRational(BigNumber, Rational, boolean)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return bigNumberRational(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b100000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLess(SmallSciNum, SmallSciNum, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return smallSciNumLess(arg1Value_, arg2Value_, arg3Value);
                    }
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.smallSciNumLong(SmallSciNum, long, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return smallSciNumLong(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                if (arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.longSmallSciNum(long, SmallSciNum, boolean)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return longSmallSciNum(arg1Value_, arg2Value_, arg3Value);
                    }
                }
                {
                    int sciNumCast2;
                    if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                        {
                            int sciNumCast1;
                            if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                                SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                                state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.sciNumLess(SciNum, SciNum, boolean)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return sciNumLess(arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = (state_0 | (bigNumberCast1 << 20) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doBigNumSciNum(BigNumber, SciNum, boolean)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return doBigNumSciNum(arg1Value_, arg2Value_, arg3Value);
                            }
                        }
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 22) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0x10000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumBigNum(SciNum, BigNumber, boolean)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doSciNumBigNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                {
                    int rationalCast1;
                    if ((rationalCast1 = TailspinTypesGen.specializeImplicitRational(arg1Value)) != 0) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational(rationalCast1, arg1Value);
                        int sciNumCast2;
                        if ((sciNumCast2 = TailspinTypesGen.specializeImplicitSciNum(arg2Value)) != 0) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast2, arg2Value);
                            state_0 = (state_0 | (rationalCast1 << 24) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (sciNumCast2 << 30) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0x20000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doRationalSciNum(Rational, SciNum, boolean)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doRationalSciNum(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                {
                    int sciNumCast1;
                    if ((sciNumCast1 = TailspinTypesGen.specializeImplicitSciNum(arg1Value)) != 0) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum(sciNumCast1, arg1Value);
                        int rationalCast2;
                        if ((rationalCast2 = TailspinTypesGen.specializeImplicitRational(arg2Value)) != 0) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational(rationalCast2, arg2Value);
                            state_0 = (state_0 | (sciNumCast1 << 28) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 26) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0x40000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.doSciNumRational(SciNum, Rational, boolean)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doSciNumRational(arg1Value_, arg2Value_, arg3Value);
                        }
                    }
                }
                state_0 = state_0 | 0x80000 /* add SpecializationActive[LessThanMatcherNode.DoLessThanNode.objectLess(Object, Object, boolean)] */;
                this.state_0_.set(arg0Value, state_0);
                return objectLess(arg1Value, arg2Value, arg3Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
