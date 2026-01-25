// CheckStyle: start generated
package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;

@GeneratedBy(Structure.class)
@SuppressWarnings("javadoc")
public final class StructureGen {

    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    static  {
        LibraryExport.register(Structure.class, new InteropLibraryExports());
    }

    private StructureGen() {
    }

    @GeneratedBy(Structure.class)
    public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {

        private InteropLibraryExports() {
            super(InteropLibrary.class, Structure.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert receiver instanceof Structure;
            InteropLibrary uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert receiver instanceof Structure;
            return new Cached(receiver);
        }

        @GeneratedBy(Structure.class)
        public static class Cached extends InteropLibrary {

            @Child private DynamicObjectLibrary receiverDynamicObjectLibrary_;
            private final Class<? extends Structure> receiverClass_;

            protected Cached(Object receiver) {
                Structure castReceiver = ((Structure) receiver) ;
                this.receiverDynamicObjectLibrary_ = DYNAMIC_OBJECT_LIBRARY_.create((castReceiver));
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                assert receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(CompilerDirectives.isExact(receiver, this.receiverClass_))) {
                    return false;
                } else if (!this.receiverDynamicObjectLibrary_.accepts((receiver))) {
                    return false;
                } else {
                    return true;
                }
            }

            /**
             * Debug Info: <pre>
             *   Specialization {@link Structure#toDisplayString(Structure, boolean, DynamicObjectLibrary)}
             *     Activation probability: 0,25000
             *     With/without class size: 7/0 bytes
             * </pre>
             */
            @Override
            public Object toDisplayString(Object arg0Value_, boolean arg1Value) {
                assert CompilerDirectives.isExact(arg0Value_, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = CompilerDirectives.castExact(arg0Value_, receiverClass_);
                {
                    DynamicObjectLibrary thisObjectLibrary__ = this.receiverDynamicObjectLibrary_;
                    return arg0Value.toDisplayString(arg1Value, thisObjectLibrary__);
                }
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            /**
             * Debug Info: <pre>
             *   Specialization {@link Structure#getMembers(Structure, boolean, DynamicObjectLibrary)}
             *     Activation probability: 0,25000
             *     With/without class size: 7/0 bytes
             * </pre>
             */
            @Override
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert CompilerDirectives.isExact(arg0Value_, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = CompilerDirectives.castExact(arg0Value_, receiverClass_);
                {
                    DynamicObjectLibrary thisObjectLibrary__ = this.receiverDynamicObjectLibrary_;
                    return arg0Value.getMembers(arg1Value, thisObjectLibrary__);
                }
            }

            /**
             * Debug Info: <pre>
             *   Specialization {@link Structure#readMember(Structure, String, DynamicObjectLibrary)}
             *     Activation probability: 0,25000
             *     With/without class size: 7/0 bytes
             * </pre>
             */
            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert CompilerDirectives.isExact(arg0Value_, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = CompilerDirectives.castExact(arg0Value_, receiverClass_);
                {
                    DynamicObjectLibrary thisObjectLibrary__ = this.receiverDynamicObjectLibrary_;
                    return arg0Value.readMember(arg1Value, thisObjectLibrary__);
                }
            }

            /**
             * Debug Info: <pre>
             *   Specialization {@link Structure#writeMember(Structure, String, Object, DynamicObjectLibrary)}
             *     Activation probability: 0,25000
             *     With/without class size: 7/0 bytes
             * </pre>
             */
            @Override
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert CompilerDirectives.isExact(arg0Value_, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = CompilerDirectives.castExact(arg0Value_, receiverClass_);
                {
                    DynamicObjectLibrary thisObjectLibrary__ = this.receiverDynamicObjectLibrary_;
                    arg0Value.writeMember(arg1Value, arg2Value, thisObjectLibrary__);
                    return;
                }
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert CompilerDirectives.isExact(receiver, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                return (CompilerDirectives.castExact(receiver, receiverClass_)).hasMembers();
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert CompilerDirectives.isExact(receiver, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                return (CompilerDirectives.castExact(receiver, receiverClass_)).isMemberReadable(member);
            }

            @Override
            public boolean isMemberModifiable(Object receiver, String member) {
                assert CompilerDirectives.isExact(receiver, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                return (CompilerDirectives.castExact(receiver, receiverClass_)).isMemberModifiable(member);
            }

            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                assert CompilerDirectives.isExact(receiver, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";
                return (CompilerDirectives.castExact(receiver, receiverClass_)).isMemberInsertable(member);
            }

        }
        @GeneratedBy(Structure.class)
        public static class Uncached extends InteropLibrary {

            private final Class<? extends Structure> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((Structure) receiver).getClass();
            }

            @Override
            @TruffleBoundary
            public boolean accepts(Object receiver) {
                assert receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public final boolean isAdoptable() {
                return false;
            }

            @Override
            public final NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @TruffleBoundary
            @Override
            public Object toDisplayString(Object arg0Value_, boolean arg1Value) {
                // declared: true
                assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = ((Structure) arg0Value_);
                return arg0Value.toDisplayString(arg1Value, DYNAMIC_OBJECT_LIBRARY_.getUncached((arg0Value)));
            }

            @TruffleBoundary
            @Override
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                // declared: true
                assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = ((Structure) arg0Value_);
                return arg0Value.getMembers(arg1Value, DYNAMIC_OBJECT_LIBRARY_.getUncached((arg0Value)));
            }

            @TruffleBoundary
            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                // declared: true
                assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = ((Structure) arg0Value_);
                return arg0Value.readMember(arg1Value, DYNAMIC_OBJECT_LIBRARY_.getUncached((arg0Value)));
            }

            @TruffleBoundary
            @Override
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                // declared: true
                assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";
                Structure arg0Value = ((Structure) arg0Value_);
                arg0Value.writeMember(arg1Value, arg2Value, DYNAMIC_OBJECT_LIBRARY_.getUncached((arg0Value)));
                return;
            }

            @TruffleBoundary
            @Override
            public boolean hasMembers(Object receiver) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((Structure) receiver) .hasMembers();
            }

            @TruffleBoundary
            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((Structure) receiver) .isMemberReadable(member);
            }

            @TruffleBoundary
            @Override
            public boolean isMemberModifiable(Object receiver, String member) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((Structure) receiver) .isMemberModifiable(member);
            }

            @TruffleBoundary
            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                // declared: true
                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";
                return ((Structure) receiver) .isMemberInsertable(member);
            }

        }
    }
}
