package tailspin.language.factory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.Reference.Slot;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.VocabularyType;

public class Scope {
  Builder rootFdb = Templates.createBasicFdb();
  Builder scopeFdb = Templates.createScopeFdb();

  Map<String, Object> definitions = new HashMap<>();
  Map<String, VocabularyType> vocabularyTypes = new HashMap<>();

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
    return  rootFdb.addSlot(FrameSlotKind.Static, null, null);
  }

  public int newResultSlot() {
    return  rootFdb.addSlot(FrameSlotKind.Static, null, null);
  }

  StatementNode block;
  public void setBlock(StatementNode blockNode) {
    block = blockNode;
  }

  boolean isInMatcher = false;
  Templates matcherTemplates;
  public Templates getOrCreateMatcherTemplates() {
    if (matcherTemplates == null) matcherTemplates = new Templates();
    return matcherTemplates;
  }

  public void setMatcherTemplates(Templates matcherTemplates) {
    isInMatcher = true;
    this.matcherTemplates = matcherTemplates;
  }

  public Templates getCalledMatcherTemplates() {
    if (matcherTemplates == null && block != null) throw new IllegalStateException("Matchers defined but never called");
    return matcherTemplates;
  }

  private void assignReferences() {
    definitions.values().stream().filter(Slot.class::isInstance).map(Slot.class::cast)
        .filter(Slot::isUndefined)
        .forEach(this::assignSlot);
  }

  private void assignSlot(Slot s) {
      int slot = s.isExported()
          ? scopeFdb.addSlot(FrameSlotKind.Static, null, null)
          : rootFdb.addSlot(FrameSlotKind.Static, null, null);
      s.setSlot(slot);
  }

  boolean needsScope;
  public Templates getTemplates() {
    assignReferences();
    Templates templates = isInMatcher? matcherTemplates : new Templates();
    if (needsScope) templates.setNeedsScope();
    templates.setCallTarget(TemplatesRootNode.create(rootFdb.build(), scopeFdb.build(), block));
    return templates;
  }

  public CallTarget createProgramRootNode(TailspinLanguage language, StatementNode programBody) {
    assignReferences();
    return ProgramRootNode.create(language, rootFdb.build(), scopeFdb.build(), programBody);
  }

  public Reference defineValue(String identifier) {
    Slot defined = new Slot();
    definitions.put(identifier, defined);
    if (isInMatcher) {
      markTemporary(identifier);
    }
    return defined.atLevel(-1);
  }

  private final List<Set<String>> temporaryIdentifiers = new ArrayList<>();
  public void markTemporary(String identifier) {
    temporaryIdentifiers.getLast().add(identifier);
  }

  public void deleteTemporaryValues() {
    Set<String> toDelete = temporaryIdentifiers.removeLast();
    for (String identifier : toDelete) {
      assignSlot((Slot) definitions.get(identifier));
      definitions.remove(identifier);
    }
  }

  public void pushTemporaryVariables() {
    temporaryIdentifiers.addLast(new HashSet<>());
  }

  public Object getSource(String identifier, int level) {
    Object defined = definitions.get(identifier);
    switch (defined) {
      case null -> {
        needsScope = true;
        if (level == -1)
          level = 0;
        if (parent == null) throw new IllegalStateException("Cannot find " + identifier);
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
    if (!isInMatcher && (scopeId == null || scopeId.equals(this.scopeId))) {
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
    else if (parent == null) throw new IllegalStateException("Cannot find templates " + name);
    else {
      needsScope = true;
      return parent.findTemplates(name);
    }
  }

  public VocabularyType getVocabularyType(String key) {
    if (vocabularyTypes.containsKey(key)) {
      return vocabularyTypes.get(key);
    } else if (parent != null) {
      return parent.getVocabularyType(key);
    }
    // new types only get default-created at the top level
    VocabularyType type = new VocabularyType(key);
    vocabularyTypes.put(key, type);
    return type;
  }

  private boolean isUntypedRegion = false;
  public void setUntypedArithmetic(boolean isUntypedRegion) {
    this.isUntypedRegion = isUntypedRegion;
  }

  public boolean isUntypedRegion() {
    return isUntypedRegion;
  }
}
