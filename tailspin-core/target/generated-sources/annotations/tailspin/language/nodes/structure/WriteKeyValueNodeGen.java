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
import java.util.Objects;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link WriteKeyValueNode#doWrite}
 *     Activation probability: 0,48333
 *     With/without class size: 13/4 bytes
 *   Specialization {@link WriteKeyValueNode#doMany}
 *     Activation probability: 0,33333
 *     With/without class size: 8/0 bytes
 *   Specialization {@link WriteKeyValueNode#doIllegal}
 *     Activation probability: 0,18333
 *     With/without class size: 6/0 bytes
 * </pre>
 */
@GeneratedBy(WriteKeyValueNode.class)
@SuppressWarnings("javadoc")
public final class WriteKeyValueNodeGen extends WriteKeyValueNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @Child private ValueNode target_;
    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link WriteKeyValueNode#doWrite}
     *   1: SpecializationActive {@link WriteKeyValueNode#doMany}
     *   2: SpecializationActive {@link WriteKeyValueNode#doIllegal}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link WriteKeyValueNode#doWrite}
     *   Parameter: {@link DynamicObjectLibrary} dynamicObjectLibrary</pre>
     */
    @Child private DynamicObjectLibrary write_dynamicObjectLibrary_;

    private WriteKeyValueNodeGen(VocabularyType type, SourceSection sourceSection, ValueNode target, ValueNode value) {
        super(type, sourceSection);
        this.target_ = target;
        this.value_ = value;
    }

    @SuppressWarnings("static-method")
    private boolean fallbackGuard_(int state_0, Object targetValue, Object valueValue) {
        if (!((state_0 & 0b1) != 0 /* is SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] */) && targetValue instanceof Structure) {
            return false;
        }
        if (targetValue instanceof ArrayList<?> && valueValue instanceof ArrayList<?>) {
            ArrayList<?> targetValue_ = (ArrayList<?>) targetValue;
            ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
            if ((targetValue_.size() == valueValue_.size())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object targetValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] || SpecializationActive[WriteKeyValueNode.doMany(VirtualFrame, ArrayList<>, ArrayList<>)] || SpecializationActive[WriteKeyValueNode.doIllegal(Object, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] */ && targetValue instanceof Structure) {
                Structure targetValue_ = (Structure) targetValue;
                {
                    DynamicObjectLibrary dynamicObjectLibrary__ = this.write_dynamicObjectLibrary_;
                    if (dynamicObjectLibrary__ != null) {
                        return doWrite(frameValue, targetValue_, valueValue, dynamicObjectLibrary__);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[WriteKeyValueNode.doMany(VirtualFrame, ArrayList<>, ArrayList<>)] */ && targetValue instanceof ArrayList<?>) {
                ArrayList<?> targetValue_ = (ArrayList<?>) targetValue;
                if (valueValue instanceof ArrayList<?>) {
                    ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                    if ((targetValue_.size() == valueValue_.size())) {
                        return doMany(frameValue, targetValue_, valueValue_);
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[WriteKeyValueNode.doIllegal(Object, Object)] */) {
                if (fallbackGuard_(state_0, targetValue, valueValue)) {
                    return doIllegal(targetValue, valueValue);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, targetValue, valueValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetValue_ = this.target_.executeGeneric(frameValue);
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if (state_0 != 0 /* is SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] || SpecializationActive[WriteKeyValueNode.doMany(VirtualFrame, ArrayList<>, ArrayList<>)] || SpecializationActive[WriteKeyValueNode.doIllegal(Object, Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] */ && targetValue_ instanceof Structure) {
                Structure targetValue__ = (Structure) targetValue_;
                {
                    DynamicObjectLibrary dynamicObjectLibrary__ = this.write_dynamicObjectLibrary_;
                    if (dynamicObjectLibrary__ != null) {
                        return doWrite(frameValue, targetValue__, valueValue_, dynamicObjectLibrary__);
                    }
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[WriteKeyValueNode.doMany(VirtualFrame, ArrayList<>, ArrayList<>)] */ && targetValue_ instanceof ArrayList<?>) {
                ArrayList<?> targetValue__ = (ArrayList<?>) targetValue_;
                if (valueValue_ instanceof ArrayList<?>) {
                    ArrayList<?> valueValue__ = (ArrayList<?>) valueValue_;
                    if ((targetValue__.size() == valueValue__.size())) {
                        return doMany(frameValue, targetValue__, valueValue__);
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[WriteKeyValueNode.doIllegal(Object, Object)] */) {
                if (fallbackGuard_(state_0, targetValue_, valueValue_)) {
                    return doIllegal(targetValue_, valueValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, targetValue_, valueValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object targetValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (targetValue instanceof Structure) {
            Structure targetValue_ = (Structure) targetValue;
            DynamicObjectLibrary dynamicObjectLibrary__ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(1)));
            Objects.requireNonNull(dynamicObjectLibrary__, "Specialization 'doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)' cache 'dynamicObjectLibrary' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.write_dynamicObjectLibrary_ = dynamicObjectLibrary__;
            state_0 = state_0 | 0b1 /* add SpecializationActive[WriteKeyValueNode.doWrite(VirtualFrame, Structure, Object, DynamicObjectLibrary)] */;
            this.state_0_ = state_0;
            return doWrite(frameValue, targetValue_, valueValue, dynamicObjectLibrary__);
        }
        if (targetValue instanceof ArrayList<?>) {
            ArrayList<?> targetValue_ = (ArrayList<?>) targetValue;
            if (valueValue instanceof ArrayList<?>) {
                ArrayList<?> valueValue_ = (ArrayList<?>) valueValue;
                if ((targetValue_.size() == valueValue_.size())) {
                    state_0 = state_0 | 0b10 /* add SpecializationActive[WriteKeyValueNode.doMany(VirtualFrame, ArrayList<>, ArrayList<>)] */;
                    this.state_0_ = state_0;
                    return doMany(frameValue, targetValue_, valueValue_);
                }
            }
        }
        state_0 = state_0 | 0b100 /* add SpecializationActive[WriteKeyValueNode.doIllegal(Object, Object)] */;
        this.state_0_ = state_0;
        return doIllegal(targetValue, valueValue);
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
    public static WriteKeyValueNode create(VocabularyType type, SourceSection sourceSection, ValueNode target, ValueNode value) {
        return new WriteKeyValueNodeGen(type, sourceSection, target, value);
    }

}
