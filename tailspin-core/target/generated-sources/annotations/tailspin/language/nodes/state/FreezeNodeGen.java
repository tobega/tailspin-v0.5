// CheckStyle: start generated
package tailspin.language.nodes.state;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.dsl.InlineSupport.ReferenceField;
import com.oracle.truffle.api.dsl.InlineSupport.RequiredField;
import com.oracle.truffle.api.dsl.InlineSupport.StateField;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.TailspinArray;

/**
 * Debug Info: <pre>
 *   Specialization {@link FreezeNode#doNull}
 *     Activation probability: 0,38500
 *     With/without class size: 8/0 bytes
 *   Specialization {@link FreezeNode#doArray}
 *     Activation probability: 0,29500
 *     With/without class size: 9/4 bytes
 *   Specialization {@link FreezeNode#doStructure}
 *     Activation probability: 0,20500
 *     With/without class size: 8/8 bytes
 *   Specialization {@link FreezeNode#doObject}
 *     Activation probability: 0,11500
 *     With/without class size: 5/0 bytes
 * </pre>
 */
@GeneratedBy(FreezeNode.class)
@SuppressWarnings({"javadoc", "unused"})
public final class FreezeNodeGen extends FreezeNode {

    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link FreezeNode#doNull}
     *   1: SpecializationActive {@link FreezeNode#doArray}
     *   2: SpecializationActive {@link FreezeNode#doStructure}
     *   3: SpecializationActive {@link FreezeNode#doObject}
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link FreezeNode#doArray}
     *   Parameter: {@link FreezeNode} childFreezer</pre>
     */
    @Child private FreezeNode array_childFreezer_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link FreezeNode#doStructure}
     *   Parameter: {@link DynamicObjectLibrary} dol</pre>
     */
    @Child private DynamicObjectLibrary structure_dol_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link FreezeNode#doStructure}
     *   Parameter: {@link FreezeNode} childFreezer</pre>
     */
    @Child private FreezeNode structure_childFreezer_;

    private FreezeNodeGen() {
    }

    @Override
    public void executeFreeze(Node arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 /* is SpecializationActive[FreezeNode.doNull(Object)] || SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] || SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] || SpecializationActive[FreezeNode.doObject(Object)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[FreezeNode.doNull(Object)] */) {
                if ((arg1Value == null)) {
                    doNull(arg1Value);
                    return;
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] */ && arg1Value instanceof TailspinArray) {
                TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                {
                    FreezeNode childFreezer__ = this.array_childFreezer_;
                    if (childFreezer__ != null) {
                        doArray(arg1Value_, childFreezer__);
                        return;
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] */ && arg1Value instanceof Structure) {
                Structure arg1Value_ = (Structure) arg1Value;
                {
                    DynamicObjectLibrary dol__ = this.structure_dol_;
                    if (dol__ != null) {
                        FreezeNode childFreezer__1 = this.structure_childFreezer_;
                        if (childFreezer__1 != null) {
                            doStructure(arg1Value_, dol__, childFreezer__1);
                            return;
                        }
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[FreezeNode.doObject(Object)] */) {
                doObject(arg1Value);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        executeAndSpecialize(arg0Value, arg1Value);
        return;
    }

    private void executeAndSpecialize(Node arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if ((arg1Value == null)) {
            state_0 = state_0 | 0b1 /* add SpecializationActive[FreezeNode.doNull(Object)] */;
            this.state_0_ = state_0;
            doNull(arg1Value);
            return;
        }
        if (arg1Value instanceof TailspinArray) {
            TailspinArray arg1Value_ = (TailspinArray) arg1Value;
            FreezeNode childFreezer__ = this.insert((FreezeNodeGen.create()));
            Objects.requireNonNull(childFreezer__, "Specialization 'doArray(TailspinArray, FreezeNode)' cache 'childFreezer' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.array_childFreezer_ = childFreezer__;
            state_0 = state_0 | 0b10 /* add SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] */;
            this.state_0_ = state_0;
            doArray(arg1Value_, childFreezer__);
            return;
        }
        if (arg1Value instanceof Structure) {
            Structure arg1Value_ = (Structure) arg1Value;
            DynamicObjectLibrary dol__ = this.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
            Objects.requireNonNull(dol__, "Specialization 'doStructure(Structure, DynamicObjectLibrary, FreezeNode)' cache 'dol' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.structure_dol_ = dol__;
            FreezeNode childFreezer__1 = this.insert((FreezeNodeGen.create()));
            Objects.requireNonNull(childFreezer__1, "Specialization 'doStructure(Structure, DynamicObjectLibrary, FreezeNode)' cache 'childFreezer' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.structure_childFreezer_ = childFreezer__1;
            state_0 = state_0 | 0b100 /* add SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] */;
            this.state_0_ = state_0;
            doStructure(arg1Value_, dol__, childFreezer__1);
            return;
        }
        state_0 = state_0 | 0b1000 /* add SpecializationActive[FreezeNode.doObject(Object)] */;
        this.state_0_ = state_0;
        doObject(arg1Value);
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
    public static FreezeNode create() {
        return new FreezeNodeGen();
    }

    /**
     * Required Fields: <ul>
     * <li>{@link Inlined#state_0_}
     * <li>{@link Inlined#array_childFreezer_}
     * <li>{@link Inlined#structure_dol_}
     * <li>{@link Inlined#structure_childFreezer_}
     * </ul>
     */
    @NeverDefault
    public static FreezeNode inline(@RequiredField(bits = 4, value = StateField.class)@RequiredField(type = Node.class, value = ReferenceField.class)@RequiredField(type = Node.class, value = ReferenceField.class)@RequiredField(type = Node.class, value = ReferenceField.class) InlineTarget target) {
        return new FreezeNodeGen.Inlined(target);
    }

    @GeneratedBy(FreezeNode.class)
    @DenyReplace
    private static final class Inlined extends FreezeNode {

        /**
         * State Info: <pre>
         *   0: SpecializationActive {@link FreezeNode#doNull}
         *   1: SpecializationActive {@link FreezeNode#doArray}
         *   2: SpecializationActive {@link FreezeNode#doStructure}
         *   3: SpecializationActive {@link FreezeNode#doObject}
         * </pre>
         */
        private final StateField state_0_;
        private final ReferenceField<FreezeNode> array_childFreezer_;
        private final ReferenceField<DynamicObjectLibrary> structure_dol_;
        private final ReferenceField<FreezeNode> structure_childFreezer_;

        @SuppressWarnings("unchecked")
        private Inlined(InlineTarget target) {
            assert target.getTargetClass().isAssignableFrom(FreezeNode.class);
            this.state_0_ = target.getState(0, 4);
            this.array_childFreezer_ = target.getReference(1, FreezeNode.class);
            this.structure_dol_ = target.getReference(2, DynamicObjectLibrary.class);
            this.structure_childFreezer_ = target.getReference(3, FreezeNode.class);
        }

        @Override
        public void executeFreeze(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if (state_0 != 0 /* is SpecializationActive[FreezeNode.doNull(Object)] || SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] || SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] || SpecializationActive[FreezeNode.doObject(Object)] */) {
                if ((state_0 & 0b1) != 0 /* is SpecializationActive[FreezeNode.doNull(Object)] */) {
                    if ((arg1Value == null)) {
                        doNull(arg1Value);
                        return;
                    }
                }
                if ((state_0 & 0b10) != 0 /* is SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] */ && arg1Value instanceof TailspinArray) {
                    TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                    {
                        FreezeNode childFreezer__ = this.array_childFreezer_.get(arg0Value);
                        if (childFreezer__ != null) {
                            doArray(arg1Value_, childFreezer__);
                            return;
                        }
                    }
                }
                if ((state_0 & 0b100) != 0 /* is SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] */ && arg1Value instanceof Structure) {
                    Structure arg1Value_ = (Structure) arg1Value;
                    {
                        DynamicObjectLibrary dol__ = this.structure_dol_.get(arg0Value);
                        if (dol__ != null) {
                            FreezeNode childFreezer__1 = this.structure_childFreezer_.get(arg0Value);
                            if (childFreezer__1 != null) {
                                doStructure(arg1Value_, dol__, childFreezer__1);
                                return;
                            }
                        }
                    }
                }
                if ((state_0 & 0b1000) != 0 /* is SpecializationActive[FreezeNode.doObject(Object)] */) {
                    doObject(arg1Value);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            executeAndSpecialize(arg0Value, arg1Value);
            return;
        }

        private void executeAndSpecialize(Node arg0Value, Object arg1Value) {
            int state_0 = this.state_0_.get(arg0Value);
            if ((arg1Value == null)) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[FreezeNode.doNull(Object)] */;
                this.state_0_.set(arg0Value, state_0);
                doNull(arg1Value);
                return;
            }
            if (arg1Value instanceof TailspinArray) {
                TailspinArray arg1Value_ = (TailspinArray) arg1Value;
                FreezeNode childFreezer__ = arg0Value.insert((FreezeNodeGen.create()));
                Objects.requireNonNull(childFreezer__, "Specialization 'doArray(TailspinArray, FreezeNode)' cache 'childFreezer' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.array_childFreezer_.set(arg0Value, childFreezer__);
                state_0 = state_0 | 0b10 /* add SpecializationActive[FreezeNode.doArray(TailspinArray, FreezeNode)] */;
                this.state_0_.set(arg0Value, state_0);
                doArray(arg1Value_, childFreezer__);
                return;
            }
            if (arg1Value instanceof Structure) {
                Structure arg1Value_ = (Structure) arg1Value;
                DynamicObjectLibrary dol__ = arg0Value.insert((DYNAMIC_OBJECT_LIBRARY_.createDispatched(2)));
                Objects.requireNonNull(dol__, "Specialization 'doStructure(Structure, DynamicObjectLibrary, FreezeNode)' cache 'dol' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.structure_dol_.set(arg0Value, dol__);
                FreezeNode childFreezer__1 = arg0Value.insert((FreezeNodeGen.create()));
                Objects.requireNonNull(childFreezer__1, "Specialization 'doStructure(Structure, DynamicObjectLibrary, FreezeNode)' cache 'childFreezer' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.structure_childFreezer_.set(arg0Value, childFreezer__1);
                state_0 = state_0 | 0b100 /* add SpecializationActive[FreezeNode.doStructure(Structure, DynamicObjectLibrary, FreezeNode)] */;
                this.state_0_.set(arg0Value, state_0);
                doStructure(arg1Value_, dol__, childFreezer__1);
                return;
            }
            state_0 = state_0 | 0b1000 /* add SpecializationActive[FreezeNode.doObject(Object)] */;
            this.state_0_.set(arg0Value, state_0);
            doObject(arg1Value);
            return;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }

    }
}
