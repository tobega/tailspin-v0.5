// CheckStyle: start generated
package tailspin.language.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.HostCompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;

@GeneratedBy(TailspinTypes.class)
public final class TailspinTypesGen extends TailspinTypes {

    protected TailspinTypesGen() {
    }

    public static boolean isLong(Object value) {
        return value instanceof Long;
    }

    public static long asLong(Object value) {
        assert value instanceof Long : "TailspinTypesGen.asLong: long expected";
        return (long) value;
    }

    public static long expectLong(Object value) throws UnexpectedResultException {
        if (value instanceof Long) {
            return (long) value;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value);
    }

    public static boolean isBigNumber(Object value) {
        return value instanceof BigNumber;
    }

    public static BigNumber asBigNumber(Object value) {
        assert value instanceof BigNumber : "TailspinTypesGen.asBigNumber: BigNumber expected";
        return (BigNumber) value;
    }

    public static BigNumber expectBigNumber(Object value) throws UnexpectedResultException {
        if (value instanceof BigNumber) {
            return (BigNumber) value;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value);
    }

    public static boolean isRational(Object value) {
        return value instanceof Rational;
    }

    public static Rational asRational(Object value) {
        assert value instanceof Rational : "TailspinTypesGen.asRational: Rational expected";
        return (Rational) value;
    }

    public static Rational expectRational(Object value) throws UnexpectedResultException {
        if (value instanceof Rational) {
            return (Rational) value;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value);
    }

    public static boolean isSmallSciNum(Object value) {
        return value instanceof SmallSciNum;
    }

    public static SmallSciNum asSmallSciNum(Object value) {
        assert value instanceof SmallSciNum : "TailspinTypesGen.asSmallSciNum: SmallSciNum expected";
        return (SmallSciNum) value;
    }

    public static SmallSciNum expectSmallSciNum(Object value) throws UnexpectedResultException {
        if (value instanceof SmallSciNum) {
            return (SmallSciNum) value;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value);
    }

    public static boolean isSciNum(Object value) {
        return value instanceof SciNum;
    }

    public static SciNum asSciNum(Object value) {
        assert value instanceof SciNum : "TailspinTypesGen.asSciNum: SciNum expected";
        return (SciNum) value;
    }

    public static SciNum expectSciNum(Object value) throws UnexpectedResultException {
        if (value instanceof SciNum) {
            return (SciNum) value;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value);
    }

    public static BigNumber expectImplicitBigNumber(int state, Object value) throws UnexpectedResultException {
        if ((state & 0b1) != 0 && value instanceof BigNumber) {
            return (BigNumber) value;
        } else if ((state & 0b10) != 0 && value instanceof Long) {
            return castBigNumber((long) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(value);
        }
    }

    public static boolean isImplicitBigNumber(int state, Object value) {
        return ((state & 0b1) != 0 && value instanceof BigNumber)
             || ((state & 0b10) != 0 && value instanceof Long);
    }

    public static boolean isImplicitBigNumber(Object value) {
        return value instanceof BigNumber
             || value instanceof Long;
    }

    public static BigNumber asImplicitBigNumber(int state, Object value) {
        if (HostCompilerDirectives.inInterpreterFastPath()) {
            return asImplicitBigNumber(value);
        }
        if ((state & 0b1) != 0 && value instanceof BigNumber) {
            return (BigNumber) value;
        } else if ((state & 0b10) != 0 && value instanceof Long) {
            return castBigNumber((long) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static BigNumber asImplicitBigNumber(Object value) {
        if (value instanceof BigNumber) {
            return (BigNumber) value;
        } else if (value instanceof Long) {
            return castBigNumber((long) value);
        } else {
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static int specializeImplicitBigNumber(Object value) {
        if (value instanceof BigNumber) {
            return 0b1;
        } else if (value instanceof Long) {
            return 0b10;
        } else {
            return 0;
        }
    }

    public static Rational expectImplicitRational(int state, Object value) throws UnexpectedResultException {
        if ((state & 0b1) != 0 && value instanceof Rational) {
            return (Rational) value;
        } else if ((state & 0b10) != 0 && value instanceof SmallRational) {
            return castRational((SmallRational) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(value);
        }
    }

    public static boolean isImplicitRational(int state, Object value) {
        return ((state & 0b1) != 0 && value instanceof Rational)
             || ((state & 0b10) != 0 && value instanceof SmallRational);
    }

    public static boolean isImplicitRational(Object value) {
        return value instanceof Rational
             || value instanceof SmallRational;
    }

    public static Rational asImplicitRational(int state, Object value) {
        if (HostCompilerDirectives.inInterpreterFastPath()) {
            return asImplicitRational(value);
        }
        if ((state & 0b1) != 0 && value instanceof Rational) {
            return (Rational) value;
        } else if ((state & 0b10) != 0 && value instanceof SmallRational) {
            return castRational((SmallRational) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static Rational asImplicitRational(Object value) {
        if (value instanceof Rational) {
            return (Rational) value;
        } else if (value instanceof SmallRational) {
            return castRational((SmallRational) value);
        } else {
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static int specializeImplicitRational(Object value) {
        if (value instanceof Rational) {
            return 0b1;
        } else if (value instanceof SmallRational) {
            return 0b10;
        } else {
            return 0;
        }
    }

    public static SciNum expectImplicitSciNum(int state, Object value) throws UnexpectedResultException {
        if ((state & 0b1) != 0 && value instanceof SciNum) {
            return (SciNum) value;
        } else if ((state & 0b10) != 0 && value instanceof SmallSciNum) {
            return castSciNum((SmallSciNum) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(value);
        }
    }

    public static boolean isImplicitSciNum(int state, Object value) {
        return ((state & 0b1) != 0 && value instanceof SciNum)
             || ((state & 0b10) != 0 && value instanceof SmallSciNum);
    }

    public static boolean isImplicitSciNum(Object value) {
        return value instanceof SciNum
             || value instanceof SmallSciNum;
    }

    public static SciNum asImplicitSciNum(int state, Object value) {
        if (HostCompilerDirectives.inInterpreterFastPath()) {
            return asImplicitSciNum(value);
        }
        if ((state & 0b1) != 0 && value instanceof SciNum) {
            return (SciNum) value;
        } else if ((state & 0b10) != 0 && value instanceof SmallSciNum) {
            return castSciNum((SmallSciNum) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static SciNum asImplicitSciNum(Object value) {
        if (value instanceof SciNum) {
            return (SciNum) value;
        } else if (value instanceof SmallSciNum) {
            return castSciNum((SmallSciNum) value);
        } else {
            throw new IllegalArgumentException("Illegal implicit source type.");
        }
    }

    public static int specializeImplicitSciNum(Object value) {
        if (value instanceof SciNum) {
            return 0b1;
        } else if (value instanceof SmallSciNum) {
            return 0b10;
        } else {
            return 0;
        }
    }

}
