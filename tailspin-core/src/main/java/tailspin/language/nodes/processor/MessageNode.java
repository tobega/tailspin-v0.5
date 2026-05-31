package tailspin.language.nodes.processor;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "processor", type = ValueNode.class)
public abstract class MessageNode extends ValueNode {
  public abstract Object executeMessage(Object processor);

  final String message;

  protected MessageNode(String message, SourceSection sourceSection) {
    super(sourceSection);
    this.message = message;
  }

  @Specialization
  protected Object doLong(long value) {
    return switch (message) {
      case "raw" -> value;
      default -> {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new TypeError("Can't send message " + message + " to long " + value, this);
      }
    };
  }


  @Specialization(guards = {
      "processorInteropLibrary.hasMembers(processor)",
      "processorInteropLibrary.isMemberReadable(processor, message)"
  }, limit = "3")
  protected Object doProcessor(Object processor,
      @CachedLibrary("processor") InteropLibrary processorInteropLibrary) {
    try {
      return processorInteropLibrary.readMember(processor, message);
    } catch (UnsupportedMessageException | UnknownIdentifierException e) {
      throw new RuntimeException(e);
    }
  }

  @Specialization(guards = "processorInteropLibrary.hasArrayElements(array)", limit = "3")
  protected Object doInteropArray(Object array,
      @CachedLibrary("array") InteropLibrary processorInteropLibrary) {
    try {
      return switch (message) {
        case "start" -> 0L;
        case "end" -> processorInteropLibrary.getArraySize(array) - 1L;
        case "length" -> processorInteropLibrary.getArraySize(array);
        default -> processorInteropLibrary.readMember(array, message);
      };
    } catch (UnsupportedMessageException | UnknownIdentifierException e) {
      throw new RuntimeException(e);
    }
  }

  @Specialization
  protected Object doUnknown(Object value) {
    CompilerDirectives.transferToInterpreterAndInvalidate();
    throw new TypeError("Can't send message " + message + " to " + value.getClass(), this);
  }

  public static MessageNode create(String message, ValueNode processor, SourceSection sourceSection) {
    return MessageNodeGen.create(message, sourceSection, processor);
  }
}
