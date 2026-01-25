// CheckStyle: start generated
package tailspin.language.nodes.structure;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link StructureReadNode#doLong}
 *     Activation probability: 0,38500
 *     With/without class size: 8/0 bytes
 *   Specialization {@link StructureReadNode#doRead}
 *     Activation probability: 0,29500
 *     With/without class size: 7/0 bytes
 *   Specialization {@link StructureReadNode#doMultiSelect}
 *     Activation probability: 0,20500
 *     With/without class size: 6/0 bytes
 *   Specialization {@link StructureReadNode#doIllegal}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(StructureReadNode.class)
@SuppressWarnings("javadoc")
public final class StructureReadNodeGen extends StructureReadNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @Child private ValueNode structure_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link StructureReadNode#doLong}
     *   1: SpecializationExcluded {@link StructureReadNode#doLong}
     *   2: SpecializationActive {@link StructureReadNode#doRead}
     *   3: SpecializationActive {@link StructureReadNode#doMultiSelect}
     *   4: SpecializationActive {@link StructureReadNode#doIllegal}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link StructureReadNode#doLong}
     *   Parameter: {@link DynamicObjectLibrary} dynamicObjectLibrary</pre>
     */
    @Child private DynamicObjectLibrary dynamicObjectLibrary;

    private StructureReadNodeGen(VocabularyType key, SourceSection sourceSection, ValueNode structure) {
        super(key, sourceSection);
        this.structure_ = structure;
    }

    @Override
    public Object executeDirect(Object structureValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11101) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] || SpecializationActive[StructureReadNode.doIllegal(Object)] */) {
            if ((state_0 & 0b101) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] */ && structureValue instanceof Structure) {
                Structure structureValue_ = (Structure) structureValue;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */) {
                    {
                        DynamicObjectLibrary dynamicObjectLibrary_ = this.dynamicObjectLibrary;
                        if (dynamicObjectLibrary_ != null) {
                            try {
                                return doLong(structureValue_, dynamicObjectLibrary_);
                            } catch (UnexpectedResultException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_;
                                state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */;
                                state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                                this.state_0_ = state_0;
                                return ex.getResult();
                            }
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] */) {
                    {
                        DynamicObjectLibrary dynamicObjectLibrary_1 = this.dynamicObjectLibrary;
                        if (dynamicObjectLibrary_1 != null) {
                            return doRead(structureValue_, dynamicObjectLibrary_1);
                        }
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] */ && structureValue instanceof ArrayList<?>) {
                ArrayList<?> structureValue_ = (ArrayList<?>) structureValue;
                return doMultiSelect(structureValue_);
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[StructureReadNode.doIllegal(Object)] */) {
                return doIllegal(structureValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(structureValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object structureValue_ = this.structure_.executeGeneric(frameValue);
        if ((state_0 & 0b11101) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] || SpecializationActive[StructureReadNode.doIllegal(Object)] */) {
            if ((state_0 & 0b101) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] */ && structureValue_ instanceof Structure) {
                Structure structureValue__ = (Structure) structureValue_;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */) {
                    {
                        DynamicObjectLibrary dynamicObjectLibrary_ = this.dynamicObjectLibrary;
                        if (dynamicObjectLibrary_ != null) {
                            try {
                                return doLong(structureValue__, dynamicObjectLibrary_);
                            } catch (UnexpectedResultException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                state_0 = this.state_0_;
                                state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */;
                                state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                                this.state_0_ = state_0;
                                return ex.getResult();
                            }
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] */) {
                    {
                        DynamicObjectLibrary dynamicObjectLibrary_1 = this.dynamicObjectLibrary;
                        if (dynamicObjectLibrary_1 != null) {
                            return doRead(structureValue__, dynamicObjectLibrary_1);
                        }
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] */ && structureValue_ instanceof ArrayList<?>) {
                ArrayList<?> structureValue__ = (ArrayList<?>) structureValue_;
                return doMultiSelect(structureValue__);
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[StructureReadNode.doIllegal(Object)] */) {
                return doIllegal(structureValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(structureValue_);
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11100) != 0 /* is SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] || SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] || SpecializationActive[StructureReadNode.doIllegal(Object)] */) {
            return TailspinTypesGen.expectLong(executeGeneric(frameValue));
        }
        Object structureValue_ = this.structure_.executeGeneric(frameValue);
        if ((state_0 & 0b1) != 0 /* is SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */ && structureValue_ instanceof Structure) {
            Structure structureValue__ = (Structure) structureValue_;
            {
                DynamicObjectLibrary dynamicObjectLibrary_ = this.dynamicObjectLibrary;
                if (dynamicObjectLibrary_ != null) {
                    try {
                        return doLong(structureValue__, dynamicObjectLibrary_);
                    } catch (UnexpectedResultException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        state_0 = this.state_0_;
                        state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */;
                        state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                        this.state_0_ = state_0;
                        return TailspinTypesGen.expectLong(ex.getResult());
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TailspinTypesGen.expectLong(executeAndSpecialize(structureValue_));
    }

    private Object executeAndSpecialize(Object structureValue) {
        int state_0 = this.state_0_;
        if (structureValue instanceof Structure) {
            Structure structureValue_ = (Structure) structureValue;
            if (((state_0 & 0b10)) == 0 /* is-not SpecializationExcluded  */) {
                DynamicObjectLibrary dynamicObjectLibrary_;
                DynamicObjectLibrary dynamicObjectLibrary__shared = this.dynamicObjectLibrary;
                if (dynamicObjectLibrary__shared != null) {
                    dynamicObjectLibrary_ = dynamicObjectLibrary__shared;
                } else {
                    dynamicObjectLibrary_ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    if (dynamicObjectLibrary_ == null) {
                        throw new IllegalStateException("Specialization 'doLong(Structure, DynamicObjectLibrary)' contains a shared cache with name 'dynamicObjectLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.dynamicObjectLibrary == null) {
                    VarHandle.storeStoreFence();
                    this.dynamicObjectLibrary = dynamicObjectLibrary_;
                }
                state_0 = state_0 | 0b1 /* add SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */;
                this.state_0_ = state_0;
                try {
                    return doLong(structureValue_, dynamicObjectLibrary_);
                } catch (UnexpectedResultException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    state_0 = this.state_0_;
                    state_0 = state_0 & 0xfffffffe /* remove SpecializationActive[StructureReadNode.doLong(Structure, DynamicObjectLibrary)] */;
                    state_0 = state_0 | 0b10 /* add SpecializationExcluded  */;
                    this.state_0_ = state_0;
                    return ex.getResult();
                }
            }
            DynamicObjectLibrary dynamicObjectLibrary_1;
            DynamicObjectLibrary dynamicObjectLibrary_1_shared = this.dynamicObjectLibrary;
            if (dynamicObjectLibrary_1_shared != null) {
                dynamicObjectLibrary_1 = dynamicObjectLibrary_1_shared;
            } else {
                dynamicObjectLibrary_1 = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                if (dynamicObjectLibrary_1 == null) {
                    throw new IllegalStateException("Specialization 'doRead(Structure, DynamicObjectLibrary)' contains a shared cache with name 'dynamicObjectLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                }
            }
            if (this.dynamicObjectLibrary == null) {
                VarHandle.storeStoreFence();
                this.dynamicObjectLibrary = dynamicObjectLibrary_1;
            }
            state_0 = state_0 | 0b100 /* add SpecializationActive[StructureReadNode.doRead(Structure, DynamicObjectLibrary)] */;
            this.state_0_ = state_0;
            return doRead(structureValue_, dynamicObjectLibrary_1);
        }
        if (structureValue instanceof ArrayList<?>) {
            ArrayList<?> structureValue_ = (ArrayList<?>) structureValue;
            state_0 = state_0 | 0b1000 /* add SpecializationActive[StructureReadNode.doMultiSelect(ArrayList<>)] */;
            this.state_0_ = state_0;
            return doMultiSelect(structureValue_);
        }
        state_0 = state_0 | 0b10000 /* add SpecializationActive[StructureReadNode.doIllegal(Object)] */;
        this.state_0_ = state_0;
        return doIllegal(structureValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b11101) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b11101) & ((state_0 & 0b11101) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static StructureReadNode create(VocabularyType key, SourceSection sourceSection, ValueNode structure) {
        return new StructureReadNodeGen(key, sourceSection, structure);
    }

}
