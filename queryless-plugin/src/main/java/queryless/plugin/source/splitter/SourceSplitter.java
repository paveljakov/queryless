package queryless.plugin.source.splitter;

import java.util.List;

import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;

public interface SourceSplitter {

    List<Query> split(Source source);

    SourceType supports();

}
