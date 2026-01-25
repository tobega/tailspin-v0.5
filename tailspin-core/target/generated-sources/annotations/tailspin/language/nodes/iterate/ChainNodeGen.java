// CheckStyle: start generated
package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import tailspin.language.nodes.TransformNode;

/**
 * Debug Info: <pre>
 *   Specialization {@link ChainNode#doChain}
 *     Activation probability: 1,00000
 *     With/without class size: 16/0 bytes
 * </pre>
 */
@GeneratedBy(ChainNode.class)
@SuppressWarnings("javadoc")
public final class ChainNodeGen extends ChainNode {

    private ChainNodeGen(int valuesSlot, int cvSlot, int resultSlot, List<TransformNode> stages, SourceSection sourceSection) {
        super(valuesSlot, cvSlot, resultSlot, stages, sourceSection);
    }

    @Override
    public void executeTransform(VirtualFrame frameValue) {
        doChain(frameValue);
        return;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static ChainNode create(int valuesSlot, int cvSlot, int resultSlot, List<TransformNode> stages, SourceSection sourceSection) {
        return new ChainNodeGen(valuesSlot, cvSlot, resultSlot, stages, sourceSection);
    }

}
