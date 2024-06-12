package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import java.util.List;
import tailspin.language.parser.ParseNode;
import tailspin.language.parser.TailspinParser;

@TruffleLanguage.Registration(id = "tt", name = "Tailspin")
public class TailspinLanguage extends TruffleLanguage<TailspinLanguageContext> {
  private static final LanguageReference<TailspinLanguage> REF =
      LanguageReference.create(TailspinLanguage.class);

  /** Retrieve the current language instance for the given {@link Node}. */
  public static TailspinLanguage get(Node node) {
    return REF.get(node);
  }

  @Override
  protected CallTarget parse(ParsingRequest request) {
    List<ParseNode> parseNodes = TailspinParser.parse(request.getSource().getReader());
    // TODO: convert parse nodes to CallTarget
    return null;
  }

  @Override
  protected TailspinLanguageContext createContext(Env env) {
    return new TailspinLanguageContext();
  }
}
