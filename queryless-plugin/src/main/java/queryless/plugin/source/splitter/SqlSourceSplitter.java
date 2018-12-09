package queryless.plugin.source.splitter;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;

import queryless.plugin.query.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;

@Component(role = SourceSplitter.class, hint = "sql")
public class SqlSourceSplitter implements SourceSplitter {

    @Override
    public List<Query> split(final Source source) {
        return null;
    }

    @Override
    public SourceType supports() {
        return SourceType.SQL;
    }

}
