package tailspin.language.factory;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayMutateNode;
import tailspin.language.nodes.array.ArrayRangeReadNode;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.iterate.StatementTransformNode;
import tailspin.language.nodes.iterate.StreamNode;
import tailspin.language.nodes.matchers.AllOfNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.AnyOfNode;
import tailspin.language.nodes.matchers.ArrayContentMatcherNode;
import tailspin.language.nodes.matchers.ArrayTypeMatcherNode;
import tailspin.language.nodes.matchers.CallDefinedTypeMatcherNode;
import tailspin.language.nodes.matchers.ConditionNode;
import tailspin.language.nodes.matchers.EqualityMatcherNode;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.InvertNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.matchers.MeasureTypeMatcher;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;
import tailspin.language.nodes.matchers.RegexMatcherNode;
import tailspin.language.nodes.matchers.StringTypeMatcherNode;
import tailspin.language.nodes.matchers.StructureKeyMatcherNode;
import tailspin.language.nodes.matchers.StructureTypeMatcherNode;
import tailspin.language.nodes.matchers.TagMatcherNode;
import tailspin.language.nodes.matchers.TypeCheckedMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.BigIntegerLiteral;
import tailspin.language.nodes.numeric.DivideNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.MathModNode;
import tailspin.language.nodes.numeric.MeasureLiteral;
import tailspin.language.nodes.numeric.MultiplyNode;
import tailspin.language.nodes.numeric.NegateNode;
import tailspin.language.nodes.numeric.SciNumLiteral;
import tailspin.language.nodes.numeric.SmallSciNumLiteral;
import tailspin.language.nodes.numeric.SquareRootNode;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.numeric.TruncateDivideNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.state.AppendStateNode;
import tailspin.language.nodes.state.ReadStateNode;
import tailspin.language.nodes.state.ReadStateValueNode;
import tailspin.language.nodes.state.WriteStateValueNode;
import tailspin.language.nodes.string.CodepointNode;
import tailspin.language.nodes.string.StringLiteral;
import tailspin.language.nodes.string.StringPart;
import tailspin.language.nodes.structure.StructureLiteral;
import tailspin.language.nodes.structure.StructureReadNode;
import tailspin.language.nodes.structure.WriteKeyValueNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.DefineTypeConstraintNode;
import tailspin.language.nodes.transform.DoNothingNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.FilterNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.PreconditionNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.SinkNode;
import tailspin.language.nodes.transform.TryNode;
import tailspin.language.nodes.value.ConsolidateLensResultNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.RejectValue;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.nodes.value.TagNode;
import tailspin.language.nodes.value.TransformLensNode;
import tailspin.language.nodes.value.TransformResultNode;
import tailspin.language.nodes.value.VoidValue;
import tailspin.language.nodes.value.WriteContextValueNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.TailspinStrings;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.TemplatesInstance;
import tailspin.language.runtime.VocabularyType;

public class NodeFactory {

  private final TailspinLanguage language;
  private final Source sourceCode;

  List<Integer> cvSlot = new ArrayList<>();
  { cvSlot.add(CV_SLOT);}
  int currentValueSlot() {
    return cvSlot.getLast();
  }

  void pushCvSlot(int slot) {
    cvSlot.addLast(slot);
  }

  void popCvSlot() {
    cvSlot.removeLast();
  }

  List<Scope> scopes = new ArrayList<>();
  private void enterNewScope(String scopeId, SourceSection sourceSection) {
    scopes.addLast(new Scope(scopeId, scopes.isEmpty() ? null : scopes.getLast(), sourceSection));
    pushCvSlot(CV_SLOT);
  }

  private Scope exitScope() {
    popCvSlot();
    return scopes.removeLast();
  }

  private Scope currentScope() {
    return scopes.getLast();
  }

  public NodeFactory(TailspinLanguage tailspinLanguage, Source sourceCode) {
    this.language = tailspinLanguage;
    this.sourceCode = sourceCode;
  }

  public CallTarget createCallTarget(ParseNode program) {
    enterNewScope(null, sourceCode.createSection(0, sourceCode.getLength()));
    StatementNode programBody = visitBlock(program.content());
    Scope scope = exitScope();
    return scope.createProgramRootNode(language, programBody);
  }

  private StatementNode visitBlock(Object block) {
    return switch (block) {
      case ParseNode(String name, Object content, int start, int end) when name.equals("block") -> visitBlock(content);
      case ParseNode(String name, Object statements, int start, int end) when name.equals("statements") -> visitBlock(statements);
      case ParseNode(String name, ParseNode statement, int start, int end) when name.equals("statement") -> visitStatement(statement);
      case ParseNode(String name, String ignored, int start, int end) when name.equals("do-nothing") -> DoNothingNode.create(
          sourceCode.createSection(start, end - start));
      case List<?> statements -> {
        int start = ((ParseNode) statements.getFirst()).start();
        int end = ((ParseNode) statements.getFirst()).end();
        yield BlockNode.create(statements.stream()
            .map(s -> visitStatement((ParseNode) s)).toList(), sourceCode.createSection(start, end - start));
      }
      default -> throw new IllegalStateException("Unexpected value: " + block);
    };
  }

  private StatementNode visitStatement(ParseNode statement) {
    return switch (statement) {
      case ParseNode(String name, ParseNode stmt, int start, int end) when name.equals("statement") -> visitStatement(stmt);
      case ParseNode(String name, Object stmt, int start, int end) when name.equals("terminated-chain") -> visitTerminatedChain(stmt);
      case ParseNode(String name, List<?> def, int start, int end) when name.equals("definition") -> visitDefinition(def);
      case ParseNode(String name, Object setExpr, int start, int end) when name.equals("set-state") -> visitSetState(setExpr, sourceCode.createSection(start, end - start));
      case ParseNode(String type, List<?> content, int start, int end) when type.equals("templates") -> {
        String name = (String) content.getLast();
        boolean isAuxiliary = false;
        if (content.getFirst() instanceof ParseNode p && p.name().equals("auxiliary")) {
          isAuxiliary = true;
          content = content.subList(1, content.size());
        }
        String templateType = (String) content.getFirst();
        enterNewScope(name, sourceCode.createSection(start, end - start));
        int nextPart = content.size() - 2;
        visitTemplatesBody((ParseNode) content.get(nextPart));
        nextPart--;
        if (nextPart > 0 && content.get(nextPart) instanceof ParseNode contract && contract.name().equals("precondition")) {
          MatcherNode precondition = visitMatcher(((ParseNode) contract.content()).content());
          currentScope().setPrecondition(PreconditionNode.create(precondition,
              sourceCode.createSection(contract.start(), contract.end() - contract.start())));
        }
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setType(templateType);
        templates.setDefinitionLevel(scopes.size());
        if (isAuxiliary) templates.setAuxiliary();
        currentScope().registerTemplates(name, templates);
        yield DoNothingNode.create(sourceCode.createSection(start, end - start));
      }
      case ParseNode(String stmtType, List<?> def, int start, int end) when stmtType.equals("type-def") -> {
        VocabularyType type = currentScope().getVocabularyType(((ParseNode) def.getFirst()).content().toString());
        enterNewScope(null, sourceCode.createSection(start, end - start));
        Templates matchTemplates = currentScope().getOrCreateMatcherTemplates();
        StatementNode passThrough = EmitNode.create(SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size() - 1, matchTemplates,
                sourceCode.createSection(start, end - start)),
            sourceCode.createSection(start, end - start));
        currentScope().setBlock(passThrough);
        enterNewScope(null, INTERNAL_CODE_SOURCE);
        currentScope().setMatcherTemplates(matchTemplates);
        MatcherNode constraint = visitAlternativeMembranes(null, true, def.subList(1, def.size()));
        currentScope().setBlock(MatchBlockNode.create(List.of(
            MatchTemplateNode.create(constraint, EmitNode.create(asTransformNode(ReadContextValueNode.create(-1, CV_SLOT)),
                    INTERNAL_CODE_SOURCE),
                INTERNAL_CODE_SOURCE)
        ), INTERNAL_CODE_SOURCE));
        currentScope().getTemplates();
        exitScope();
        Scope definedScope = exitScope();
        Templates templates = definedScope.getTemplates();
        if (templates.needsScope() || templates.needsState()) {
          definedScope.getOrCreateMatcherTemplates().setDefinitionLevel(scopes.size());
          TemplatesInstance definedTemplates = new TemplatesInstance(null, templates.getCallTarget());
          type.setConstraint(CallDefinedTypeMatcherNode.create(definedTemplates, INTERNAL_CODE_SOURCE));
          yield DefineTypeConstraintNode.create(definedTemplates, sourceCode.createSection(start, end - start));
        }
        type.setConstraint(constraint);
        yield DoNothingNode.create(INTERNAL_CODE_SOURCE);
      }
      default -> throw new IllegalStateException("Unexpected value: " + statement);
    };
  }

  private StatementNode visitTerminatedChain(Object stmt) {
    return switch(stmt) {
      case ParseNode valueChain -> EmitNode.create(asTransformNode(visitValueChain(valueChain)),
          sourceCode.createSection(valueChain.start(), valueChain.end() - valueChain.start()));
      case List<?> parts when parts.getLast() instanceof ParseNode(String name, Object sinkType, int start, int end) && name.equals("sink") -> {
        ParseNode valueChain = (ParseNode) parts.getFirst();
        ArrayList<Object> transforms = new ArrayList<>();
        if (valueChain.content() instanceof List<?> l) {
          transforms.addAll(l);
        } else {
          transforms.add(valueChain.content());
        }
        if (sinkType.equals("VOID")) {
          transforms.addLast("VOID");
        } else if (sinkType.equals("REJECT")) {
          transforms.addLast("REJECT");
        } else if (sinkType.equals("#")) {
          transforms.addLast(new ParseNode("transform", "#", start, end));
        } else if (sinkType instanceof ParseNode(String call, ParseNode id, int cs, int ce) && call.equals("templates-call")) {
          Templates sink = currentScope().findTemplates(id.content().toString());
          if (!sink.getType().equals("sink")) throw new IllegalStateException("Can only sink to sink " + id.content());
          transforms.addLast(new ParseNode("transform", sinkType, start, end));
        } else {
          throw new IllegalStateException("Unexpected value: " + sinkType);
        }
        yield SinkNode.create(asTransformNode(visitValueChain(new ParseNode(valueChain.name(), transforms, start, end))),
            sourceCode.createSection(start, end - start));
      }
      case List<?> parts when parts.getLast() instanceof ParseNode(String name, ParseNode setState, int start, int end) && name.equals("accumulator-state") -> {
        ParseNode valueChain = (ParseNode) parts.getFirst();
        ArrayList<Object> transforms = new ArrayList<>();
        if (valueChain.content() instanceof List<?> l) {
          transforms.addAll(l);
        } else {
          transforms.add(valueChain.content());
        }
        transforms.add(setState);
        yield SinkNode.create(asTransformNode(visitValueChain(new ParseNode(valueChain.name(), transforms, start, end))),
            sourceCode.createSection(start, end - start));
      }
      default -> throw new IllegalStateException("Unexpected value: " + stmt);
    };
  }

  private StatementNode visitSetState(Object expr, SourceSection sourceSection) {
    enum Mode {SET, APPEND}
    TailspinNode value;
    String scopeId = null;
    int stateLevel;
    switch (expr) {
      case ParseNode valueChain -> {
        value = visitValueChain(valueChain);
        stateLevel = currentScope().accessState(scopeId);
      }
      case List<?> l -> {
        value = visitValueChain((ParseNode) l.getLast());
        l = l.subList(0, l.size() - 1);
        Mode mode = Mode.SET;
        if (l.getFirst().equals("..|")) {
          mode = Mode.APPEND;
          l = l.subList(1, l.size());
        }
        if (!l.isEmpty() && l.getFirst() instanceof ParseNode(String type, String identifier, int start, int end) && type.equals("ID")) {
          scopeId = identifier;
          l = l.subList(1, l.size());
        }
        stateLevel = currentScope().accessState(scopeId);
        ValueNode target = ReadStateValueNode.create(stateLevel);
        if (!l.isEmpty()) {
          if (l.getFirst() instanceof ParseNode(String type, Object lensExpr, int start, int end) && type.equals("lens-expression")) {
            if (mode == Mode.APPEND) {
              value = visitAppendState(target, lensExpr, asTransformResult(value),
                  (target1, value1) -> AppendStateNode.create(target1, value1, sourceSection));
            } else {
              value = visitWriteLensExpression(target, lensExpr, value);
            }
          } else {
            throw new IllegalStateException("Unexpected value: " + l);
          }
        } else if (mode == Mode.APPEND) {
          value = AppendStateNode.create(target, asTransformResult(value), sourceSection);
        }
      }
      default -> throw new IllegalStateException("Unexpected value: " + expr);
    }
    return WriteStateValueNode.create(stateLevel, asSingleValueNode(value));
  }

  private ValueNode visitWriteLensExpression(ValueNode target, Object lensExpression, TailspinNode value) {
    return switch (lensExpression) {
      case ParseNode(String type, Object lensDimension, int start, int end) when type.equals("lens-dimension")
          -> visitWriteLensExpression(target, lensDimension, value);
      case ParseNode(String type, ParseNode source, int start, int end) when type.equals("source")
          -> ArrayMutateNode.create(target, asSingleValueNode(visitSource(source)), asTransformResult(value),
          sourceCode.createSection(start, end - start));
      case ParseNode(String type, ParseNode id, int start, int end) when type.equals("key")
          -> WriteKeyValueNode.create(currentScope().getVocabularyType((String) id.content()), target, asTransformResult(value), sourceCode.createSection(start, end - start));
      case List<?> dimension -> {
        Reference indexVar = null;
        if (dimension.get(1) instanceof ParseNode(String type, ParseNode(String ignored, String identifier, int is, int ie), int start, int end) && type.equals("index-variable")) {
          indexVar = currentScope().defineValue(identifier);
        }
        ValueNode thisDimension = visitReadLensDimension(target, dimension.getFirst(), indexVar);
        ValueNode modifiedTarget = visitWriteLensExpression(thisDimension, ((ParseNode) dimension.getLast()).content(), value);
        yield visitWriteLensExpression(target, dimension.getFirst(), modifiedTarget);
      }
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
    };
  }

  private ValueNode visitAppendState(ValueNode target, Object lensExpression, ValueNode value, BinaryOperator<ValueNode> create) {
    return switch (lensExpression) {
      case ParseNode(String type, Object lensDimension, int start, int end) when type.equals("lens-dimension")
          -> visitAppendState(target, lensDimension, value, create);
      case List<?> dimension -> {
        Reference indexVar = null;
        if (dimension.size() > 1 && dimension.get(1) instanceof ParseNode(String type, ParseNode(String ignored, String identifier, int is, int ie), int start, int end) && type.equals("index-variable")) {
          indexVar = currentScope().defineValue(identifier);
        }
        ValueNode thisDimension = visitReadLensDimension(target, dimension.getFirst(), indexVar);
        ValueNode modifiedTarget = visitAppendState(thisDimension, ((ParseNode) dimension.getLast()).content(), value, create);
        yield visitWriteLensExpression(target, dimension.getFirst(), modifiedTarget);
      }
      default -> visitWriteLensExpression(target, lensExpression, create.apply(visitReadLensDimension(target, lensExpression, null), value));
     };
  }

  private StatementNode visitDefinition(List<?> def) {
    if (def.getFirst() instanceof ParseNode(String name, String identifier, int start, int end) && name.equals("ID")) {
      Reference defined = currentScope().defineValue(identifier);
      TailspinNode expr = visitValueChain((ParseNode) def.getLast());
      return WriteContextValueNode.create(defined, asSingleValueNode(expr));
    }
    throw new IllegalStateException("Unexpected value: " + def);
  }

  private TailspinNode visitValueChain(ParseNode valueChain) {
    if (!valueChain.name().equals("value-chain")) throw new IllegalStateException("Unexpected value: " + valueChain);
    if (valueChain.content() instanceof ParseNode(String name, List<?> bounds, int start, int end) && name.equals("range")) {
      RangeIteration r = visitRange(bounds, sourceCode.createSection(start, end - start));
      pushCvSlot(currentScope().newTempSlot());
      r.setStage(currentValueSlot(), ResultAggregatingNode.create(ReadContextValueNode.create(-1, currentValueSlot())
      ));
      r.setResultSlot(currentScope().newResultSlot());
      popCvSlot();
      return r;
    } else if (valueChain.content() instanceof ParseNode(String name, ParseNode source, int start, int end) && name.equals("source")) {
      return visitSource(source);
    } else if (valueChain.content() instanceof List<?> chain) {
      ChainSlots chainSlots = currentScope().newChainSlots();
      List<TransformNode> stages = new ArrayList<>();
      RangeIteration pendingIteration = null;
      for (Object stage : chain) {
        TailspinNode stageNode = switch (stage) {
          case ParseNode(String name, Object transform, int start, int end) when name.equals("transform") -> visitTransform(transform);
          case ParseNode(String name, List<?> bounds, int start, int end) when name.equals("range") -> visitRange(bounds,
              sourceCode.createSection(start, end - start));
          case ParseNode(String name, ParseNode transform, int start, int end) when name.equals("source") -> visitSource(transform);
          case ParseNode(String name, String ignored, int start, int end) when name.equals("stream") -> StreamNode.create(ReadContextValueNode.create(-1, currentValueSlot()),
              sourceCode.createSection(start, end - start));
          case ParseNode(String name, Object setState, int start, int end) when name.equals("set-state") -> visitSetState(setState, sourceCode.createSection(start, end - start));
          case String v when v.equals("VOID") -> VoidValue.create(sourceCode.createSection(valueChain.end() - 4, 4));
          case String v when v.equals("REJECT") -> RejectValue.create(sourceCode.createSection(valueChain.end() - 4, 4));
          default -> throw new IllegalStateException("Unexpected value: " + stage);
        };
        if (stages.isEmpty()) { // after the first
          pushCvSlot(chainSlots.cv());
        }
        if (pendingIteration != null) {
          pendingIteration.setStage(currentValueSlot(), asTransformNode(stageNode));
          popCvSlot();
          if (stageNode instanceof RangeIteration r) {
            pendingIteration = r;
            pushCvSlot(currentScope().newTempSlot());
          } else {
            pendingIteration = null;
          }
        } else if (stageNode instanceof RangeIteration r) {
          stages.add(r);
          pendingIteration = r;
          pushCvSlot(currentScope().newTempSlot());
        } else {
          stages.add(asTransformNode(stageNode));
        }
      }
      if (pendingIteration != null) {
        pendingIteration.setStage(currentValueSlot(), ResultAggregatingNode.create(ReadContextValueNode.create(-1, currentValueSlot())
        ));
        pendingIteration.setResultSlot(currentScope().newResultSlot());
        popCvSlot();
      }
      popCvSlot();
      return ChainNode.create(chainSlots.values(), chainSlots.cv(), chainSlots.result(), stages);
    }
    throw new IllegalStateException("Unexpected value " + valueChain.content());
  }

  private TransformNode asTransformNode(TailspinNode node) {
    if (node instanceof ValueNode v) {
      ResultAggregatingNode resultAggregatingNode = ResultAggregatingNode.create(v);
      resultAggregatingNode.setResultSlot(currentScope().newResultSlot());
      return resultAggregatingNode;
    }
    if (node instanceof TransformNode t)
      return t;
    if (node instanceof StatementNode s)
      return StatementTransformNode.create(s, INTERNAL_CODE_SOURCE);
    throw new IllegalStateException("Unknown source node " + node);
  }

  private ValueNode asTransformResult(TailspinNode node) {
    if (node instanceof ValueNode v)
      return v;
    if (node instanceof TransformNode t)
      return new TransformResultNode(t, INTERNAL_CODE_SOURCE);
    throw new IllegalStateException("Unknown source node " + node);
  }

  private ValueNode asSingleValueNode(TailspinNode node) {
    if (node instanceof ValueNode v)
      return v;
    if (node instanceof TransformNode t)
      return SingleValueNode.create(t);
    throw new IllegalStateException("Unknown source node " + node);
  }

  private TransformNode visitTransform(Object transform) {
    return switch(transform) {
      case ParseNode(String name, ParseNode source, int start, int end) when name.equals("source") -> asTransformNode(visitSource(source));
      case ParseNode(String name, List<?> bounds, int start, int end) when name.equals("range") -> asTransformNode(visitRange(bounds,
          sourceCode.createSection(start, end - start)));
      case ParseNode(String name, Object implementation, int start, int end) when name.equals("inline-templates-call") -> {
        List<?> content = implementation instanceof List<?> objects ? objects : List.of(implementation);
        boolean isTry = false;
        if (content.getFirst() instanceof ParseNode p && p.name().equals("try")) {
          isTry = true;
          content = content.subList(1, content.size());
        }
        boolean isAuxiliary = false;
        if (content.getFirst() instanceof ParseNode p && p.name().equals("auxiliary")) {
          isAuxiliary = true;
          content = content.subList(1, content.size());
        }
        enterNewScope(null, sourceCode.createSection(start, end - start));
        int nextPart = content.size() - 1;
        visitTemplatesBody((ParseNode) content.get(nextPart));
        nextPart--;
        if (nextPart >= 0 && content.get(nextPart) instanceof ParseNode contract && contract.name().equals("precondition")) {
          MatcherNode precondition = visitMatcher(((ParseNode) contract.content()).content());
          currentScope().setPrecondition(PreconditionNode.create(precondition,
              sourceCode.createSection(contract.start(), contract.end() - contract.start())));
        }
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setDefinitionLevel(scopes.size());
        if (isAuxiliary) templates.setAuxiliary();
        TransformNode tn = SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(), templates,
            INTERNAL_CODE_SOURCE);
        if (isTry) {
          SourceSection tss = tn.getEncapsulatingSourceSection();
          yield TryNode.create(tn, tss);
        } else {
          yield tn;
        }
      }
      case String crosshatch when crosshatch.equals("#") -> {
        Templates matchers = currentScope().getOrCreateMatcherTemplates();
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(), matchers,
            INTERNAL_CODE_SOURCE);
      }
      case ParseNode(String type, Object call, int start, int end) when type.equals("templates-call") -> {
        boolean isTry = false;
        if (call instanceof List<?> l && l.size() == 2 && l.getFirst() instanceof ParseNode p && p.name().equals("try")) {
          isTry = true;
          call = l.getLast();
        }
        TransformNode tn = SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(),
            currentScope().findTemplates((String) ((ParseNode) call).content()), sourceCode.createSection(start, end - start));
        if (isTry) {
          SourceSection tss = tn.getEncapsulatingSourceSection();
          yield TryNode.create(tn, tss);
        } else {
          yield tn;
        }
      }
      case ParseNode(String type, ParseNode matcher, int start, int end) when type.equals("filter")
          -> FilterNode.create(ReadContextValueNode.create(-1, currentValueSlot()), visitMatcher(matcher.content()), sourceCode.createSection(start, end - start));
      default -> throw new IllegalStateException("Unexpected value: " + transform);
    };
  }

  private void visitTemplatesBody(ParseNode body) {
    switch (body) {
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object block, int bs, int be), int start, int end)
          when name.equals("with-block"):
        Object matchBlock = null;
        if (block instanceof List<?> list
            && list.size() == 2
            && list.getLast() instanceof ParseNode(String type, Object m, int ms, int me)
            && type.equals("matchers")) {
          matchBlock = m;
          block = list.getFirst();
        }
        StatementNode blockNode = visitBlock(block);
        currentScope().setBlock(blockNode);
        if (matchBlock != null) {
          visitMatchers(matchBlock);
        }
      break;
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object matchers, int ms, int me), int start, int end)
          when name.equals("matchers"):
        Templates matchTemplates = currentScope().getOrCreateMatcherTemplates();
        StatementNode passThrough = EmitNode.create(SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(), matchTemplates,
                INTERNAL_CODE_SOURCE),
            INTERNAL_CODE_SOURCE);
        currentScope().setBlock(passThrough);
        visitMatchers(matchers);
      break;
      default: throw new IllegalStateException("Unexpected value: " + body);
    }
  }

  private void visitMatchers(Object matchers) {
    Templates matcherTemplates = currentScope().getCalledMatcherTemplates();
    matcherTemplates.setDefinitionLevel(scopes.size());
    enterNewScope(null, INTERNAL_CODE_SOURCE);
    currentScope().setMatcherTemplates(matcherTemplates);
    MatchBlockNode matchBlockNode = switch (matchers) {
      case ParseNode(String name, List<?> matchTemplate, int start, int end) when name.equals("match-template") -> MatchBlockNode.create(List.of(visitMatchTemplate(matchTemplate)),
          sourceCode.createSection(start, end - start));
      case List<?> matchTemplates -> MatchBlockNode.create(matchTemplates.stream()
          .map(mt -> {
            if (mt instanceof ParseNode(String name, List<?> matchTemplate, int start, int end) && name.equals("match-template")) return visitMatchTemplate(matchTemplate);
            else throw new IllegalStateException("Expected match-template got " + mt);
          }).toList(), INTERNAL_CODE_SOURCE);
      default -> throw new IllegalStateException("Unexpected value: " + matchers);
    };
    currentScope().setBlock(matchBlockNode);
    currentScope().getTemplates();
    exitScope();
  }

  private MatchTemplateNode visitMatchTemplate(List<?> matchTemplate) {
    currentScope().pushTemporaryVariables();
    MatcherNode matcherNode = switch (matchTemplate.getFirst()) {
      case ParseNode(String name, @SuppressWarnings("unused")Object c, int start, int end)
          when name.equals("otherwise") -> AlwaysTrueMatcherNode.create(sourceCode.createSection(start, end - start));
      case ParseNode(String name, ParseNode matcher, int start, int end)
          when name.equals("when-do") -> visitMatcher(matcher.content());
      default -> throw new IllegalStateException("Unexpected value: " + matchTemplate.getFirst());
    };
    if (matchTemplate.size() != 2) throw new IllegalStateException("Unexpected list size in match template");
    StatementNode block = visitBlock(matchTemplate.getLast());
    currentScope().deleteTemporaryValues();
    return MatchTemplateNode.create(matcherNode, block, INTERNAL_CODE_SOURCE);
  }

  private MatcherNode visitMatcher(Object matcherContent) {
    if (matcherContent instanceof List<?> membranes
        && membranes.getFirst() instanceof ParseNode(String tb, Object types, int start, int end)
        && tb.equals("type-bound")) {
      MatcherNode typeBound = visitAlternativeMembranes(null, true, types);
      return TypeCheckedMatcherNode.create(typeBound, visitAlternativeMembranes(null, true, membranes.subList(1, membranes.size())),
          sourceCode.createSection(start, end - start));
    } else {
      return visitAlternativeMembranes(null, false, matcherContent);
    }
  }

  private MatcherNode visitAlternativeMembranes(VocabularyType contextType, boolean isTypeChecked, Object membranes) {
    List<MatcherNode> alternatives = new ArrayList<>();
    List<?> membraneSpecs;
    if (membranes instanceof List<?> l) {
      membraneSpecs = l;
    } else {
      membraneSpecs = List.of(membranes);
    }
    if (membraneSpecs.getFirst().equals("~")) {
      return InvertNode.create(visitAlternativeMembranes(contextType, isTypeChecked,
          membraneSpecs.subList(1, membraneSpecs.size())), INTERNAL_CODE_SOURCE);
    }
    for (Object membraneSpec : membraneSpecs) {
      if (membraneSpec instanceof ParseNode(String m, Object conditions, int start, int end) && m.equals("membrane")) {
        alternatives.add(visitMembrane(contextType, isTypeChecked, conditions));
      } else {
        throw new IllegalStateException("Unknown membrane " + membraneSpec);
      }
    }
    if (alternatives.size() == 1) {
      return alternatives.getFirst();
    }
    return AnyOfNode.create(alternatives, INTERNAL_CODE_SOURCE);
  }

  private MatcherNode visitMembrane(VocabularyType contextType, boolean isTypeChecked, Object conditions) {
    Integer conditionSlot = null;
    List<MatcherNode> conjunction = new ArrayList<>();
    List<?> conditionSpecs;
    if (conditions instanceof List<?> l) {
      conditionSpecs = l;
    } else {
      conditionSpecs = List.of(conditions);
    }
    for (Object conditionSpec : conditionSpecs) {
      switch (conditionSpec) {
        case ParseNode(String type, ParseNode typeMatch, int start, int end) when type.equals("type-match")
            -> conjunction.addAll(visitTypeMatch(contextType, isTypeChecked, typeMatch));
        case ParseNode(String type, ParseNode(String name, ParseNode value, int ns, int ne), int start, int end) when type.equals("literal-match") && name.equals("source")
            -> {
          ValueNode valueNode = asSingleValueNode(visitSource(value));
          if (contextType != null) {
            valueNode = TagNode.create(contextType, valueNode, INTERNAL_CODE_SOURCE);
          }
          conjunction.add(EqualityMatcherNode.create(isTypeChecked, valueNode, sourceCode.createSection(start, end - start)));
        }
        case ParseNode(String type, List<?> condition, int start, int end) when type.equals("condition") -> {
          if (conditionSlot == null) {
            conditionSlot = currentScope().newTempSlot();
            pushCvSlot(conditionSlot);
          }
          conjunction.add(visitCondition(conditionSlot, condition));
        }
        default -> throw new IllegalStateException("Unexpected value: " + conditionSpec);
      }
    }
    if (conditionSlot != null) popCvSlot();
    if (conjunction.size() == 1) {
      return conjunction.getFirst();
    }
    return AllOfNode.create(conjunction, INTERNAL_CODE_SOURCE);
  }

  private MatcherNode visitCondition(Integer conditionSlot, List<?> condition) {
    if (condition.size() != 2) throw new IllegalStateException("Unexpected condition " + condition);
    ValueNode toMatch = asSingleValueNode(visitValueChain((ParseNode) condition.getFirst()));
    ParseNode matcher = (ParseNode) condition.getLast();
    return ConditionNode.create(conditionSlot, toMatch, visitMatcher(matcher.content()), sourceCode.createSection(
        matcher.start(), matcher.end() - matcher.start()));
  }

  private List<MatcherNode> visitTypeMatch(VocabularyType contextType, boolean isTypeChecked, ParseNode typeMatch) {
    SourceSection sourceSection = sourceCode.createSection(
        typeMatch.start(), typeMatch.end() - typeMatch.start());
    return switch (typeMatch.name()) {
      case "range-match" -> {
        if (typeMatch.content() instanceof List<?> bounds)
          yield visitRangeMatch(contextType, isTypeChecked, bounds);
        else yield List.of(NumericTypeMatcherNode.create());
      }
      case "array-match" -> {
        List<MatcherNode> conditionNodes = new ArrayList<>();
        conditionNodes.addLast(ArrayTypeMatcherNode.create(sourceSection));
        if (typeMatch.content() instanceof List<?> conditions) {
          if (conditions.getLast() instanceof ParseNode(String type, ParseNode content, int start, int end)
              && type.equals("array-length-condition")) {
            conditionNodes.addLast(visitArrayLengthCondition(isTypeChecked, content));
            conditions = conditions.subList(0, conditions.size() - 1);
          }
          if (!conditions.getFirst().equals("[")) throw new IllegalStateException("Unexpected array match: " + conditions.getFirst());
          if (conditions.size() > 1) conditionNodes.addAll(visitArrayContentMatchers(conditions.subList(1, conditions.size())));
        } else if (!typeMatch.content().equals("[")) throw new IllegalStateException("Unexpected conditions: " + typeMatch.content());
        yield conditionNodes;
      }
      case "structure-match" -> {
        boolean allowExtraFields = true;
        List<Object> conditions;
        if (typeMatch.content() instanceof List<?> l && "VOID".equals(l.getLast())) {
          allowExtraFields = false;
          typeMatch = new ParseNode(typeMatch.name(), ((List<?>) typeMatch.content()).getFirst(), typeMatch.start(),
              typeMatch.end());
        }
        if (typeMatch.content().equals("{")) conditions = List.of();
        else if (typeMatch.content() instanceof ParseNode(String name, ParseNode keyMatcher, int start, int end) && name.equals("key-matchers")) {
          conditions = List.of(keyMatcher.content());
        } else if (typeMatch.content() instanceof ParseNode(String name, List<?> kms, int start, int end) && name.equals("key-matchers")) {
          conditions = new ArrayList<>();
          conditions.add(((ParseNode) kms.getFirst()).content());
          for (Object akm : kms.subList(1, kms.size())) {
            Object km = ((ParseNode) akm).content();
            conditions.add(((ParseNode) km).content());
          }
        } else throw new IllegalStateException("Unexpected conditions: " + typeMatch.content());
        List<MatcherNode> conditionNodes = new ArrayList<>();
        List<VocabularyType> requiredKeys = new ArrayList<>();
        List<VocabularyType> optionalKeys = new ArrayList<>();
        for (Object condition : conditions) {
          switch (condition) {
            case ParseNode(String ignored, String key, int start, int end) -> requiredKeys.add(currentScope().getVocabularyType(key));
            case List<?> km -> {
              boolean isOptional = false;
              if (km.getFirst() instanceof String s && "?".equals(s)) {
                isOptional = true;
                km = km.subList(1, km.size());
              }
              String key = (String) ((ParseNode) km.getFirst()).content();
              VocabularyType type = currentScope().getVocabularyType(key);
              if (isOptional) {
                optionalKeys.add(type);
              } else {
                requiredKeys.add(type);
              }
              if (km.size() > 1) {
                MatcherNode matcher = visitAlternativeMembranes(type,
                    true, ((ParseNode) km.getLast()).content());
                conditionNodes.addLast(StructureKeyMatcherNode.create(type, matcher, isOptional,
                    sourceSection));
              }
            }
            default -> throw new IllegalStateException("Unexpected value: " + condition);
          }
        }
        conditionNodes.addFirst(StructureTypeMatcherNode.create(requiredKeys.toArray(VocabularyType[]::new), allowExtraFields, optionalKeys.toArray(VocabularyType[]::new),
            sourceSection));
        yield conditionNodes;
      }
      case "ID" -> {
        VocabularyType type = currentScope().getVocabularyType(typeMatch.content().toString());
        yield List.of(TagMatcherNode.create(type, sourceSection));
      }
      case "measure-type-match" -> {
        if (typeMatch.content().equals("\"\"")) {
          yield List.of(MeasureTypeMatcher.create(null, sourceSection));
        }
        yield List.of(MeasureTypeMatcher.create(visitUnit(typeMatch.content()), sourceSection));
      }
      case "string-literal" -> {
        if (typeMatch.content().equals("'")) {
          yield List.of(StringTypeMatcherNode.create());
        }
        yield List.of(RegexMatcherNode.create(isTypeChecked, visitStringLiteral(typeMatch.content(),
                sourceCode.createSection(typeMatch.start(), typeMatch.end() - typeMatch.start())),
            INTERNAL_CODE_SOURCE));
      }
      default -> throw new IllegalStateException("Unexpected value: " + typeMatch);
    };
  }

  private List<MatcherNode> visitArrayContentMatchers(List<?> arrayContentMatchers) {
    List<MatcherNode> contentMatchers = new ArrayList<>();
    for (Object acm : arrayContentMatchers) {
      if (acm instanceof ParseNode pn && pn.name().equals("array-content-matcher")) {
        ParseNode matcher = (ParseNode) pn.content();
        contentMatchers.add(ArrayContentMatcherNode.create(visitMatcher(matcher.content()),
            sourceCode.createSection(pn.start(), pn.end() - pn.start())));
      }
    }
    return contentMatchers;
  }

  private MatcherNode visitArrayLengthCondition(boolean isTypeChecked, ParseNode content) {
    int conditionSlot = currentScope().newTempSlot();
    pushCvSlot(conditionSlot);
    List<MatcherNode> lengthCondition = switch (content) {
      case ParseNode(String type, ParseNode(String name, ParseNode value, int ns, int ne), int start, int end) when type.equals("literal-match") && name.equals("source")
          -> List.of(EqualityMatcherNode.create(isTypeChecked, asSingleValueNode(visitSource(value)),
          sourceCode.createSection(start, end - start)));
      case ParseNode(String type, List<?> range, int start, int end) when type.equals("range-match") -> visitRangeMatch(
          null, isTypeChecked, range);
      default -> throw new IllegalStateException("Unexpected value: " + content);
    };
    popCvSlot();
    return ConditionNode.create(conditionSlot,
        MessageNode.create("length", ReadContextValueNode.create(-1, conditionSlot), INTERNAL_CODE_SOURCE),
        lengthCondition.size() == 1 ? lengthCondition.getFirst() : AllOfNode.create(lengthCondition,
            INTERNAL_CODE_SOURCE), sourceCode.createSection(content.start(), content.end() - content.start()));
  }

  private List<MatcherNode> visitRangeMatch(VocabularyType contextType, boolean isTypeChecked, List<?> content) {
    List<MatcherNode> bounds = new ArrayList<>();
    int separator = content.indexOf("..");
    if (separator > 0) {
      boolean inclusive = !content.get(separator - 1).equals("~");
      ValueNode low = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.get(separator - (inclusive ? 1 : 2))).content()));
      if (content.getFirst() instanceof ParseNode(String type, ParseNode(String ignored, String key, int is, int ie), int start, int end) && type.equals("tag")) {
        low = TagNode.create(currentScope().getVocabularyType(key), low, sourceCode.createSection(start, end - start));
      }
      if (contextType != null) {
        low = TagNode.create(contextType, low, INTERNAL_CODE_SOURCE);
      }
      bounds.add(GreaterThanMatcherNode.create(isTypeChecked, inclusive, low, INTERNAL_CODE_SOURCE));
    }
    if (separator + 1 < content.size()) {
      boolean inclusive = !content.get(separator + 1).equals("~");
      ValueNode high = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.getLast()).content()));
      if (content.get(separator + (inclusive ? 1 : 2)) instanceof ParseNode(String type, ParseNode(String ignored, String key, int is, int ie), int start, int end) && type.equals("tag")) {
        high = TagNode.create(currentScope().getVocabularyType(key), high, sourceCode.createSection(start, end - start));
      }
      if (contextType != null) {
        high = TagNode.create(contextType, high, INTERNAL_CODE_SOURCE);
      }
      bounds.add(LessThanMatcherNode.create(isTypeChecked, inclusive, high, INTERNAL_CODE_SOURCE));
    }
    return bounds;
  }

  private TailspinNode visitSource(ParseNode source) {
    return switch (source) {
      case ParseNode(String name, ParseNode ae, int start, int end) when name.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      case ParseNode(String name, Object ref, int start, int end) when name.equals("reference") -> visitReference(ref);
      case ParseNode(String name, ParseNode vc, int start, int end) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      case ParseNode(String name, List<?> cast, int start, int end) when name.equals("single-value-chain") -> {
        ParseNode vc = (ParseNode) cast.getFirst();
        TruffleString unit = visitUnit(cast.getLast());
        currentScope().setUntypedArithmetic(true);
        ValueNode value = asSingleValueNode(visitValueChain(vc));
        currentScope().setUntypedArithmetic(false);
        yield MeasureLiteral.create(value, unit, sourceCode.createSection(start, end - start));
      }
      case ParseNode(String name, Object contents, int start, int end) when name.equals("array-literal") -> visitArrayLiteral(contents,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, Object contents, int start, int end) when name.equals("structure-literal") -> visitStructureLiteral(contents,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, Object contents, int start, int end) when name.equals("string-literal") -> visitStringLiteral(contents,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, List<?> contents, int start, int end) when name.equals("type-cast") && contents.size() == 2 -> visitTypeCast((ParseNode) contents.getFirst(), (ParseNode) contents.getLast());
      default -> throw new IllegalStateException("Unexpected value: " + source);
    };
  }

  private ValueNode visitTypeCast(ParseNode tag, ParseNode value) {
    ValueNode valueNode = switch (value) {
      case ParseNode(String name, Object content, int start, int end) when name.equals("numeric-literal") -> visitNumericLiteral(content, sourceCode.createSection(start, end - start));
      case ParseNode(String name, Object content, int start, int end) when name.equals("structure-literal") -> visitStructureLiteral(content, sourceCode.createSection(start, end - start));
      case ParseNode(String name, Object content, int start, int end) when name.equals("string-literal") -> visitStringLiteral(content, sourceCode.createSection(start, end - start));
      case ParseNode(String name, Object content, int start, int end) when name.equals("array-literal") -> visitArrayLiteral(content,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, ParseNode content, int start, int end) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(content));
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
    String key = (String) ((ParseNode) tag.content()).content();
    return TagNode.create(currentScope().getVocabularyType(key), valueNode, sourceCode.createSection(tag.start(), tag.end() - tag.start()));
  }

  private ValueNode visitStringLiteral(Object contents, SourceSection sourceSection) {
    List<?> parts;
    if (contents instanceof List<?> l) {
      parts = l.subList(1, l.size());
    } else {
      parts = List.of();
    }
    return StringLiteral.create(parts.stream().map(this::visitStringPart).toList(), sourceSection);
  }

  private ValueNode visitStringPart(Object partSpec) {
    return switch (partSpec) {
      case ParseNode(String name, String part, int start, int end) when name.equals("string-part") -> StringPart.create(part,
          sourceCode.createSection(start, end - start));
      case String s when s.equals("''") -> StringPart.create("'", INTERNAL_CODE_SOURCE);
      case String s when s.equals("$$") -> StringPart.create("$", INTERNAL_CODE_SOURCE);
      case ParseNode(String name, ParseNode valueChain, int start, int end) when name.equals("interpolate") -> asTransformResult(visitValueChain(valueChain));
      case ParseNode(String name, ParseNode valueChain, int start, int end) when name.equals("codepoint") -> CodepointNode.create(asSingleValueNode(visitValueChain(valueChain)),
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, String bytes, int start, int end) when name.equals("unicode-bytes") -> CodepointNode.create(IntegerLiteral.create(Long.valueOf(bytes, 16),
          sourceCode.createSection(start, end - start)), sourceCode.createSection(start, end - start));
      default -> throw new IllegalStateException("Unexpected value: " + partSpec);
    };
  }

  private ValueNode visitStructureLiteral(Object contents, SourceSection section) {
    List<Object> keyValues;
    if (contents instanceof ParseNode(String ignored, ParseNode kv, int start, int end)) {
      keyValues = List.of(kv);
    } else if (contents instanceof ParseNode(String ignored, List<?> kvs, int start, int end)) {
      keyValues = new ArrayList<>();
      keyValues.add(kvs.getFirst());
      for (Object akv : kvs.subList(1, kvs.size())) {
        Object kv = ((ParseNode) akv).content();
        keyValues.add(kv);
      }
    } else if (contents.equals("{")) {
      keyValues = List.of();
    } else throw new IllegalStateException("Unexpected value: " + contents);
    List<VocabularyType> keys = new ArrayList<>();
    List<ValueNode> values = new ArrayList<>();
    for (Object sc : keyValues) {
      if (sc instanceof ParseNode(String name, List<?> kv, int start, int end) && name.equals("key-value")) {
        String key = ((ParseNode) kv.getFirst()).content().toString();
        keys.add(currentScope().getVocabularyType(key));
        values.add(asSingleValueNode(visitValueChain((ParseNode) kv.getLast())));
      } else if (sc instanceof ParseNode vc) {
        keys.add(null);
        values.add(asTransformResult(visitValueChain(vc)));
      } else throw new IllegalStateException("Unexpected " + sc);
    }
    return StructureLiteral.create(language.rootShape, keys, values, section);
  }

  private RangeIteration visitRange(List<?> bounds, SourceSection sourceSection) {
    ValueNode start = asSingleValueNode(visitSource(
        (ParseNode) ((ParseNode) bounds.getFirst()).content()));
    boolean inclusiveStart = true;
    int index = 1;
    if (bounds.get(index).equals("~")) {
      inclusiveStart = false;
      index++;
    }
    index++; // skip dots
    boolean inclusiveEnd = true;
    if (bounds.get(index).equals("~")) {
      inclusiveEnd = false;
      index++;
    }
    ValueNode end = asSingleValueNode(visitSource(
        (ParseNode) ((ParseNode) bounds.get(index)).content()));
    index++;
    ValueNode stride;
    if (index < bounds.size()) {
      stride = asSingleValueNode(visitSource((ParseNode) (
          (ParseNode) ((ParseNode) bounds.get(index)).content()).content()));
    } else {
      stride = VoidValue.create(INTERNAL_CODE_SOURCE);
    }
    return RangeIteration.create(currentScope().newTempSlot(), start, inclusiveStart, currentScope().newTempSlot(), end, inclusiveEnd, currentScope().newTempSlot(), stride,
        sourceSection);
  }

  private ValueNode visitArrayLiteral(Object contents, SourceSection sourceSection) {
    List<TransformNode> contentNodes = switch (contents) {
      case String bracket when bracket.equals("[") -> List.of();
      case ParseNode(String name, ParseNode value, int start, int end) when name.equals("array-contents")
          -> List.of(asTransformNode(visitValueChain(value)));
      case ParseNode(String name, List<?> values, int start, int end) when name.equals("array-contents") -> {
        List<TransformNode> valueNodes = new ArrayList<>();
        valueNodes.add(asTransformNode(visitValueChain((ParseNode) values.getFirst())));
        for (int i = 1; i < values.size(); i++) {
          Object valueChain = ((ParseNode) values.get(i)).content();
          valueNodes.add(asTransformNode(visitValueChain((ParseNode) valueChain)));
        }
        yield valueNodes;
      }
      default -> throw new IllegalStateException("Unexpected value: " + contents);
    };
    return ArrayLiteral.create(currentScope().createBuildSlot(), contentNodes, sourceSection);
  }

  private ValueNode visitNumericLiteral(Object literal, SourceSection section) {
    return switch (literal) {
      case ParseNode(String name, Long value, int start, int end) when name.equals("INT") -> IntegerLiteral.create(value,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, BigInteger value, int start, int end) when name.equals("INT") -> BigIntegerLiteral.create(value,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, SmallSciNum value, int start, int end) when name.equals("NUM") -> SmallSciNumLiteral.create(value,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, SciNum value, int start, int end) when name.equals("NUM") -> SciNumLiteral.create(value,
          sourceCode.createSection(start, end - start));
      case List<?> m when m.size() == 2 -> MeasureLiteral.create(visitNumericLiteral(m.getFirst(),
              INTERNAL_CODE_SOURCE), visitUnit(m.getLast()),
          section);
      default -> throw new IllegalStateException("Unexpected value: " + literal);
    };
  }

  private final Map<TruffleString, TruffleString> units = new HashMap<>();

  private TruffleString visitUnit(Object unitSpec) {
    if (unitSpec instanceof String ignored) return Measure.SCALAR;
    if (!(unitSpec instanceof ParseNode p && p.name().equals("unit"))) throw new IllegalStateException("Unexpected value: " + unitSpec);
    List<?> items;
    if (p.content() instanceof List<?> l) items = l;
    else items = List.of(p.content());
    StringBuilder unit = new StringBuilder();
    while (!items.isEmpty()) {
      Object item = items.getFirst();
      items = items.subList(1, items.size());
      if (item instanceof ParseNode(String mp, ParseNode(String ignored, String part, int is, int ie), int start, int end) && mp.equals("measure-product")) {
        unit.append(part).append(' ');
      } else if (item instanceof ParseNode(String md, Object denominator, int start, int end) && md.equals("measure-denominator")) {
        unit.deleteCharAt(unit.length() - 1).append('/');
        if (denominator instanceof List<?> l) items = l;
        else items = List.of(denominator);
      } else throw new IllegalStateException("Unexpected value: " + item);
    }
    TruffleString s = TailspinStrings.fromJavaString(unit.deleteCharAt(unit.length() - 1).toString());
    TruffleString result = units.putIfAbsent(s, s);
    return result == null ? s : result;
  }

  private TailspinNode visitReference(Object ref) {
    boolean readsState = false;
    List<?> predicate = List.of();
    TailspinNode value = switch (ref) {
      case String s when s.equals("$") -> ReadContextValueNode.create(-1, currentValueSlot());
      case List<?> l when l.getFirst().equals("$") && l.get(1) instanceof ParseNode(String ignored, String identifier, int start, int end) -> {
        Object source = currentScope().getSource(identifier, -1);
        if (source instanceof Reference reference) {
          predicate = l.subList(2, l.size());
          yield ReadContextValueNode.create(reference);
        } else if (source instanceof Templates templates){
          if (l.size() > 2) throw new IllegalStateException("Cannot have lens expressions or message sends on call to " + identifier);
          yield SendToTemplatesNode.create(VoidValue.create(INTERNAL_CODE_SOURCE), scopes.size(), templates,
              sourceCode.createSection(start, end - start));
        } else {
          throw new IllegalStateException("Cannot use " + identifier + " as a source");
        }
      }
      case List<?> l when l.getFirst().equals("$") && l.get(1).equals("@") -> {
        readsState = true;
        predicate = l.subList(2, l.size());
        String scopeId = null;
        if (!predicate.isEmpty() && predicate.getFirst() instanceof ParseNode(String ignored, String identifier, int start, int end)) {
          scopeId = identifier;
          predicate = predicate.subList(1, predicate.size());
        }
        yield ReadStateValueNode.create(currentScope().accessState(scopeId));
      }
      case List<?> l when l.getFirst().equals("$") -> {
        predicate = l.subList(1, l.size());
        yield ReadContextValueNode.create(-1, currentValueSlot());
      }
      default -> throw new IllegalStateException("Unexpected value: " + ref);
    };
    if (!predicate.isEmpty() && predicate.getFirst() instanceof ParseNode(String type, Object lensExpression, int start, int end) && type.equals("lens-expression")){
      value = visitReadLensExpression(asSingleValueNode(value), lensExpression);
      value = ConsolidateLensResultNode.create(asTransformResult(value));
    }
    if (readsState) {
      value = ReadStateNode.create((ValueNode) value, INTERNAL_CODE_SOURCE);
    }
    if (!predicate.isEmpty() && predicate.getLast() instanceof ParseNode(String sendType, ParseNode(String ignored, String message, int is, int ie), int start, int end)
        && sendType.equals("message-send")){
      value = MessageNode.create(message, asSingleValueNode(value), sourceCode.createSection(start, end - start));
    }
    return value;
  }

  private ValueNode visitReadLensDimension(ValueNode target, Object lensDimension, Reference indexVar) {
    return switch (lensDimension) {
      case ParseNode(String type, ParseNode source, int start, int end) when type.equals("source")
          -> ArrayReadNode.create(false, target, asSingleValueNode(visitSource(source)), indexVar,
          sourceCode.createSection(start, end - start));
      case ParseNode(String type, Object bounds, int start, int end) when type.equals("lens-range")
          -> asTransformResult(visitLensRange(target, bounds, indexVar, sourceCode.createSection(start, end - start)));
      case ParseNode(String type, ParseNode(String ignored, String key, int is, int ie), int start, int end) when type.equals("key")
          -> StructureReadNode.create(target, currentScope().getVocabularyType(key), sourceCode.createSection(start, end - start));
      case List<?> dimension -> {
        indexVar = null;
        if (dimension.get(1) instanceof ParseNode(String type, ParseNode(String ignored, String identifier, int is, int ie), int start, int end) && type.equals("index-variable")) {
          indexVar = currentScope().defineValue(identifier);
          currentScope().markTemporary(identifier);
        }
        ValueNode thisDimension = visitReadLensDimension(target, dimension.getFirst(), indexVar);
        if (!dimension.isEmpty() && dimension.getLast() instanceof ParseNode(String type, ParseNode nextDimension, int start, int end) && type.equals("next-lens-dimension")) {
          thisDimension = visitReadLensDimension(thisDimension, nextDimension.content(), null);
        }
        yield thisDimension;
      }
      default -> throw new IllegalStateException("Unexpected value: " + lensDimension);
    };
  }

  @SuppressWarnings("unchecked")
  private ValueNode visitReadLensExpression(ValueNode target, Object lensExpression) {
    currentScope().pushTemporaryVariables();
    ValueNode expression = switch (lensExpression) {
      case ParseNode(String type, Object lensDimension, int start, int end) when type.equals("lens-dimension")
          -> visitReadLensDimension(target, lensDimension, null);
      case List<?> dimension -> {
        ParseNode lensDimension = (ParseNode) dimension.getFirst();
        ValueNode thisDimension = visitReadLensDimension(target, lensDimension.content(), null);
        Object lensTransforms = ((ParseNode) dimension.getLast()).content();
        List<ParseNode> transforms = new ArrayList<>();
        if (lensTransforms instanceof ParseNode p) {
          transforms.add(p);
        } else {
          transforms.addAll((List<ParseNode>) lensTransforms);
        }
        if (!transforms.isEmpty()) {
          pushCvSlot(currentScope().newTempSlot());
          transforms.addFirst(new ParseNode("source", new ParseNode("reference", "$", transforms.getFirst().start(), transforms.getFirst().start()), transforms.getFirst().start(), transforms.getFirst().start()));
          thisDimension = TransformLensNode.create(asTransformResult(thisDimension),
              currentValueSlot(),
              asTransformNode(visitValueChain(new ParseNode("value-chain", transforms, transforms.getFirst().start(), transforms.getLast().end()))),
              currentScope().newResultSlot(), sourceCode.createSection(lensDimension.start(), transforms.getLast().end() - lensDimension.start()));
          popCvSlot();
        }
        yield thisDimension;
      }
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
    };
    currentScope().deleteTemporaryValues();
    return expression;
  }

  @SuppressWarnings("unchecked")
  private ArrayRangeReadNode visitLensRange(ValueNode target, Object boundsSpec, Reference indexVar,
      SourceSection sourceSection) {
    List<Object> bounds;
    if (boundsSpec instanceof List<?> l) {
      bounds = (List<Object>) l;
    } else {
      bounds = List.of(boundsSpec);
    }
    int separator = bounds.indexOf("..");
    ValueNode stride;
    if (bounds.getLast() instanceof ParseNode(String name, ParseNode content, int start, int end) && name.equals("stride")) {
      stride = asSingleValueNode(visitSource((ParseNode) content.content()));
      bounds = bounds.subList(0, bounds.size() - 1);
    } else {
      stride = VoidValue.create(sourceSection);
    }
    boolean inclusiveStart;
    ValueNode start;
    if (separator > 0 && !bounds.getFirst().equals("~")) {
      inclusiveStart = separator == 1;
      start = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) bounds.getFirst()).content()));
    } else {
      inclusiveStart = separator == 0;
      start = VoidValue.create(sourceSection);
    }
    boolean inclusiveEnd;
    ValueNode end;
    if (separator + 1 < bounds.size() && !bounds.getLast().equals("~")) {
      inclusiveEnd = separator + 2 == bounds.size();
      end = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) bounds.getLast()).content()));
    } else {
      inclusiveEnd = separator + 1 == bounds.size();
      end = VoidValue.create(sourceSection);
    }
    RangeIteration r = RangeIteration.create(currentScope().newTempSlot(), start, inclusiveStart, currentScope().newTempSlot(), end, inclusiveEnd, currentScope().newTempSlot(), stride,
        sourceSection);
    pushCvSlot(currentScope().newTempSlot());
    r.setStage(currentValueSlot(), ResultAggregatingNode.create(ArrayReadNode.create(true, ReadContextValueNode.create(-1, LENS_CONTEXT_SLOT), ReadContextValueNode.create(-1, currentValueSlot()), indexVar,
            sourceSection)
    ));
    r.setIsLensRange();
    popCvSlot();
    return ArrayRangeReadNode.create(r, currentScope().newResultSlot(), target, sourceSection);
  }

  private ValueNode visitArithmeticExpression(ParseNode ae) {
    return switch (ae) {
      case ParseNode(String name, Object literal, int start, int end) when name.equals("numeric-literal") -> visitNumericLiteral(literal,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, List<?> addition, int start, int end) when name.equals("addition") -> visitAddition(addition);
      case ParseNode(String name, List<?> multiplication, int start, int end) when name.equals("multiplication") -> visitMultiplication(multiplication);
      case ParseNode(String name, ParseNode(String ignored, ParseNode square, int is, int ie), int start, int end) when name.equals("square-root") -> SquareRootNode.create(visitTerm(square), currentScope().isUntypedRegion(),
          sourceCode.createSection(start, end - start));
      // We also handle term here just to simplify the recursive expression parsing
      case ParseNode(String name, ParseNode term, int start, int end) when name.equals("term") -> visitTerm(term);
      case ParseNode(String name, ParseNode term, int start, int end) when name.equals("negated-term") -> NegateNode.create(visitTerm(term));
      default -> throw new IllegalStateException("Unexpected value: " + ae);
    };
  }

  private ValueNode visitAddition(List<?> addition) {
    ParseNode first = (ParseNode) addition.getFirst();
    ValueNode left = visitArithmeticExpression(first);
    ParseNode last = (ParseNode) addition.getLast();
    ValueNode right = visitArithmeticExpression(last);
    if(addition.get(1).equals("+")) {
      return AddNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    } else if(addition.get(1).equals("-")) {
      return SubtractNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    }
    throw new IllegalStateException("Unexpected value: " + addition);
  }

  private ValueNode visitMultiplication(List<?> multiplication) {
    ParseNode first = (ParseNode) multiplication.getFirst();
    ValueNode left = visitArithmeticExpression(first);
    ParseNode last = (ParseNode) multiplication.getLast();
    ValueNode right = visitArithmeticExpression(last);
    if(multiplication.get(1).equals("*")) {
      return MultiplyNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    } else if(multiplication.get(1).equals("/")) {
      return DivideNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    } else if(multiplication.get(1).equals("~/")) {
      return TruncateDivideNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    } else if(multiplication.get(1).equals("mod")) {
      return MathModNode.create(left, right, currentScope().isUntypedRegion(), sourceCode.createSection(
          first.start(), last.end() - first.start()));
    }
    throw new IllegalStateException("Unexpected value: " + multiplication);
  }

  private ValueNode visitTerm(ParseNode term) {
    return switch (term) {
      case ParseNode(String name, Object literal, int start, int end) when name.equals("numeric-literal") -> visitNumericLiteral(literal,
          sourceCode.createSection(start, end - start));
      case ParseNode(String name, ParseNode negated, int start, int end) when name.equals("negated-term") -> NegateNode.create(visitTerm(negated));
      case ParseNode(String name, Object ref, int start, int end) when name.equals("reference") -> asSingleValueNode(visitReference(ref));
      case ParseNode(String name, ParseNode vc, int start, int end) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      case ParseNode(String name, List<?> cast, int start, int end) when name.equals("single-value-chain") -> {
        ParseNode vc = (ParseNode) cast.getFirst();
        TruffleString unit = visitUnit(cast.getLast());
        currentScope().setUntypedArithmetic(true);
        ValueNode value = asSingleValueNode(visitValueChain(vc));
        currentScope().setUntypedArithmetic(false);
        yield MeasureLiteral.create(value, unit, sourceCode.createSection(start, end - start));
      }
      case ParseNode(String name, ParseNode(String ignored, ParseNode square, int is, int ie), int start, int end) when name.equals("square-root") -> SquareRootNode.create(visitTerm(square), currentScope().isUntypedRegion(), sourceCode.createSection(start, end - start));
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
