package tailspin.language.factory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.HashMap;
import java.util.Map;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.Templates;

public class Scope {
  Builder rootFdb = Templates.createBasicFdb();
  Builder scopeFdb = Templates.createScopeFdb();

  Map<String, Object> definitions = new HashMap<>();

  public ChainSlots newChainSlots() {
    return ChainSlots.on(rootFdb);
  }

  StatementNode block;
  Builder blockRootFdb;
  public void setBlock(StatementNode blockNode) {
    block = blockNode;
    blockRootFdb = rootFdb;
    rootFdb = Templates.createBasicFdb();
  }

  Templates matcherTemplates;
  public Templates getOrCreateMatcherTemplates() {
    if (matcherTemplates == null) matcherTemplates = new Templates();
    return matcherTemplates;
  }

  public void verifyMatcherIsCalled() {
    if (matcherTemplates == null && block != null) throw new IllegalStateException("Matchers defined but never called");
  }

  public void makeMatcherCallTarget(MatchBlockNode matchBlockNode) {
    getOrCreateMatcherTemplates();
    matcherTemplates.setCallTarget(TemplatesRootNode.create(rootFdb.build(), block == null ? scopeFdb.build() : null, matchBlockNode));
  }

  private void assignReferences(Builder fdb) {
    definitions.values().stream().filter(Reference.class::isInstance).map(Reference.class::cast)
        .filter(r -> r.getSlot() < 0)
        .forEach(r -> {
          int slot = r.getLevel() < 0
              ? fdb.addSlot(FrameSlotKind.Illegal, null, null)
              : scopeFdb.addSlot(FrameSlotKind.Illegal, null, null);
          r.setSlot(slot);
        });
  }

  public Templates getTemplates() {
    if (block == null) return matcherTemplates;
    assignReferences(blockRootFdb);
    Templates templates = new Templates();
    templates.setCallTarget(TemplatesRootNode.create(blockRootFdb.build(), scopeFdb.build(), block));
    return templates;
  }

  public CallTarget createProgramRootNode(TailspinLanguage language, StatementNode programBody) {
    assignReferences(rootFdb);
    return ProgramRootNode.create(language, rootFdb.build(), scopeFdb.build(), programBody);
  }

  public Reference defineIdentifier(String identifier) {
    Reference defined = new Reference();
    definitions.put(identifier, defined);
    return defined;
  }

  public Reference getIdentifier(String identifier, int level) {
    if (block != null && level == -1) {
      matcherTemplates.setNeedsScope();
      level = 0;
    }
    Reference reference = (Reference) definitions.get(identifier);
    reference.setLevel(level);
    return reference;
  }

  public int accessState() {
    if (block != null) {
      matcherTemplates.setNeedsScope();
    }
    return 0;
  }
}
