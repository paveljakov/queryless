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
package queryless.core.source.service;

import org.apache.commons.io.FilenameUtils;
import queryless.core.logging.Log;
import queryless.core.source.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.model.Resource;
import queryless.core.source.model.ResourceType;
import queryless.core.source.preprocessor.Preprocessors;
import queryless.core.source.splitter.SourceSplitters;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class SourcesServiceImpl implements SourcesService {

    private final Log log;

    private final SourceSplitters sourceSplitters;
    private final Preprocessors preprocessor;

    @Inject
    public SourcesServiceImpl(final Log log, final SourceSplitters sourceSplitters, final Preprocessors preprocessor) {
        this.log = log;
        this.sourceSplitters = sourceSplitters;
        this.preprocessor = preprocessor;
    }

    @Override
    public List<Source> load(final Set<Path> sources) {
        return sources.stream()
                .sorted()
                .map(this::buildResource)
                .map(this::build)
                .collect(Collectors.toList());
    }

    private Source build(final Resource resource) {
        log.info("Using source file: " + resource);

        final List<Query> queries = split(resource).stream()
                .map(q -> preprocessor.preprocess(q))
                .collect(Collectors.toList());

        return buildSource(resource, queries);
    }

    private List<Query> split(final Resource resource) {
        return sourceSplitters.get(resource.getType()).split(resource);
    }

    private ResourceType resolveType(final Path path) {
        return ResourceType.resolve(FilenameUtils.getExtension(path.getFileName().toString()));
    }

    private Resource buildResource(final Path path) {
        return new Resource(path, resolveType(path));
    }

    private Source buildSource(final Resource resource, final List<Query> queries) {
        return new Source(resource.getName(), resource.getBundleName(), queries);
    }

}
