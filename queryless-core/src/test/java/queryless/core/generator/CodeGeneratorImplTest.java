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
package queryless.core.generator;

import com.squareup.javapoet.JavaFile;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queryless.core.bundle.model.Bundle;
import queryless.core.config.DefaultConfiguration;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Query;
import queryless.core.source.splitter.queries.SplitterQueries;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CodeGeneratorImplTest {

    @Mock
    private PluginConfiguration configuration;

    private CodeGeneratorImpl codeGenerator;

    private Bundle bundle;

    @Before
    public void setUp() throws Exception {
        when(configuration.getPackageName()).thenReturn(DefaultConfiguration.DEFAULT_PACKAGE_NAME);
        when(configuration.isAddGenerateTimestamp()).thenReturn(false);

        codeGenerator = new CodeGeneratorImpl(configuration);

        final List<Query> nestedQueries = new ArrayList<>();
        nestedQueries.add(SplitterQueries.EMPLOYEE_FIND);
        nestedQueries.add(SplitterQueries.EMPLOYEE_INSERT);

        final Bundle nestedBundle = new Bundle("employee", nestedQueries, Collections.emptyList());

        final List<Query> queries = new ArrayList<>();
        queries.add(SplitterQueries.GET_ALL_EMPLOYEES);

        final List<Bundle> nested = new ArrayList<>();
        nested.add(nestedBundle);

        bundle = new Bundle("test", queries, nested);
    }

    @Test
    @Ignore
    public void generate() throws URISyntaxException, IOException {
        final JavaFile result = codeGenerator.generate(bundle);

        final File file = new File(getClass().getResource("/java/Test.java").toURI());

        final String expected = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        assertThat(result.toString()).isEqualTo(expected);
    }
}
