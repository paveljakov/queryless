package queryless.plugin.source.loader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import queryless.plugin.config.ConfigurationProvider;
import queryless.plugin.source.model.Source;

@Component(role = SourcesLoader.class)
public class SourcesLoaderImpl implements SourcesLoader {

    @Requirement
    private Logger logger;

    @Requirement
    private ConfigurationProvider configurationProvider;

    @Override
    public Set<Source> load(final String[] sources, final Path root, final String resourcesPath) {
        final Set<Path> paths = resolveSources(sources, root, resourcesPath);

        logger.info(configurationProvider.getConfiguration().toString());

        return Collections.emptySet();
    }

    private Set<Path> resolveSources(final String[] sources, final Path root, final String resourcesPath) {
        final Set<Path> paths = new HashSet<>();

        for (final String source : sources) {
            final Path sourcePath = Paths.get(root.toString(), resourcesPath, source);

            FileUtils.listFiles(sourcePath.toFile(), null, true)
                    .forEach(file -> paths.add(file.toPath()));
        }

        return paths;
    }

}
