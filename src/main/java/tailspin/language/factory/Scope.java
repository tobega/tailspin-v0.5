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

  private final String scopeId;
  private final Scope parent;

  public Scope(String scopeId, Scope parent) {
    this.scopeId = scopeId;
    this.parent = parent;
  }

  public ChainSlots newChainSlots() {
    return ChainSlots.on(rootFdb);
  }

  public int newTempSlot() {
    return  rootFdb.addSlot(FrameSlotKind.Illegal, null, null);
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

  public Reference defineValue(String identifier) {
    Slot defined = new Slot();
    definitions.put(identifier, defined);
    return defined.atLevel(-1);
  }

  public Object getSource(String identifier, int level) {
    if (level == -1 && (block != null || matcherTemplates == null)) {
      // TODO: We should be able to have local matcher values
      getOrCreateMatcherTemplates().setNeedsScope();
      level = 0;
    }
    Object defined = definitions.get(identifier);
    switch (defined) {
      case null -> {
        needsScope = true;
        if (level == -1)
          level = 0;
        return parent.getSource(identifier, level + 1);
      }
      case Slot slot -> {
        return slot.atLevel(level);
      }
      case Templates templates when templates.getType().equals("source") -> {
        return templates;
      }
      default -> throw new IllegalStateException("May not use " + identifier + " as a source");
    }
  }

  public int accessState(String scopeId) {
    if (block != null) {
      matcherTemplates.setNeedsScope();
    }
    if (scopeId == null || scopeId.equals(this.scopeId)) {
      return 0;
    } else {
      needsScope = true;
      return 1 + parent.accessState(scopeId);
    }
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
