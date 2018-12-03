package queryless.plugin.source.loader;

import java.nio.file.Path;
import java.util.List;

import queryless.plugin.source.model.Source;

public interface SourcesLoader {

    List<Source> load(String[] sources, Path root, String resourcesPath);

}
