// CheckStyle: start generated
package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.InlineSupport.InlineTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.nodes.value.WriteContextValueNodeGen.WriteLocalValueNodeGen;
import tailspin.language.runtime.Templates;

/**
 * Debug Info: <pre>
 *   Specialization {@link DefineTemplatesNode#doDefine}
 *     Activation probability: 1,00000
 *     With/without class size: 20/0 bytes
 * </pre>
 */
@GeneratedBy(DefineTemplatesNode.class)
@SuppressWarnings("javadoc")
public final class DefineTemplatesNodeGen extends DefineTemplatesNode {

    /**
     * Source Info: <pre>
     *   Specialization: {@link DefineTemplatesNode#doDefine}
     *   Parameter: {@link WriteLocalValueNode} writeValue
     *   Inline method: {@link WriteLocalValueNodeGen#inline}</pre>
     */
    private static final WriteLocalValueNode INLINED_WRITE_VALUE_ = WriteLocalValueNodeGen.inline(InlineTarget.create(WriteLocalValueNode.class));

    private DefineTemplatesNodeGen(Templates templates, int slot, SourceSection sourceSection) {
        super(templates, slot, sourceSection);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        doDefine(frameValue, INLINED_WRITE_VALUE_);
        return;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @NeverDefault
    public static DefineTemplatesNode create(Templates templates, int slot, SourceSection sourceSection) {
        return new DefineTemplatesNodeGen(templates, slot, sourceSection);
    }

}
