package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.TagNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

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

  @Specialization(guards = "targets.size() == values.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(VirtualFrame frame, ArrayList<?> targets, ArrayList<?> values) {
    for (int i = 0; i < targets.size(); i++) {
      ((ArrayList<Object>) targets).set(i, executeDirect(frame, targets.get(i), values.get(i)));
    }
    return targets;
  }

  @Fallback
  protected Object doIllegal(Object target, Object ignored) {
    throw new TypeError(String.format("Cannot write %s by %s", target.getClass(), type), this);
  }
}
