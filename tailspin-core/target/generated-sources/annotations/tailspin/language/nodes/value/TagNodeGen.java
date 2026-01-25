// CheckStyle: start generated
package tailspin.language.nodes.value;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.invoke.VarHandle;
import java.util.Objects;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

/**
 * Debug Info: <pre>
 *   Specialization {@link TagNode#doAlreadyTagged}
 *     Activation probability: 0,23929
 *     With/without class size: 6/0 bytes
 *   Specialization {@link TagNode#doTagLong}
 *     Activation probability: 0,20714
 *     With/without class size: 8/4 bytes
 *   Specialization {@link TagNode#doTagBigNumber}
 *     Activation probability: 0,17500
 *     With/without class size: 7/4 bytes
 *   Specialization {@link TagNode#doTagRational}
 *     Activation probability: 0,14286
 *     With/without class size: 6/4 bytes
 *   Specialization {@link TagNode#doTagSmallSciNum}
 *     Activation probability: 0,11071
 *     With/without class size: 6/4 bytes
 *   Specialization {@link TagNode#doTagSciNum}
 *     Activation probability: 0,07857
 *     With/without class size: 5/4 bytes
 *   Specialization {@link TagNode#doTypeCheck}
 *     Activation probability: 0,04643
 *     With/without class size: 4/4 bytes
 * </pre>
 */
@GeneratedBy(TagNode.class)
@SuppressWarnings("javadoc")
public final class TagNodeGen extends TagNode {

    @Child private ValueNode value_;
    /**
     * State Info: <pre>
     *   0: SpecializationActive {@link TagNode#doAlreadyTagged}
     *   1: SpecializationActive {@link TagNode#doTagLong}
     *   2: SpecializationActive {@link TagNode#doTagBigNumber}
     *   3: SpecializationActive {@link TagNode#doTagRational}
     *   4: SpecializationActive {@link TagNode#doTagSmallSciNum}
     *   5: SpecializationActive {@link TagNode#doTagSciNum}
     *   6: SpecializationActive {@link TagNode#doTypeCheck}
     *   7-8: ImplicitCast[type=BigNumber, index=0]
     *   9-10: ImplicitCast[type=Rational, index=0]
     *   11-12: ImplicitCast[type=SciNum, index=0]
     * </pre>
     */
    @CompilationFinal private int state_0_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTagLong}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode tagLong_typeConstraint_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTagBigNumber}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode tagBigNumber_typeConstraint_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTagRational}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode tagRational_typeConstraint_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTagSmallSciNum}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode tagSmallSciNum_typeConstraint_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTagSciNum}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode tagSciNum_typeConstraint_;
    /**
     * Source Info: <pre>
     *   Specialization: {@link TagNode#doTypeCheck}
     *   Parameter: {@link MatcherNode} typeConstraint</pre>
     */
    @Child private MatcherNode typeCheck_typeConstraint_;

    private TagNodeGen(VocabularyType type, SourceSection sourceSection, ValueNode value) {
        super(type, sourceSection);
        this.value_ = value;
    }

    @Override
    public Object executeDirect(VirtualFrame frameValue, Object valueValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111111) != 0 /* is SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] || SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] || SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] || SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] || SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] || SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] || SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] */ && valueValue instanceof TaggedValue) {
                TaggedValue valueValue_ = (TaggedValue) valueValue;
                if ((type == valueValue_.type())) {
                    return doAlreadyTagged(frameValue, valueValue_);
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] */ && valueValue instanceof Long) {
                long valueValue_ = (long) valueValue;
                {
                    MatcherNode typeConstraint__ = this.tagLong_typeConstraint_;
                    if (typeConstraint__ != null) {
                        return doTagLong(frameValue, valueValue_, typeConstraint__);
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000) >>> 7 /* get-int ImplicitCast[type=BigNumber, index=0] */, valueValue)) {
                BigNumber valueValue_ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000) >>> 7 /* get-int ImplicitCast[type=BigNumber, index=0] */, valueValue);
                {
                    MatcherNode typeConstraint__1 = this.tagBigNumber_typeConstraint_;
                    if (typeConstraint__1 != null) {
                        return doTagBigNumber(frameValue, valueValue_, typeConstraint__1);
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000) >>> 9 /* get-int ImplicitCast[type=Rational, index=0] */, valueValue)) {
                Rational valueValue_ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000) >>> 9 /* get-int ImplicitCast[type=Rational, index=0] */, valueValue);
                {
                    MatcherNode typeConstraint__2 = this.tagRational_typeConstraint_;
                    if (typeConstraint__2 != null) {
                        return doTagRational(frameValue, valueValue_, typeConstraint__2);
                    }
                }
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] */ && valueValue instanceof SmallSciNum) {
                SmallSciNum valueValue_ = (SmallSciNum) valueValue;
                {
                    MatcherNode typeConstraint__3 = this.tagSmallSciNum_typeConstraint_;
                    if (typeConstraint__3 != null) {
                        return doTagSmallSciNum(frameValue, valueValue_, typeConstraint__3);
                    }
                }
            }
            if ((state_0 & 0b100000) != 0 /* is SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000) >>> 11 /* get-int ImplicitCast[type=SciNum, index=0] */, valueValue)) {
                SciNum valueValue_ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000) >>> 11 /* get-int ImplicitCast[type=SciNum, index=0] */, valueValue);
                {
                    MatcherNode typeConstraint__4 = this.tagSciNum_typeConstraint_;
                    if (typeConstraint__4 != null) {
                        return doTagSciNum(frameValue, valueValue_, typeConstraint__4);
                    }
                }
            }
            if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */) {
                {
                    MatcherNode typeConstraint__5 = this.typeCheck_typeConstraint_;
                    if (typeConstraint__5 != null) {
                        return doTypeCheck(frameValue, valueValue, typeConstraint__5);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, valueValue);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111101) == 0 /* only-active SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] */ && ((state_0 & 0b1111111) != 0  /* is-not SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] && SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] && SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] && SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] && SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] && SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] && SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */)) {
            return executeGeneric_long0(state_0, frameValue);
        } else {
            return executeGeneric_generic1(state_0, frameValue);
        }
    }

    private Object executeGeneric_long0(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        long valueValue_;
        try {
            valueValue_ = this.value_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state_0 & 0b10) != 0 /* is SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] */;
        {
            MatcherNode typeConstraint__ = this.tagLong_typeConstraint_;
            if (typeConstraint__ != null) {
                return doTagLong(frameValue, valueValue_, typeConstraint__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, valueValue_);
    }

    private Object executeGeneric_generic1(int state_0__, VirtualFrame frameValue) {
        int state_0 = state_0__;
        Object valueValue_ = this.value_.executeGeneric(frameValue);
        if ((state_0 & 0b1111111) != 0 /* is SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] || SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] || SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] || SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] || SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] || SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] || SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */) {
            if ((state_0 & 0b1) != 0 /* is SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] */ && valueValue_ instanceof TaggedValue) {
                TaggedValue valueValue__ = (TaggedValue) valueValue_;
                if ((type == valueValue__.type())) {
                    return doAlreadyTagged(frameValue, valueValue__);
                }
            }
            if ((state_0 & 0b10) != 0 /* is SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] */ && valueValue_ instanceof Long) {
                long valueValue__ = (long) valueValue_;
                {
                    MatcherNode typeConstraint__ = this.tagLong_typeConstraint_;
                    if (typeConstraint__ != null) {
                        return doTagLong(frameValue, valueValue__, typeConstraint__);
                    }
                }
            }
            if ((state_0 & 0b100) != 0 /* is SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] */ && TailspinTypesGen.isImplicitBigNumber((state_0 & 0b110000000) >>> 7 /* get-int ImplicitCast[type=BigNumber, index=0] */, valueValue_)) {
                BigNumber valueValue__ = TailspinTypesGen.asImplicitBigNumber((state_0 & 0b110000000) >>> 7 /* get-int ImplicitCast[type=BigNumber, index=0] */, valueValue_);
                {
                    MatcherNode typeConstraint__1 = this.tagBigNumber_typeConstraint_;
                    if (typeConstraint__1 != null) {
                        return doTagBigNumber(frameValue, valueValue__, typeConstraint__1);
                    }
                }
            }
            if ((state_0 & 0b1000) != 0 /* is SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] */ && TailspinTypesGen.isImplicitRational((state_0 & 0b11000000000) >>> 9 /* get-int ImplicitCast[type=Rational, index=0] */, valueValue_)) {
                Rational valueValue__ = TailspinTypesGen.asImplicitRational((state_0 & 0b11000000000) >>> 9 /* get-int ImplicitCast[type=Rational, index=0] */, valueValue_);
                {
                    MatcherNode typeConstraint__2 = this.tagRational_typeConstraint_;
                    if (typeConstraint__2 != null) {
                        return doTagRational(frameValue, valueValue__, typeConstraint__2);
                    }
                }
            }
            if ((state_0 & 0b10000) != 0 /* is SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] */ && valueValue_ instanceof SmallSciNum) {
                SmallSciNum valueValue__ = (SmallSciNum) valueValue_;
                {
                    MatcherNode typeConstraint__3 = this.tagSmallSciNum_typeConstraint_;
                    if (typeConstraint__3 != null) {
                        return doTagSmallSciNum(frameValue, valueValue__, typeConstraint__3);
                    }
                }
            }
            if ((state_0 & 0b100000) != 0 /* is SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] */ && TailspinTypesGen.isImplicitSciNum((state_0 & 0b1100000000000) >>> 11 /* get-int ImplicitCast[type=SciNum, index=0] */, valueValue_)) {
                SciNum valueValue__ = TailspinTypesGen.asImplicitSciNum((state_0 & 0b1100000000000) >>> 11 /* get-int ImplicitCast[type=SciNum, index=0] */, valueValue_);
                {
                    MatcherNode typeConstraint__4 = this.tagSciNum_typeConstraint_;
                    if (typeConstraint__4 != null) {
                        return doTagSciNum(frameValue, valueValue__, typeConstraint__4);
                    }
                }
            }
            if ((state_0 & 0b1000000) != 0 /* is SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */) {
                {
                    MatcherNode typeConstraint__5 = this.typeCheck_typeConstraint_;
                    if (typeConstraint__5 != null) {
                        return doTypeCheck(frameValue, valueValue_, typeConstraint__5);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, valueValue_);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object valueValue) {
        int state_0 = this.state_0_;
        if (valueValue instanceof TaggedValue) {
            TaggedValue valueValue_ = (TaggedValue) valueValue;
            if ((type == valueValue_.type())) {
                state_0 = state_0 | 0b1 /* add SpecializationActive[TagNode.doAlreadyTagged(VirtualFrame, TaggedValue)] */;
                this.state_0_ = state_0;
                return doAlreadyTagged(frameValue, valueValue_);
            }
        }
        if (valueValue instanceof Long) {
            long valueValue_ = (long) valueValue;
            MatcherNode typeConstraint__ = this.insert((type.getConstraint(valueValue_)));
            Objects.requireNonNull(typeConstraint__, "Specialization 'doTagLong(VirtualFrame, long, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.tagLong_typeConstraint_ = typeConstraint__;
            state_0 = state_0 | 0b10 /* add SpecializationActive[TagNode.doTagLong(VirtualFrame, long, MatcherNode)] */;
            this.state_0_ = state_0;
            return doTagLong(frameValue, valueValue_, typeConstraint__);
        }
        {
            int bigNumberCast0;
            if ((bigNumberCast0 = TailspinTypesGen.specializeImplicitBigNumber(valueValue)) != 0) {
                BigNumber valueValue_ = TailspinTypesGen.asImplicitBigNumber(bigNumberCast0, valueValue);
                MatcherNode typeConstraint__1 = this.insert((type.getConstraint(valueValue_)));
                Objects.requireNonNull(typeConstraint__1, "Specialization 'doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.tagBigNumber_typeConstraint_ = typeConstraint__1;
                state_0 = (state_0 | (bigNumberCast0 << 7) /* set-int ImplicitCast[type=BigNumber, index=0] */);
                state_0 = state_0 | 0b100 /* add SpecializationActive[TagNode.doTagBigNumber(VirtualFrame, BigNumber, MatcherNode)] */;
                this.state_0_ = state_0;
                return doTagBigNumber(frameValue, valueValue_, typeConstraint__1);
            }
        }
        {
            int rationalCast0;
            if ((rationalCast0 = TailspinTypesGen.specializeImplicitRational(valueValue)) != 0) {
                Rational valueValue_ = TailspinTypesGen.asImplicitRational(rationalCast0, valueValue);
                MatcherNode typeConstraint__2 = this.insert((type.getConstraint(valueValue_)));
                Objects.requireNonNull(typeConstraint__2, "Specialization 'doTagRational(VirtualFrame, Rational, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.tagRational_typeConstraint_ = typeConstraint__2;
                state_0 = (state_0 | (rationalCast0 << 9) /* set-int ImplicitCast[type=Rational, index=0] */);
                state_0 = state_0 | 0b1000 /* add SpecializationActive[TagNode.doTagRational(VirtualFrame, Rational, MatcherNode)] */;
                this.state_0_ = state_0;
                return doTagRational(frameValue, valueValue_, typeConstraint__2);
            }
        }
        if (valueValue instanceof SmallSciNum) {
            SmallSciNum valueValue_ = (SmallSciNum) valueValue;
            MatcherNode typeConstraint__3 = this.insert((type.getConstraint(valueValue_)));
            Objects.requireNonNull(typeConstraint__3, "Specialization 'doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
            VarHandle.storeStoreFence();
            this.tagSmallSciNum_typeConstraint_ = typeConstraint__3;
            state_0 = state_0 | 0b10000 /* add SpecializationActive[TagNode.doTagSmallSciNum(VirtualFrame, SmallSciNum, MatcherNode)] */;
            this.state_0_ = state_0;
            return doTagSmallSciNum(frameValue, valueValue_, typeConstraint__3);
        }
        {
            int sciNumCast0;
            if ((sciNumCast0 = TailspinTypesGen.specializeImplicitSciNum(valueValue)) != 0) {
                SciNum valueValue_ = TailspinTypesGen.asImplicitSciNum(sciNumCast0, valueValue);
                MatcherNode typeConstraint__4 = this.insert((type.getConstraint(valueValue_)));
                Objects.requireNonNull(typeConstraint__4, "Specialization 'doTagSciNum(VirtualFrame, SciNum, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
                VarHandle.storeStoreFence();
                this.tagSciNum_typeConstraint_ = typeConstraint__4;
                state_0 = (state_0 | (sciNumCast0 << 11) /* set-int ImplicitCast[type=SciNum, index=0] */);
                state_0 = state_0 | 0b100000 /* add SpecializationActive[TagNode.doTagSciNum(VirtualFrame, SciNum, MatcherNode)] */;
                this.state_0_ = state_0;
                return doTagSciNum(frameValue, valueValue_, typeConstraint__4);
            }
        }
        MatcherNode typeConstraint__5 = this.insert((type.getConstraint(valueValue)));
        Objects.requireNonNull(typeConstraint__5, "Specialization 'doTypeCheck(VirtualFrame, Object, MatcherNode)' cache 'typeConstraint' returned a 'null' default value. The cache initializer must never return a default value for this cache. Use @Cached(neverDefault=false) to allow default values for this cached value or make sure the cache initializer never returns 'null'.");
        VarHandle.storeStoreFence();
        this.typeCheck_typeConstraint_ = typeConstraint__5;
        state_0 = state_0 | 0b1000000 /* add SpecializationActive[TagNode.doTypeCheck(VirtualFrame, Object, MatcherNode)] */;
        this.state_0_ = state_0;
        return doTypeCheck(frameValue, valueValue, typeConstraint__5);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0b1111111) == 0) {
            return NodeCost.UNINITIALIZED;
        } else {
            if (((state_0 & 0b1111111) & ((state_0 & 0b1111111) - 1)) == 0 /* is-single  */) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @NeverDefault
    public static TagNode create(VocabularyType type, SourceSection sourceSection, ValueNode value) {
        return new TagNodeGen(type, sourceSection, value);
    }

}
