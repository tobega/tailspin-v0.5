// CheckStyle: start generated
package tailspin.language.nodes.processor;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.DSLSupport.SpecializationDataNode;
import com.oracle.truffle.api.dsl.InlineSupport.ReferenceField;
import com.oracle.truffle.api.dsl.InlineSupport.UnsafeAccessedField;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link MessageNode#doLong}
 *     Activation probability: 0,38500
 *     With/without class size: 8/0 bytes
 *   Specialization {@link MessageNode#doProcessor}
 *     Activation probability: 0,29500
 *     With/without class size: 11/4 bytes
 *   Specialization {@link MessageNode#doProcessor}
 *     Activation probability: 0,20500
 *     With/without class size: 6/0 bytes
 *   Specialization {@link MessageNode#doUnknown}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(MessageNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class MessageNodeGen extends MessageNode {

    static final ReferenceField<Processor0Data> PROCESSOR0_CACHE_UPDATER = ReferenceField.create(MethodHandles.lookup(), "processor0_cache", Processor0Data.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @Child private ValueNode processor_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link MessageNode#doLong}
     *   1: SpecializationActive {@link MessageNode#doProcessor}
     *   2: SpecializationActive {@link MessageNode#doProcessor}
     *   3: SpecializationActive {@link MessageNode#doUnknown}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    @UnsafeAccessedField @Child private Processor0Data processor0_cache;

    private MessageNodeGen(String message, SourceSection sourceSection, ValueNode processor) {
        super(message, sourceSection);
        this.processor_ = processor;
    }

    @ExplodeLoop
    @Override
    public Object executeMessage(Object processorValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[MessageNode.doLong(long)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doUnknown(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[MessageNode.doLong(long)] */ && processorValue instanceof Long) {
                long processorValue_ = (long) processorValue;
                return doLong(processorValue_);
            }
            if ((state_0 & 0b1110) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doUnknown(Object)] */) {
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */) {
                    Processor0Data s1_ = this.processor0_cache;
                    while (s1_ != null) {
                        if ((s1_.processorInteropLibrary_.accepts(processorValue)) && (s1_.processorInteropLibrary_.hasMembers(processorValue))) {
                            return doProcessor(processorValue, s1_.processorInteropLibrary_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        {
                            InteropLibrary processorInteropLibrary__ = (INTEROP_LIBRARY_.getUncached());
                            if ((processorInteropLibrary__.hasMembers(processorValue))) {
                                return this.processor1Boundary(state_0, processorValue);
                            }
                        }
                    } finally {
                        encapsulating_.set(prev_);
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MessageNode.doUnknown(Object)] */) {
                    return doUnknown(processorValue);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(processorValue);
    }

    @SuppressWarnings("static-method")
    @TruffleBoundary
    private Object processor1Boundary(int state_0, Object processorValue) {
        {
            InteropLibrary processorInteropLibrary__ = (INTEROP_LIBRARY_.getUncached());
            return doProcessor(processorValue, processorInteropLibrary__);
        }
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1110) == 0 /* only-active SpecializationActive[MessageNode.doLong(long)] */ && (state_0 != 0  /* is-not SpecializationActive[MessageNode.doLong(long)] && SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] && SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] && SpecializationActive[MessageNode.doUnknown(Object)] */)) {
            return executeGeneric_long0(state_0, frameValue);
        } else {
            return executeGeneric_generic1(state_0, frameValue);
        }
    }

    private Object executeGeneric_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long processorValue_;
        try {
            processorValue_ = this.processor_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(ex.getResult());
        }
        assert (state_0 & 0b1) != 0 /* is SpecializationActive[MessageNode.doLong(long)] */;
        return doLong(processorValue_);
    }

    @SuppressWarnings("static-method")
    @TruffleBoundary
    private Object processor1Boundary0(int state_0, Object processorValue_) {
        {
            InteropLibrary processorInteropLibrary__ = (INTEROP_LIBRARY_.getUncached());
            return doProcessor(processorValue_, processorInteropLibrary__);
        }
    }

    @ExplodeLoop
    private Object executeGeneric_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object processorValue_ = this.processor_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[MessageNode.doLong(long)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doUnknown(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[MessageNode.doLong(long)] */ && processorValue_ instanceof Long) {
                long processorValue__ = (long) processorValue_;
                return doLong(processorValue__);
            }
            if ((state_0 & 0b1110) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] || SpecializationActive[MessageNode.doUnknown(Object)] */) {
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */) {
                    Processor0Data s1_ = this.processor0_cache;
                    while (s1_ != null) {
                        if ((s1_.processorInteropLibrary_.accepts(processorValue_)) && (s1_.processorInteropLibrary_.hasMembers(processorValue_))) {
                            return doProcessor(processorValue_, s1_.processorInteropLibrary_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        {
                            InteropLibrary processorInteropLibrary__ = (INTEROP_LIBRARY_.getUncached());
                            if ((processorInteropLibrary__.hasMembers(processorValue_))) {
                                return this.processor1Boundary0(state_0, processorValue_);
                            }
                        }
                    } finally {
                        encapsulating_.set(prev_);
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[MessageNode.doUnknown(Object)] */) {
                    return doUnknown(processorValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(processorValue_);
    }

    @SuppressWarnings("unused")
    private Object executeAndSpecialize(Object processorValue) {
        int state_0 = this.state_0_;
        if (processorValue instanceof Long) {
            long processorValue_ = (long) processorValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[MessageNode.doLong(long)] */;
            this.state_0_ = state_0;
            return doLong(processorValue_);
        }
        if (((state_0 & 0b100)) == 0 /* is-not SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */) {
            while (true) {
                int count1_ = 0;
                Processor0Data s1_ = PROCESSOR0_CACHE_UPDATER.getVolatile(this);
                Processor0Data s1_original = s1_;
                while (s1_ != null) {
                    if ((s1_.processorInteropLibrary_.accepts(processorValue)) && (s1_.processorInteropLibrary_.hasMembers(processorValue))) {
                        break;
                    }
                    count1_++;
                    s1_ = s1_.next_;
                }
                if (s1_ == null) {
                    {
                        InteropLibrary processorInteropLibrary__ = this.insert((INTEROP_LIBRARY_.create(processorValue)));
                        // assert (s1_.processorInteropLibrary_.accepts(processorValue));
                        if ((processorInteropLibrary__.hasMembers(processorValue)) && count1_ < (2)) {
                            s1_ = this.insert(new Processor0Data(s1_original));
                            Objects.requireNonNull(s1_.insert(processorInteropLibrary__), "Specialization 'doProcessor(Object, InteropLibrary)' cache 'processorInteropLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                            s1_.processorInteropLibrary_ = processorInteropLibrary__;
                            if (!PROCESSOR0_CACHE_UPDATER.compareAndSet(this, s1_original, s1_)) {
                                continue;
                            }
                            state_0 = state_0 | 0b10 /* add SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */;
                            this.state_0_ = state_0;
                        }
                    }
                }
                if (s1_ != null) {
                    return doProcessor(processorValue, s1_.processorInteropLibrary_);
                }
                break;
            }
        }
        {
            InteropLibrary processorInteropLibrary__ = null;
            {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    {
                        processorInteropLibrary__ = (INTEROP_LIBRARY_.getUncached());
                        if ((processorInteropLibrary__.hasMembers(processorValue))) {
                            this.processor0_cache = null;
                            state_0 = state_0 & 0xfffffffd /* remove SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */;
                            state_0 = state_0 | 0b100 /* add SpecializationActive[MessageNode.doProcessor(Object, InteropLibrary)] */;
                            this.state_0_ = state_0;
                            return doProcessor(processorValue, processorInteropLibrary__);
                        }
                    }
                } finally {
                    encapsulating_.set(prev_);
                }
            }
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[MessageNode.doUnknown(Object)] */;
        this.state_0_ = state_0;
        return doUnknown(processorValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if ((state_0 & (state_0 - 1)) == 0 /* is-single  */) {
                Processor0Data s1_ = this.processor0_cache;
                if ((s1_ == null || s1_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static MessageNode create(String message, SourceSection sourceSection, ValueNode processor) {
        return new MessageNodeGen(message, sourceSection, processor);
    }

    @GeneratedBy(MessageNode.class)
    @DenyReplace
    private static final class Processor0Data extends Node implements SpecializationDataNode {

        @Child Processor0Data next_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link MessageNode#doProcessor}
         *   Parameter: {@link InteropLibrary} processorInteropLibrary</pre>
         */
        @Child InteropLibrary processorInteropLibrary_;

        Processor0Data(Processor0Data next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

    }
}
