package tailspin.language.nodes.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.runtime.Structure;

public class StructureTest {
  public final Shape rootShape = Shape.newBuilder().build();
  @Test
  void create() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    ValueNode structureNode = StructureLiteral.create(rootShape, List.of("foo", "bar"), List.of(IntegerLiteral.create(3L), IntegerLiteral.create(5L)));
    Structure created = (Structure) TestUtil.evaluate(structureNode, fdb.build(), List.of());
    assertEquals("{ bar: 5, foo: 3 }", created.toDisplayString(false, DynamicObjectLibrary.getUncached()));
  }
}
