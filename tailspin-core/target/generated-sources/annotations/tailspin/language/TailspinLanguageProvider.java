// CheckStyle: start generated
package tailspin.language;

import com.oracle.truffle.api.TruffleLanguage.Registration;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.provider.TruffleLanguageProvider;
import java.util.Collection;
import java.util.List;

@GeneratedBy(TailspinLanguage.class)
@Registration(id = "tt", name = "Tailspin")
public final class TailspinLanguageProvider extends TruffleLanguageProvider {

    @Override
    protected String getLanguageClassName() {
        return "tailspin.language.TailspinLanguage";
    }

    @Override
    protected Object create() {
        return new TailspinLanguage();
    }

    @Override
    protected Collection<String> getServicesClassNames() {
        return List.of();
    }

    @Override
    protected List<?> createFileTypeDetectors() {
        return List.of();
    }

    @Override
    protected List<String> getInternalResourceIds() {
        return List.of();
    }

    @Override
    protected Object createInternalResource(String resourceId) {
        throw new IllegalArgumentException(String.format("Unsupported internal resource id %s, supported ids are ", resourceId));
    }

}
