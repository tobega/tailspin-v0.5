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
import com.oracle.truffle.api.strings.TruffleString.ConcatNode;
import com.oracle.truffle.api.strings.TruffleString.FromJavaStringNode;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link StringLiteral#doBuild}
 *     Activation probability: 1,00000
 *     With/without class size: 24/4 bytes
 * </pre>
 */
@GeneratedBy(StringLiteral.class)
@SuppressWarnings({"javadoc", "unused"})
public final class StringLiteralNodeGen extends StringLiteral {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StringLiteral#doBuild}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link StringLiteral#doBuild}
     *   Parameter: {@link AppendStringNode} appendNode</pre>
     */
    @Child private AppendStringNode appendNode_;

    private StringLiteralNodeGen(List<ValueNode> parts, SourceSection sourceSection) {
        super(parts, sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[StringLiteral.doBuild(VirtualFrame, AppendStringNode)] */) {
            {
                AppendStringNode appendNode__ = this.appendNode_;
                if (appendNode__ != null) {
                    return doBuild(frameValue, appendNode__);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue);
    }

    private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        AppendStringNode appendNode__ = this.insert((AppendStringNode.create()));
        Objects.requireNonNull(appendNode__, "Specialization 'doBuild(VirtualFrame, AppendStringNode)' cache 'appendNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
        VarHandle.storeStoreFence();
        this.appendNode_ = appendNode__;
        state_0 = state_0 | 0b1 /* add SpecializationActive[StringLiteral.doBuild(VirtualFrame, AppendStringNode)] */;
        this.state_0_ = state_0;
        return doBuild(frameValue, appendNode__);
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
    public static StringLiteral create(List<ValueNode> parts, SourceSection sourceSection) {
        return new StringLiteralNodeGen(parts, sourceSection);
    }

    /**
     * Debug Info: <pre>
     *   Specialization {@link AppendStringNode#doAppendString}
     *     Activation probability: 0,38500
     *     With/without class size: 8/0 bytes
     *   Specialization {@link AppendStringNode#doAppendMany}
     *     Activation probability: 0,29500
     *     With/without class size: 7/0 bytes
     *   Specialization {@link AppendStringNode#doAppendNone}
     *     Activation probability: 0,20500
     *     With/without class size: 6/0 bytes
     *   Specialization {@link AppendStringNode#doAppendObject}
     *     Activation probability: 0,11500
     *     With/without class size: 6/4 bytes
     * </pre>
     */
    @GeneratedBy(AppendStringNode.class)
    @SuppressWarnings("javadoc")
    static final class AppendStringNodeGen extends AppendStringNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link AppendStringNode#doAppendString}
         *   1: SpecializationActive {@link AppendStringNode#doAppendMany}
         *   2: SpecializationActive {@link AppendStringNode#doAppendNone}
         *   3: SpecializationActive {@link AppendStringNode#doAppendObject}
         * </pre>
         */
        @CompilationFinal private int state_0_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link AppendStringNode#doAppendString}
         *   Parameter: {@link ConcatNode} concatNode</pre>
         */
        @Child private ConcatNode concatNode;
        /**
         * Source Info: <pre>
         *   Specialization: {@link AppendStringNode#doAppendObject}
         *   Parameter: {@link FromJavaStringNode} fromJavaStringNode</pre>
         */
        @Child private FromJavaStringNode fallback_fromJavaStringNode_;

        private AppendStringNodeGen() {
        }

        @SuppressWarnings("static-method")
        private boolean fallbackGuard_(int state_0, TruffleString arg0Value, Object arg1Value) {
            if (arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString) arg1Value;
                if ((arg1Value_ != null)) {
                    return false;
                }
            }
            if (arg1Value instanceof ArrayList<?>) {
                ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                if ((arg1Value_ != null)) {
                    return false;
                }
            }
            if (!((state_0 & 0b100) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendNone(TruffleString, Object)] */) && (arg1Value == null)) {
                return false;
            }
            return true;
        }

        @Override
        TruffleString executeAppend(TruffleString arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendString(TruffleString, TruffleString, ConcatNode)] || SpecializationActive[StringLiteral.AppendStringNode.doAppendMany(TruffleString, ArrayList<>)] || SpecializationActive[StringLiteral.AppendStringNode.doAppendNone(TruffleString, Object)] || SpecializationActive[StringLiteral.AppendStringNode.doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendString(TruffleString, TruffleString, ConcatNode)] */ && arg1Value instanceof TruffleString) {
                    TruffleString arg1Value_ = (TruffleString) arg1Value;
                    {
                        ConcatNode concatNode_ = this.concatNode;
                        if (concatNode_ != null) {
                            if ((arg1Value_ != null)) {
                                return doAppendString(arg0Value, arg1Value_, concatNode_);
                            }
                        }
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendMany(TruffleString, ArrayList<>)] */ && arg1Value instanceof ArrayList<?>) {
                    ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                    if ((arg1Value_ != null)) {
                        return doAppendMany(arg0Value, arg1Value_);
                    }
                }
                if ((state_0 & 0b1100) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendNone(TruffleString, Object)] || SpecializationActive[StringLiteral.AppendStringNode.doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)] */) {
                    if ((state_0 & 0b100) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendNone(TruffleString, Object)] */) {
                        if ((arg1Value == null)) {
                            return doAppendNone(arg0Value, arg1Value);
                        }
                    }
                    if ((state_0 & 0b1000) != 0 /* is SpecializationActive[StringLiteral.AppendStringNode.doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)] */) {
                        {
                            ConcatNode concatNode_1 = this.concatNode;
                            if (concatNode_1 != null) {
                                FromJavaStringNode fromJavaStringNode__ = this.fallback_fromJavaStringNode_;
                                if (fromJavaStringNode__ != null) {
                                    if (fallbackGuard_(state_0, arg0Value, arg1Value)) {
                                        return doAppendObject(arg0Value, arg1Value, concatNode_1, fromJavaStringNode__);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arg0Value, arg1Value);
        }

        private TruffleString executeAndSpecialize(TruffleString arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString) arg1Value;
                if ((arg1Value_ != null)) {
                    ConcatNode concatNode_;
                    ConcatNode concatNode__shared = this.concatNode;
                    if (concatNode__shared != null) {
                        concatNode_ = concatNode__shared;
                    } else {
                        concatNode_ = this.insert((ConcatNode.create()));
                        if (concatNode_ == null) {
                            throw new IllegalStateException("Specialization 'doAppendString(TruffleString, TruffleString, ConcatNode)' contains a shared cache with name 'concatNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                        }
                    }
                    if (this.concatNode == null) {
                        VarHandle.storeStoreFence();
                        this.concatNode = concatNode_;
                    }
                    state_0 = state_0 | 0b1 /* add SpecializationActive[StringLiteral.AppendStringNode.doAppendString(TruffleString, TruffleString, ConcatNode)] */;
                    this.state_0_ = state_0;
                    return doAppendString(arg0Value, arg1Value_, concatNode_);
                }
            }
            if (arg1Value instanceof ArrayList<?>) {
                ArrayList<?> arg1Value_ = (ArrayList<?>) arg1Value;
                if ((arg1Value_ != null)) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[StringLiteral.AppendStringNode.doAppendMany(TruffleString, ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doAppendMany(arg0Value, arg1Value_);
                }
            }
            if ((arg1Value == null)) {
                state_0 = state_0 | 0b100 /* add SpecializationActive[StringLiteral.AppendStringNode.doAppendNone(TruffleString, Object)] */;
                this.state_0_ = state_0;
                return doAppendNone(arg0Value, arg1Value);
            }
            ConcatNode concatNode_1;
            ConcatNode concatNode_1_shared = this.concatNode;
            if (concatNode_1_shared != null) {
                concatNode_1 = concatNode_1_shared;
            } else {
                concatNode_1 = this.insert((ConcatNode.create()));
                if (concatNode_1 == null) {
                    throw new IllegalStateException("Specialization 'doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)' contains a shared cache with name 'concatNode' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.concatNode == null) {
                VarHandle.storeStoreFence();
                this.concatNode = concatNode_1;
            }
            FromJavaStringNode fromJavaStringNode__ = this.insert((FromJavaStringNode.create()));
            Objects.requireNonNull(fromJavaStringNode__, "Specialization 'doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)' cache 'fromJavaStringNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.fallback_fromJavaStringNode_ = fromJavaStringNode__;
            state_0 = state_0 | 0b1000 /* add SpecializationActive[StringLiteral.AppendStringNode.doAppendObject(TruffleString, Object, ConcatNode, FromJavaStringNode)] */;
            this.state_0_ = state_0;
            return doAppendObject(arg0Value, arg1Value, concatNode_1, fromJavaStringNode__);
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
        public static AppendStringNode create() {
            return new AppendStringNodeGen();
        }

    }
}
