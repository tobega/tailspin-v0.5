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
import tailspin.language.runtime.Reference.Slot;
import tailspin.language.runtime.Templates;

public class Scope {
  Builder rootFdb = Templates.createBasicFdb();
  Builder scopeFdb = Templates.createScopeFdb();

  Map<String, Object> definitions = new HashMap<>();

  private final Scope parent;

  public Scope(Scope parent) {
    this.parent = parent;
  }

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
    definitions.values().stream().filter(Slot.class::isInstance).map(Slot.class::cast)
        .filter(Slot::isUndefined)
        .forEach(s -> {
          int slot = s.isExported()
              ? scopeFdb.addSlot(FrameSlotKind.Illegal, null, null)
              : fdb.addSlot(FrameSlotKind.Illegal, null, null);
          s.setSlot(slot);
        });
  }

  boolean needsScope;
  public Templates getTemplates() {
    if (block == null) return matcherTemplates;
    assignReferences(blockRootFdb);
    Templates templates = new Templates();
    if (needsScope) templates.setNeedsScope();
    templates.setCallTarget(TemplatesRootNode.create(blockRootFdb.build(), scopeFdb.build(), block));
    return templates;
  }

  public CallTarget createProgramRootNode(TailspinLanguage language, StatementNode programBody) {
    assignReferences(rootFdb);
    return ProgramRootNode.create(language, rootFdb.build(), scopeFdb.build(), programBody);
  }

  public Reference defineIdentifier(String identifier) {
    Slot defined = new Slot();
    definitions.put(identifier, defined);
    return defined.atLevel(-1);
  }

  public Reference getIdentifier(String identifier, int level) {
    if (block != null && level == -1) {
      // TODO: We should be able to have local matcher values
      matcherTemplates.setNeedsScope();
      level = 0;
    }
    Slot slot = (Slot) definitions.get(identifier);
    if (slot == null) {
      needsScope = true;
      if (level == -1) level = 0;
      return parent.getIdentifier(identifier, level + 1);
    }
    return slot.atLevel(level);
  }

  public int accessState() {
    if (block != null) {
      matcherTemplates.setNeedsScope();
    }
    return 0;
  }

  public int createBuildSlot() {
    return rootFdb.addSlot(FrameSlotKind.Static, null, null);
  }

  public void registerTemplates(String name, Templates templates) {
    if (definitions.put(name, templates) != null)
      throw new IllegalStateException("Attempt to define " + name + " more than once");
  }

  public Templates findTemplates(String name) {
    if (definitions.containsKey(name)) return (Templates) definitions.get(name);
    else return parent.findTemplates(name);
  }
}
