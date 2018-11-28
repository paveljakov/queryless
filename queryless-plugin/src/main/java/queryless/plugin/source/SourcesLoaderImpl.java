package queryless.plugin.source;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = SourcesLoader.class)
public class SourcesLoaderImpl implements SourcesLoader {

    @Override
    public Set<Path> resolveSources(final String[] sources, final Path root, final String resourcesPath) {
        final Set<Path> paths = new HashSet<>();

        for (final String source : sources) {
            final Path sourcePath = Paths.get(root.toString(), resourcesPath, source);

            FileUtils.listFiles(sourcePath.toFile(), new String[] {"xml"}, true)
                    .forEach(file -> paths.add(file.toPath()));
        }

        return paths;
    }

}
