package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.factory.NodeFactory;
import tailspin.language.parser.ParseNode;
import tailspin.language.parser.TailspinParser;

@TruffleLanguage.Registration(id = "tt", name = "Tailspin")
public class TailspinLanguage extends TruffleLanguage<TailspinLanguageContext> {
  private static final LanguageReference<TailspinLanguage> REF =
      LanguageReference.create(TailspinLanguage.class);

  public static final SourceSection INTERNAL_CODE_SOURCE = Source.newBuilder("tt", "", "internal").build().createUnavailableSection();

  /** Retrieve the current language instance for the given {@link Node}. */
  public static TailspinLanguage get(Node node) {
    return REF.get(node);
  }

  public final Shape rootShape = Shape.newBuilder().build();

  @Override
  protected CallTarget parse(ParsingRequest request) {
    ParseNode parseNode = TailspinParser.parse(request.getSource().getReader());
    return new NodeFactory(this, request.getSource()).createCallTarget(parseNode);
  }

  @Override
  protected TailspinLanguageContext createContext(Env env) {
    return new TailspinLanguageContext();
  }
}
