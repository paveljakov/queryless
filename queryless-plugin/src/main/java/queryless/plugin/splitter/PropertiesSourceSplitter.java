package queryless.plugin.splitter;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;

import queryless.plugin.query.model.Query;
import queryless.plugin.source.model.Source;

@Component(role = SourceSplitter.class, hint = "properties")
public class PropertiesSourceSplitter implements SourceSplitter {

    @Override
    public List<Query> split(final Source source) {
        return null;
    }

    @Override
    public String[] getSupportedExtensions() {
        return new String[] {"properties", "xml"};
    }

}
