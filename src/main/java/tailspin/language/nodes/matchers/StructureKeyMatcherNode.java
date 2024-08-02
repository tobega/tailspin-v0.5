package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;

@GenerateInline(false)
public abstract class StructureKeyMatcherNode extends MatcherNode {
  final String key;
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  MatcherNode matcher;

  protected StructureKeyMatcherNode(String key, MatcherNode matcher) {
    this.key = key;
    this.matcher = matcher;
  }

  @Specialization
  protected boolean doKeyMatch(VirtualFrame frame, Structure structure,
      @CachedLibrary(limit = "2") DynamicObjectLibrary dynamicObjectLibrary) {
    Object value = dynamicObjectLibrary.getOrDefault(structure, key, null);
    if (value == null) return false;
    return matcher.executeMatcherGeneric(frame, value);
  }

  public static StructureKeyMatcherNode create(String key, MatcherNode matcher) {
    return StructureKeyMatcherNodeGen.create(key, matcher);
  }
}
