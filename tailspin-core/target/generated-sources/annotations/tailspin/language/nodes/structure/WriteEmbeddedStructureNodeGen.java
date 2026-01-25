// CheckStyle: start generated
package tailspin.language.nodes.structure;

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
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

/**
 * Debug Info: <pre>
 *   Specialization {@link WriteEmbeddedStructureNode#writeNone}
 *     Activation probability: 0,38500
 *     With/without class size: 8/0 bytes
 *   Specialization {@link WriteEmbeddedStructureNode#writeEmbedded}
 *     Activation probability: 0,29500
 *     With/without class size: 7/0 bytes
 *   Specialization {@link WriteEmbeddedStructureNode#writeManyEmbedded}
 *     Activation probability: 0,20500
 *     With/without class size: 6/0 bytes
 *   Specialization {@link WriteEmbeddedStructureNode#writeIllegal}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(WriteEmbeddedStructureNode.class)
@SuppressWarnings("javadoc")
public final class WriteEmbeddedStructureNodeGen extends WriteEmbeddedStructureNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @Child private ValueNode target_;
    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link WriteEmbeddedStructureNode#writeNone}
     *   1: SpecializationActive {@link WriteEmbeddedStructureNode#writeEmbedded}
     *   2: SpecializationActive {@link WriteEmbeddedStructureNode#writeManyEmbedded}
     *   3: SpecializationActive {@link WriteEmbeddedStructureNode#writeIllegal}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteEmbeddedStructureNode#writeEmbedded}
     *   Parameter: {@link DynamicObjectLibrary} getLibrary</pre>
     */
    @Child private DynamicObjectLibrary get;
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteEmbeddedStructureNode#writeEmbedded}
     *   Parameter: {@link DynamicObjectLibrary} putLibrary</pre>
     */
    @Child private DynamicObjectLibrary put;

    private WriteEmbeddedStructureNodeGen(SourceSection sourceSection, ValueNode target, ValueNode value) {
        super(sourceSection);
        this.target_ = target;
        this.value_ = value;
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object targetValue, Object valueValue) {
        if (targetValue instanceof Structure) {
            if (!((state_0 & 0b1) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeNone(Structure, Object)] */) && (valueValue == null)) {
                return false;
            }
            if (!((state_0 & 0b10) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)] */) && valueValue instanceof Structure) {
                return false;
            }
            if (!((state_0 & 0b100) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)] */) && valueValue instanceof ArrayList<?>) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetValue_ = this.target_.executeGeneric(frameValue);
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeNone(Structure, Object)] || SpecializationActive[WriteEmbeddedStructureNode.writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)] || SpecializationActive[WriteEmbeddedStructureNode.writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)] || SpecializationActive[WriteEmbeddedStructureNode.writeIllegal(Object, Object)] */) {
            if ((state_0 & 0b111) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeNone(Structure, Object)] || SpecializationActive[WriteEmbeddedStructureNode.writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)] || SpecializationActive[WriteEmbeddedStructureNode.writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)] */ && targetValue_ instanceof Structure) {
                Structure targetValue__ = (Structure) targetValue_;
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeNone(Structure, Object)] */) {
                    if ((valueValue_ == null)) {
                        return writeNone(targetValue__, valueValue_);
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)] */ && valueValue_ instanceof Structure) {
                    Structure valueValue__ = (Structure) valueValue_;
                    {
                        DynamicObjectLibrary get_ = this.get;
                        if (get_ != null) {
                            DynamicObjectLibrary put_ = this.put;
                            if (put_ != null) {
                                return writeEmbedded(targetValue__, valueValue__, get_, put_);
                            }
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)] */ && valueValue_ instanceof ArrayList<?>) {
                    ArrayList<?> valueValue__ = (ArrayList<?>) valueValue_;
                    {
                        DynamicObjectLibrary get_1 = this.get;
                        if (get_1 != null) {
                            DynamicObjectLibrary put_1 = this.put;
                            if (put_1 != null) {
                                return writeManyEmbedded(targetValue__, valueValue__, get_1, put_1);
                            }
                        }
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[WriteEmbeddedStructureNode.writeIllegal(Object, Object)] */) {
                if (fallbackGuard_(state_0, targetValue_, valueValue_)) {
                    return writeIllegal(targetValue_, valueValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(targetValue_, valueValue_);
    }

    private Structure executeAndSpecialize(Object targetValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (targetValue instanceof Structure) {
            Structure targetValue_ = (Structure) targetValue;
            if ((valueValue == null)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[WriteEmbeddedStructureNode.writeNone(Structure, Object)] */;
                this.state_0_ = state_0;
                return writeNone(targetValue_, valueValue);
            }
            if (valueValue instanceof Structure) {
                Structure valueValue_ = (Structure) valueValue;
                DynamicObjectLibrary get_;
                DynamicObjectLibrary get__shared = this.get;
                if (get__shared != null) {
                    get_ = get__shared;
                } else {
                    get_ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    if (get_ == null) {
                        throw new IllegalStateException("Specialization 'writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)' contains a shared cache with name 'getLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.get == null) {
                    VarHandle.storeStoreFence();
                    this.get = get_;
                }
                DynamicObjectLibrary put_;
                DynamicObjectLibrary put__shared = this.put;
                if (put__shared != null) {
                    put_ = put__shared;
                } else {
                    put_ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    if (put_ == null) {
                        throw new IllegalStateException("Specialization 'writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)' contains a shared cache with name 'putLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.put == null) {
                    VarHandle.storeStoreFence();
                    this.put = put_;
                }
                state_0 = state_0 | 0b10 /* add SpecializationActive[WriteEmbeddedStructureNode.writeEmbedded(Structure, Structure, DynamicObjectLibrary, DynamicObjectLibrary)] */;
                this.state_0_ = state_0;
                return writeEmbedded(targetValue_, valueValue_, get_, put_);
            }
            if (valueValue instanceof ArrayList<?>) {
                ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                DynamicObjectLibrary get_1;
                DynamicObjectLibrary get_1_shared = this.get;
                if (get_1_shared != null) {
                    get_1 = get_1_shared;
                } else {
                    get_1 = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    if (get_1 == null) {
                        throw new IllegalStateException("Specialization 'writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)' contains a shared cache with name 'getLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.get == null) {
                    VarHandle.storeStoreFence();
                    this.get = get_1;
                }
                DynamicObjectLibrary put_1;
                DynamicObjectLibrary put_1_shared = this.put;
                if (put_1_shared != null) {
                    put_1 = put_1_shared;
                } else {
                    put_1 = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                    if (put_1 == null) {
                        throw new IllegalStateException("Specialization 'writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)' contains a shared cache with name 'putLibrary' that returned a default value for the cached initializer. Default values are not supported for shared cached initializers because the default value is reserved for the uninitialized state.");
                    }
                }
                if (this.put == null) {
                    VarHandle.storeStoreFence();
                    this.put = put_1;
                }
                state_0 = state_0 | 0b100 /* add SpecializationActive[WriteEmbeddedStructureNode.writeManyEmbedded(Structure, ArrayList<>, DynamicObjectLibrary, DynamicObjectLibrary)] */;
                this.state_0_ = state_0;
                return writeManyEmbedded(targetValue_, valueValue_, get_1, put_1);
            }
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[WriteEmbeddedStructureNode.writeIllegal(Object, Object)] */;
        this.state_0_ = state_0;
        return writeIllegal(targetValue, valueValue);
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
    public static WriteEmbeddedStructureNode create(SourceSection sourceSection, ValueNode target, ValueNode value) {
        return new WriteEmbeddedStructureNodeGen(sourceSection, target, value);
    }

}
