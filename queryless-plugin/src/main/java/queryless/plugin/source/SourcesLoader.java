package queryless.plugin.source;

import java.nio.file.Path;
import java.util.Set;

public interface SourcesLoader {

    Set<Path> resolveSources(String[] sources, Path root, String resourcesPath);

}
