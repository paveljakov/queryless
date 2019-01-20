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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubstitutingPreprocessorTest {

    private static final String QUERY = "${test.variable}${test.variable1}${test.variable2}${test.variable3}";

    @Mock
    private Log log;

    @Mock
    private PluginConfiguration configuration;

    private SubstitutingPreprocessor preprocessor;

    @Before
    public void setUp() throws Exception {
        final Map<String, String> variables = new HashMap<>();
        variables.put("test.variable", "test");

        final File variablesSource = new File(getClass().getResource("/variables/test.variables.properties").toURI());
        final Set<Path> variableSources = new HashSet<>();
        variableSources.add(variablesSource.toPath());

        when(configuration.getVariables()).thenReturn(variables);
        when(configuration.getVariableSources()).thenReturn(variableSources);

        preprocessor = new SubstitutingPreprocessor(configuration, log);
    }

    @Test
    public void preprocess() {
        final String result = preprocessor.preprocess(QUERY);

        assertThat(result).isEqualTo("testtest1test2test3");
    }
}
