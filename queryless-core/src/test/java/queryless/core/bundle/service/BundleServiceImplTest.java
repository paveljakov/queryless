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
package queryless.core.bundle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import queryless.core.bundle.model.Bundle;
import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Source;
import queryless.core.source.splitter.SourceSplitter;
import queryless.core.source.splitter.SourceSplitters;

public class BundleServiceImplTest {

    private BundleServiceImpl bundleService;

    private SourceSplitter splitter;

    @Before
    public void setUp() throws Exception {
        splitter = mock(SourceSplitter.class);

        final SourceSplitters sourceSplitters = mock(SourceSplitters.class);
        when(sourceSplitters.get(any())).thenReturn(splitter);

        final PluginConfiguration configuration = mock(PluginConfiguration.class);
        when(configuration.getNestedBundleSeparator()).thenReturn(".");

        bundleService = new BundleServiceImpl(sourceSplitters, configuration);
    }

    @Test
    public void singleLevelBundleTest() {
        final Source source = mock(Source.class);
        final List<Query> queries = Lists.list(
                new Query("query1", ""),
                new Query("query2", ""),
                new Query("query3", ""));

        when(splitter.split(source)).thenReturn(queries);

        final Bundle bundle = bundleService.build("test", Collections.singletonList(source));

        assertThat(bundle.getQueries()).containsExactlyElementsOf(queries);
    }

    @Test
    public void twoLevelBundleTest() {
        final Source source = mock(Source.class);

        final List<Query> queries = Lists.list(
                new Query("query1", ""),
                new Query("nested.query2", ""),
                new Query("nested.query3", ""));

        when(splitter.split(source)).thenReturn(queries);

        final Bundle bundle = bundleService.build("test", Collections.singletonList(source));

        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(bundle.getQueries()).containsExactly(new Query("query1", ""));
        softly.assertThat(bundle.getNested()).hasSize(1);
        softly.assertThat(bundle.getNested().get(0).getQueries()).containsExactly(new Query("query2", ""), new Query("query3", ""));

        softly.assertAll();
    }

    @Test
    public void multilevelBundleTest() {
        final Source source = mock(Source.class);

        final List<Query> queries = Lists.list(
                new Query("query1", ""),
                new Query("query2", ""),
                new Query("nested1.query3", ""),
                new Query("nested1.query4", ""),
                new Query("nested2.query5", ""),
                new Query("nested2.query6", ""),
                new Query("nested2.nested1.query7", ""));

        when(splitter.split(source)).thenReturn(queries);

        final Bundle bundle = bundleService.build("test", Collections.singletonList(source));

        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(bundle.getQueries()).containsExactly(new Query("query1", ""), new Query("query2", ""));
        softly.assertThat(bundle.getNested()).hasSize(2);
        softly.assertThat(bundle.getNested().get(0).getQueries()).containsExactly(new Query("query3", ""), new Query("query4", ""));
        softly.assertThat(bundle.getNested().get(1).getQueries()).containsExactly(new Query("query5", ""), new Query("query6", ""));
        softly.assertThat(bundle.getNested().get(1).getNested()).hasSize(1);
        softly.assertThat(bundle.getNested().get(1).getNested().get(0).getQueries()).containsExactly(new Query("query7", ""));

        softly.assertAll();

    }

}
