// CheckStyle: start generated
package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import java.math.BigInteger;

@GeneratedBy(SmallSciNum.class)
final class SmallSciNumGen {

    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    static  {
        LibraryExport.register(SmallSciNum.class, new InteropLibraryExports());
    }

    private SmallSciNumGen() {
    }

    @GeneratedBy(SmallSciNum.class)
    private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {

        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, SmallSciNum.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert receiver instanceof SmallSciNum;
            InteropLibrary uncached = InteropLibraryExports.UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert receiver instanceof SmallSciNum;
            return InteropLibraryExports.CACHE;
        }

        @GeneratedBy(SmallSciNum.class)
        private static final class Cached extends InteropLibrary {

            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert !(receiver instanceof SmallSciNum) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof SmallSciNum;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).hasMembers();
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).isMemberReadable(member);
            }

            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).readMember(member);
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).getMembers(includeInternal);
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).isNumber();
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInByte();
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInShort();
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInInt();
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInLong();
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInDouble();
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInFloat();
            }

            @Override
            public boolean fitsInBigInteger(Object receiver) {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).fitsInBigInteger();
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asByte();
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asShort();
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asInt();
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asLong();
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asFloat();
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asDouble();
            }

            @Override
            public BigInteger asBigInteger(Object receiver) throws UnsupportedMessageException {
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return (((SmallSciNum) receiver)).asBigInteger();
            }

        }
        @GeneratedBy(SmallSciNum.class)
        @DenyReplace
        private static final class Uncached extends InteropLibrary {

            protected Uncached() {
            }

            @Override
            @TruffleBoundary
            public boolean accepts(Object receiver) {
                assert !(receiver instanceof SmallSciNum) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof SmallSciNum;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @TruffleBoundary
            @Override
            public boolean hasMembers(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .hasMembers();
            }

            @TruffleBoundary
            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .isMemberReadable(member);
            }

            @TruffleBoundary
            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .readMember(member);
            }

            @TruffleBoundary
            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .getMembers(includeInternal);
            }

            @TruffleBoundary
            @Override
            public boolean isNumber(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .isNumber();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInByte(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInByte();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInShort(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInShort();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInInt(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInInt();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInLong(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInLong();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInDouble(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInDouble();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInFloat(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInFloat();
            }

            @TruffleBoundary
            @Override
            public boolean fitsInBigInteger(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .fitsInBigInteger();
            }

            @TruffleBoundary
            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asByte();
            }

            @TruffleBoundary
            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asShort();
            }

            @TruffleBoundary
            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asInt();
            }

            @TruffleBoundary
            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asLong();
            }

            @TruffleBoundary
            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asFloat();
            }

            @TruffleBoundary
            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asDouble();
            }

            @TruffleBoundary
            @Override
            public BigInteger asBigInteger(Object receiver) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((SmallSciNum) receiver) .asBigInteger();
            }

        }
    }
}
