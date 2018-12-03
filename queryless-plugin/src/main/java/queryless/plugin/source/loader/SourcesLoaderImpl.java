package queryless.plugin.source.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;

@Component(role = SourcesLoader.class)
public class SourcesLoaderImpl implements SourcesLoader {

    @Requirement
    private Logger logger;

    @Override
    public List<Source> load(final String[] sources, final Path root, final String resourcesPath) {
        final Set<Path> paths = resolveSources(sources, root, resourcesPath);

        return paths.stream()
                .sorted()
                .map(this::build)
                .collect(Collectors.toList());
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

    private Source build(final Path path) {
        try {
            logger.info("Loading source file: " + path);

            final SourceType type = resolveType(path);
            final String content = loadContent(path);

            return new Source(path, type, content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SourceType resolveType(final Path path) {
        return SourceType.resolve(FilenameUtils.getExtension(path.getFileName().toString()));
    }

    private String loadContent(final Path path) throws IOException {
        return FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
    }

}
