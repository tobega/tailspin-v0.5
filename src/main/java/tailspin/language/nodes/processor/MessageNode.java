package tailspin.language.nodes.processor;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "processor", type = ValueNode.class)
public abstract class MessageNode extends ValueNode {
  public abstract Object executeMessage(Object processor);

  private final String message;

  protected MessageNode(String message) {
    this.message = message;
  }

  @Specialization
  protected Object doLong(long value) {
    return switch (message) {
      case "raw" -> value;
      default -> throw new TypeError("Can't send message " + message + " to long " + value);
    };
  }

  @Specialization(guards = "processorInteropLibrary.hasMembers(processor)", limit = "2")
  protected Object doProcessor(Object processor,
      @CachedLibrary("processor") InteropLibrary processorInteropLibrary) {
    try {
      return processorInteropLibrary.readMember(processor, message);
    } catch (UnsupportedMessageException | UnknownIdentifierException e) {
      throw new RuntimeException(e);
    }
  }

  @Specialization
  protected Object doUnknown(Object value) {
    throw new TypeError("Can't send message " + message + " to " + value.getClass());
  }

  public static MessageNode create(String message, ValueNode processor) {
    return MessageNodeGen.create(message, processor);
  }
}
