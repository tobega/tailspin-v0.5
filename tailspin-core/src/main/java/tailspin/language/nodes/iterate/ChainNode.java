package tailspin.language.nodes.iterate;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import tailspin.language.PreconditionFailed;
import tailspin.language.RejectSinkReached;
import tailspin.language.TypeError;
import tailspin.language.nodes.TransformNode;

public abstract class ChainNode extends TransformNode {
  @Children
  private final TransformNode[] stages;

  ChainNode(int valuesSlot, int cvSlot, int resultSlot, List<TransformNode> stages,
      SourceSection sourceSection) {
    super(sourceSection);
    this.stages = stages.toArray(new TransformNode[0]);
    this.stages[0].setResultSlot(valuesSlot);
    for (int i = 1; i < this.stages.length; i++) {
      this.stages[i] = ChainStageNode.create(valuesSlot, cvSlot, this.stages[i]);
      this.stages[i].setResultSlot(resultSlot);
      int temp = valuesSlot;
      valuesSlot = resultSlot;
      resultSlot = temp;
    }
    super.setResultSlot(valuesSlot);
  }

  @Override
  public void setResultSlot(int resultSlot) {
    super.setResultSlot(resultSlot);
    stages[stages.length - 1].setResultSlot(resultSlot);
  }

  public static ChainNode create(int chainValuesSlot, int chainCvSlot, int chainResultSlot, List<TransformNode> stages) {
    return ChainNodeGen.create(chainValuesSlot, chainCvSlot, chainResultSlot, stages, INTERNAL_CODE_SOURCE);
  }

  @Specialization
  @ExplodeLoop
  public void doChain(VirtualFrame frame) {
    try {
      for (int i = 0; i < stages.length; i++) {
        stages[i].executeTransform(frame);
      }
    } catch (PreconditionFailed pf) {
      throw new TypeError(pf.getMessage(), pf.getLocation());
    } catch (RejectSinkReached rsr) {
      throw new PreconditionFailed(rsr.getMessage(), rsr.getLocation());
    }
  }
}
