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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;

import queryless.core.bundle.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;
import queryless.core.utils.QueryTextUtils;

@Singleton
public class PropertiesSourceSplitter implements SourceSplitter {

    @Inject
    public PropertiesSourceSplitter() {
    }

    @Override
    public List<Query> split(final Source source) {
        final Properties properties = loadProperties(source);
        return properties.entrySet()
                .stream()
                .map(this::buildQuery)
                .collect(Collectors.toList());
    }

    @Override
    public SourceType supports() {
        return SourceType.PROPERTIES;
    }

    private Properties loadProperties(final Source source) {
        try (final InputStream stream = IOUtils.toInputStream(source.getContent(), StandardCharsets.UTF_8)) {
            final Properties properties = new Properties();
            properties.load(stream);

            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Query buildQuery(final Map.Entry<Object, Object> entry) {
        return new Query(entry.getKey().toString(), QueryTextUtils.removeIndentation(entry.getValue().toString()));
    }

}
