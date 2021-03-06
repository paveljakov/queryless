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
package queryless.core.source.splitter;

import queryless.core.logging.Log;
import queryless.core.source.model.Query;
import queryless.core.source.model.Resource;
import queryless.core.source.model.ResourceType;
import queryless.core.utils.PropertiesUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Singleton
public class PropertiesSourceSplitter implements SourceSplitter {

    private final Log log;

    @Inject
    public PropertiesSourceSplitter(final Log log) {
        this.log = log;
    }

    @Override
    public List<Query> split(final Resource resource) {
        return loadProperties(resource).entrySet()
                .stream()
                .map(this::buildQuery)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceType supports() {
        return ResourceType.PROPERTIES;
    }

    private Map<String, String> loadProperties(final Resource resource) {
        try (final InputStream stream = resource.getContentStream()) {
            return PropertiesUtils.loadProperties(stream);

        } catch (IOException e) {
            log.warn("Error occurred while reading source file " + resource.getPath() + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

    private Query buildQuery(final Entry<String, String> entry) {
        return new Query(entry.getKey(), entry.getValue());
    }

}
