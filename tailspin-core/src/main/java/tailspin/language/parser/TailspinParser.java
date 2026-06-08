package tailspin.language.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
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

  static final String tailspinSyntax =
      """
     program rule (?<|ignorable-text>) <|statements>

     statement rule <|definition|set-state|templates|type-def|terminated-chain> (?<|ignorable-text>)
     block rule <|do-nothing|statements>
     statements rule +<|statement>
     comment rule (<|'--.*(\\R|\\z)'>)
     ignorable-text rule (+<|WS|comment>)
     do-nothing rule <|='VOID'> (?<|ignorable-text>)
     definition rule <|ID> (<|ignorable-text> <|='is'> <|ignorable-text>) <|value-chain> (<|=';'> ?<|ignorable-text>)
     type-def rule <|ID> (<|ignorable-text> <|='requires'> <|ignorable-text> <|='<'>) ?<|='~'> (?<|ignorable-text>) +<|membrane> (<|='>'> ?<|ignorable-text>)
     set-state rule ?<|='..\\'> (<|='@'>) ?<|ID> ?<|lens-expression> (?<|ignorable-text> <|='set'> ?<|ignorable-text>) <|value-chain> (<|=';'> ?<|ignorable-text>)

     terminated-chain rule <|value-chain> <|emit|sink|accumulator-state>
     emit rule (?<|ignorable-text> <|='!'>)
     sink rule (?<|ignorable-text> <|='->'> ?<|ignorable-text> <|='!'> ?<|ignorable-text>) <|='VOID'|='REJECT'|='#'|templates-call>
     accumulator-state rule (?<|ignorable-text> <|='->'> ?<|ignorable-text>) <|set-state>

     value-chain rule <|range|source> *<|transform|stream>

     simple-value rule <|non-numeric|numeric-literal|parenthesized-expression>
     non-numeric rule <|structure-literal|string-literal|array-literal>
     source rule <|type-cast|numericish|non-numeric>
     reference rule <|='$'> ?<|='@'> ?<|ID> ?<|lens-expression> ?<|message-send>
     parenthesized-expression rule (<|='('> ?<|ignorable-text>) <|numericish> (?<|ignorable-text> <|=')'>) ?<|='"1"'|unit>
     range rule <|term> ?<|='~'> <|='..'> ?<|='~'> (?<|ignorable-text>) <|term> ?<|stride> (?<|ignorable-text>)
     stride rule (<|=':'> ?<|ignorable-text>) <|term> (?<|ignorable-text>)
     string-literal rule <|=''''> *<|string-part|=''''''|='$$'|unicode-bytes|codepoint|interpolate> (<|=''''> ?<|ignorable-text>)
     string-part rule <|'[^''$]+'>
     unicode-bytes rule (<|='$#U+'>) <|'[0-9a-fA-F]+'> (<|=';'>)
     codepoint rule (<|='$#'> ?<|ignorable-text>) <|value-chain> (<|=';'>)
     interpolate rule (<|='$:'|'(?=\\$)'> ?<|ignorable-text>) <|value-chain> (<|=';'>)

     type-cast rule <|tag> <|simple-value>
     tag rule <|ID> (<|='´'>)

     lens-expression rule (<|='('> ?<|ignorable-text>) <|lens-dimension>  ?<|lens-transform> (<|=')'>)
     lens-transform rule (<|=';'> ?<|ignorable-text>) <|transform> *<|transform|stream>
     lens-dimension rule <|lens-range|numericish|array-literal|key> (?<|ignorable-text>) ?<|index-variable> ?<|next-lens-dimension> (?<|ignorable-text>)
     index-variable rule (<|='as'> <|ignorable-text>) <|ID> (?<|ignorable-text>)
     key rule <|ID> (<|=':'> ?<|ignorable-text>)
     next-lens-dimension rule (?<|ignorable-text> <|=';'> ?<|ignorable-text>) <|lens-dimension>
     lens-range rule ?<|term> ?<|='~'> <|='..'> ?<|='~'> (?<|ignorable-text>) ?<|term> ?<|stride>

     message-send rule (<|='::'>) <|ID>

     array-literal rule <|array-contents|='['> (?<|ignorable-text> <|=']'> ?<|ignorable-text>)
     array-contents rule (<|='['> ?<|ignorable-text>) <|value-chain> (?<|ignorable-text>) *<|more-array-contents>
     more-array-contents rule (<|=','> ?<|ignorable-text>) <|value-chain> (?<|ignorable-text>)

     structure-literal rule <|key-values|='{'> (?<|ignorable-text> <|='}'> ?<|ignorable-text>)
     key-values rule (<|='{'> ?<|ignorable-text>) <|key-value|value-chain> *<|additional-key-value>
     key-value rule <|ID> (<|=':'> ?<|ignorable-text>) <|value-chain>
     additional-key-value rule (<|=','> ?<|ignorable-text>) <|key-value|value-chain>

     transform rule (<|='->'> ?<|ignorable-text>) <|range|source|inline-templates-call|='#'|filter|templates-call> (?<|ignorable-text>)
     try rule <|='try'> (<|ignorable-text>)
     templates-call rule ?<|try> <|ID>
     inline-templates-call rule ?<|try> ?<|auxiliary> (<|='templates'> <|ignorable-text>) ?<|precondition> <|templates-body>  (<|='end'> ?<|ignorable-text>)
     templates rule (name is <|ID>; <|ignorable-text>) ?<|auxiliary> <|='templates'|='source'|='sink'> (<|ignorable-text>) ?<|precondition> <|templates-body>  (<|='end'> <|ignorable-text>) <|=$name>
     auxiliary rule <|='auxiliary'> (<|ignorable-text>)
     filter rule (<|='if'> ?<|ignorable-text>) <|matcher>
     stream rule <|='...'> (?<|ignorable-text>)

     precondition rule (<|='requires'> <|ignorable-text>) <|matcher> (<|ignorable-text>)
     templates-body rule <|with-block|matchers>
     with-block rule <|block> ?<|matchers>

     matchers rule +<|match-template>
     match-template rule <|when-do|otherwise> <|block>
     otherwise rule <|='otherwise'> (?<|ignorable-text>)
     when-do rule (<|='when'> ?<|ignorable-text>) <|matcher>  (<|='do'> ?<|ignorable-text>)
     matcher rule ?<|type-bound> (<|='<'>) ?<|='~'> (?<|ignorable-text>) +<|membrane> (<|='>'> ?<|ignorable-text>)
     membrane rule (<|='|'>) ?<|literal-match|type-match> (?<|ignorable-text>) *<|condition>
     condition rule (<|='?('> ?<|ignorable-text>) <|value-chain> (<|='matches'> <|ignorable-text>) <|matcher> (<|=')'> ?<|ignorable-text>)
     type-bound rule (<|='´'>) +<|membrane> (<|='´'> ?<|ignorable-text>)

     literal-match rule (<|='='> ?<|ignorable-text>) <|source>
     type-match rule <|range-match|array-match|structure-match|measure-type-match|string-literal|ID>
     range-match rule ?<|tag> ?<|term> ?<|='~'> <|='..'> ?<|='~'> (?<|ignorable-text>) ?<|tag> ?<|term>
     array-match rule <|='['> (?<|ignorable-text>) *<|array-content-matcher> (<|=']'> ?<|ignorable-text>) ?<|array-length-condition>
     array-content-matcher rule <|matcher>
     array-length-condition rule (<|='('> ?<|ignorable-text>) <|literal-match|range-match> (?<|ignorable-text> <|=')'> ?<|ignorable-text>)
     structure-match rule <|key-matchers|='{'> (?<|ignorable-text>) ?<|='VOID'> (?<|ignorable-text> <|='}'> ?<|ignorable-text>)
     key-matchers rule (<|='{'> ?<|ignorable-text>) <|key-matcher> *<|additional-key-matcher>
     key-matcher rule ?<|='?'> <|ID> (<|=':'> ?<|ignorable-text>) ?<|content-matcher>
     additional-key-matcher rule (<|=','> ?<|ignorable-text>) <|key-matcher>
     content-matcher rule (<|='<'>) ?<|='~'> (?<|ignorable-text>) +<|membrane> (<|='>'> ?<|ignorable-text>)
     measure-type-match rule <|='""'|='"1"'|unit> (?<|ignorable-text>)

     numericish rule <|term> *<|multiplication> *<|addition> (?<|ignorable-text>)
     multiplication rule <|'\\*|/|~/|mod'> (?<|ignorable-text>) <|term>
     addition rule <|'[+-]'> (?<|ignorable-text>) <|term> *<|multiplication>
     term rule ?<|='\\'> <|numeric-literal|signed-numeric-expression> ?<|='\\'> (?<|ignorable-text>)
     signed-numeric-expression rule ?<|='-'> <|numeric-expression>
     numeric-expression rule <|parenthesized-expression|reference|square-root> (?<|ignorable-text>)
     square-root rule (<|='√'>) <|term>
     numeric-literal rule <|NUM|INT> ?<|='"1"'|unit> (?<|ignorable-text>)

     unit rule (<|='"'>) +<|measure-product> ?<|measure-denominator> (<|='"'> ?<|ignorable-text>)
     measure-product rule <|ID> (?<|ignorable-text>)
     measure-denominator rule (<|='/'> ?<|ignorable-text>) +<|measure-product>
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
    if (end.pos != s.length()) {
      int unparsedEnd = Math.min(s.length(), end.maxParsed + 20);
      int parsedStart = Math.max(0, end.maxParsed - 20);
      throw new AssertionError("Parse failed at\n" + s.substring(end.maxParsed, unparsedEnd) + "\nafter\n" + s.substring(parsedStart, end.maxParsed));
    }
    return  (ParseNode) ParseNode.normalizeValues(composer.getValues());
  }

  public static void main(String[] args) throws IOException {
    String source = Files.readString(Path.of(args[0]));
    System.out.println(parse(source));
  }
}
