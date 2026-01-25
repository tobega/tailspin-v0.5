// CheckStyle: start generated
package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link StructureTypeMatcherNode#isAnyStructure}
 *     Activation probability: 0,48333
 *     With/without class size: 9/0 bytes
 *   Specialization {@link StructureTypeMatcherNode#isStructure}
 *     Activation probability: 0,33333
 *     With/without class size: 10/4 bytes
 *   Specialization {@link StructureTypeMatcherNode#notStructure}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(StructureTypeMatcherNode.class)
@SuppressWarnings("javadoc")
public final class StructureTypeMatcherNodeGen extends StructureTypeMatcherNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StructureTypeMatcherNode#isAnyStructure}
     *   1: SpecializationActive {@link StructureTypeMatcherNode#isStructure}
     *   2: SpecializationActive {@link StructureTypeMatcherNode#notStructure}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link StructureTypeMatcherNode#isStructure}
     *   Parameter: {@link DynamicObjectLibrary} dynamicObjectLibrary</pre>
     */
    @Child private DynamicObjectLibrary isStructure_dynamicObjectLibrary_;

    private StructureTypeMatcherNodeGen(VocabularyType[] requiredKeys, boolean allowExtraFields, VocabularyType[] optionalKeys, SourceSection sourceSection) {
        super(requiredKeys, allowExtraFields, optionalKeys, sourceSection);
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object arg0Value) {
        if (arg0Value instanceof Structure) {
            if (!((state_0 & 0b1) != 0 /* is SpecializationActive[StructureTypeMatcherNode.isAnyStructure(Structure)] */) && (requiredKeys.length == 0)) {
                return false;
            }
            if (!((state_0 & 0b10) != 0 /* is SpecializationActive[StructureTypeMatcherNode.isStructure(Structure, DynamicObjectLibrary)] */) && (requiredKeys.length > 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean executeMatcherGeneric(VirtualFrame frameValue, Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[StructureTypeMatcherNode.isAnyStructure(Structure)] || SpecializationActive[StructureTypeMatcherNode.isStructure(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureTypeMatcherNode.notStructure(Object)] */) {
            if ((state_0 & 0b11) != 0 /* is SpecializationActive[StructureTypeMatcherNode.isAnyStructure(Structure)] || SpecializationActive[StructureTypeMatcherNode.isStructure(Structure, DynamicObjectLibrary)] */ && arg0Value instanceof Structure) {
                Structure arg0Value_ = (Structure) arg0Value;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[StructureTypeMatcherNode.isAnyStructure(Structure)] */) {
                    if ((requiredKeys.length == 0)) {
                        return isAnyStructure(arg0Value_);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[StructureTypeMatcherNode.isStructure(Structure, DynamicObjectLibrary)] */) {
                    {
                        DynamicObjectLibrary dynamicObjectLibrary__ = this.isStructure_dynamicObjectLibrary_;
                        if (dynamicObjectLibrary__ != null) {
                            if ((requiredKeys.length > 0)) {
                                return isStructure(arg0Value_, dynamicObjectLibrary__);
                            }
                        }
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[StructureTypeMatcherNode.notStructure(Object)] */) {
                if (fallbackGuard_(state_0, arg0Value)) {
                    return notStructure(arg0Value);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    @Override
    public boolean executeMatcherLong(VirtualFrame frameValue, long arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b100) != 0 /* is SpecializationActive[StructureTypeMatcherNode.notStructure(Object)] */) {
            if (fallbackGuard_(state_0, arg0Value)) {
                return notStructure(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof Structure) {
            Structure arg0Value_ = (Structure) arg0Value;
            if ((requiredKeys.length == 0)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[StructureTypeMatcherNode.isAnyStructure(Structure)] */;
                this.state_0_ = state_0;
                return isAnyStructure(arg0Value_);
            }
            if ((requiredKeys.length > 0)) {
                DynamicObjectLibrary dynamicObjectLibrary__ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                Objects.requireNonNull(dynamicObjectLibrary__, "Specialization 'isStructure(Structure, DynamicObjectLibrary)' cache 'dynamicObjectLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.isStructure_dynamicObjectLibrary_ = dynamicObjectLibrary__;
                state_0 = state_0 | 0b10 /* add SpecializationActive[StructureTypeMatcherNode.isStructure(Structure, DynamicObjectLibrary)] */;
                this.state_0_ = state_0;
                return isStructure(arg0Value_, dynamicObjectLibrary__);
            }
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[StructureTypeMatcherNode.notStructure(Object)] */;
        this.state_0_ = state_0;
        return notStructure(arg0Value);
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
    public static StructureTypeMatcherNode create(VocabularyType[] requiredKeys, boolean allowExtraFields, VocabularyType[] optionalKeys, SourceSection sourceSection) {
        return new StructureTypeMatcherNodeGen(requiredKeys, allowExtraFields, optionalKeys, sourceSection);
    }

}
