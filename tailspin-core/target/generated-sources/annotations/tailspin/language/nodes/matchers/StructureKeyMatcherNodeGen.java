// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link StructureKeyMatcherNode#doKeyMatch}
 *     Activation probability: 1,00000
 *     With/without class size: 24/4 bytes
 * </pre>
 */
@GeneratedBy(StructureKeyMatcherNode.class)
@SuppressWarnings("javadoc")
public final class StructureKeyMatcherNodeGen extends StructureKeyMatcherNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StructureKeyMatcherNode#doKeyMatch}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link StructureKeyMatcherNode#doKeyMatch}
     *   Parameter: {@link DynamicObjectLibrary} dynamicObjectLibrary</pre>
     */
    @Child private DynamicObjectLibrary dynamicObjectLibrary_;

    private StructureKeyMatcherNodeGen(VocabularyType key, MatcherNode matcher, boolean isOptional, SourceSection sourceSection) {
        super(key, matcher, isOptional, sourceSection);
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[StructureKeyMatcherNode.doKeyMatch(VirtualFrame, Structure, DynamicObjectLibrary)] */ && arg0Value instanceof Structure) {
            Structure arg0Value_ = (Structure) arg0Value;
            {
                DynamicObjectLibrary dynamicObjectLibrary__ = this.dynamicObjectLibrary_;
                if (dynamicObjectLibrary__ != null) {
                    return doKeyMatch(frameValue, arg0Value_, dynamicObjectLibrary__);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        throw CompilerDirectives.shouldNotReachHere("Delegation failed.");
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof Structure) {
            Structure arg0Value_ = (Structure) arg0Value;
            DynamicObjectLibrary dynamicObjectLibrary__ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
            Objects.requireNonNull(dynamicObjectLibrary__, "Specialization 'doKeyMatch(VirtualFrame, Structure, DynamicObjectLibrary)' cache 'dynamicObjectLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.dynamicObjectLibrary_ = dynamicObjectLibrary__;
            state_0 = state_0 | 0b1 /* add SpecializationActive[StructureKeyMatcherNode.doKeyMatch(VirtualFrame, Structure, DynamicObjectLibrary)] */;
            this.state_0_ = state_0;
            return doKeyMatch(frameValue, arg0Value_, dynamicObjectLibrary__);
        }
        throw new UnsupportedSpecializationException(this, new Node[] {null}, arg0Value);
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
    public static StructureKeyMatcherNode create(VocabularyType key, MatcherNode matcher, boolean isOptional, SourceSection sourceSection) {
        return new StructureKeyMatcherNodeGen(key, matcher, isOptional, sourceSection);
    }

}
