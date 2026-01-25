// CheckStyle: start generated
package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.dsl.DSLSupport.SpecializationDataNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.CreateCodePointIteratorNode;
import com.oracle.truffle.api.strings.TruffleStringBuilder.AppendCodePointNode;
import com.oracle.truffle.api.strings.TruffleStringBuilder.ToStringNode;
import com.oracle.truffle.api.strings.TruffleStringIterator.NextNode;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link StreamNode#doArray}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link StreamNode#doString}
 *     Activation probability: 0,35000
 *     With/without class size: 15/16 bytes
 * </pre>
 */
@GeneratedBy(StreamNode.class)
@SuppressWarnings("javadoc")
public final class StreamNodeGen extends StreamNode {

    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StreamNode#doArray}
     *   1: SpecializationActive {@link StreamNode#doString}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    @Child private StringData string_cache;

    private StreamNodeGen(SourceSection sourceSection, ValueNode value) {
        super(sourceSection);
        this.value_ = value;
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[StreamNode.doArray(VirtualFrame, TailspinArray)] || SpecializationActive[StreamNode.doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[StreamNode.doArray(VirtualFrame, TailspinArray)] */ && valueValue_ instanceof TailspinArray) {
                TailspinArray valueValue__ = (TailspinArray) valueValue_;
                doArray(frameValue, valueValue__);
                return;
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[StreamNode.doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)] */ && valueValue_ instanceof TruffleString) {
                TruffleString valueValue__ = (TruffleString) valueValue_;
                StringData s1_ = this.string_cache;
                if (s1_ != null) {
                    doString(frameValue, valueValue__, s1_.createCodePointIteratorNode_, s1_.nextNode_, s1_.appendCodePointNode_, s1_.toStringNode_);
                    return;
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(frameValue, valueValue_);
        return;
    }

    private void executeAndSpecialize(VirtualFrame frameValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (valueValue instanceof TailspinArray) {
            TailspinArray valueValue_ = (TailspinArray) valueValue;
            state_0 = state_0 | 0b1 /* add SpecializationActive[StreamNode.doArray(VirtualFrame, TailspinArray)] */;
            this.state_0_ = state_0;
            doArray(frameValue, valueValue_);
            return;
        }
        if (valueValue instanceof TruffleString) {
            TruffleString valueValue_ = (TruffleString) valueValue;
            StringData s1_ = this.insert(new StringData());
            CreateCodePointIteratorNode createCodePointIteratorNode__ = s1_.insert((CreateCodePointIteratorNode.create()));
            Objects.requireNonNull(createCodePointIteratorNode__, "Specialization 'doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)' cache 'createCodePointIteratorNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            s1_.createCodePointIteratorNode_ = createCodePointIteratorNode__;
            NextNode nextNode__ = s1_.insert((NextNode.create()));
            Objects.requireNonNull(nextNode__, "Specialization 'doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)' cache 'nextNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            s1_.nextNode_ = nextNode__;
            AppendCodePointNode appendCodePointNode__ = s1_.insert((AppendCodePointNode.create()));
            Objects.requireNonNull(appendCodePointNode__, "Specialization 'doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)' cache 'appendCodePointNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            s1_.appendCodePointNode_ = appendCodePointNode__;
            ToStringNode toStringNode__ = s1_.insert((ToStringNode.create()));
            Objects.requireNonNull(toStringNode__, "Specialization 'doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)' cache 'toStringNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            s1_.toStringNode_ = toStringNode__;
            VarHandle.storeStoreFence();
            this.string_cache = s1_;
            state_0 = state_0 | 0b10 /* add SpecializationActive[StreamNode.doString(VirtualFrame, TruffleString, CreateCodePointIteratorNode, NextNode, AppendCodePointNode, ToStringNode)] */;
            this.state_0_ = state_0;
            doString(frameValue, valueValue_, createCodePointIteratorNode__, nextNode__, appendCodePointNode__, toStringNode__);
            return;
        }
        throw new UnsupportedSpecializationException(this, new Node[] {this.value_}, valueValue);
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
    public static StreamNode create(SourceSection sourceSection, ValueNode value) {
        return new StreamNodeGen(sourceSection, value);
    }

    @GeneratedBy(StreamNode.class)
    @DenyReplace
    private static final class StringData extends Node implements SpecializationDataNode {

        /**
         * Source Info: <pre>
         *   Specialization: {@link StreamNode#doString}
         *   Parameter: {@link CreateCodePointIteratorNode} createCodePointIteratorNode</pre>
         */
        @Child CreateCodePointIteratorNode createCodePointIteratorNode_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link StreamNode#doString}
         *   Parameter: {@link NextNode} nextNode</pre>
         */
        @Child NextNode nextNode_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link StreamNode#doString}
         *   Parameter: {@link AppendCodePointNode} appendCodePointNode</pre>
         */
        @Child AppendCodePointNode appendCodePointNode_;
        /**
         * Source Info: <pre>
         *   Specialization: {@link StreamNode#doString}
         *   Parameter: {@link ToStringNode} toStringNode</pre>
         */
        @Child ToStringNode toStringNode_;

        StringData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

    }
}
