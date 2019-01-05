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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static queryless.core.source.splitter.queries.SqlSplitterQueries.*;

@RunWith(MockitoJUnitRunner.class)
public class SqlSourceSplitterTest {

    private SqlSourceSplitter splitter;

    @Before
    public void setUp() {
        final PluginConfiguration configuration = Mockito.mock(PluginConfiguration.class);
        when(configuration.getQueryKeyMarker()).thenReturn("id:");
        when(configuration.getQueryCommentPrefix()).thenReturn("--");

        splitter = new SqlSourceSplitter(configuration, Mockito.mock(Log.class));
    }

    @Test
    public void testSplit() throws Exception {
        final File sqlFile = new File(getClass().getResource("/queries/test3.sql").toURI());

        final List<Query> queries = splitter.split(new Source(sqlFile.toPath(), SourceType.SQL));

        assertThat(queries).containsExactly(EMPLOYEE_INSERT, EMPLOYEE_FIND, GET_ALL_EMPLOYEES);
    }

}
