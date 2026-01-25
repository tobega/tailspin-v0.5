// CheckStyle: start generated
package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.DSLSupport;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.dsl.DSLSupport.SpecializationDataNode;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;

/**
 * Debug Info: <pre>
 *   Specialization {@link RangeIteration#doIterateMeasureDefault}
 *     Activation probability: 0,32000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link RangeIteration#doIterateNumberDefault}
 *     Activation probability: 0,26000
 *     With/without class size: 7/0 bytes
 *   Specialization {@link RangeIteration#doIterateInferStart}
 *     Activation probability: 0,20000
 *     With/without class size: 6/0 bytes
 *   Specialization {@link RangeIteration#doIterateInferEnd}
 *     Activation probability: 0,14000
 *     With/without class size: 5/0 bytes
 *   Specialization {@link RangeIteration#doIterate}
 *     Activation probability: 0,08000
 *     With/without class size: 4/0 bytes
 * </pre>
 */
@GeneratedBy(RangeIteration.class)
@SuppressWarnings("javadoc")
public final class RangeIterationNodeGen extends RangeIteration {

    @Child private ValueNode start_;
    @Child private ValueNode end_;
    @Child private ValueNode increment_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link RangeIteration#doIterateMeasureDefault}
     *   1: SpecializationActive {@link RangeIteration#doIterateNumberDefault}
     *   2: SpecializationActive {@link RangeIteration#doIterateInferStart}
     *   3: SpecializationActive {@link RangeIteration#doIterateInferEnd}
     *   4: SpecializationActive {@link RangeIteration#doIterate}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link RangeIteration#doIterateInferStart}
     *   Parameter: {@link MessageNode} getFirst</pre>
     */
    @Child private MessageNode getFirst;
    /**
     * Source Info: <pre>
     *   Specialization: {@link RangeIteration#doIterateInferStart}
     *   Parameter: {@link MessageNode} getLast</pre>
     */
    @Child private MessageNode getLast;

    private RangeIterationNodeGen(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart, boolean inclusiveEnd, SourceSection sourceSection, ValueNode start, ValueNode end, ValueNode increment) {
        super(startSlot, endSlot, incrementSlot, inclusiveStart, inclusiveEnd, sourceSection);
        this.start_ = start;
        this.end_ = end;
        this.increment_ = increment;
    }

    @Override
    public void executeDirect(VirtualFrame frameValue, Object startValue, Object endValue, Object incrementValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[RangeIteration.doIterateMeasureDefault(VirtualFrame, Measure, Measure, Object)] || SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] || SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.doIterateMeasureDefault(VirtualFrame, Measure, Measure, Object)] */ && startValue instanceof Measure) {
                Measure startValue_ = (Measure) startValue;
                if (endValue instanceof Measure) {
                    Measure endValue_ = (Measure) endValue;
                    if ((incrementValue == null)) {
                        doIterateMeasureDefault(frameValue, startValue_, endValue_, incrementValue);
                        return;
                    }
                }
            }
            if ((state_0 & 0b11110) != 0 /* is SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] || SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] */) {
                    if ((incrementValue == null)) {
                        doIterateNumberDefault(frameValue, startValue, endValue, incrementValue);
                        return;
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */) {
                    {
                        MessageNode getFirst_ = this.getFirst;
                        if (getFirst_ != null) {
                            MessageNode getLast_ = this.getLast;
                            if (getLast_ != null) {
                                if ((startValue == null)) {
                                    doIterateInferStart(frameValue, startValue, endValue, incrementValue, getFirst_, getLast_);
                                    return;
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */) {
                    {
                        MessageNode getFirst_1 = this.getFirst;
                        if (getFirst_1 != null) {
                            MessageNode getLast_1 = this.getLast;
                            if (getLast_1 != null) {
                                if ((endValue == null)) {
                                    doIterateInferEnd(frameValue, startValue, endValue, incrementValue, getFirst_1, getLast_1);
                                    return;
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
                    if ((startValue != null) && (endValue != null) && (incrementValue != null)) {
                        doIterate(frameValue, startValue, endValue, incrementValue);
                        return;
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, startValue, endValue, incrementValue);
        return;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object startValue_ = this.start_.executeGeneric(frameValue);
        Object endValue_ = this.end_.executeGeneric(frameValue);
        Object incrementValue_ = this.increment_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[RangeIteration.doIterateMeasureDefault(VirtualFrame, Measure, Measure, Object)] || SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] || SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.doIterateMeasureDefault(VirtualFrame, Measure, Measure, Object)] */ && startValue_ instanceof Measure) {
                Measure startValue__ = (Measure) startValue_;
                if (endValue_ instanceof Measure) {
                    Measure endValue__ = (Measure) endValue_;
                    if ((incrementValue_ == null)) {
                        doIterateMeasureDefault(frameValue, startValue__, endValue__, incrementValue_);
                        return;
                    }
                }
            }
            if ((state_0 & 0b11110) != 0 /* is SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] || SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] || SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] */) {
                    if ((incrementValue_ == null)) {
                        doIterateNumberDefault(frameValue, startValue_, endValue_, incrementValue_);
                        return;
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */) {
                    {
                        MessageNode getFirst_ = this.getFirst;
                        if (getFirst_ != null) {
                            MessageNode getLast_ = this.getLast;
                            if (getLast_ != null) {
                                if ((startValue_ == null)) {
                                    doIterateInferStart(frameValue, startValue_, endValue_, incrementValue_, getFirst_, getLast_);
                                    return;
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */) {
                    {
                        MessageNode getFirst_1 = this.getFirst;
                        if (getFirst_1 != null) {
                            MessageNode getLast_1 = this.getLast;
                            if (getLast_1 != null) {
                                if ((endValue_ == null)) {
                                    doIterateInferEnd(frameValue, startValue_, endValue_, incrementValue_, getFirst_1, getLast_1);
                                    return;
                                }
                            }
                        }
                    }
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */) {
                    if ((startValue_ != null) && (endValue_ != null) && (incrementValue_ != null)) {
                        doIterate(frameValue, startValue_, endValue_, incrementValue_);
                        return;
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, startValue_, endValue_, incrementValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object startValue, Object endValue, Object incrementValue) {
        int state_0 = this.state_0_;
        if (startValue instanceof Measure) {
            Measure startValue_ = (Measure) startValue;
            if (endValue instanceof Measure) {
                Measure endValue_ = (Measure) endValue;
                if ((incrementValue == null)) {
                    state_0 = state_0 | 0b1 /* add SpecializationActive[RangeIteration.doIterateMeasureDefault(VirtualFrame, Measure, Measure, Object)] */;
                    this.state_0_ = state_0;
                    doIterateMeasureDefault(frameValue, startValue_, endValue_, incrementValue);
                    return;
                }
            }
        }
        if ((incrementValue == null)) {
            state_0 = state_0 | 0b10 /* add SpecializationActive[RangeIteration.doIterateNumberDefault(VirtualFrame, Object, Object, Object)] */;
            this.state_0_ = state_0;
            doIterateNumberDefault(frameValue, startValue, endValue, incrementValue);
            return;
        }
        if ((startValue == null)) {
            MessageNode getFirst_;
            MessageNode getFirst__shared = this.getFirst;
            if (getFirst__shared != null) {
                getFirst_ = getFirst__shared;
            } else {
                getFirst_ = this.insert((createGetFirst()));
                if (getFirst_ == null) {
                    throw new IllegalStateException("Specialization 'doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)' contains a shared cache with name 'getFirst' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.getFirst == null) {
                VarHandle.storeStoreFence();
                this.getFirst = getFirst_;
            }
            MessageNode getLast_;
            MessageNode getLast__shared = this.getLast;
            if (getLast__shared != null) {
                getLast_ = getLast__shared;
            } else {
                getLast_ = this.insert((createGetLast()));
                if (getLast_ == null) {
                    throw new IllegalStateException("Specialization 'doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)' contains a shared cache with name 'getLast' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.getLast == null) {
                VarHandle.storeStoreFence();
                this.getLast = getLast_;
            }
            state_0 = state_0 | 0b100 /* add SpecializationActive[RangeIteration.doIterateInferStart(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */;
            this.state_0_ = state_0;
            doIterateInferStart(frameValue, startValue, endValue, incrementValue, getFirst_, getLast_);
            return;
        }
        if ((endValue == null)) {
            MessageNode getFirst_1;
            MessageNode getFirst_1_shared = this.getFirst;
            if (getFirst_1_shared != null) {
                getFirst_1 = getFirst_1_shared;
            } else {
                getFirst_1 = this.insert((createGetFirst()));
                if (getFirst_1 == null) {
                    throw new IllegalStateException("Specialization 'doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)' contains a shared cache with name 'getFirst' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.getFirst == null) {
                VarHandle.storeStoreFence();
                this.getFirst = getFirst_1;
            }
            MessageNode getLast_1;
            MessageNode getLast_1_shared = this.getLast;
            if (getLast_1_shared != null) {
                getLast_1 = getLast_1_shared;
            } else {
                getLast_1 = this.insert((createGetLast()));
                if (getLast_1 == null) {
                    throw new IllegalStateException("Specialization 'doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)' contains a shared cache with name 'getLast' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.getLast == null) {
                VarHandle.storeStoreFence();
                this.getLast = getLast_1;
            }
            state_0 = state_0 | 0b1000 /* add SpecializationActive[RangeIteration.doIterateInferEnd(VirtualFrame, Object, Object, Object, MessageNode, MessageNode)] */;
            this.state_0_ = state_0;
            doIterateInferEnd(frameValue, startValue, endValue, incrementValue, getFirst_1, getLast_1);
            return;
        }
        if ((startValue != null) && (endValue != null) && (incrementValue != null)) {
            state_0 = state_0 | 0b10000 /* add SpecializationActive[RangeIteration.doIterate(VirtualFrame, Object, Object, Object)] */;
            this.state_0_ = state_0;
            doIterate(frameValue, startValue, endValue, incrementValue);
            return;
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.start_, this.end_, this.increment_}, startValue, endValue, incrementValue);
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
    public static RangeIteration create(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart, boolean inclusiveEnd, SourceSection sourceSection, ValueNode start, ValueNode end, ValueNode increment) {
        return new RangeIterationNodeGen(startSlot, endSlot, incrementSlot, inclusiveStart, inclusiveEnd, sourceSection, start, end, increment);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link InitializeRangeIteratorNode#doInitializeInclusive}
     *     Activation probability: 0,65000
     *     With/without class size: 14/0 bytes
     *   Specialization {@link InitializeRangeIteratorNode#doInitialize}
     *     Activation probability: 0,35000
     *     With/without class size: 11/4 bytes
     * </pre>
     */
    @GeneratedBy(InitializeRangeIteratorNode.class)
    @SuppressWarnings("javadoc")
    static final class InitializeRangeIteratorNodeGen extends InitializeRangeIteratorNode {

        /**
         * Source Info: <pre>
         *   Specialization: {@link InitializeRangeIteratorNode#doInitializeInclusive}
         *   Parameter: {@link WriteLocalValueNode} writeStart
         *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
         */
        private static final WriteLocalValueNode INLINED_START = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));
        /**
         * Source Info: <pre>
         *   Specialization: {@link InitializeRangeIteratorNode#doInitializeInclusive}
         *   Parameter: {@link WriteLocalValueNode} writeEnd
         *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
         */
        private static final WriteLocalValueNode INLINED_END = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));
        /**
         * Source Info: <pre>
         *   Specialization: {@link InitializeRangeIteratorNode#doInitializeInclusive}
         *   Parameter: {@link WriteLocalValueNode} writeIncrement
         *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
         */
        private static final WriteLocalValueNode INLINED_INCREMENT = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link InitializeRangeIteratorNode#doInitializeInclusive}
         *   1: SpecializationActive {@link InitializeRangeIteratorNode#doInitialize}
         * </pre>
         */
        @CompilationFinal private int state_0_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link InitializeRangeIteratorNode#doInitialize}
         *   Parameter: {@link AddNode} addNode</pre>
         */
        @Child private AddNode initialize_addNode_;

        private InitializeRangeIteratorNodeGen(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart) {
            super(startSlot, endSlot, incrementSlot, inclusiveStart);
        }

        @Override
        public void executeInitialize(VirtualFrame frameValue, Object arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitializeInclusive(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode)] || SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitialize(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode, AddNode)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitializeInclusive(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode)] */) {
                    assert DSLSupport.assertIdempotence((inclusiveStart));
                    doInitializeInclusive(frameValue, arg0Value, arg1Value, arg2Value, INLINED_START, INLINED_END, INLINED_INCREMENT);
                    return;
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitialize(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode, AddNode)] */) {
                    {
                        AddNode addNode__ = this.initialize_addNode_;
                        if (addNode__ != null) {
                            doInitialize(frameValue, arg0Value, arg1Value, arg2Value, INLINED_START, INLINED_END, INLINED_INCREMENT, addNode__);
                            return;
                        }
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
            return;
        }

        private void executeAndSpecialize(VirtualFrame frameValue, Object arg0Value, Object arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if ((inclusiveStart)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitializeInclusive(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode)] */;
                this.state_0_ = state_0;
                doInitializeInclusive(frameValue, arg0Value, arg1Value, arg2Value, INLINED_START, INLINED_END, INLINED_INCREMENT);
                return;
            }
            AddNode addNode__ = this.insert((createAddNode()));
            Objects.requireNonNull(addNode__, "Specialization 'doInitialize(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode, AddNode)' cache 'addNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.initialize_addNode_ = addNode__;
            state_0 = state_0 | 0b10 /* add SpecializationActive[RangeIteration.InitializeRangeIteratorNode.doInitialize(VirtualFrame, Object, Object, Object, WriteLocalValueNode, WriteLocalValueNode, WriteLocalValueNode, AddNode)] */;
            this.state_0_ = state_0;
            doInitialize(frameValue, arg0Value, arg1Value, arg2Value, INLINED_START, INLINED_END, INLINED_INCREMENT, addNode__);
            return;
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
        public static InitializeRangeIteratorNode create(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart) {
            return new InitializeRangeIteratorNodeGen(startSlot, endSlot, incrementSlot, inclusiveStart);
        }

    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link RangeIteratorNode#doIncreasing}
     *     Activation probability: 0,65000
     *     With/without class size: 17/5 bytes
     *   Specialization {@link RangeIteratorNode#doDecreasing}
     *     Activation probability: 0,35000
     *     With/without class size: 11/5 bytes
     * </pre>
     */
    @GeneratedBy(RangeIteratorNode.class)
    @SuppressWarnings("javadoc")
    static final class RangeIteratorNodeGen extends RangeIteratorNode {

        private static final StateField STATE_0_UPDATER = StateField.create(MethodHandles.lookup(), "state_0_");
        /**
         * Source Info: <pre>
         *   Specialization: {@link RangeIteratorNode#doIncreasing}
         *   Parameter: {@link GtZeroNode} gtZeroNode
         *   Inline method: {@link GtZeroNodeGen#inline}</pre>
         */
        private static final GtZeroNode INLINED_GT_ZERO_NODE = GtZeroNodeGen.inline(InlineTarget.create(GtZeroNode.class, STATE_0_UPDATER.subUpdater(2, 7)));
        /**
         * Source Info: <pre>
         *   Specialization: {@link RangeIteratorNode#doIncreasing}
         *   Parameter: {@link WriteLocalValueNode} writeCurrent
         *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
         */
        private static final WriteLocalValueNode INLINED_CURRENT = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link RangeIteratorNode#doIncreasing}
         *   1: SpecializationActive {@link RangeIteratorNode#doDecreasing}
         *   2-8: InlinedCache
         *        Specialization: {@link RangeIteratorNode#doIncreasing}
         *        Parameter: {@link GtZeroNode} gtZeroNode
         *        Inline method: {@link GtZeroNodeGen#inline}
         * </pre>
         */
        @CompilationFinal @UnsafeAccessedField private int state_0_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link RangeIteratorNode#doIncreasing}
         *   Parameter: {@link AddNode} addNode</pre>
         */
        @Child private AddNode addNode;
        @Child private IncreasingData increasing_cache;
        @Child private DecreasingData decreasing_cache;

        private RangeIteratorNodeGen(int currentSlot, int endSlot, int incrementSlot, boolean inclusiveEnd) {
            super(currentSlot, endSlot, incrementSlot, inclusiveEnd);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object currentNodeValue_ = super.currentNode.executeGeneric(frameValue);
            Object endNodeValue_ = super.endNode.executeGeneric(frameValue);
            Object incrementNodeValue_ = super.incrementNode.executeGeneric(frameValue);
            if ((state_0 & 0b11) != 0 /* is SpecializationActive[RangeIteration.RangeIteratorNode.doIncreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, GreaterThanMatcherNode, WriteLocalValueNode, AddNode)] || SpecializationActive[RangeIteration.RangeIteratorNode.doDecreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, LessThanMatcherNode, WriteLocalValueNode, AddNode)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.RangeIteratorNode.doIncreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, GreaterThanMatcherNode, WriteLocalValueNode, AddNode)] */) {
                    IncreasingData s0_ = this.increasing_cache;
                    if (s0_ != null) {
                        {
                            AddNode addNode_ = this.addNode;
                            if (addNode_ != null) {
                                Node node__ = (this);
                                assert DSLSupport.assertIdempotence((s0_.isGt0_));
                                return doIncreasing(frameValue, currentNodeValue_, endNodeValue_, incrementNodeValue_, node__, INLINED_GT_ZERO_NODE, s0_.isGt0_, s0_.upperBoundExceeded_, INLINED_CURRENT, addNode_);
                            }
                        }
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.RangeIteratorNode.doDecreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, LessThanMatcherNode, WriteLocalValueNode, AddNode)] */) {
                    DecreasingData s1_ = this.decreasing_cache;
                    if (s1_ != null) {
                        {
                            AddNode addNode_1 = this.addNode;
                            if (addNode_1 != null) {
                                Node node__1 = (this);
                                return doDecreasing(frameValue, currentNodeValue_, endNodeValue_, incrementNodeValue_, node__1, INLINED_GT_ZERO_NODE, s1_.isGt0_, s1_.lowerBoundExceeded_, INLINED_CURRENT, addNode_1);
                            }
                        }
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(frameValue, currentNodeValue_, endNodeValue_, incrementNodeValue_);
        }

        private Object executeAndSpecialize(VirtualFrame frameValue, Object currentNodeValue, Object endNodeValue, Object incrementNodeValue) {
            int state_0 = this.state_0_;
            {
                Node node__ = null;
                {
                    node__ = (this);
                    boolean isGt0__ = (INLINED_GT_ZERO_NODE.executeTest(node__, incrementNodeValue));
                    if ((isGt0__)) {
                        IncreasingData s0_ = this.insert(new IncreasingData());
                        s0_.isGt0_ = isGt0__;
                        GreaterThanMatcherNode upperBoundExceeded__ = s0_.insert((createUpperBoundExceeded()));
                        Objects.requireNonNull(upperBoundExceeded__, "Specialization 'doIncreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, GreaterThanMatcherNode, WriteLocalValueNode, AddNode)' cache 'upperBoundExceeded' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                        s0_.upperBoundExceeded_ = upperBoundExceeded__;
                        AddNode addNode_;
                        AddNode addNode__shared = this.addNode;
                        if (addNode__shared != null) {
                            addNode_ = addNode__shared;
                        } else {
                            addNode_ = s0_.insert((createAddNode()));
                            if (addNode_ == null) {
                                throw new IllegalStateException("Specialization 'doIncreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, GreaterThanMatcherNode, WriteLocalValueNode, AddNode)' contains a shared cache with name 'addNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                            }
                        }
                        if (this.addNode == null) {
                            this.addNode = addNode_;
                        }
                        VarHandle.storeStoreFence();
                        this.increasing_cache = s0_;
                        state_0 = state_0 | 0b1 /* add SpecializationActive[RangeIteration.RangeIteratorNode.doIncreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, GreaterThanMatcherNode, WriteLocalValueNode, AddNode)] */;
                        this.state_0_ = state_0;
                        return doIncreasing(frameValue, currentNodeValue, endNodeValue, incrementNodeValue, node__, INLINED_GT_ZERO_NODE, isGt0__, upperBoundExceeded__, INLINED_CURRENT, addNode_);
                    }
                }
            }
            {
                Node node__1 = null;
                DecreasingData s1_ = this.insert(new DecreasingData());
                node__1 = (this);
                s1_.isGt0_ = (INLINED_GT_ZERO_NODE.executeTest(node__1, incrementNodeValue));
                LessThanMatcherNode lowerBoundExceeded__ = s1_.insert((createLowerBoundExceeded()));
                Objects.requireNonNull(lowerBoundExceeded__, "Specialization 'doDecreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, LessThanMatcherNode, WriteLocalValueNode, AddNode)' cache 'lowerBoundExceeded' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                s1_.lowerBoundExceeded_ = lowerBoundExceeded__;
                AddNode addNode_1;
                AddNode addNode_1_shared = this.addNode;
                if (addNode_1_shared != null) {
                    addNode_1 = addNode_1_shared;
                } else {
                    addNode_1 = s1_.insert((createAddNode()));
                    if (addNode_1 == null) {
                        throw new IllegalStateException("Specialization 'doDecreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, LessThanMatcherNode, WriteLocalValueNode, AddNode)' contains a shared cache with name 'addNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.addNode == null) {
                    this.addNode = addNode_1;
                }
                VarHandle.storeStoreFence();
                this.decreasing_cache = s1_;
                state_0 = state_0 | 0b10 /* add SpecializationActive[RangeIteration.RangeIteratorNode.doDecreasing(VirtualFrame, Object, Object, Object, Node, GtZeroNode, boolean, LessThanMatcherNode, WriteLocalValueNode, AddNode)] */;
                this.state_0_ = state_0;
                return doDecreasing(frameValue, currentNodeValue, endNodeValue, incrementNodeValue, node__1, INLINED_GT_ZERO_NODE, s1_.isGt0_, lowerBoundExceeded__, INLINED_CURRENT, addNode_1);
            }
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
        public static RangeIteratorNode create(int currentSlot, int endSlot, int incrementSlot, boolean inclusiveEnd) {
            return new RangeIteratorNodeGen(currentSlot, endSlot, incrementSlot, inclusiveEnd);
        }

        @GeneratedBy(RangeIteratorNode.class)
        @DenyReplace
        private static final class IncreasingData extends Node implements SpecializationDataNode {

            /**
             * Source Info: <pre>
             *   Specialization: {@link RangeIteratorNode#doIncreasing}
             *   Parameter: boolean isGt0</pre>
             */
            @CompilationFinal boolean isGt0_;
            /**
             * Source Info: <pre>
             *   Specialization: {@link RangeIteratorNode#doIncreasing}
             *   Parameter: {@link GreaterThanMatcherNode} upperBoundExceeded</pre>
             */
            @Child GreaterThanMatcherNode upperBoundExceeded_;

            IncreasingData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

        }
        @GeneratedBy(RangeIteratorNode.class)
        @DenyReplace
        private static final class DecreasingData extends Node implements SpecializationDataNode {

            /**
             * Source Info: <pre>
             *   Specialization: {@link RangeIteratorNode#doDecreasing}
             *   Parameter: boolean isGt0</pre>
             */
            @CompilationFinal boolean isGt0_;
            /**
             * Source Info: <pre>
             *   Specialization: {@link RangeIteratorNode#doDecreasing}
             *   Parameter: {@link LessThanMatcherNode} lowerBoundExceeded</pre>
             */
            @Child LessThanMatcherNode lowerBoundExceeded_;

            DecreasingData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

        }
    }
    /**
     * Debug Info: <pre>
     *   Specialization {@link GtZeroNode#compareMeasure}
     *     Activation probability: 0,23929
     *     With/without class size: 6/0 bytes
     *   Specialization {@link GtZeroNode#compareLong}
     *     Activation probability: 0,20714
     *     With/without class size: 6/0 bytes
     *   Specialization {@link GtZeroNode#compareBigNumber}
     *     Activation probability: 0,17500
     *     With/without class size: 6/0 bytes
     *   Specialization {@link GtZeroNode#compareSmallRational}
     *     Activation probability: 0,14286
     *     With/without class size: 5/0 bytes
     *   Specialization {@link GtZeroNode#compareRational}
     *     Activation probability: 0,11071
     *     With/without class size: 5/0 bytes
     *   Specialization {@link GtZeroNode#compareSmallSciNum}
     *     Activation probability: 0,07857
     *     With/without class size: 4/0 bytes
     *   Specialization {@link GtZeroNode#compareSciNum}
     *     Activation probability: 0,04643
     *     With/without class size: 4/0 bytes
     * </pre>
     */
    @GeneratedBy(GtZeroNode.class)
    @SuppressWarnings("javadoc")
    static final class GtZeroNodeGen extends GtZeroNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link GtZeroNode#compareMeasure}
         *   1: SpecializationActive {@link GtZeroNode#compareLong}
         *   2: SpecializationActive {@link GtZeroNode#compareBigNumber}
         *   3: SpecializationActive {@link GtZeroNode#compareSmallRational}
         *   4: SpecializationActive {@link GtZeroNode#compareRational}
         *   5: SpecializationActive {@link GtZeroNode#compareSmallSciNum}
         *   6: SpecializationActive {@link GtZeroNode#compareSciNum}
         * </pre>
         */
        @CompilationFinal private int state_0_;

        private GtZeroNodeGen() {
        }

        @Override
        public boolean executeTest(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] || SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] || SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] || SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] || SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] || SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] || SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] */ && arg1Value instanceof Measure) {
                    Measure arg1Value_ = (Measure) arg1Value;
                    return compareMeasure(this, arg1Value_);
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] */ && arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    return compareLong(arg1Value_);
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] */ && arg1Value instanceof BigNumber) {
                    BigNumber arg1Value_ = (BigNumber) arg1Value;
                    return compareBigNumber(arg1Value_);
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    return compareSmallRational(arg1Value_);
                }
                if ((state_0 & 0b10000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] */ && arg1Value instanceof Rational) {
                    Rational arg1Value_ = (Rational) arg1Value;
                    return compareRational(arg1Value_);
                }
                if ((state_0 & 0b100000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    return compareSmallSciNum(arg1Value_);
                }
                if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */ && arg1Value instanceof SciNum) {
                    SciNum arg1Value_ = (SciNum) arg1Value;
                    return compareSciNum(arg1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value);
        }

        private boolean executeAndSpecialize(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof Measure) {
                Measure arg1Value_ = (Measure) arg1Value;
                state_0 = state_0 | 0b1 /* add SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] */;
                this.state_0_ = state_0;
                return compareMeasure(this, arg1Value_);
            }
            if (arg1Value instanceof Long) {
                long arg1Value_ = (long) arg1Value;
                state_0 = state_0 | 0b10 /* add SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] */;
                this.state_0_ = state_0;
                return compareLong(arg1Value_);
            }
            if (arg1Value instanceof BigNumber) {
                BigNumber arg1Value_ = (BigNumber) arg1Value;
                state_0 = state_0 | 0b100 /* add SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] */;
                this.state_0_ = state_0;
                return compareBigNumber(arg1Value_);
            }
            if (arg1Value instanceof SmallRational) {
                SmallRational arg1Value_ = (SmallRational) arg1Value;
                state_0 = state_0 | 0b1000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] */;
                this.state_0_ = state_0;
                return compareSmallRational(arg1Value_);
            }
            if (arg1Value instanceof Rational) {
                Rational arg1Value_ = (Rational) arg1Value;
                state_0 = state_0 | 0b10000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] */;
                this.state_0_ = state_0;
                return compareRational(arg1Value_);
            }
            if (arg1Value instanceof SmallSciNum) {
                SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                state_0 = state_0 | 0b100000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] */;
                this.state_0_ = state_0;
                return compareSmallSciNum(arg1Value_);
            }
            if (arg1Value instanceof SciNum) {
                SciNum arg1Value_ = (SciNum) arg1Value;
                state_0 = state_0 | 0b1000000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */;
                this.state_0_ = state_0;
                return compareSciNum(arg1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[] {null, null}, arg0Value, arg1Value);
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
        public static GtZeroNode create() {
            return new GtZeroNodeGen();
        }

        /**
         * Required Fields: <ul>
         * <li>{@link Inlined#state_0_}
         * </ul>
         */
        @NeverDefault
        public static GtZeroNode inline(@RequiredField(bits = 7, value = StateField.class) InlineTarget target) {
            return new GtZeroNodeGen.Inlined(target);
        }

        @GeneratedBy(GtZeroNode.class)
        @DenyReplace
        private static final class Inlined extends GtZeroNode {

            /**
             * State Info: <pre>
             *   0: SpecializationActive {@link GtZeroNode#compareMeasure}
             *   1: SpecializationActive {@link GtZeroNode#compareLong}
             *   2: SpecializationActive {@link GtZeroNode#compareBigNumber}
             *   3: SpecializationActive {@link GtZeroNode#compareSmallRational}
             *   4: SpecializationActive {@link GtZeroNode#compareRational}
             *   5: SpecializationActive {@link GtZeroNode#compareSmallSciNum}
             *   6: SpecializationActive {@link GtZeroNode#compareSciNum}
             * </pre>
             */
            private final StateField state_0_;

            private Inlined(InlineTarget target) {
                assert target.getTargetClass().isAssignableFrom(GtZeroNode.class);
                this.state_0_ = target.getState(0, 7);
            }

            @Override
            public boolean executeTest(Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (state_0 != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] || SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] || SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] || SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] || SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] || SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] || SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */) {
                    if ((state_0 & 0b1) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] */ && arg1Value instanceof Measure) {
                        Measure arg1Value_ = (Measure) arg1Value;
                        return compareMeasure(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 0b10) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] */ && arg1Value instanceof Long) {
                        long arg1Value_ = (long) arg1Value;
                        return compareLong(arg1Value_);
                    }
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] */ && arg1Value instanceof BigNumber) {
                        BigNumber arg1Value_ = (BigNumber) arg1Value;
                        return compareBigNumber(arg1Value_);
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] */ && arg1Value instanceof SmallRational) {
                        SmallRational arg1Value_ = (SmallRational) arg1Value;
                        return compareSmallRational(arg1Value_);
                    }
                    if ((state_0 & 0b10000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] */ && arg1Value instanceof Rational) {
                        Rational arg1Value_ = (Rational) arg1Value;
                        return compareRational(arg1Value_);
                    }
                    if ((state_0 & 0b100000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] */ && arg1Value instanceof SmallSciNum) {
                        SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                        return compareSmallSciNum(arg1Value_);
                    }
                    if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */ && arg1Value instanceof SciNum) {
                        SciNum arg1Value_ = (SciNum) arg1Value;
                        return compareSciNum(arg1Value_);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return executeAndSpecialize(arg0Value, arg1Value);
            }

            private boolean executeAndSpecialize(Node arg0Value, Object arg1Value) {
                int state_0 = this.state_0_.get(arg0Value);
                if (arg1Value instanceof Measure) {
                    Measure arg1Value_ = (Measure) arg1Value;
                    state_0 = state_0 | 0b1 /* add SpecializationActive[RangeIteration.GtZeroNode.compareMeasure(Node, Measure)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareMeasure(arg0Value, arg1Value_);
                }
                if (arg1Value instanceof Long) {
                    long arg1Value_ = (long) arg1Value;
                    state_0 = state_0 | 0b10 /* add SpecializationActive[RangeIteration.GtZeroNode.compareLong(long)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareLong(arg1Value_);
                }
                if (arg1Value instanceof BigNumber) {
                    BigNumber arg1Value_ = (BigNumber) arg1Value;
                    state_0 = state_0 | 0b100 /* add SpecializationActive[RangeIteration.GtZeroNode.compareBigNumber(BigNumber)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareBigNumber(arg1Value_);
                }
                if (arg1Value instanceof SmallRational) {
                    SmallRational arg1Value_ = (SmallRational) arg1Value;
                    state_0 = state_0 | 0b1000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSmallRational(SmallRational)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareSmallRational(arg1Value_);
                }
                if (arg1Value instanceof Rational) {
                    Rational arg1Value_ = (Rational) arg1Value;
                    state_0 = state_0 | 0b10000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareRational(Rational)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareRational(arg1Value_);
                }
                if (arg1Value instanceof SmallSciNum) {
                    SmallSciNum arg1Value_ = (SmallSciNum) arg1Value;
                    state_0 = state_0 | 0b100000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSmallSciNum(SmallSciNum)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareSmallSciNum(arg1Value_);
                }
                if (arg1Value instanceof SciNum) {
                    SciNum arg1Value_ = (SciNum) arg1Value;
                    state_0 = state_0 | 0b1000000 /* add SpecializationActive[RangeIteration.GtZeroNode.compareSciNum(SciNum)] */;
                    this.state_0_.set(arg0Value, state_0);
                    return compareSciNum(arg1Value_);
                }
                throw new UnsupportedSpecializationException(this, new Node[] {null, null}, arg0Value, arg1Value);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

        }
    }
}
