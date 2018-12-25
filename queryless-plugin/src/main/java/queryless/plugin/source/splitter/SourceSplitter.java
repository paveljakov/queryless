package queryless.plugin.source.splitter;

import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;

import java.util.List;

public interface SourceSplitter {

    List<Query> split(Source source);

    SourceType supports();

}
