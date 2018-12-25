package queryless.core.source.splitter;

import queryless.core.bundle.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

import java.util.List;

public interface SourceSplitter {

    List<Query> split(Source source);

    SourceType supports();

}
