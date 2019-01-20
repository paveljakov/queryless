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
package queryless.core.source.preprocessor;

import org.apache.commons.text.StringSubstitutor;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.core.utils.PropertiesUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SubstitutingPreprocessor implements Preprocessor {

    private final Log log;

    private final StringSubstitutor substitutor;

    @Inject
    public SubstitutingPreprocessor(final PluginConfiguration configuration, final Log log) {
        this.log = log;

        final Map<String, String> variables = new HashMap<>();

        if (configuration.getVariables() != null) {
            variables.putAll(configuration.getVariables());
        }

        if (configuration.getVariableSources() != null) {
            configuration.getVariableSources().stream()
                    .map(this::loadProperties)
                    .forEach(variables::putAll);
        }

        substitutor = new StringSubstitutor(variables);
    }

    @Override
    public String preprocess(final String query) {
        return substitutor.replace(query);
    }

    private Map<String, String> loadProperties(final Path path) {
        try {
            return PropertiesUtils.loadProperties(path);
        } catch (IOException e) {
            log.warn("Error occurred while loading properties file " + path + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

}
