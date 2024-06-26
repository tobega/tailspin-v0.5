package tailspin.language.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.CompositionSpec.Resolver;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.Scope;
import tailspin.language.parser.composer.SequenceSubComposer;
import tailspin.language.parser.composer.SubComposerFactory;

public class TailspinParser {

  static final String tailspinSyntax = """
     rule program: (<WS>?) <statement>+ (<WS>?)
     
     rule statement: <emit> (<WS>?)
     rule emit: <value-chain> (<WS>? <='!'>)
     
     rule value-chain: <source> <transform>*
     rule source: <arithmetic-expression|reference|single-value-chain> (<WS>?)
     rule reference: <='$'>
     rule single-value-chain: (<='('> <WS>?) <value-chain> (<WS>? <=')'>)

     rule transform: (<='->'> <WS>?) <source|inline-templates-call|='#'> (<WS>?)
     
     rule inline-templates-call: (<='templates'> <WS>) <anonymous-templates-body>
     rule anonymous-templates-body: <with-block|matchers> (<='end'> <WS>?)
     rule with-block: <statement>+ <matchers>?
     
     rule matchers: <match-template>+
     rule match-template: <when-do|otherwise> <statement>+
     rule otherwise: <='otherwise'> (<WS>?)
     rule when-do: (<='when'> <WS>? <='<'> <WS>?) <membrane> <else-membrane>* (<='>'> <WS>? <='do'> <WS>?)
     rule else-membrane: (<='|'> <WS>?) <membrane>
     rule membrane: <literal-match|type-match> (<WS>?)
     rule literal-match: (<='='> <WS>?) <source>
     rule type-match: <range-match>
     rule range-match: <arithmetic-expression>? <='~'>? <='..'> <='~'>? (<WS>?) <arithmetic-expression>?
     
     rule arithmetic-expression: <addition|multiplication|numeric-literal>
     rule addition: <addition|multiplication|term> <'[+-]'> (<WS>?) <multiplication|term> (<WS>?)
     rule multiplication: <multiplication|term> <'\\*|~/|mod'> (<WS>?) <term> (<WS>?)
     rule numeric-literal: <INT>
     rule term: <numeric-literal|single-value-chain|reference> (<WS>?)
     """;

  static final Map<String, List<CompositionSpec>> syntaxRules = ParserParser.createSyntaxRules(tailspinSyntax);

  public static ParseNode parse(String s) {
    return parseRule("program", s);
  }

  public static ParseNode parse(Reader reader) {
    try (BufferedReader br = new BufferedReader(reader)) {
      return parse(br.lines().collect(Collectors.joining("\n")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ParseNode parseRule(String rule, String s) {
    Resolver resolver = new ParseComposerFactory(new SubComposerFactory(syntaxRules));
    SequenceSubComposer composer = new SequenceSubComposer(List.of(
        new NamedComposition(rule)), new Scope(null), resolver);
    Memo end = composer.nibble(s, Memo.root(0));
    if (end.pos != s.length()) throw new AssertionError("Parse failed at \n" + s.substring(end.pos));
    return  (ParseNode) ParseNode.normalizeValues(composer.getValues());
  }
}
