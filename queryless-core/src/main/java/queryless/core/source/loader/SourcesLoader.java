package queryless.core.source.loader;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import queryless.core.source.model.Source;

public interface SourcesLoader {

    List<Source> load(Set<Path> sources);

}
