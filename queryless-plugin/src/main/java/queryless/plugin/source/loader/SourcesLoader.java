package queryless.plugin.source.loader;

import queryless.plugin.source.model.Source;

import java.util.List;

public interface SourcesLoader {

    List<Source> load(String[] sources);

}
