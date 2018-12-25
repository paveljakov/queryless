package queryless.core.source.loader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class SourcesLoaderImpl implements SourcesLoader {

    private final Log log;

    private final PluginConfiguration configuration;

    @Inject
    public SourcesLoaderImpl(final Log log, final PluginConfiguration configuration) {
        this.log = log;
        this.configuration = configuration;
    }

    @Override
    public List<Source> load(final String[] sources) {
        return Arrays.stream(sources)
                .map(this::resolveFiles)
                .flatMap(Collection::stream)
                .map(Path::normalize)
                .distinct()
                .sorted()
                .map(this::build)
                .collect(Collectors.toList());
    }

    private Set<Path> resolveFiles(final String source) {
        final Path sourcePath = configuration.getResourcesPath().resolve(source);

        if (Files.isDirectory(sourcePath)) {
            return collectDirectoryFiles(sourcePath);
        }

        if (Files.isRegularFile(sourcePath)) {
            return Collections.singleton(sourcePath);
        }

        log.warn("Path is not file or folder: " + sourcePath);
        return Collections.emptySet();
    }

    private Set<Path> collectDirectoryFiles(final Path directory) {
        final Set<Path> paths = new HashSet<>();

        FileUtils.listFiles(directory.toFile(), null, true)
                .forEach(file -> paths.add(file.toPath()));

        return paths;
    }

    private Source build(final Path path) {
        try {
            log.info("Loading source file: " + path);

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
