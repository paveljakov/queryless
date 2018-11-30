package queryless.plugin.splitter;

import java.util.List;

import queryless.plugin.query.model.Query;
import queryless.plugin.source.model.Source;

public interface SourceSplitter {

    List<Query> split(Source source);

    String[] getSupportedExtensions();

}
