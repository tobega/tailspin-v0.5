package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.TagNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;
import tailspin.language.runtime.stream.ListStream;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class WriteKeyValueNode extends ValueNode {
  public abstract Object executeDirect(VirtualFrame frame, Object target, Object value);

  final VocabularyType type;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  protected TagNode typeCheck;

  protected WriteKeyValueNode(VocabularyType type, SourceSection sourceSection) {
    super(sourceSection);
    this.type = type;
    this.typeCheck = TagNode.create(type, null, sourceSection);
  }

  public static WriteKeyValueNode create(VocabularyType key, ValueNode target, ValueNode value, SourceSection sourceSection) {
    return WriteKeyValueNodeGen.create(key, sourceSection, target, value);
  }

  @Specialization
  Structure doWrite(VirtualFrame frame, Structure target, Object value,
      @CachedLibrary(limit = "1") DynamicObjectLibrary dynamicObjectLibrary) {
    target = target.getThawed();
    value = typeCheck.executeDirect(frame, value);
    dynamicObjectLibrary.put(target, type, value);
    return target;
  }

  @Specialization(guards = "targetStream.size() == valueStream.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(VirtualFrame frame, ListStream targetStream, ListStream valueStream) {
    Object[] targets = targetStream.getArray();
    Object[] values = valueStream.getArray();
    int size = targetStream.size();
    for (int i = 0; i < size; i++) {
      targets[i] = executeDirect(frame, targets[i], values[i]);
    }
    return targetStream;
  }

  @Fallback
  protected Object doIllegal(Object target, Object values) {
    throw new TypeError(String.format("Cannot write %s by %s for %s", target.getClass(), type, values.getClass()), this);
  }
}
