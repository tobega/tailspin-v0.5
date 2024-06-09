package tailspin.language.parser;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import tailspin.language.parser.composer.CompositionSpec;
import tailspin.language.parser.composer.CompositionSpec.NamedComposition;
import tailspin.language.parser.composer.CompositionSpec.Resolver;
import tailspin.language.parser.composer.Memo;
import tailspin.language.parser.composer.Scope;
import tailspin.language.parser.composer.SequenceSubComposer;
import tailspin.language.parser.composer.SubComposerFactory;

public class TailspinParser {

  static final String tailspinSyntax = """
     rule arithmeticExpression: <addition|multiplication|term>
     rule addition: <addition|multiplication|term> (<WS>?) <'[+-]'> (<WS>?) <multiplication|term>
     rule multiplication: <multiplication|term> (<WS>?) <'\\*|~/|mod'> (<WS>?) <term>
     rule term: <INT|parentheses>
     rule parentheses: (<='('> <WS>?) <addition|multiplication|term> (<WS>? <=')'>)
     """;

  static final Map<String, List<CompositionSpec>> syntaxRules = ParserParser.createSyntaxRules(tailspinSyntax);

  public static List<ParseNode> parse(String s) {
    return null;
  }

  public static List<ParseNode> parse(Reader reader) {
    return null;
  }

  public static List<ParseNode> parseRule(String rule, String s) {
    Resolver resolver = new ParseComposerFactory(new SubComposerFactory(syntaxRules));
    SequenceSubComposer composer = new SequenceSubComposer(List.of(
        new NamedComposition(rule)), new Scope(null), resolver);
    Memo end = composer.nibble(s, Memo.root(0));
    if (end.pos != s.length()) throw new AssertionError("Parse failed at \n" + s.substring(end.pos));
    @SuppressWarnings("unchecked")
    List<ParseNode> parseNodes = (List<ParseNode>) composer.getValues();
    return parseNodes;
  }
}
