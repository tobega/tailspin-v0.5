// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link TagMatcherNode#doSameTag}
 *     Activation probability: 0,65000
 *     With/without class size: 11/0 bytes
 *   Specialization {@link TagMatcherNode#doOther}
 *     Activation probability: 0,35000
 *     With/without class size: 11/4 bytes
 * </pre>
 */
@GeneratedBy(TagMatcherNode.class)
@SuppressWarnings("javadoc")
public final class TagMatcherNodeGen extends TagMatcherNode {

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link TagMatcherNode#doSameTag}
     *   1: SpecializationActive {@link TagMatcherNode#doOther}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagMatcherNode#doOther}
     *   Parameter: {@link MatcherNode} matcherNode</pre>
     */
    @Child private MatcherNode fallback_matcherNode_;

    private TagMatcherNodeGen(VocabularyType type, SourceSection sourceSection) {
        super(type, sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(Object arg0Value) {
        if (arg0Value instanceof TaggedValue) {
            TaggedValue arg0Value_ = (TaggedValue) arg0Value;
            if ((arg0Value_.type() == type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[TagMatcherNode.doSameTag(TaggedValue)] || SpecializationActive[TagMatcherNode.doOther(VirtualFrame, Object, MatcherNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[TagMatcherNode.doSameTag(TaggedValue)] */ && arg0Value instanceof TaggedValue) {
                TaggedValue arg0Value_ = (TaggedValue) arg0Value;
                if ((arg0Value_.type() == type)) {
                    return doSameTag(arg0Value_);
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[TagMatcherNode.doOther(VirtualFrame, Object, MatcherNode)] */) {
                {
                    MatcherNode matcherNode__ = this.fallback_matcherNode_;
                    if (matcherNode__ != null) {
                        if (fallbackGuard_(arg0Value)) {
                            return doOther(frameValue, arg0Value, matcherNode__);
                        }
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b10) != 0 /* is SpecializationActive[TagMatcherNode.doOther(VirtualFrame, Object, MatcherNode)] */) {
            {
                MatcherNode matcherNode__ = this.fallback_matcherNode_;
                if (matcherNode__ != null) {
                    if (fallbackGuard_(arg0Value)) {
                        return doOther(frameValue, arg0Value, matcherNode__);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value);
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof TaggedValue) {
            TaggedValue arg0Value_ = (TaggedValue) arg0Value;
            if ((arg0Value_.type() == type)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[TagMatcherNode.doSameTag(TaggedValue)] */;
                this.state_0_ = state_0;
                return doSameTag(arg0Value_);
            }
        }
        MatcherNode matcherNode__ = this.insert((getConstraint()));
        Objects.requireNonNull(matcherNode__, "Specialization 'doOther(VirtualFrame, Object, MatcherNode)' cache 'matcherNode' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
        VarHandle.storeStoreFence();
        this.fallback_matcherNode_ = matcherNode__;
        state_0 = state_0 | 0b10 /* add SpecializationActive[TagMatcherNode.doOther(VirtualFrame, Object, MatcherNode)] */;
        this.state_0_ = state_0;
        return doOther(frameValue, arg0Value, matcherNode__);
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
    public static TagMatcherNode create(VocabularyType type, SourceSection sourceSection) {
        return new TagMatcherNodeGen(type, sourceSection);
    }

}
