package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.DecreasingIntegerRangeIterator;
import tailspin.language.runtime.IncreasingIntegerRangeIterator;

@NodeChild(value = "start", type = ValueNode.class)
@NodeChild(value = "end", type = ValueNode.class)
@NodeChild(value = "increment", type = ValueNode.class)
public abstract class RangeLiteral extends ValueNode {

  public static RangeLiteral create(ValueNode start, ValueNode end, ValueNode increment) {
    return RangeLiteralNodeGen.create(start, end, increment);
  }

  @Specialization(guards = "increment > 0")
  public Object doIncreasingLong(long start, long end, long increment) {
    return new IncreasingIntegerRangeIterator(start, end, increment);
  }

  @Specialization(guards = "increment < 0")
  public Object doDecreasingLong(long start, long end, long increment) {
    return new DecreasingIntegerRangeIterator(start, end, -increment);
  }

  @Fallback
  public Object doObject(Object start, Object end, Object increment) {
    throw new UnsupportedOperationException(String.format("No range iterator for %s %s %s", start.getClass().getName(), end.getClass().getName(), increment.getClass().getName()));
  }

}
