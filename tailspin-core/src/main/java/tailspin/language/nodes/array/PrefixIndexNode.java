package tailspin.language.nodes.array;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.processor.MessageNode;

public abstract class PrefixIndexNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  ValueNode prefixIndex;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  GetLensContextNode lensContext;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed(with = "lensContext")
  MessageNode start;

  public abstract Object executePrefixIndex(Object prefixIndex, Object lensContext, Object start);

  public PrefixIndexNode(SourceSection sourceSection, ValueNode prefixIndex) {
    super(sourceSection);
    this.prefixIndex = prefixIndex;
    lensContext = GetLensContextNode.create(sourceSection);
    start = MessageNode.create("start", null, INTERNAL_CODE_SOURCE);
  }

  @Specialization
  long doIndexLong(long prefixIndex, Object lensContext, long start) {
    return start + prefixIndex - 1L;
  }

  public static PrefixIndexNode create(SourceSection sourceSection, ValueNode prefixIndexNode) {
    return PrefixIndexNodeGen.create(sourceSection, prefixIndexNode);
  }
}
