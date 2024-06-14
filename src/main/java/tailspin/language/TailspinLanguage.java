package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.factory.NodeFactory;
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
    ParseNode parseNode = TailspinParser.parse(request.getSource().getReader());
    return new NodeFactory(this).createCallTarget(parseNode);
  }

  @Override
  protected TailspinLanguageContext createContext(Env env) {
    return new TailspinLanguageContext();
  }
}
