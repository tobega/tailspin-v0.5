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
import tailspin.language.parser.composer.SequenceSubComposer;
import tailspin.language.parser.composer.SubComposerFactory;

public class TailspinParser {

  static final String tailspinSyntax = """
     program rule (<|WS>?) <|statement>+ (<|WS>?)
     
     statement rule <|emit|definition|set-state|templates|sink> (<|WS>?)
     emit rule <|value-chain> (<|WS>? <|='!'>)
     definition rule <|ID> (<|WS> <|='is'> <|WS>) <|value-chain> (<|=';'> <|WS>?)
     set-state rule (<|='@'>) <|ID>? <|lens-expression>? (<|WS>? <|='set'> <|WS>?) <|value-chain> (<|=';'> <|WS>?)
     sink rule <|value-chain> (<|WS>? <|='->'> <|WS>? <|='!'> <|WS>?) <|='VOID'|='#'|templates-call>
     
     value-chain rule <|source> <|transform>*
     source rule <|arithmetic-expression|reference|single-value-chain|array-literal|range|structure-literal> (<|WS>?)
     reference rule <|='$'> <|='@'>? <|ID>? <|lens-expression>? <|message-send>?
     single-value-chain rule (<|='('> <|WS>?) <|value-chain> (<|WS>? <|=')'>)
     range rule <|range-bound> <|='~'>? <|='..'> <|='~'>? (<|WS>?) <|range-bound> <|stride>?
     stride rule (<|=':'> <|WS>?) <|range-bound> (<|WS>?)
     
     lens-expression rule (<|='('> <|WS>?) <|source|key> <|next-lens-dimension>? (<|WS>? <|=')'>)
     key rule <|ID> (<|=':'> <|WS>?)
     next-lens-dimension rule (<|WS>? <|=';'> <|WS>?) <|source|key> <|next-lens-dimension>?
     
     message-send rule (<|='::'>) <|ID>

     array-literal rule <|='['|array-contents>? (<|WS>? <|=']'> <|WS>?)
     array-contents rule (<|='['> <|WS>?) <|value-chain> (<|WS>?) <|more-array-contents>*
     more-array-contents rule (<|=','> <|WS>?) <|value-chain> (<|WS>?)
     
     structure-literal rule <|='{'|key-values> (<|WS>? <|='}'> <|WS>?)
     key-values rule (<|='{'> <|WS>?) <|key-value> <|additional-key-value>*
     key-value rule <|ID> (<|=':'> <|WS>?) <|value-chain>
     additional-key-value rule (<|=','> <|WS>?) <|key-value>

     transform rule (<|='->'> <|WS>?) <|source|inline-templates-call|='#'|templates-call> (<|WS>?)
     templates-call rule <|ID>
     inline-templates-call rule (<|='templates'> <|WS>) <|templates-body>  (<|='end'> <|WS>?)
     templates rule (name is <|ID>; <|WS>) <|='templates'|='source'|='sink'> (<|WS>) <|templates-body>  (<|='end'> <|WS>) <|=$name>
     
     templates-body rule <|with-block|matchers>
     with-block rule <|statement>+ <|matchers>?
     
     matchers rule <|match-template>+
     match-template rule <|when-do|otherwise> <|statement>+
     otherwise rule <|='otherwise'> (<|WS>?)
     when-do rule (<|='when'> <|WS>? <|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>? <|='do'> <|WS>?)
     membrane rule (<|='|'>) <|literal-match|type-match>? (<|WS>?) <|condition>*
     condition rule (<|='?('> <|WS>?) <|value-chain> (<|='matches'> <|WS> <|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>? <|=')'> <|WS>?)

     literal-match rule (<|='='> <|WS>?) <|source>
     type-match rule <|range-match|array-match|structure-match>
     range-match rule <|range-bound>? <|='~'>? <|='..'> <|='~'>? (<|WS>?) <|range-bound>?
     range-bound rule <|arithmetic-expression|reference>
     array-match rule <|='['> (<|WS>?) (<|=']'> <|WS>?) <|array-length-condition>?
     array-length-condition rule (<|='('> <|WS>?) <|literal-match|range-match> (<|WS>? <|=')'> <|WS>?)
     structure-match rule <|='{'|key-matchers> (<|WS>? <|='}'> <|WS>?)
     key-matchers rule (<|='{'> <|WS>?) <|key-matcher> <|additional-key-matcher>*
     key-matcher rule <|ID> (<|=':'> <|WS>?) <|content-matcher>?
     additional-key-matcher rule (<|=','> <|WS>?) <|key-matcher>
     content-matcher rule (<|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>?)
     
     arithmetic-expression rule <|addition|multiplication|numeric-literal>
     addition rule <|addition|multiplication|term> <|'[+-]'> (<|WS>?) <|multiplication|term> (<|WS>?)
     multiplication rule <|multiplication|term> <|'\\*|~/|mod'> (<|WS>?) <|term> (<|WS>?)
     numeric-literal rule <|INT>
     term rule <|numeric-literal|single-value-chain|reference> (<|WS>?)
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
        new NamedComposition(rule)), new ParseNodeScope(null), resolver);
    Memo end = composer.nibble(s, Memo.root(0));
    if (end.pos != s.length()) throw new AssertionError("Parse failed at \n" + s.substring(end.pos));
    return  (ParseNode) ParseNode.normalizeValues(composer.getValues());
  }
}
