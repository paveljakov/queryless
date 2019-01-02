/*
 * ==============================LICENSE_START=============================
 * Queryless (query constants generation)
 * ========================================================================
 * Copyright (C) 2018 - 2019 Pavel Jakovlev
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============================LICENSE_END==============================
 */
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
