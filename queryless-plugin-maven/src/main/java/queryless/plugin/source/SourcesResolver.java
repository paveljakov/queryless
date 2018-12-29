package queryless.plugin.source;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public final class SourcesResolver {

    public static Set<Path> resolve(final String[] sources, final Path resourcesPath) {
        return Arrays.stream(sources)
                .map(resourcesPath::resolve)
                .map(SourcesResolver::resolveFiles)
                .flatMap(Collection::stream)
                .map(Path::normalize)
                .collect(Collectors.toSet());
    }

    private static Set<Path> resolveFiles(final Path sourcePath) {
        if (Files.isDirectory(sourcePath)) {
            return collectDirectoryFiles(sourcePath);
        }

        if (Files.isRegularFile(sourcePath)) {
            return Collections.singleton(sourcePath);
        }

        return Collections.emptySet();
    }

    private static Set<Path> collectDirectoryFiles(final Path directory) {
        final Set<Path> paths = new HashSet<>();

        FileUtils.listFiles(directory.toFile(), null, true)
                .forEach(file -> paths.add(file.toPath()));

        return paths;
    }

    private SourcesResolver() {
    }
}
