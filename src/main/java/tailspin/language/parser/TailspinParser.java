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
     
     statement rule <|definition|set-state|templates|type-def|terminated-chain> (<|WS>?)
     definition rule <|ID> (<|WS> <|='is'> <|WS>) <|value-chain> (<|=';'> <|WS>?)
     type-def rule <|ID> (<|WS> <|='requires'> <|WS> <|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>?)
     set-state rule (<|='@'>) <|ID>? <|lens-expression>? (<|WS>? <|='set'> <|WS>?) <|value-chain> (<|=';'> <|WS>?)
     
     terminated-chain rule <|value-chain> <|emit|sink|accumulator-state>
     emit rule (<|WS>? <|='!'>)
     sink rule (<|WS>? <|='->'> <|WS>? <|='!'> <|WS>?) <|='VOID'|='#'|templates-call>
     accumulator-state rule (<|WS>? <|='->'> <|WS>?) <|set-state>
     
     value-chain rule <|range|source> <|transform|stream>*
     source rule <|arithmetic-expression|reference|single-value-chain|array-literal|structure-literal|string-literal> (<|WS>?)
     reference rule <|='$'> <|='@'>? <|ID>? <|lens-expression>? <|message-send>?
     single-value-chain rule (<|='('> <|WS>?) <|value-chain> (<|WS>? <|=')'>)
     range rule <|range-bound> <|='~'>? <|='..'> <|='~'>? (<|WS>?) <|range-bound> <|stride>? (<|WS>?)
     stride rule (<|=':'> <|WS>?) <|range-bound> (<|WS>?)
     string-literal rule <|=''''> <|string-part|=''''''|='$$'|unicode-bytes|codepoint|interpolate>* (<|=''''> <|WS>?)
     string-part rule <|'[^''$]+'>
     unicode-bytes rule (<|='$#U+'>) <|'[0-9a-fA-F]+'> (<|=';'>)
     codepoint rule (<|='$#'> <|WS>?) <|value-chain> (<|=';'>)
     interpolate rule (<|='$:'|'(?=\\$)'> <|WS>?) <|value-chain> (<|=';'>)
     
     lens-expression rule (<|='('> <|WS>?) <|lens-dimension>  <|lens-transform>? (<|=')'>)
     lens-transform rule (<|=';'> <|WS>?) <|transform>+
     lens-dimension rule <|lens-range|source|key> (<|WS>?) <|index-variable>? <|next-lens-dimension>? (<|WS>?)
     index-variable rule (<|='as'> <|WS>) <|ID> (<|WS>?)
     key rule <|ID> (<|=':'> <|WS>?)
     next-lens-dimension rule (<|WS>? <|=';'> <|WS>?) <|lens-dimension>
     lens-range rule <|range-bound>? <|='~'>? <|='..'> <|='~'>? (<|WS>?) <|range-bound>? <|stride>?
     
     message-send rule (<|='::'>) <|ID>

     array-literal rule <|='['|array-contents>? (<|WS>? <|=']'> <|WS>?)
     array-contents rule (<|='['> <|WS>?) <|value-chain> (<|WS>?) <|more-array-contents>*
     more-array-contents rule (<|=','> <|WS>?) <|value-chain> (<|WS>?)
     
     structure-literal rule <|='{'|key-values> (<|WS>? <|='}'> <|WS>?)
     key-values rule (<|='{'> <|WS>?) <|key-value|value-chain> <|additional-key-value>*
     key-value rule <|ID> (<|=':'> <|WS>?) <|value-chain>
     additional-key-value rule (<|=','> <|WS>?) <|key-value|value-chain>

     transform rule (<|='->'> <|WS>?) <|source|range|inline-templates-call|='#'|templates-call|filter> (<|WS>?)
     templates-call rule <|ID>
     inline-templates-call rule (<|='templates'> <|WS>) <|templates-body>  (<|='end'> <|WS>?)
     templates rule (name is <|ID>; <|WS>) <|='templates'|='source'|='sink'> (<|WS>) <|templates-body>  (<|='end'> <|WS>) <|=$name>
     filter rule (<|='if'> <|WS>? <|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>?)
     stream rule <|='...'> (<|WS>?)
     
     templates-body rule <|with-block|matchers>
     with-block rule <|statement>+ <|matchers>?
     
     matchers rule <|match-template>+
     match-template rule <|when-do|otherwise> <|statement>+
     otherwise rule <|='otherwise'> (<|WS>?)
     when-do rule (<|='when'> <|WS>?) <|type-bound>? (<|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>? <|='do'> <|WS>?)
     membrane rule (<|='|'>) <|literal-match|type-match>? (<|WS>?) <|condition>*
     condition rule (<|='?('> <|WS>?) <|value-chain> (<|='matches'> <|WS> <|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>? <|=')'> <|WS>?)
     type-bound rule (<|='´'>) <|membrane>+ (<|='´'> <|WS>?)

     literal-match rule (<|='='> <|WS>?) <|source>
     type-match rule <|range-match|array-match|structure-match|measure-type-match>
     range-match rule <|range-bound>? <|='~'>? <|='..'> <|='~'>? (<|WS>?) <|range-bound>?
     range-bound rule <|arithmetic-expression|reference>
     array-match rule <|='['> (<|WS>?) (<|=']'> <|WS>?) <|array-length-condition>?
     array-length-condition rule (<|='('> <|WS>?) <|literal-match|range-match> (<|WS>? <|=')'> <|WS>?)
     structure-match rule <|='{'|key-matchers> (<|WS>?) <|='VOID'>? (<|WS>? <|='}'> <|WS>?)
     key-matchers rule (<|='{'> <|WS>?) <|key-matcher> <|additional-key-matcher>*
     key-matcher rule <|='?'>? <|ID> (<|=':'> <|WS>?) <|content-matcher>?
     additional-key-matcher rule (<|=','> <|WS>?) <|key-matcher>
     content-matcher rule (<|='<'> <|WS>?) <|membrane>+ (<|='>'> <|WS>?)
     measure-type-match rule <|='""'|unit> (<|WS>?)
     
     arithmetic-expression rule <|addition|multiplication|numeric-literal|square-root>
     addition rule <|addition|multiplication|term> <|'[+-]'> (<|WS>?) <|multiplication|term> (<|WS>?)
     multiplication rule <|multiplication|term> <|'\\*|/|~/|mod'> (<|WS>?) <|term> (<|WS>?)
     square-root rule (<|='√'>) <|term>
     numeric-literal rule <|INT|NUM> <|='"1"'|unit>?
     term rule <|numeric-literal|single-value-chain|reference|square-root> (<|WS>?)
     
     unit rule (<|='"'>) <|measure-product>+ <|measure-denominator>? (<|='"'> <|WS>?)
     measure-product rule <|ID> (<|WS>?)
     measure-denominator rule (<|='/'> <|WS>?) <|measure-product>+
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
