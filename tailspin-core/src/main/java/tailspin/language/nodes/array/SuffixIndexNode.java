package tailspin.language.nodes.array;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.processor.MessageNode;

public abstract class SuffixIndexNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  ValueNode suffixIndex;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  GetLensContextNode lensContext;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed(with = "lensContext")
  MessageNode start;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed(with = "lensContext")
  MessageNode length;

  public abstract Object executePrefixIndex(Object prefixIndex, Object lensContext, Object start, long length);

  public SuffixIndexNode(SourceSection sourceSection, ValueNode suffixIndex) {
    super(sourceSection);
    this.suffixIndex = suffixIndex;
    lensContext = GetLensContextNode.create(sourceSection);
    start = MessageNode.create("start", null, INTERNAL_CODE_SOURCE);
    length = MessageNode.create("length", null, INTERNAL_CODE_SOURCE);
  }

  @Specialization
  long doIndexLong(long suffixIndex, Object lensContext, long start, long length) {
    return start + length - suffixIndex;
  }

  public static SuffixIndexNode create(SourceSection sourceSection, ValueNode suffixIndexNode) {
    return SuffixIndexNodeGen.create(sourceSection, suffixIndexNode);
  }
}
