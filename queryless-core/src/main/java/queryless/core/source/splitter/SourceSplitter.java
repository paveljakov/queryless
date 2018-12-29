package queryless.core.source.splitter;

import java.util.List;

import queryless.core.bundle.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

public interface SourceSplitter {

    List<Query> split(Source source);

    SourceType supports();

}
