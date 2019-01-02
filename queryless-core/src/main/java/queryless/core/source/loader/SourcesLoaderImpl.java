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
package queryless.core.source.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import queryless.core.logging.Log;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

@Singleton
public class SourcesLoaderImpl implements SourcesLoader {

    private final Log log;

    @Inject
    public SourcesLoaderImpl(final Log log) {
        this.log = log;
    }

    @Override
    public List<Source> load(final Set<Path> sources) {
        return sources.stream()
                .sorted()
                .map(this::build)
                .collect(Collectors.toList());
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
