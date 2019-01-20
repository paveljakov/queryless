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
package queryless.core.source.model;

import org.apache.commons.io.LineIterator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTest {

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        final File sqlFile = new File(getClass().getResource("/queries/test.query.file.sql").toURI());
        resource = new Resource(sqlFile.toPath(), ResourceType.SQL);
    }

    @Test
    public void getContentStream() throws IOException {
        try (final InputStream stream = resource.getContentStream()) {
            assertThat(stream).isNotNull();
        }
    }

    @Test
    public void getLineIterator() throws IOException {
        try (final LineIterator iterator = resource.getLineIterator()) {
            assertThat(iterator).isNotNull();
        }
    }

    @Test
    public void getName() {
        assertThat(resource.getName()).isEqualTo("test.query.file");
    }

    @Test
    public void getBundleName() {
        assertThat(resource.getBundleName()).isEqualTo("test");
    }
}
