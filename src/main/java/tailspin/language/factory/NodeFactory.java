package tailspin.language.factory;

import static tailspin.language.parser.ParseNode.normalizeValues;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.STATE_SLOT;

import com.oracle.truffle.api.CallTarget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tailspin.language.TailspinLanguage;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayMutateNode;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.iterate.StatementTransformNode;
import tailspin.language.nodes.iterate.StreamNode;
import tailspin.language.nodes.matchers.AllOfNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.AnyOfNode;
import tailspin.language.nodes.matchers.ArrayTypeMatcherNode;
import tailspin.language.nodes.matchers.CallDefinedTypeMatcherNode;
import tailspin.language.nodes.matchers.ConditionNode;
import tailspin.language.nodes.matchers.EqualityMatcherNode;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.matchers.MeasureTypeMatcher;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;
import tailspin.language.nodes.matchers.StructureKeyMatcherNode;
import tailspin.language.nodes.matchers.StructureTypeMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.BigIntegerLiteral;
import tailspin.language.nodes.numeric.DivideNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.MathModNode;
import tailspin.language.nodes.numeric.MeasureLiteral;
import tailspin.language.nodes.numeric.MultiplyNode;
import tailspin.language.nodes.numeric.SciNumLiteral;
import tailspin.language.nodes.numeric.SquareRootNode;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.numeric.TruncateDivideNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.state.ReadStateNode;
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
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.SinkNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.nodes.value.TransformResultNode;
import tailspin.language.nodes.value.VoidValue;
import tailspin.language.nodes.value.WriteContextValueNode;
import tailspin.language.parser.ParseNode;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.TemplatesInstance;
import tailspin.language.runtime.VocabularyType;

public class NodeFactory {

  private final TailspinLanguage language;

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
  private void enterNewScope(String scopeId) {
    scopes.addLast(new Scope(scopeId, scopes.isEmpty() ? null : scopes.getLast()));
    pushCvSlot(CV_SLOT);
  }

  private Scope exitScope() {
    popCvSlot();
    return scopes.removeLast();
  }

  private Scope currentScope() {
    return scopes.getLast();
  }

  public NodeFactory(TailspinLanguage tailspinLanguage) {
    this.language = tailspinLanguage;
  }

  public CallTarget createCallTarget(ParseNode program) {
    enterNewScope(null);
    StatementNode programBody = visitBlock(program.content());
    Scope scope = exitScope();
    return scope.createProgramRootNode(language, programBody);
  }

  private StatementNode visitBlock(Object block) {
    return switch (block) {
      case ParseNode(String name, ParseNode statement) when name.equals("statement") -> visitStatement(statement);
      case List<?> statements -> BlockNode.create(statements.stream()
          .map(s -> visitStatement((ParseNode) s)).toList());
      default -> throw new IllegalStateException("Unexpected value: " + block);
    };
  }

  private StatementNode visitStatement(ParseNode statement) {
    return switch (statement) {
      case ParseNode(String name, ParseNode stmt) when name.equals("statement") -> visitStatement(stmt);
      case ParseNode(String name, Object stmt) when name.equals("terminated-chain") -> visitTerminatedChain(stmt);
      case ParseNode(String name, List<?> def) when name.equals("definition") -> visitDefinition(def);
      case ParseNode(String name, Object setExpr) when name.equals("set-state") -> visitSetState(setExpr);
      case ParseNode(String type, List<?> content) when type.equals("templates") -> {
        String name = (String) content.getLast();
        String templateType = (String) content.getFirst();
        enterNewScope(name);
        visitTemplatesBody((ParseNode) content.get(1));
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setType(templateType);
        templates.setDefinitionLevel(scopes.size());
        currentScope().registerTemplates(name, templates);
        yield DoNothingNode.create();
      }
      case ParseNode(String stmtType, List<?> def) when stmtType.equals("type-def") -> {
        VocabularyType type = currentScope().getVocabularyType(((ParseNode) def.getFirst()).content().toString());
        enterNewScope(null);
        MatcherNode constraint = visitAlternativeMembranes(null, def.subList(1, def.size()));
        Scope definedScope = exitScope();
        Templates templates = definedScope.getOrCreateMatcherTemplates();
        if (templates.needsScope()) {
          definedScope.makeMatcherCallTarget(MatchBlockNode.create(List.of(
              MatchTemplateNode.create(constraint, EmitNode.create(asTransformNode(ReadContextValueNode.create(-1, CV_SLOT))))
          )));
          TemplatesInstance definedTemplates = new TemplatesInstance(null, templates.getCallTarget());
          type.setConstraint(CallDefinedTypeMatcherNode.create(definedTemplates));
          yield DefineTypeConstraintNode.create(definedTemplates);
        }
        type.setConstraint(constraint);
        yield DoNothingNode.create();
      }
      default -> throw new IllegalStateException("Unexpected value: " + statement);
    };
  }

  private StatementNode visitTerminatedChain(Object stmt) {
    return switch(stmt) {
      case ParseNode valueChain -> EmitNode.create(asTransformNode(visitValueChain(valueChain)));
      case List<?> parts when parts.getLast() instanceof ParseNode(String name, Object sinkType) && name.equals("sink") -> {
        ParseNode valueChain = (ParseNode) parts.getFirst();
        ArrayList<Object> transforms = new ArrayList<>();
        if (valueChain.content() instanceof List<?> l) {
          transforms.addAll(l);
        } else {
          transforms.add(valueChain.content());
        }
        if (sinkType.equals("VOID")) {
          transforms.addLast("VOID");
        } else if (sinkType.equals("#")) {
          transforms.addLast(new ParseNode("transform", "#"));
        } else if (sinkType instanceof ParseNode(String call, ParseNode id) && call.equals("templates-call")) {
          Templates sink = currentScope().findTemplates(id.content().toString());
          if (!sink.getType().equals("sink")) throw new IllegalStateException("Can only sink to sink " + id.content());
          transforms.addLast(new ParseNode("transform", sinkType));
        } else {
          throw new IllegalStateException("Unexpected value: " + sinkType);
        }
        yield SinkNode.create(asTransformNode(visitValueChain(new ParseNode(valueChain.name(), transforms))));
      }
      case List<?> parts when parts.getLast() instanceof ParseNode(String name, ParseNode setState) && name.equals("accumulator-state") -> {
        ParseNode valueChain = (ParseNode) parts.getFirst();
        ArrayList<Object> transforms = new ArrayList<>();
        if (valueChain.content() instanceof List<?> l) {
          transforms.addAll(l);
        } else {
          transforms.add(valueChain.content());
        }
        transforms.add(setState);
        yield SinkNode.create(asTransformNode(visitValueChain(new ParseNode(valueChain.name(), transforms))));
      }
      default -> throw new IllegalStateException("Unexpected value: " + stmt);
    };
  }

  private StatementNode visitSetState(Object expr) {
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
        if (l.getFirst() instanceof ParseNode(String type, String identifier) && type.equals("ID")) {
          scopeId = identifier;
          l = l.subList(1, l.size());
        }
        stateLevel = currentScope().accessState(scopeId);
        ValueNode target = ReadContextValueNode.create(stateLevel, STATE_SLOT);
        if (!l.isEmpty()) {
          if (l.getFirst() instanceof ParseNode(String type, Object lensExpr) && type.equals("lens-expression")) {
            value = visitWriteLensExpression(target, lensExpr, value);
          } else {
            throw new IllegalStateException("Unexpected value: " + l);
          }
        }
      }
      default -> throw new IllegalStateException("Unexpected value: " + expr);
    }
    return WriteContextValueNode.create(stateLevel, STATE_SLOT, asSingleValueNode(value));
  }

  private ValueNode visitWriteLensExpression(ValueNode target, Object lensExpression, TailspinNode value) {
    return switch (lensExpression) {
      case ParseNode(String type, ParseNode source) when type.equals("source")
          -> ArrayMutateNode.create(target, asSingleValueNode(visitSource(source)), asSingleValueNode(value));
      case ParseNode(String type, ParseNode id) when type.equals("key")
          -> WriteKeyValueNode.create(currentScope().getVocabularyType((String) id.content()), target, asSingleValueNode(value));
      case List<?> dimension -> {
        ValueNode thisDimension = visitReadLensExpression(target, dimension.getFirst());
        ValueNode modifiedTarget = visitWriteLensExpression(thisDimension, ((ParseNode) dimension.getLast()).content(), value);
        yield visitWriteLensExpression(target, dimension.getFirst(), modifiedTarget);
      }
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
    };
  }

  private StatementNode visitDefinition(List<?> def) {
    if (def.getFirst() instanceof ParseNode(String name, String identifier) && name.equals("ID")) {
      Reference defined = currentScope().defineValue(identifier);
      TailspinNode expr = visitValueChain((ParseNode) def.getLast());
      return WriteContextValueNode.create(defined, asSingleValueNode(expr));
    }
    throw new IllegalStateException("Unexpected value: " + def);
  }

  private TailspinNode visitValueChain(ParseNode valueChain) {
    if (!valueChain.name().equals("value-chain")) throw new IllegalStateException("Unexpected value: " + valueChain);
    if (valueChain.content() instanceof ParseNode(String name, ParseNode source) && name.equals("source")) {
      TailspinNode sourceNode = visitSource(source);
      if (sourceNode instanceof RangeIteration r) {
        pushCvSlot(currentScope().newTempSlot());
        r.setStage(currentValueSlot(), ResultAggregatingNode.create(ReadContextValueNode.create(-1, currentValueSlot())));
        popCvSlot();
      }
      return sourceNode;
    } else if (valueChain.content() instanceof List<?> chain) {
      ChainSlots chainSlots = currentScope().newChainSlots();
      List<TransformNode> stages = new ArrayList<>();
      RangeIteration pendingIteration = null;
      for (Object stage : chain) {
        TailspinNode stageNode = switch (stage) {
          case ParseNode(String name, Object transform) when name.equals("transform") -> visitTransform(transform);
          case ParseNode(String name, ParseNode transform) when name.equals("source") -> visitSource(transform);
          case ParseNode(String name, String ignored) when name.equals("stream") -> StreamNode.create(ReadContextValueNode.create(-1, currentValueSlot()));
          case ParseNode(String name, ParseNode setState) when name.equals("set-state") -> visitSetState(setState);
          case String v when v.equals("VOID") -> VoidValue.create();
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
        pendingIteration.setStage(currentValueSlot(), ResultAggregatingNode.create(ReadContextValueNode.create(-1, currentValueSlot())));
        popCvSlot();
      }
      popCvSlot();
      return ChainNode.create(chainSlots.values(), chainSlots.cv(), chainSlots.result(), stages);
    }
    throw new IllegalStateException("Unexpected value " + valueChain.content());
  }

  private TransformNode asTransformNode(TailspinNode node) {
    if (node instanceof ValueNode v)
      return ResultAggregatingNode.create(v);
    if (node instanceof TransformNode t)
      return t;
    if (node instanceof StatementNode s)
      return StatementTransformNode.create(s);
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
      case ParseNode(String name, ParseNode source) when name.equals("source") -> asTransformNode(visitSource(source));
      case ParseNode(String name, ParseNode body) when name.equals("inline-templates-call") -> {
        enterNewScope(null);
        visitTemplatesBody(body);
        Scope scope = exitScope();
        Templates templates = scope.getTemplates();
        templates.setDefinitionLevel(scopes.size());
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(), templates);
      }
      case String crosshatch when crosshatch.equals("#") -> {
        Templates matchers = currentScope().getOrCreateMatcherTemplates();
        yield SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size() - 1, matchers);
      }
      case ParseNode(String type, ParseNode id) when type.equals("templates-call")
          -> SendToTemplatesNode.create(ReadContextValueNode.create(-1, currentValueSlot()), scopes.size(),
          currentScope().findTemplates((String) id.content()));
      case ParseNode(String type, Object membranes) when type.equals("filter")
          -> FilterNode.create(ReadContextValueNode.create(-1, currentValueSlot()), visitAlternativeMembranes(
          null, membranes));
      default -> throw new IllegalStateException("Unexpected value: " + transform);
    };
  }

  private void visitTemplatesBody(ParseNode body) {
    switch (body) {
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object block))
          when name.equals("with-block"):
        Object matchBlock = null;
        if (block instanceof List<?> list
            && list.getLast() instanceof ParseNode(String type, Object m)
            && type.equals("matchers")) {
          matchBlock = m;
          block = list.subList(0, list.size() - 1);
        }
        StatementNode blockNode = visitBlock(block);
        currentScope().setBlock(blockNode);
        if (matchBlock != null) {
          currentScope().verifyMatcherIsCalled();
          visitMatchers(matchBlock);
        }
      break;
      case ParseNode(@SuppressWarnings("unused") String bodyName, ParseNode(String name, Object matchers))
          when name.equals("matchers"):
        visitMatchers(matchers);
      break;
      default: throw new IllegalStateException("Unexpected value: " + body);
    }
  }

  private void visitMatchers(Object matchers) {
    MatchBlockNode matchBlockNode = switch (matchers) {
      case ParseNode(String name, List<?> matchTemplate) when name.equals("match-template") -> MatchBlockNode.create(List.of(visitMatchTemplate(matchTemplate)));
      case List<?> matchTemplates -> MatchBlockNode.create(matchTemplates.stream()
          .map(mt -> {
            if (mt instanceof ParseNode(String name, List<?> matchTemplate) && name.equals("match-template")) return visitMatchTemplate(matchTemplate);
            else throw new IllegalStateException("Expected match-template got " + mt);
          }).toList());
      default -> throw new IllegalStateException("Unexpected value: " + matchers);
    };
    currentScope().getOrCreateMatcherTemplates().setDefinitionLevel(scopes.size() - 1);
    currentScope().makeMatcherCallTarget(matchBlockNode);
  }

  private MatchTemplateNode visitMatchTemplate(List<?> matchTemplate) {
    MatcherNode matcherNode = visitMatcher((ParseNode) matchTemplate.getFirst());
    StatementNode block = visitBlock(normalizeValues(matchTemplate.subList(1, matchTemplate.size())));
    return MatchTemplateNode.create(matcherNode, block);
  }

  private MatcherNode visitMatcher(ParseNode matcher) {
    return switch (matcher) {
      case ParseNode(String name, @SuppressWarnings("unused") Object c)
          when name.equals("otherwise") -> AlwaysTrueMatcherNode.create();
      case ParseNode(String name, List<?> membranes)
          when name.equals("when-do")
          && membranes.getFirst() instanceof ParseNode(String tb, Object types)
          && tb.equals("type-bound") -> {
        MatcherNode typeBound = visitAlternativeMembranes(null, types);
        yield visitAlternativeMembranes(typeBound, membranes.subList(1, membranes.size()));
      }
      case ParseNode(String name, Object membranes)
          when name.equals("when-do") -> visitAlternativeMembranes(null, membranes);
      default -> throw new IllegalStateException("Unexpected value: " + matcher);
    };
  }

  private MatcherNode visitAlternativeMembranes(MatcherNode typeBound, Object membranes) {
    List<MatcherNode> alternatives = new ArrayList<>();
    List<?> membraneSpecs;
    if (membranes instanceof List<?> l) {
      membraneSpecs = l;
    } else {
      membraneSpecs = List.of(membranes);
    }
    for (Object membraneSpec : membraneSpecs) {
      if (membraneSpec instanceof ParseNode(String m, Object conditions) && m.equals("membrane")) {
        alternatives.add(visitMembrane(typeBound, conditions));
      } else {
        throw new IllegalStateException("Unknown membrane " + membraneSpec);
      }
    }
    if (alternatives.size() == 1) {
      return alternatives.getFirst();
    }
    return AnyOfNode.create(alternatives);
  }

  private MatcherNode visitMembrane(MatcherNode typeBound, Object conditions) {
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
        case ParseNode(String type, ParseNode typeMatch) when type.equals("type-match")
            -> conjunction.addAll(visitTypeMatch(typeBound, typeMatch));
        case ParseNode(String type, ParseNode(String name, ParseNode value)) when type.equals("literal-match") && name.equals("source")
            -> conjunction.add(EqualityMatcherNode.create(typeBound, asSingleValueNode(visitSource(value))));
        case ParseNode(String type, List<?> condition) when type.equals("condition") -> {
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
    return AllOfNode.create(conjunction);
  }

  private MatcherNode visitCondition(Integer conditionSlot, List<?> condition) {
    ValueNode toMatch = asSingleValueNode(visitValueChain((ParseNode) condition.getFirst()));
    MatcherNode matcher = visitAlternativeMembranes(null, condition.subList(1, condition.size()));
    return ConditionNode.create(conditionSlot, toMatch, matcher);
  }

  private List<MatcherNode> visitTypeMatch(MatcherNode typeBound, ParseNode typeMatch) {
    return switch (typeMatch.name()) {
      case "range-match" -> {
        if (typeMatch.content() instanceof List<?> bounds)
          yield visitRangeMatch(typeBound, bounds);
        else yield List.of(NumericTypeMatcherNode.create());
      }
      case "array-match" -> {
        List<MatcherNode> conditionNodes = new ArrayList<>();
        conditionNodes.addLast(ArrayTypeMatcherNode.create());
        if (typeMatch.content() instanceof List<?> conditions) {
          if (conditions.getLast() instanceof ParseNode(String type, ParseNode content)
              && type.equals("array-length-condition")) {
            conditionNodes.addLast(visitArrayLengthCondition(content));
            conditions = conditions.subList(0, conditions.size() - 1);
          }
          if (!conditions.getFirst().equals("[")) throw new IllegalStateException("Unexpected array match: " + conditions.getFirst());
          if (conditions.size() > 1) throw new UnsupportedOperationException(conditions.toString());
        } else if (!typeMatch.content().equals("[")) throw new IllegalStateException("Unexpected conditions: " + typeMatch.content());
        yield conditionNodes;
      }
      case "structure-match" -> {
        boolean allowExtraFields = true;
        List<Object> conditions;
        if (typeMatch.content() instanceof List<?> l && "VOID".equals(l.getLast())) {
          allowExtraFields = false;
          typeMatch = new ParseNode(typeMatch.name(), ((List<?>) typeMatch.content()).getFirst());
        }
        if (typeMatch.content().equals("{")) conditions = List.of();
        else if (typeMatch.content() instanceof ParseNode(String name, ParseNode keyMatcher) && name.equals("key-matchers")) {
          conditions = List.of(keyMatcher.content());
        } else if (typeMatch.content() instanceof ParseNode(String name, List<?> kms) && name.equals("key-matchers")) {
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
            case ParseNode(String ignored, String key) -> requiredKeys.add(currentScope().getVocabularyType(key));
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
                MatcherNode matcher = visitAlternativeMembranes(
                    null, ((ParseNode) km.getLast()).content());
                conditionNodes.addLast(StructureKeyMatcherNode.create(type, matcher, isOptional));
              }
            }
            default -> throw new IllegalStateException("Unexpected value: " + condition);
          }
        }
        conditionNodes.addFirst(StructureTypeMatcherNode.create(requiredKeys.toArray(VocabularyType[]::new), allowExtraFields, optionalKeys.toArray(VocabularyType[]::new)));
        yield conditionNodes;
      }
      case "measure-type-match" -> {
        if (typeMatch.content().equals("\"\"")) {
          yield List.of(MeasureTypeMatcher.create(null));
        }
        yield List.of(MeasureTypeMatcher.create(visitUnit(typeMatch.content())));
      }
      default -> throw new IllegalStateException("Unexpected value: " + typeMatch);
    };
  }

  private MatcherNode visitArrayLengthCondition(ParseNode content) {
    int conditionSlot = currentScope().newTempSlot();
    pushCvSlot(conditionSlot);
    List<MatcherNode> lengthCondition = switch (content) {
      case ParseNode(String type, ParseNode(String name, ParseNode value)) when type.equals("literal-match") && name.equals("source")
          -> List.of(EqualityMatcherNode.create(NumericTypeMatcherNode.create(), asSingleValueNode(visitSource(value))));
      case ParseNode(String type, List<?> range) when type.equals("range-match") -> visitRangeMatch(NumericTypeMatcherNode.create(), range);
      default -> throw new IllegalStateException("Unexpected value: " + content);
    };
    popCvSlot();
    return ConditionNode.create(conditionSlot,
        MessageNode.create("length", ReadContextValueNode.create(-1, conditionSlot)),
        lengthCondition.size() == 1 ? lengthCondition.getFirst() : AllOfNode.create(lengthCondition));
  }

  private List<MatcherNode> visitRangeMatch(MatcherNode typeBound, List<?> content) {
    List<MatcherNode> bounds = new ArrayList<>();
    int separator = content.indexOf("..");
    if (separator > 0) {
      boolean inclusive = separator == 1;
      ValueNode low = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.getFirst()).content()));
      bounds.add(GreaterThanMatcherNode.create(inclusive, typeBound, low));
    }
    if (separator + 1 < content.size()) {
      boolean inclusive = separator + 2 == content.size();
      ValueNode high = asSingleValueNode(visitSource(
          (ParseNode) ((ParseNode) content.getLast()).content()));
      bounds.add(LessThanMatcherNode.create(inclusive, typeBound, high));
    }
    return bounds;
  }

  private TailspinNode visitSource(ParseNode source) {
    return switch (source) {
      case ParseNode(String name, ParseNode ae) when name.equals("arithmetic-expression") -> visitArithmeticExpression(ae);
      case ParseNode(String name, Object ref) when name.equals("reference") -> visitReference(ref);
      case ParseNode(String name, ParseNode literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, ParseNode vc) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      case ParseNode(String name, Object contents) when name.equals("array-literal") -> visitArrayLiteral(contents);
      case ParseNode(String name, List<?> bounds) when name.equals("range") -> visitRange(bounds);
      case ParseNode(String name, Object contents) when name.equals("structure-literal") -> visitStructureLiteral(contents);
      default -> throw new IllegalStateException("Unexpected value: " + source);
    };
  }

  private ValueNode visitStructureLiteral(Object contents) {
    List<Object> keyValues;
    if (contents instanceof ParseNode(String ignored, ParseNode kv)) {
      keyValues = List.of(kv);
    } else if (contents instanceof ParseNode(String ignored, List<?> kvs)) {
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
      if (sc instanceof ParseNode(String name, List<?> kv) && name.equals("key-value")) {
        String key = ((ParseNode) kv.getFirst()).content().toString();
        keys.add(currentScope().getVocabularyType(key));
        values.add(asSingleValueNode(visitValueChain((ParseNode) kv.getLast())));
      } else if (sc instanceof ParseNode vc) {
        keys.add(null);
        values.add(new TransformResultNode(asTransformNode(visitValueChain(vc))));
      } else throw new IllegalStateException("Unexpected " + sc);
    }
    return StructureLiteral.create(language.rootShape, keys, values);
  }

  private RangeIteration visitRange(List<?> bounds) {
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
      stride = IntegerLiteral.create(1L);
    }
    return RangeIteration.create(currentScope().newTempSlot(), start, inclusiveStart, currentScope().newTempSlot(), end, inclusiveEnd, currentScope().newTempSlot(), stride);
  }

  private ValueNode visitArrayLiteral(Object contents) {
    List<TransformNode> contentNodes = switch (contents) {
      case String bracket when bracket.equals("[") -> List.of();
      case ParseNode(String name, ParseNode value) when name.equals("array-contents")
          -> List.of(asTransformNode(visitValueChain(value)));
      case ParseNode(String name, List<?> values) when name.equals("array-contents") -> {
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
    return ArrayLiteral.create(currentScope().createBuildSlot(), contentNodes);
  }

  private ValueNode visitNumericLiteral(Object literal) {
    return switch (literal) {
      case ParseNode(String name, Long value) when name.equals("INT") -> IntegerLiteral.create(value);
      case ParseNode(String name, BigInteger value) when name.equals("INT") -> BigIntegerLiteral.create(value);
      case ParseNode(String name, SciNum value) when name.equals("NUM") -> SciNumLiteral.create(value);
      case List<?> m when m.size() == 2 -> MeasureLiteral.create(visitNumericLiteral(m.getFirst()), visitUnit(m.getLast()));
      default -> throw new IllegalStateException("Unexpected value: " + literal);
    };
  }

  private final Map<String, String> units = new HashMap<>();

  private Object visitUnit(Object unitSpec) {
    if (unitSpec instanceof String ignored) return Measure.SCALAR;
    if (!(unitSpec instanceof ParseNode p && p.name().equals("unit"))) throw new IllegalStateException("Unexpected value: " + unitSpec);
    List<?> items;
    if (p.content() instanceof List<?> l) items = l;
    else items = List.of(p.content());
    StringBuilder unit = new StringBuilder();
    while (!items.isEmpty()) {
      Object item = items.getFirst();
      items = items.subList(1, items.size());
      if (item instanceof ParseNode(String mp, ParseNode(String ignored, String part)) && mp.equals("measure-product")) {
        unit.append(part).append(' ');
      } else if (item instanceof ParseNode(String md, Object denominator) && md.equals("measure-denominator")) {
        unit.deleteCharAt(unit.length() - 1).append('/');
        if (denominator instanceof List<?> l) items = l;
        else items = List.of(denominator);
      } else throw new IllegalStateException("Unexpected value: " + item);
    }
    String s = unit.deleteCharAt(unit.length() - 1).toString();
    String result = units.putIfAbsent(s, s);
    return result == null ? s : result;
  }

  private TailspinNode visitReference(Object ref) {
    boolean readsState = false;
    List<?> predicate = List.of();
    TailspinNode value = switch (ref) {
      case String s when s.equals("$") -> ReadContextValueNode.create(-1, currentValueSlot());
      case List<?> l when l.getFirst().equals("$") && l.get(1) instanceof ParseNode(String ignored, String identifier) -> {
        Object source = currentScope().getSource(identifier, -1);
        if (source instanceof Reference reference) {
          predicate = l.subList(2, l.size());
          yield ReadContextValueNode.create(reference);
        } else if (source instanceof Templates templates){
          if (l.size() > 2) throw new IllegalStateException("Cannot have lens expressions or message sends on call to " + identifier);
          yield SendToTemplatesNode.create(VoidValue.create(), scopes.size(), templates);
        } else {
          throw new IllegalStateException("Cannot use " + identifier + " as a source");
        }
      }
      case List<?> l when l.getFirst().equals("$") && l.get(1).equals("@") -> {
        readsState = true;
        predicate = l.subList(2, l.size());
        String scopeId = null;
        if (!predicate.isEmpty() && predicate.getFirst() instanceof ParseNode(String ignored, String identifier)) {
          scopeId = identifier;
          predicate = predicate.subList(1, predicate.size());
        }
        yield ReadContextValueNode.create(currentScope().accessState(scopeId), STATE_SLOT);
      }
      case List<?> l when l.getFirst().equals("$") -> {
        predicate = l.subList(1, l.size());
        yield ReadContextValueNode.create(-1, currentValueSlot());
      }
      default -> throw new IllegalStateException("Unexpected value: " + ref);
    };
    if (!predicate.isEmpty() && predicate.getFirst() instanceof ParseNode(String type, Object lensExpression) && type.equals("lens-expression")){
      value = visitReadLensExpression(asSingleValueNode(value), lensExpression);
    }
    if (readsState) {
      value = ReadStateNode.create((ValueNode) value);
    }
    if (!predicate.isEmpty() && predicate.getLast() instanceof ParseNode(String sendType, ParseNode(String ignored, String message))
        && sendType.equals("message-send")){
      value = MessageNode.create(message, asSingleValueNode(value));
    }
    return value;
  }

  private ValueNode visitReadLensExpression(ValueNode target, Object lensExpression) {
    return switch (lensExpression) {
      case ParseNode(String type, ParseNode source) when type.equals("source")
          -> ArrayReadNode.create(target, asSingleValueNode(visitSource(source)));
      case ParseNode(String type, ParseNode(String ignored, String key)) when type.equals("key")
          -> StructureReadNode.create(target, currentScope().getVocabularyType(key));
      case List<?> dimension -> {
        ValueNode thisDimension = visitReadLensExpression(target, dimension.getFirst());
        yield visitReadLensExpression(thisDimension, ((ParseNode) dimension.getLast()).content());
      }
      default -> throw new IllegalStateException("Unexpected value: " + lensExpression);
    };
  }

  private ValueNode visitArithmeticExpression(ParseNode ae) {
    return switch (ae) {
      case ParseNode(String name, Object literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, List<?> addition) when name.equals("addition") -> visitAddition(addition);
      case ParseNode(String name, List<?> multiplication) when name.equals("multiplication") -> visitMultiplication(multiplication);
      case ParseNode(String name, ParseNode(String ignored, ParseNode square)) when name.equals("square-root") -> SquareRootNode.create(visitTerm(square));
      // We also handle term here just to simplify the recursive expression parsing
      case ParseNode(String name, ParseNode term) when name.equals("term") -> visitTerm(term);
      default -> throw new IllegalStateException("Unexpected value: " + ae);
    };
  }

  private ValueNode visitAddition(List<?> addition) {
    ValueNode left = visitArithmeticExpression((ParseNode) addition.getFirst());
    ValueNode right = visitArithmeticExpression((ParseNode) addition.getLast());
    if(addition.get(1).equals("+")) {
      return AddNode.create(left, right);
    } else if(addition.get(1).equals("-")) {
      return SubtractNode.create(left, right);
    }
    throw new IllegalStateException("Unexpected value: " + addition);
  }

  private ValueNode visitMultiplication(List<?> multiplication) {
    ValueNode left = visitArithmeticExpression((ParseNode) multiplication.getFirst());
    ValueNode right = visitArithmeticExpression((ParseNode) multiplication.getLast());
    if(multiplication.get(1).equals("*")) {
      return MultiplyNode.create(left, right);
    } else if(multiplication.get(1).equals("/")) {
      return DivideNode.create(left, right);
    } else if(multiplication.get(1).equals("~/")) {
      return TruncateDivideNode.create(left, right);
    } else if(multiplication.get(1).equals("mod")) {
      return MathModNode.create(left, right);
    }
    throw new IllegalStateException("Unexpected value: " + multiplication);
  }

  private ValueNode visitTerm(ParseNode term) {
    return switch (term) {
      case ParseNode(String name, Object literal) when name.equals("numeric-literal") -> visitNumericLiteral(literal);
      case ParseNode(String name, Object ref) when name.equals("reference") -> asSingleValueNode(visitReference(ref));
      case ParseNode(String name, ParseNode vc) when name.equals("single-value-chain") -> asSingleValueNode(visitValueChain(vc));
      case ParseNode(String name, ParseNode(String ignored, ParseNode square)) when name.equals("square-root") -> SquareRootNode.create(visitTerm(square));
      default -> throw new IllegalStateException("Unexpected value: " + term);
    };
  }
}
