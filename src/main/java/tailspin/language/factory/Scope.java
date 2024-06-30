package tailspin.language.factory;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.runtime.Templates;

public class Scope {
  Builder rootFdb = Templates.createBasicFdb();
  Builder scopeFdb = Templates.createScopeFdb();

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

  public void checkMatchersCalled() {
    if (matcherTemplates == null && block != null) throw new IllegalStateException("Matchers defined but never called");
  }

  public void makeMatcherCallTarget(MatchBlockNode matchBlockNode) {
    getOrCreateMatcherTemplates();
    matcherTemplates.setCallTarget(TemplatesRootNode.create(rootFdb.build(), block == null ? scopeFdb.build() : null, matchBlockNode));
  }

  public Templates getTemplates() {
    if (block == null) return matcherTemplates;
    Templates templates = new Templates();
    templates.setCallTarget(TemplatesRootNode.create(blockRootFdb.build(), scopeFdb.build(), block));
    return templates;
  }

  public CallTarget createProgramRootNode(TailspinLanguage language, StatementNode programBody) {
    return ProgramRootNode.create(language, rootFdb.build(), scopeFdb.build(), programBody);
  }
}
