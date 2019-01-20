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

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import queryless.core.config.DefaultConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;

@Data
public class Resource implements Comparable<Resource> {

    private final Path path;
    private final ResourceType type;

    public InputStream getContentStream() throws IOException {
        return FileUtils.openInputStream(path.toFile());
    }

    public LineIterator getLineIterator() throws IOException {
        return FileUtils.lineIterator(path.toFile(), StandardCharsets.UTF_8.name());
    }

    public String getName() {
        return FilenameUtils.removeExtension(path.getFileName().toString());
    }

    public String getBundleName() {
        return StringUtils.substringBefore(getName(), DefaultConfiguration.DEFAULT_FILENAME_BUNDLE_SEPARATOR);
    }

    @Override
    public int compareTo(final Resource o) {
        return Objects.compare(getPath(), o.getPath(), Path::compareTo);
    }

}
