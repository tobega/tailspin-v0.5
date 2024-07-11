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
     program rule (<WS>?) <statement>+ (<WS>?)
     
     statement rule <emit|definition> (<WS>?)
     emit rule <value-chain> (<WS>? <='!'>)
     definition rule (<='def'> <WS>) <'(?U)\\w[-\\w]*'> (<WS>? <=':'> <WS>?) <value-chain> (<=';'> <WS>?)
     
     value-chain rule <source> <transform>*
     source rule <arithmetic-expression|reference|single-value-chain> (<WS>?)
     reference rule <='$'> <'(?U)\\w[-\\w]*'>?
     single-value-chain rule (<='('> <WS>?) <value-chain> (<WS>? <=')'>)

     transform rule (<='->'> <WS>?) <source|inline-templates-call|='#'> (<WS>?)
     
     inline-templates-call rule (<='templates'> <WS>) <anonymous-templates-body>
     anonymous-templates-body rule <with-block|matchers> (<='end'> <WS>?)
     with-block rule <statement>+ <matchers>?
     
     matchers rule <match-template>+
     match-template rule <when-do|otherwise> <statement>+
     otherwise rule <='otherwise'> (<WS>?)
     when-do rule (<='when'> <WS>? <='<'> <WS>?) <membrane> <else-membrane>* (<='>'> <WS>? <='do'> <WS>?)
     else-membrane rule (<='|'> <WS>?) <membrane>
     membrane rule <literal-match|type-match> (<WS>?)
     literal-match rule (<='='> <WS>?) <source>
     type-match rule <range-match>
     range-match rule <arithmetic-expression>? <='~'>? <='..'> <='~'>? (<WS>?) <arithmetic-expression>?
     
     arithmetic-expression rule <addition|multiplication|numeric-literal>
     addition rule <addition|multiplication|term> <'[+-]'> (<WS>?) <multiplication|term> (<WS>?)
     multiplication rule <multiplication|term> <'\\*|~/|mod'> (<WS>?) <term> (<WS>?)
     numeric-literal rule <INT>
     term rule <numeric-literal|single-value-chain|reference> (<WS>?)
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
