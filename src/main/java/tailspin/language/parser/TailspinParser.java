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
     program rule (<|ignorable-text>?) <|statement>+
     
     statement rule <|definition|set-state|templates|type-def|terminated-chain> (<|ignorable-text>?)
     comment rule (<|'--.*(\\R|\\z)'>)
     ignorable-text rule (<|WS|comment>+)
     definition rule <|ID> (<|ignorable-text> <|='is'> <|ignorable-text>) <|value-chain> (<|=';'> <|ignorable-text>?)
     type-def rule <|ID> (<|ignorable-text> <|='requires'> <|ignorable-text> <|='<'>) <|='~'>? (<|ignorable-text>?) <|membrane>+ (<|='>'> <|ignorable-text>?)
     set-state rule <|='..|'>? (<|='@'>) <|ID>? <|lens-expression>? (<|ignorable-text>? <|='set'> <|ignorable-text>?) <|value-chain> (<|=';'> <|ignorable-text>?)
     
     terminated-chain rule <|value-chain> <|emit|sink|accumulator-state>
     emit rule (<|ignorable-text>? <|='!'>)
     sink rule (<|ignorable-text>? <|='->'> <|ignorable-text>? <|='!'> <|ignorable-text>?) <|='VOID'|='#'|templates-call>
     accumulator-state rule (<|ignorable-text>? <|='->'> <|ignorable-text>?) <|set-state>
     
     value-chain rule <|range|source> <|transform|stream>*
     source rule <|arithmetic-expression|reference|single-value-chain|array-literal|structure-literal|string-literal> (<|ignorable-text>?)
     reference rule <|='$'> <|='@'>? <|ID>? <|lens-expression>? <|message-send>?
     single-value-chain rule (<|='('> <|ignorable-text>?) <|value-chain> (<|ignorable-text>? <|=')'>) <|='"1"'|unit>?
     range rule <|range-bound> <|='~'>? <|='..'> <|='~'>? (<|ignorable-text>?) <|range-bound> <|stride>? (<|ignorable-text>?)
     stride rule (<|=':'> <|ignorable-text>?) <|range-bound> (<|ignorable-text>?)
     string-literal rule <|=''''> <|string-part|=''''''|='$$'|unicode-bytes|codepoint|interpolate>* (<|=''''> <|ignorable-text>?)
     string-part rule <|'[^''$]+'>
     unicode-bytes rule (<|='$#U+'>) <|'[0-9a-fA-F]+'> (<|=';'>)
     codepoint rule (<|='$#'> <|ignorable-text>?) <|value-chain> (<|=';'>)
     interpolate rule (<|='$:'|'(?=\\$)'> <|ignorable-text>?) <|value-chain> (<|=';'>)
     
     lens-expression rule (<|='('> <|ignorable-text>?) <|lens-dimension>  <|lens-transform>? (<|=')'>)
     lens-transform rule (<|=';'> <|ignorable-text>?) <|transform>+
     lens-dimension rule <|lens-range|source|key> (<|ignorable-text>?) <|index-variable>? <|next-lens-dimension>? (<|ignorable-text>?)
     index-variable rule (<|='as'> <|ignorable-text>) <|ID> (<|ignorable-text>?)
     key rule <|ID> (<|=':'> <|ignorable-text>?)
     next-lens-dimension rule (<|ignorable-text>? <|=';'> <|ignorable-text>?) <|lens-dimension>
     lens-range rule <|range-bound>? <|='~'>? <|='..'> <|='~'>? (<|ignorable-text>?) <|range-bound>? <|stride>?
     
     message-send rule (<|='::'>) <|ID>

     array-literal rule <|='['|array-contents>? (<|ignorable-text>? <|=']'> <|ignorable-text>?)
     array-contents rule (<|='['> <|ignorable-text>?) <|value-chain> (<|ignorable-text>?) <|more-array-contents>*
     more-array-contents rule (<|=','> <|ignorable-text>?) <|value-chain> (<|ignorable-text>?)
     
     structure-literal rule <|='{'|key-values> (<|ignorable-text>? <|='}'> <|ignorable-text>?)
     key-values rule (<|='{'> <|ignorable-text>?) <|key-value|value-chain> <|additional-key-value>*
     key-value rule <|ID> (<|=':'> <|ignorable-text>?) <|value-chain>
     additional-key-value rule (<|=','> <|ignorable-text>?) <|key-value|value-chain>

     transform rule (<|='->'> <|ignorable-text>?) <|source|range|inline-templates-call|='#'|templates-call|filter> (<|ignorable-text>?)
     templates-call rule <|ID>
     inline-templates-call rule (<|='templates'> <|ignorable-text>) <|templates-body>  (<|='end'> <|ignorable-text>?)
     templates rule (name is <|ID>; <|ignorable-text>) <|='templates'|='source'|='sink'> (<|ignorable-text>) <|templates-body>  (<|='end'> <|ignorable-text>) <|=$name>
     filter rule (<|='if'> <|ignorable-text>?) <|matcher>
     stream rule <|='...'> (<|ignorable-text>?)
     
     templates-body rule <|with-block|matchers>
     with-block rule <|statement>+ <|matchers>?
     
     matchers rule <|match-template>+
     match-template rule <|when-do|otherwise> <|statement>+
     otherwise rule <|='otherwise'> (<|ignorable-text>?)
     when-do rule (<|='when'> <|ignorable-text>?) <|matcher>  (<|='do'> <|ignorable-text>?)
     matcher rule <|type-bound>? (<|='<'>) <|='~'>? (<|ignorable-text>?) <|membrane>+ (<|='>'> <|ignorable-text>?)
     membrane rule (<|='|'>) <|literal-match|type-match>? (<|ignorable-text>?) <|condition>*
     condition rule (<|='?('> <|ignorable-text>?) <|value-chain> (<|='matches'> <|ignorable-text>) <|matcher> (<|=')'> <|ignorable-text>?)
     type-bound rule (<|='´'>) <|membrane>+ (<|='´'> <|ignorable-text>?)

     literal-match rule (<|='='> <|ignorable-text>?) <|source>
     type-match rule <|range-match|array-match|structure-match|measure-type-match|string-literal>
     range-match rule <|range-bound>? <|='~'>? <|='..'> <|='~'>? (<|ignorable-text>?) <|range-bound>?
     range-bound rule <|arithmetic-expression|reference>
     array-match rule <|='['> (<|ignorable-text>?) <|array-content-matcher>* (<|=']'> <|ignorable-text>?) <|array-length-condition>?
     array-content-matcher rule <|matcher>
     array-length-condition rule (<|='('> <|ignorable-text>?) <|literal-match|range-match> (<|ignorable-text>? <|=')'> <|ignorable-text>?)
     structure-match rule <|='{'|key-matchers> (<|ignorable-text>?) <|='VOID'>? (<|ignorable-text>? <|='}'> <|ignorable-text>?)
     key-matchers rule (<|='{'> <|ignorable-text>?) <|key-matcher> <|additional-key-matcher>*
     key-matcher rule <|='?'>? <|ID> (<|=':'> <|ignorable-text>?) <|content-matcher>?
     additional-key-matcher rule (<|=','> <|ignorable-text>?) <|key-matcher>
     content-matcher rule (<|='<'>) <|='~'>? (<|ignorable-text>?) <|membrane>+ (<|='>'> <|ignorable-text>?)
     measure-type-match rule <|='""'|unit> (<|ignorable-text>?)
     
     arithmetic-expression rule <|addition|multiplication|numeric-literal|square-root|negated-term>
     addition rule <|addition|multiplication|term> <|'[+-]'> (<|ignorable-text>?) <|multiplication|term> (<|ignorable-text>?)
     multiplication rule <|multiplication|term> <|'\\*|/|~/|mod'> (<|ignorable-text>?) <|term> (<|ignorable-text>?)
     square-root rule (<|='√'>) <|term>
     numeric-literal rule <|INT|NUM> <|='"1"'|unit>?
     term rule <|numeric-literal|negated-term|single-value-chain|reference|square-root> (<|ignorable-text>?)
     negated-term rule (<|='-'>) <|single-value-chain|reference|square-root> (<|ignorable-text>?)
     
     unit rule (<|='"'>) <|measure-product>+ <|measure-denominator>? (<|='"'> <|ignorable-text>?)
     measure-product rule <|ID> (<|ignorable-text>?)
     measure-denominator rule (<|='/'> <|ignorable-text>?) <|measure-product>+
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
