package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class WriteKeyValueNode extends ValueNode {
  public abstract Object executeDirect(Object target, Object value);

  final VocabularyType type;

  protected WriteKeyValueNode(VocabularyType type) {
    this.type = type;
  }

  public static WriteKeyValueNode create(VocabularyType key, ValueNode target, ValueNode value) {
    return WriteKeyValueNodeGen.create(key, target, value);
  }

  @Specialization
  Structure doWrite(Structure target, Object value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint,
      @CachedLibrary(limit = "1") DynamicObjectLibrary dynamicObjectLibrary) {
    target = target.getThawed();
    if (!typeConstraint.executeMatcherGeneric(null, value)) {
      throw new TypeError(type.toString() + " cannot be " + value);
    }
    dynamicObjectLibrary.put(target, type, value);
    return target;
  }

  @Specialization(guards = "targets.size() == values.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(ArrayList<?> targets, ArrayList<?> values) {
    for (int i = 0; i < targets.size(); i++) {
      ((ArrayList<Object>) targets).set(i, executeDirect(targets.get(i), values.get(i)));
    }
    return targets;
  }

  @Fallback
  protected Object doIllegal(Object target, Object ignored) {
    throw new TypeError(String.format("Cannot write %s by %s", target.getClass(), type));
  }
}
