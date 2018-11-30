package queryless.plugin.source.loader;

import java.nio.file.Path;
import java.util.Set;

import queryless.plugin.source.model.Source;

public interface SourcesLoader {

    Set<Source> load(String[] sources, Path root, String resourcesPath);

}
