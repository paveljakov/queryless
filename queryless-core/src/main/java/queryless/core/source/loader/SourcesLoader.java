package queryless.core.source.loader;

import queryless.core.source.model.Source;

import java.util.List;

public interface SourcesLoader {

    List<Source> load(String[] sources);

}
