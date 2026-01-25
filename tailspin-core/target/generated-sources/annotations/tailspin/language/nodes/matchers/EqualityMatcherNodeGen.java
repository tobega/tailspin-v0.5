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
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link EqualityMatcherNode#doStaticBound}
 *     Activation probability: 0,48333
 *     With/without class size: 11/0 bytes
 *   Specialization {@link EqualityMatcherNode#doDynamicCachedBound}
 *     Activation probability: 0,33333
 *     With/without class size: 12/4 bytes
 *   Specialization {@link EqualityMatcherNode#doDynamicBound}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(EqualityMatcherNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class EqualityMatcherNodeGen extends EqualityMatcherNode {

    private static final StateField STATE_1_UPDATER = StateField.create(MethodHandles.lookup(), "state_1_");
    private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
    static final ReferenceField<DynamicCachedBoundData> DYNAMIC_CACHED_BOUND_CACHE_UPDATER = ReferenceField.create(MethodHandles.lookup(), "dynamicCachedBound_cache", DynamicCachedBoundData.class);
    /**
     * Source Info: <pre>
     *   Specialization: {@link EqualityMatcherNode#doStaticBound}
     *   Parameter: {@link DoEqualityNode} doEqualityNode
     *   Inline method: {@link DoEqualityNodeGen#inline}</pre>
     */
    private static final DoEqualityNode INLINED_DO_EQUALITY_NODE = DoEqualityNodeGen.inline(InlineTarget.create(DoEqualityNode.class, STATE_1_UPDATER.subUpdater(0, 32), STATE_0_UPDATER.subUpdater(3, 2), ReferenceField.create(MethodHandles.lookup(), "doEqualityNode_field2_", Node.class), ReferenceField.create(MethodHandles.lookup(), "doEqualityNode_field3_", Node.class), ReferenceField.create(MethodHandles.lookup(), "doEqualityNode_field4_", Node.class)));
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @Child private ValueNode dummy_;
    @Child private ValueNode valueNode_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link EqualityMatcherNode#doStaticBound}
     *   1: SpecializationActive {@link EqualityMatcherNode#doDynamicCachedBound}
     *   2: SpecializationActive {@link EqualityMatcherNode#doDynamicBound}
     *   3-4: InlinedCache
     *        Specialization: {@link EqualityMatcherNode#doStaticBound}
     *        Parameter: {@link DoEqualityNode} doEqualityNode
     *        Inline method: {@link DoEqualityNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_0_;
    /**
     * State Info: <pre>
     *   0-31: InlinedCache
     *        Specialization: {@link EqualityMatcherNode#doStaticBound}
     *        Parameter: {@link DoEqualityNode} doEqualityNode
     *        Inline method: {@link DoEqualityNodeGen#inline}
     * </pre>
     */
    @CompilationFinal @UnsafeAccessedField private int state_1_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link EqualityMatcherNode#doStaticBound}
     *   Parameter: {@link DoEqualityNode} doEqualityNode
     *   Inline method: {@link DoEqualityNodeGen#inline}
     *   Inline field: {@link Node} field2</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node doEqualityNode_field2_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link EqualityMatcherNode#doStaticBound}
     *   Parameter: {@link DoEqualityNode} doEqualityNode
     *   Inline method: {@link DoEqualityNodeGen#inline}
     *   Inline field: {@link Node} field3</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node doEqualityNode_field3_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link EqualityMatcherNode#doStaticBound}
     *   Parameter: {@link DoEqualityNode} doEqualityNode
     *   Inline method: {@link DoEqualityNodeGen#inline}
     *   Inline field: {@link Node} field4</pre>
     */
    @Child @UnsafeAccessedField @SuppressWarnings("unused") private Node doEqualityNode_field4_;
    @UnsafeAccessedField @Child private DynamicCachedBoundData dynamicCachedBound_cache;

    private EqualityMatcherNodeGen(boolean isTypeChecked, SourceSection sourceSection, ValueNode dummy, ValueNode valueNode) {
        super(isTypeChecked, sourceSection);
        this.dummy_ = dummy;
        this.valueNode_ = valueNode;
    }

    @ExplodeLoop
    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object dummyValue) {
        int state_0 = this.state_0_;
        Object valueNodeValue_ = this.valueNode_.executeGeneric(frameValue);
        if ((state_0 & 0b111) != 0 /* is SpecializationActive[EqualityMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoEqualityNode, MatcherNode)] || SpecializationActive[EqualityMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[EqualityMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
                assert DSLSupport.assertIdempotence((isTypeChecked));
                return doStaticBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[EqualityMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoEqualityNode, MatcherNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                DynamicCachedBoundData s1_ = this.dynamicCachedBound_cache;
                while (s1_ != null) {
                    if ((s1_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue_))) {
                        return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE, s1_.dynamicBound_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[EqualityMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                return doDynamicBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE);
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
        if ((state_0 & 0b111) != 0 /* is SpecializationActive[EqualityMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoEqualityNode, MatcherNode)] || SpecializationActive[EqualityMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[EqualityMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
                assert DSLSupport.assertIdempotence((isTypeChecked));
                return doStaticBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE);
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[EqualityMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoEqualityNode, MatcherNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                DynamicCachedBoundData s1_ = this.dynamicCachedBound_cache;
                while (s1_ != null) {
                    if ((s1_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue_))) {
                        return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE, s1_.dynamicBound_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[EqualityMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoEqualityNode)] */) {
                assert DSLSupport.assertIdempotence((!(isTypeChecked)));
                return doDynamicBound(frameValue, dummyValue, valueNodeValue_, INLINED_DO_EQUALITY_NODE);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, dummyValue, valueNodeValue_);
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object dummyValue, Object valueNodeValue) {
        int state_0 = this.state_0_;
        if ((isTypeChecked)) {
            state_0 = state_0 | 0b1 /* add SpecializationActive[EqualityMatcherNode.doStaticBound(VirtualFrame, Object, Object, DoEqualityNode)] */;
            this.state_0_ = state_0;
            return doStaticBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_EQUALITY_NODE);
        }
        if ((!(isTypeChecked))) {
            while (true) {
                int count1_ = 0;
                DynamicCachedBoundData s1_ = DYNAMIC_CACHED_BOUND_CACHE_UPDATER.getVolatile(this);
                DynamicCachedBoundData s1_original = s1_;
                while (s1_ != null) {
                    if ((s1_.dynamicBound_.executeMatcherGeneric(frameValue, valueNodeValue))) {
                        break;
                    }
                    count1_++;
                    s1_ = s1_.next_;
                }
                if (s1_ == null) {
                    {
                        MatcherNode dynamicBound__ = this.insert((autoType(valueNodeValue)));
                        if ((dynamicBound__.executeMatcherGeneric(frameValue, valueNodeValue)) && count1_ < (3)) {
                            s1_ = this.insert(new DynamicCachedBoundData(s1_original));
                            s1_.dynamicBound_ = s1_.insert(dynamicBound__);
                            if (!DYNAMIC_CACHED_BOUND_CACHE_UPDATER.compareAndSet(this, s1_original, s1_)) {
                                continue;
                            }
                            state_0 = state_0 | 0b10 /* add SpecializationActive[EqualityMatcherNode.doDynamicCachedBound(VirtualFrame, Object, Object, DoEqualityNode, MatcherNode)] */;
                            this.state_0_ = state_0;
                        }
                    }
                }
                if (s1_ != null) {
                    return doDynamicCachedBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_EQUALITY_NODE, s1_.dynamicBound_);
                }
                break;
            }
        }
        if ((!(isTypeChecked))) {
            state_0 = state_0 | 0b100 /* add SpecializationActive[EqualityMatcherNode.doDynamicBound(VirtualFrame, Object, Object, DoEqualityNode)] */;
            this.state_0_ = state_0;
            return doDynamicBound(frameValue, dummyValue, valueNodeValue, INLINED_DO_EQUALITY_NODE);
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.dummy_, this.valueNode_}, dummyValue, valueNodeValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            int counter = 0;
            counter += Integer.bitCount((state_0 & 0b111));
            if (counter == 1) {
                DynamicCachedBoundData s1_ = this.dynamicCachedBound_cache;
                if ((s1_ == null || s1_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static EqualityMatcherNode create(boolean isTypeChecked, SourceSection sourceSection, ValueNode dummy, ValueNode valueNode) {
        return new EqualityMatcherNodeGen(isTypeChecked, sourceSection, dummy, valueNode);
    }

    @GeneratedBy(EqualityMatcherNode.class)
    @DenyReplace
    private static final class DynamicCachedBoundData extends Node implements SpecializationDataNode {

        @Child DynamicCachedBoundData next_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link EqualityMatcherNode#doDynamicCachedBound}
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
     *   Specialization {@link DoEqualityNode#longEquals}
     *     Activation probability: 0,08281
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#bigNumberEquals}
     *     Activation probability: 0,07925
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#smallRationalEquals}
     *     Activation probability: 0,07569
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#smallRationalLong}
     *     Activation probability: 0,07213
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#longSmallRational}
     *     Activation probability: 0,06858
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#rationalEquals}
     *     Activation probability: 0,06502
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#rationalBigNumber}
     *     Activation probability: 0,06146
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#bigNumberRational}
     *     Activation probability: 0,05791
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doSmallSciNum}
     *     Activation probability: 0,05435
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doSmallSciNumLong}
     *     Activation probability: 0,05079
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doLongSmallSciNum}
     *     Activation probability: 0,04723
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doSciNum}
     *     Activation probability: 0,04368
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doBigNumSciNum}
     *     Activation probability: 0,04012
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doSciNumBigNum}
     *     Activation probability: 0,03656
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doRationalSciNum}
     *     Activation probability: 0,03300
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doSciNumRational}
     *     Activation probability: 0,02945
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doMeasure}
     *     Activation probability: 0,02589
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doTaggedValue}
     *     Activation probability: 0,02233
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doString}
     *     Activation probability: 0,01877
     *     With/without class size: 4/0 bytes
     *   Specialization {@link DoEqualityNode#doArray}
     *     Activation probability: 0,01522
     *     With/without class size: 4/8 bytes
     *   Specialization {@link DoEqualityNode#doStructure}
     *     Activation probability: 0,01166
     *     With/without class size: 4/8 bytes
     *   Specialization {@link DoEqualityNode#objectEquals}
     *     Activation probability: 0,00810
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(DoEqualityNode.class)
    @SuppressWarnings("javadoc")
    public static final class DoEqualityNodeGen extends DoEqualityNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link DoEqualityNode#longEquals}
         *   1: SpecializationActive {@link DoEqualityNode#bigNumberEquals}
         *   2: SpecializationActive {@link DoEqualityNode#smallRationalEquals}
         *   3: SpecializationActive {@link DoEqualityNode#smallRationalLong}
         *   4: SpecializationActive {@link DoEqualityNode#longSmallRational}
         *   5: SpecializationActive {@link DoEqualityNode#rationalEquals}
         *   6: SpecializationActive {@link DoEqualityNode#rationalBigNumber}
         *   7: SpecializationActive {@link DoEqualityNode#bigNumberRational}
         *   8: SpecializationActive {@link DoEqualityNode#doSmallSciNum}
         *   9: SpecializationActive {@link DoEqualityNode#doSmallSciNumLong}
         *   10: SpecializationActive {@link DoEqualityNode#doLongSmallSciNum}
         *   11: SpecializationActive {@link DoEqualityNode#doSciNum}
         *   12: SpecializationActive {@link DoEqualityNode#doBigNumSciNum}
         *   13: SpecializationActive {@link DoEqualityNode#doSciNumBigNum}
         *   14: SpecializationActive {@link DoEqualityNode#doRationalSciNum}
         *   15: SpecializationActive {@link DoEqualityNode#doSciNumRational}
         *   16: SpecializationActive {@link DoEqualityNode#doMeasure}
         *   17: SpecializationActive {@link DoEqualityNode#doTaggedValue}
         *   18: SpecializationActive {@link DoEqualityNode#doString}
         *   19: SpecializationActive {@link DoEqualityNode#doArray}
         *   20: SpecializationActive {@link DoEqualityNode#doStructure}
         *   21: SpecializationActive {@link DoEqualityNode#objectEquals}
         *   22-23: ImplicitCast[type=BigNumber, index=1]
         *   24-25: ImplicitCast[type=BigNumber, index=2]
         *   26-27: ImplicitCast[type=Rational, index=1]
         *   28-29: ImplicitCast[type=Rational, index=2]
         *   30-31: ImplicitCast[type=SciNum, index=1]
         * </pre>
         */
        @CompilationFinal private int state_0_;
        /**
         * State Info: <pre>
         *   0-1: ImplicitCast[type=SciNum, index=2]
         * </pre>
         */
        @CompilationFinal private int state_1_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link DoEqualityNode#doMeasure}
         *   Parameter: {@link DoEqualityNode} doEqualityNode</pre>
         */
        @Child private DoEqualityNode doEqualityNode;
        @Child private ArrayData array_cache;
        @Child private StructureData structure_cache;

        private DoEqualityNodeGen() {
        }

        @SuppressWarnings("static-method")
        private boolean fallbackGuard_(int state_0, Node arg0Value, Object arg1Value, Object arg2Value) {
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
            if (!((state_0 & 0x10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */) && arg1Value instanceof Measure && arg2Value instanceof Measure) {
                return false;
            }
            if (!((state_0 & 0x20000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */) && arg1Value instanceof TaggedValue && arg2Value instanceof TaggedValue) {
                return false;
            }
            if (!((state_0 & 0x40000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */) && arg1Value instanceof TruffleString && arg2Value instanceof TruffleString) {
                return false;
            }
            if (!((state_0 & 0x80000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */) && arg1Value instanceof TailspinArray && arg2Value instanceof TailspinArray) {
                return false;
            }
            if (!((state_0 & 0x100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */) && arg1Value instanceof Structure && arg2Value instanceof Structure) {
                return false;
            }
            return true;
        }

        @Override
        public boolean executeEquals(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x3fffff) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        return longEquals(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return bigNumberEquals(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1100) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        return smallRationalEquals(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        return smallRationalLong(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        return longSmallRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return rationalEquals(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return rationalBigNumber(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1100000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1100000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum(state_1_ >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                    SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(state_1_ >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                    if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        return doSciNum(arg1Value_, arg2Value_);
                    }
                    if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        return doBigNumSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                        return doSciNumBigNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                    Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitSciNum(state_1_ >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(state_1_ >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        return doRationalSciNum(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                    SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                    if (TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                        Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */ && arg1Value instanceof Measure) {
                    Measure arg1Value_ = (Measure) arg1Value;
                    if (arg2Value instanceof Measure) {
                        Measure arg2Value_ = (Measure) arg2Value;
                        {
                            DoEqualityNode doEqualityNode_ = this.doEqualityNode;
                            if (doEqualityNode_ != null) {
                                return doMeasure(this, arg1Value_, arg2Value_, doEqualityNode_);
                            }
                        }
                    }
                }
                if ((state_0 & 0x20000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */ && arg1Value instanceof TaggedValue) {
                    TaggedValue arg1Value_ = (TaggedValue) arg1Value;
                    if (arg2Value instanceof TaggedValue) {
                        TaggedValue arg2Value_ = (TaggedValue) arg2Value;
                        {
                            DoEqualityNode doEqualityNode_1 = this.doEqualityNode;
                            if (doEqualityNode_1 != null) {
                                return doTaggedValue(this, arg1Value_, arg2Value_, doEqualityNode_1);
                            }
                        }
                    }
                }
                if ((state_0 & 0x40000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */ && arg1Value instanceof TruffleString) {
                    TruffleString arg1Value_ = (TruffleString) arg1Value;
                    if (arg2Value instanceof TruffleString) {
                        TruffleString arg2Value_ = (TruffleString) arg2Value;
                        return doString(arg1Value_, arg2Value_);
                    }
                }
                if ((state_0 & 0x80000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */ && arg1Value instanceof TailspinArray) {
                    TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                    if (arg2Value instanceof TailspinArray) {
                        TailspinArray arg2Value_ = (TailspinArray) arg2Value;
                        ArrayData s19_ = this.array_cache;
                        if (s19_ != null) {
                            {
                                DoEqualityNode doEqualityNode_2 = this.doEqualityNode;
                                if (doEqualityNode_2 != null) {
                                    return doArray(this, arg1Value_, arg2Value_, s19_.indexEqualityNode_, s19_.sizeEqualityNode_, doEqualityNode_2);
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0x100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */ && arg1Value instanceof Structure) {
                    Structure arg1Value_ = (Structure) arg1Value;
                    if (arg2Value instanceof Structure) {
                        Structure arg2Value_ = (Structure) arg2Value;
                        StructureData s20_ = this.structure_cache;
                        if (s20_ != null) {
                            {
                                DoEqualityNode doEqualityNode_3 = this.doEqualityNode;
                                if (doEqualityNode_3 != null) {
                                    return doStructure(this, arg1Value_, arg2Value_, s20_.leftLibrary_, s20_.rightLibrary_, doEqualityNode_3);
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0x200000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */) {
                    if (fallbackGuard_(state_0, arg0Value, arg1Value, arg2Value)) {
                        return objectEquals(arg1Value, arg2Value);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private boolean executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            if (((state_0 & 0b10)) == 0 /* is-not SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */ && arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */;
                    this.state_0_ = state_0;
                    return longEquals(arg1Value_, arg2Value_);
                }
            }
            {
                int bigNumberCast1;
                if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                    BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                    int bigNumberCast2;
                    if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                        BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */;
                        state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b10 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */;
                        this.state_0_ = state_0;
                        return bigNumberEquals(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b100 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] */;
                    this.state_0_ = state_0;
                    return smallRationalEquals(arg1Value_, arg2Value_);
                }
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (long) arg2Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */;
                    this.state_0_ = state_0;
                    return smallRationalLong(arg1Value_, arg2Value_);
                }
            }
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                if (arg2Value instanceof SmallRational) {
                    SmallRational arg2Value_ = (SmallRational) arg2Value;
                    state_0 = state_0 | 0b10000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] */;
                    this.state_0_ = state_0;
                    return longSmallRational(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b100000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] */;
                            this.state_0_ = state_0;
                            return rationalEquals(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b1000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */;
                            this.state_0_ = state_0;
                            return rationalBigNumber(arg1Value_, arg2Value_);
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
                        state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                        state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b10000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] */;
                        this.state_0_ = state_0;
                        return bigNumberRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b100000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    return doSmallSciNum(arg1Value_, arg2Value_);
                }
                if (arg2Value instanceof Long) {
                    Long arg2Value_ = (Long) arg2Value;
                    state_0 = state_0 | 0b1000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                    this.state_0_ = state_0;
                    return doSmallSciNumLong(arg1Value_, arg2Value_);
                }
            }
            if (arg1Value instanceof Long) {
                Long arg1Value_ = (Long) arg1Value;
                if (arg2Value instanceof SmallSciNum) {
                    SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                    state_0 = state_0 | 0b10000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                    this.state_0_ = state_0;
                    return doLongSmallSciNum(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    {
                        int bigNumberCast1;
                        if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                            state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */;
                            this.state_0_ = state_0;
                            this.state_1_ = state_1;
                            return doBigNumSciNum(arg1Value_, arg2Value_);
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
                        state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                        state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] */;
                        this.state_0_ = state_0;
                        return doSciNumBigNum(arg1Value_, arg2Value_);
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
                        state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                        state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                        state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] */;
                        this.state_0_ = state_0;
                        this.state_1_ = state_1;
                        return doRationalSciNum(arg1Value_, arg2Value_);
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
                        state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                        state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                        state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] */;
                        this.state_0_ = state_0;
                        return doSciNumRational(arg1Value_, arg2Value_);
                    }
                }
            }
            if (arg1Value instanceof Measure) {
                Measure arg1Value_ = (Measure) arg1Value;
                if (arg2Value instanceof Measure) {
                    Measure arg2Value_ = (Measure) arg2Value;
                    DoEqualityNode doEqualityNode_;
                    DoEqualityNode doEqualityNode__shared = this.doEqualityNode;
                    if (doEqualityNode__shared != null) {
                        doEqualityNode_ = doEqualityNode__shared;
                    } else {
                        doEqualityNode_ = this.insert((DoEqualityNodeGen.create()));
                        if (doEqualityNode_ == null) {
                            throw new IllegalStateException("Specialization 'doMeasure(Node, Measure, Measure, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                        }
                    }
                    if (this.doEqualityNode == null) {
                        VarHandle.storeStoreFence();
                        this.doEqualityNode = doEqualityNode_;
                    }
                    state_0 = state_0 | 0x10000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */;
                    this.state_0_ = state_0;
                    return doMeasure(this, arg1Value_, arg2Value_, doEqualityNode_);
                }
            }
            if (arg1Value instanceof TaggedValue) {
                TaggedValue arg1Value_ = (TaggedValue) arg1Value;
                if (arg2Value instanceof TaggedValue) {
                    TaggedValue arg2Value_ = (TaggedValue) arg2Value;
                    DoEqualityNode doEqualityNode_1;
                    DoEqualityNode doEqualityNode_1_shared = this.doEqualityNode;
                    if (doEqualityNode_1_shared != null) {
                        doEqualityNode_1 = doEqualityNode_1_shared;
                    } else {
                        doEqualityNode_1 = this.insert((DoEqualityNodeGen.create()));
                        if (doEqualityNode_1 == null) {
                            throw new IllegalStateException("Specialization 'doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                        }
                    }
                    if (this.doEqualityNode == null) {
                        VarHandle.storeStoreFence();
                        this.doEqualityNode = doEqualityNode_1;
                    }
                    state_0 = state_0 | 0x20000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */;
                    this.state_0_ = state_0;
                    return doTaggedValue(this, arg1Value_, arg2Value_, doEqualityNode_1);
                }
            }
            if (arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString) arg1Value;
                if (arg2Value instanceof TruffleString) {
                    TruffleString arg2Value_ = (TruffleString) arg2Value;
                    state_0 = state_0 | 0x40000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */;
                    this.state_0_ = state_0;
                    return doString(arg1Value_, arg2Value_);
                }
            }
            if (arg1Value instanceof TailspinArray) {
                TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                if (arg2Value instanceof TailspinArray) {
                    TailspinArray arg2Value_ = (TailspinArray) arg2Value;
                    ArrayData s19_ = this.insert(new ArrayData());
                    DoEqualityNode indexEqualityNode__ = s19_.insert((DoEqualityNodeGen.create()));
                    Objects.requireNonNull(indexEqualityNode__, "Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' cache 'indexEqualityNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                    s19_.indexEqualityNode_ = indexEqualityNode__;
                    DoEqualityNode sizeEqualityNode__ = s19_.insert((DoEqualityNodeGen.create()));
                    Objects.requireNonNull(sizeEqualityNode__, "Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' cache 'sizeEqualityNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                    s19_.sizeEqualityNode_ = sizeEqualityNode__;
                    DoEqualityNode doEqualityNode_2;
                    DoEqualityNode doEqualityNode_2_shared = this.doEqualityNode;
                    if (doEqualityNode_2_shared != null) {
                        doEqualityNode_2 = doEqualityNode_2_shared;
                    } else {
                        doEqualityNode_2 = s19_.insert((DoEqualityNodeGen.create()));
                        if (doEqualityNode_2 == null) {
                            throw new IllegalStateException("Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                        }
                    }
                    if (this.doEqualityNode == null) {
                        this.doEqualityNode = doEqualityNode_2;
                    }
                    VarHandle.storeStoreFence();
                    this.array_cache = s19_;
                    state_0 = state_0 | 0x80000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */;
                    this.state_0_ = state_0;
                    return doArray(this, arg1Value_, arg2Value_, indexEqualityNode__, sizeEqualityNode__, doEqualityNode_2);
                }
            }
            if (arg1Value instanceof Structure) {
                Structure arg1Value_ = (Structure) arg1Value;
                if (arg2Value instanceof Structure) {
                    Structure arg2Value_ = (Structure) arg2Value;
                    StructureData s20_ = this.insert(new StructureData());
                    DynamicObjectLibrary leftLibrary__ = s20_.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    Objects.requireNonNull(leftLibrary__, "Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' cache 'leftLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                    s20_.leftLibrary_ = leftLibrary__;
                    DynamicObjectLibrary rightLibrary__ = s20_.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    Objects.requireNonNull(rightLibrary__, "Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' cache 'rightLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                    s20_.rightLibrary_ = rightLibrary__;
                    DoEqualityNode doEqualityNode_3;
                    DoEqualityNode doEqualityNode_3_shared = this.doEqualityNode;
                    if (doEqualityNode_3_shared != null) {
                        doEqualityNode_3 = doEqualityNode_3_shared;
                    } else {
                        doEqualityNode_3 = s20_.insert((DoEqualityNodeGen.create()));
                        if (doEqualityNode_3 == null) {
                            throw new IllegalStateException("Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                        }
                    }
                    if (this.doEqualityNode == null) {
                        this.doEqualityNode = doEqualityNode_3;
                    }
                    VarHandle.storeStoreFence();
                    this.structure_cache = s20_;
                    state_0 = state_0 | 0x100000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */;
                    this.state_0_ = state_0;
                    return doStructure(this, arg1Value_, arg2Value_, leftLibrary__, rightLibrary__, doEqualityNode_3);
                }
            }
            state_0 = state_0 | 0x200000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */;
            this.state_0_ = state_0;
            return objectEquals(arg1Value, arg2Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 0x3fffff) == 0) {
                return NodeCost.UNINITIALIZED;
            } else {
                int counter = 0;
                counter += Integer.bitCount((state_0 & 0x3fffff));
                if (counter == 1) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @NeverDefault
        public static DoEqualityNode create() {
            return new DoEqualityNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * <li>{@link Inlined#state_1_}
         * <li>{@link Inlined#doEqualityNode}
         * <li>{@link Inlined#array_cache}
         * <li>{@link Inlined#structure_cache}
         * </ul>
         */
        @NeverDefault
        public static DoEqualityNode inline(@RequiredField(bits = 32, value = StateField.class)@RequiredField(bits = 2, value = StateField.class)@RequiredField(type = Node.class, value = ReferenceField.class)@RequiredField(type = Node.class, value = ReferenceField.class)@RequiredField(type = Node.class, value = ReferenceField.class) InlineTarget target) {
            return new DoEqualityNodeGen.Inlined(target);
        }

        @GeneratedBy(DoEqualityNode.class)
        @DenyReplace
        private static final class Inlined extends DoEqualityNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link DoEqualityNode#longEquals}
             *   1: SpecializationActive {@link DoEqualityNode#bigNumberEquals}
             *   2: SpecializationActive {@link DoEqualityNode#smallRationalEquals}
             *   3: SpecializationActive {@link DoEqualityNode#smallRationalLong}
             *   4: SpecializationActive {@link DoEqualityNode#longSmallRational}
             *   5: SpecializationActive {@link DoEqualityNode#rationalEquals}
             *   6: SpecializationActive {@link DoEqualityNode#rationalBigNumber}
             *   7: SpecializationActive {@link DoEqualityNode#bigNumberRational}
             *   8: SpecializationActive {@link DoEqualityNode#doSmallSciNum}
             *   9: SpecializationActive {@link DoEqualityNode#doSmallSciNumLong}
             *   10: SpecializationActive {@link DoEqualityNode#doLongSmallSciNum}
             *   11: SpecializationActive {@link DoEqualityNode#doSciNum}
             *   12: SpecializationActive {@link DoEqualityNode#doBigNumSciNum}
             *   13: SpecializationActive {@link DoEqualityNode#doSciNumBigNum}
             *   14: SpecializationActive {@link DoEqualityNode#doRationalSciNum}
             *   15: SpecializationActive {@link DoEqualityNode#doSciNumRational}
             *   16: SpecializationActive {@link DoEqualityNode#doMeasure}
             *   17: SpecializationActive {@link DoEqualityNode#doTaggedValue}
             *   18: SpecializationActive {@link DoEqualityNode#doString}
             *   19: SpecializationActive {@link DoEqualityNode#doArray}
             *   20: SpecializationActive {@link DoEqualityNode#doStructure}
             *   21: SpecializationActive {@link DoEqualityNode#objectEquals}
             *   22-23: ImplicitCast[type=BigNumber, index=1]
             *   24-25: ImplicitCast[type=BigNumber, index=2]
             *   26-27: ImplicitCast[type=Rational, index=1]
             *   28-29: ImplicitCast[type=Rational, index=2]
             *   30-31: ImplicitCast[type=SciNum, index=1]
             * </pre>
             */
            private final StateField state_0_;
            /**
             * State Info: <pre>
             *   0-1: ImplicitCast[type=SciNum, index=2]
             * </pre>
             */
            private final StateField state_1_;
            private final ReferenceField<DoEqualityNode> doEqualityNode;
            private final ReferenceField<ArrayData> array_cache;
            private final ReferenceField<StructureData> structure_cache;

            @SuppressWarnings("unchecked")
            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(DoEqualityNode.class);
                this.state_0_ = target.getState(0, 32);
                this.state_1_ = target.getState(1, 2);
                this.doEqualityNode = target.getReference(2, DoEqualityNode.class);
                this.array_cache = target.getReference(3, ArrayData.class);
                this.structure_cache = target.getReference(4, StructureData.class);
            }

            @SuppressWarnings("static-method")
            private boolean fallbackGuard_(int state_0, Node arg0Value, Object arg1Value, Object arg2Value) {
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
                if (!((state_0 & 0x10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */) && arg1Value instanceof Measure && arg2Value instanceof Measure) {
                    return false;
                }
                if (!((state_0 & 0x20000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */) && arg1Value instanceof TaggedValue && arg2Value instanceof TaggedValue) {
                    return false;
                }
                if (!((state_0 & 0x40000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */) && arg1Value instanceof TruffleString && arg2Value instanceof TruffleString) {
                    return false;
                }
                if (!((state_0 & 0x80000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */) && arg1Value instanceof TailspinArray && arg2Value instanceof TailspinArray) {
                    return false;
                }
                if (!((state_0 & 0x100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */) && arg1Value instanceof Structure && arg2Value instanceof Structure) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean executeEquals(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if ((state_0 & 0x3fffff) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            return longEquals(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return bigNumberEquals(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1100) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        if ((state_0 & 0b100) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] */ && arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            return smallRationalEquals(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b1000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */ && arg2Value instanceof Long) {
                            long arg2Value_ = (long) arg2Value;
                            return smallRationalLong(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        if (arg2Value instanceof SmallRational) {
                            SmallRational arg2Value_ = (SmallRational) arg2Value;
                            return longSmallRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if ((state_0 & 0b100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return rationalEquals(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return rationalBigNumber(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1100000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        if ((state_0 & 0b100000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */ && arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            return doSmallSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b1000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */ && arg2Value instanceof Long) {
                            Long arg2Value_ = (Long) arg2Value;
                            return doSmallSciNumLong(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] */ && arg1Value instanceof Long) {
                        Long arg1Value_ = (Long) arg1Value;
                        if (arg2Value instanceof SmallSciNum) {
                            SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                            return doLongSmallSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1100000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] || SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitSciNum(state_1_.get(arg0Value) >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                        SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(state_1_.get(arg0Value) >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                        if ((state_0 & 0b100000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                            SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                            return doSciNum(arg1Value_, arg2Value_);
                        }
                        if ((state_0 & 0b1000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value)) {
                            BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0xc00000) >>> 22 /* get-int ImplicitCast[type=BigNumber, index=1] */, arg1Value);
                            return doBigNumSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b10000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value)) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0x3000000) >>> 24 /* get-int ImplicitCast[type=BigNumber, index=2] */, arg2Value);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b100000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value)) {
                        Rational arg1Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0xc000000) >>> 26 /* get-int ImplicitCast[type=Rational, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitSciNum(state_1_.get(arg0Value) >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value)) {
                            SciNum arg2Value_ = TailspinTypesGen.asImplicitSciNum(state_1_.get(arg0Value) >>> 0 /* get-int ImplicitCast[type=SciNum, index=2] */, arg2Value);
                            return doRationalSciNum(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0b1000000000000000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value)) {
                        SciNum arg1Value_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0xc0000000) >>> 30 /* get-int ImplicitCast[type=SciNum, index=1] */, arg1Value);
                        if (TailspinTypesGen.isImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value)) {
                            Rational arg2Value_ = TailspinTypesGen.asImplicitRational((state_0 & 0x30000000) >>> 28 /* get-int ImplicitCast[type=Rational, index=2] */, arg2Value);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x10000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */ && arg1Value instanceof Measure) {
                        Measure arg1Value_ = (Measure) arg1Value;
                        if (arg2Value instanceof Measure) {
                            Measure arg2Value_ = (Measure) arg2Value;
                            {
                                DoEqualityNode doEqualityNode_ = this.doEqualityNode.get(arg0Value);
                                if (doEqualityNode_ != null) {
                                    return doMeasure(arg0Value, arg1Value_, arg2Value_, doEqualityNode_);
                                }
                            }
                        }
                    }
                    if ((state_0 & 0x20000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */ && arg1Value instanceof TaggedValue) {
                        TaggedValue arg1Value_ = (TaggedValue) arg1Value;
                        if (arg2Value instanceof TaggedValue) {
                            TaggedValue arg2Value_ = (TaggedValue) arg2Value;
                            {
                                DoEqualityNode doEqualityNode_1 = this.doEqualityNode.get(arg0Value);
                                if (doEqualityNode_1 != null) {
                                    return doTaggedValue(arg0Value, arg1Value_, arg2Value_, doEqualityNode_1);
                                }
                            }
                        }
                    }
                    if ((state_0 & 0x40000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */ && arg1Value instanceof TruffleString) {
                        TruffleString arg1Value_ = (TruffleString) arg1Value;
                        if (arg2Value instanceof TruffleString) {
                            TruffleString arg2Value_ = (TruffleString) arg2Value;
                            return doString(arg1Value_, arg2Value_);
                        }
                    }
                    if ((state_0 & 0x80000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */ && arg1Value instanceof TailspinArray) {
                        TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                        if (arg2Value instanceof TailspinArray) {
                            TailspinArray arg2Value_ = (TailspinArray) arg2Value;
                            ArrayData s19_ = this.array_cache.get(arg0Value);
                            if (s19_ != null) {
                                {
                                    DoEqualityNode doEqualityNode_2 = this.doEqualityNode.get(arg0Value);
                                    if (doEqualityNode_2 != null) {
                                        return doArray(arg0Value, arg1Value_, arg2Value_, s19_.indexEqualityNode_, s19_.sizeEqualityNode_, doEqualityNode_2);
                                    }
                                }
                            }
                        }
                    }
                    if ((state_0 & 0x100000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */ && arg1Value instanceof Structure) {
                        Structure arg1Value_ = (Structure) arg1Value;
                        if (arg2Value instanceof Structure) {
                            Structure arg2Value_ = (Structure) arg2Value;
                            StructureData s20_ = this.structure_cache.get(arg0Value);
                            if (s20_ != null) {
                                {
                                    DoEqualityNode doEqualityNode_3 = this.doEqualityNode.get(arg0Value);
                                    if (doEqualityNode_3 != null) {
                                        return doStructure(arg0Value, arg1Value_, arg2Value_, s20_.leftLibrary_, s20_.rightLibrary_, doEqualityNode_3);
                                    }
                                }
                            }
                        }
                    }
                    if ((state_0 & 0x200000) != 0 /* is SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */) {
                        if (fallbackGuard_(state_0, arg0Value, arg1Value, arg2Value)) {
                            return objectEquals(arg1Value, arg2Value);
                        }
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private boolean executeAndSpecialize(Node arg0Value, Object arg1Value, Object arg2Value) {
                int state_0 = this.state_0_.get(arg0Value);
                int state_1 = this.state_1_.get(arg0Value);
                if (((state_0 & 0b10)) == 0 /* is-not SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return longEquals(arg1Value_, arg2Value_);
                    }
                }
                {
                    int bigNumberCast1;
                    if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                        BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                        int bigNumberCast2;
                        if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                            BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                            state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[EqualityMatcherNode.DoEqualityNode.longEquals(long, long)] */;
                            state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberEquals(BigNumber, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return bigNumberEquals(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b100 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalEquals(SmallRational, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return smallRationalEquals(arg1Value_, arg2Value_);
                    }
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (long) arg2Value;
                        state_0 = state_0 | 0b1000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.smallRationalLong(SmallRational, long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return smallRationalLong(arg1Value_, arg2Value_);
                    }
                }
                if (arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    if (arg2Value instanceof SmallRational) {
                        SmallRational arg2Value_ = (SmallRational) arg2Value;
                        state_0 = state_0 | 0b10000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.longSmallRational(long, SmallRational)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return longSmallRational(arg1Value_, arg2Value_);
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
                                state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                                state_0 = state_0 | 0b100000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalEquals(Rational, Rational)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return rationalEquals(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast2;
                            if ((bigNumberCast2 = TailspinTypesGen.specializeImplicitBigNumber(arg2Value)) != 0) {
                                BigNumber arg2Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast2, arg2Value);
                                state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                                state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                                state_0 = state_0 | 0b1000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.rationalBigNumber(Rational, BigNumber)] */;
                                this.state_0_.set(arg0Value, state_0);
                                return rationalBigNumber(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b10000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.bigNumberRational(BigNumber, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return bigNumberRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b100000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNum(SmallSciNum, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doSmallSciNum(arg1Value_, arg2Value_);
                    }
                    if (arg2Value instanceof Long) {
                        Long arg2Value_ = (Long) arg2Value;
                        state_0 = state_0 | 0b1000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSmallSciNumLong(SmallSciNum, Long)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doSmallSciNumLong(arg1Value_, arg2Value_);
                    }
                }
                if (arg1Value instanceof Long) {
                    Long arg1Value_ = (Long) arg1Value;
                    if (arg2Value instanceof SmallSciNum) {
                        SmallSciNum arg2Value_ = (SmallSciNum) arg2Value;
                        state_0 = state_0 | 0b10000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doLongSmallSciNum(Long, SmallSciNum)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doLongSmallSciNum(arg1Value_, arg2Value_);
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
                                state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b100000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNum(SciNum, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doSciNum(arg1Value_, arg2Value_);
                            }
                        }
                        {
                            int bigNumberCast1;
                            if ((bigNumberCast1 = TailspinTypesGen.specializeImplicitBigNumber(arg1Value)) != 0) {
                                BigNumber arg1Value_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast1, arg1Value);
                                state_0 = (state_0 | (bigNumberCast1 << 22) /* set-int ImplicitCast[type=BigNumber, index=1] */);
                                state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                                state_0 = state_0 | 0b1000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doBigNumSciNum(BigNumber, SciNum)] */;
                                this.state_0_.set(arg0Value, state_0);
                                this.state_1_.set(arg0Value, state_1);
                                return doBigNumSciNum(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (bigNumberCast2 << 24) /* set-int ImplicitCast[type=BigNumber, index=2] */);
                            state_0 = state_0 | 0b10000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumBigNum(SciNum, BigNumber)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doSciNumBigNum(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (rationalCast1 << 26) /* set-int ImplicitCast[type=Rational, index=1] */);
                            state_1 = (state_1 | (sciNumCast2 << 0) /* set-int ImplicitCast[type=SciNum, index=2] */);
                            state_0 = state_0 | 0b100000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doRationalSciNum(Rational, SciNum)] */;
                            this.state_0_.set(arg0Value, state_0);
                            this.state_1_.set(arg0Value, state_1);
                            return doRationalSciNum(arg1Value_, arg2Value_);
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
                            state_0 = (state_0 | (sciNumCast1 << 30) /* set-int ImplicitCast[type=SciNum, index=1] */);
                            state_0 = (state_0 | (rationalCast2 << 28) /* set-int ImplicitCast[type=Rational, index=2] */);
                            state_0 = state_0 | 0b1000000000000000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doSciNumRational(SciNum, Rational)] */;
                            this.state_0_.set(arg0Value, state_0);
                            return doSciNumRational(arg1Value_, arg2Value_);
                        }
                    }
                }
                if (arg1Value instanceof Measure) {
                    Measure arg1Value_ = (Measure) arg1Value;
                    if (arg2Value instanceof Measure) {
                        Measure arg2Value_ = (Measure) arg2Value;
                        DoEqualityNode doEqualityNode_;
                        DoEqualityNode doEqualityNode__shared = this.doEqualityNode.get(arg0Value);
                        if (doEqualityNode__shared != null) {
                            doEqualityNode_ = doEqualityNode__shared;
                        } else {
                            doEqualityNode_ = arg0Value.insert((DoEqualityNodeGen.create()));
                            if (doEqualityNode_ == null) {
                                throw new IllegalStateException("Specialization 'doMeasure(Node, Measure, Measure, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                            }
                        }
                        if (this.doEqualityNode.get(arg0Value) == null) {
                            VarHandle.storeStoreFence();
                            this.doEqualityNode.set(arg0Value, doEqualityNode_);
                        }
                        state_0 = state_0 | 0x10000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doMeasure(Node, Measure, Measure, DoEqualityNode)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doMeasure(arg0Value, arg1Value_, arg2Value_, doEqualityNode_);
                    }
                }
                if (arg1Value instanceof TaggedValue) {
                    TaggedValue arg1Value_ = (TaggedValue) arg1Value;
                    if (arg2Value instanceof TaggedValue) {
                        TaggedValue arg2Value_ = (TaggedValue) arg2Value;
                        DoEqualityNode doEqualityNode_1;
                        DoEqualityNode doEqualityNode_1_shared = this.doEqualityNode.get(arg0Value);
                        if (doEqualityNode_1_shared != null) {
                            doEqualityNode_1 = doEqualityNode_1_shared;
                        } else {
                            doEqualityNode_1 = arg0Value.insert((DoEqualityNodeGen.create()));
                            if (doEqualityNode_1 == null) {
                                throw new IllegalStateException("Specialization 'doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                            }
                        }
                        if (this.doEqualityNode.get(arg0Value) == null) {
                            VarHandle.storeStoreFence();
                            this.doEqualityNode.set(arg0Value, doEqualityNode_1);
                        }
                        state_0 = state_0 | 0x20000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doTaggedValue(Node, TaggedValue, TaggedValue, DoEqualityNode)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doTaggedValue(arg0Value, arg1Value_, arg2Value_, doEqualityNode_1);
                    }
                }
                if (arg1Value instanceof TruffleString) {
                    TruffleString arg1Value_ = (TruffleString) arg1Value;
                    if (arg2Value instanceof TruffleString) {
                        TruffleString arg2Value_ = (TruffleString) arg2Value;
                        state_0 = state_0 | 0x40000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doString(TruffleString, TruffleString)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doString(arg1Value_, arg2Value_);
                    }
                }
                if (arg1Value instanceof TailspinArray) {
                    TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                    if (arg2Value instanceof TailspinArray) {
                        TailspinArray arg2Value_ = (TailspinArray) arg2Value;
                        ArrayData s19_ = arg0Value.insert(new ArrayData());
                        DoEqualityNode indexEqualityNode__ = s19_.insert((DoEqualityNodeGen.create()));
                        Objects.requireNonNull(indexEqualityNode__, "Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' cache 'indexEqualityNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                        s19_.indexEqualityNode_ = indexEqualityNode__;
                        DoEqualityNode sizeEqualityNode__ = s19_.insert((DoEqualityNodeGen.create()));
                        Objects.requireNonNull(sizeEqualityNode__, "Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' cache 'sizeEqualityNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                        s19_.sizeEqualityNode_ = sizeEqualityNode__;
                        DoEqualityNode doEqualityNode_2;
                        DoEqualityNode doEqualityNode_2_shared = this.doEqualityNode.get(arg0Value);
                        if (doEqualityNode_2_shared != null) {
                            doEqualityNode_2 = doEqualityNode_2_shared;
                        } else {
                            doEqualityNode_2 = s19_.insert((DoEqualityNodeGen.create()));
                            if (doEqualityNode_2 == null) {
                                throw new IllegalStateException("Specialization 'doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                            }
                        }
                        if (this.doEqualityNode.get(arg0Value) == null) {
                            this.doEqualityNode.set(arg0Value, doEqualityNode_2);
                        }
                        VarHandle.storeStoreFence();
                        this.array_cache.set(arg0Value, s19_);
                        state_0 = state_0 | 0x80000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doArray(Node, TailspinArray, TailspinArray, DoEqualityNode, DoEqualityNode, DoEqualityNode)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doArray(arg0Value, arg1Value_, arg2Value_, indexEqualityNode__, sizeEqualityNode__, doEqualityNode_2);
                    }
                }
                if (arg1Value instanceof Structure) {
                    Structure arg1Value_ = (Structure) arg1Value;
                    if (arg2Value instanceof Structure) {
                        Structure arg2Value_ = (Structure) arg2Value;
                        StructureData s20_ = arg0Value.insert(new StructureData());
                        DynamicObjectLibrary leftLibrary__ = s20_.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                        Objects.requireNonNull(leftLibrary__, "Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' cache 'leftLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                        s20_.leftLibrary_ = leftLibrary__;
                        DynamicObjectLibrary rightLibrary__ = s20_.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                        Objects.requireNonNull(rightLibrary__, "Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' cache 'rightLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                        s20_.rightLibrary_ = rightLibrary__;
                        DoEqualityNode doEqualityNode_3;
                        DoEqualityNode doEqualityNode_3_shared = this.doEqualityNode.get(arg0Value);
                        if (doEqualityNode_3_shared != null) {
                            doEqualityNode_3 = doEqualityNode_3_shared;
                        } else {
                            doEqualityNode_3 = s20_.insert((DoEqualityNodeGen.create()));
                            if (doEqualityNode_3 == null) {
                                throw new IllegalStateException("Specialization 'doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)' contains a shared cache with name 'doEqualityNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                            }
                        }
                        if (this.doEqualityNode.get(arg0Value) == null) {
                            this.doEqualityNode.set(arg0Value, doEqualityNode_3);
                        }
                        VarHandle.storeStoreFence();
                        this.structure_cache.set(arg0Value, s20_);
                        state_0 = state_0 | 0x100000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.doStructure(Node, Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary, DoEqualityNode)] */;
                        this.state_0_.set(arg0Value, state_0);
                        return doStructure(arg0Value, arg1Value_, arg2Value_, leftLibrary__, rightLibrary__, doEqualityNode_3);
                    }
                }
                state_0 = state_0 | 0x200000 /* add SpecializationActive[EqualityMatcherNode.DoEqualityNode.objectEquals(Object, Object)] */;
                this.state_0_.set(arg0Value, state_0);
                return objectEquals(arg1Value, arg2Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
        @GeneratedBy(DoEqualityNode.class)
        @DenyReplace
        private static final class ArrayData extends Node implements SpecializationDataNode {

            /**
             * Source Info: <pre>
             *   Specialization: {@link DoEqualityNode#doArray}
             *   Parameter: {@link DoEqualityNode} indexEqualityNode</pre>
             */
            @Child DoEqualityNode indexEqualityNode_;
            /**
             * Source Info: <pre>
             *   Specialization: {@link DoEqualityNode#doArray}
             *   Parameter: {@link DoEqualityNode} sizeEqualityNode</pre>
             */
            @Child DoEqualityNode sizeEqualityNode_;

            ArrayData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

        }
        @GeneratedBy(DoEqualityNode.class)
        @DenyReplace
        private static final class StructureData extends Node implements SpecializationDataNode {

            /**
             * Source Info: <pre>
             *   Specialization: {@link DoEqualityNode#doStructure}
             *   Parameter: {@link DynamicObjectLibrary} leftLibrary</pre>
             */
            @Child DynamicObjectLibrary leftLibrary_;
            /**
             * Source Info: <pre>
             *   Specialization: {@link DoEqualityNode#doStructure}
             *   Parameter: {@link DynamicObjectLibrary} rightLibrary</pre>
             */
            @Child DynamicObjectLibrary rightLibrary_;

            StructureData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

        }
    }
}
