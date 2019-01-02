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

import java.nio.file.Path;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Source implements Comparable<Source> {

    private final Path path;
    private final SourceType type;

    private final String content;

    public String getName() {
        return FilenameUtils.removeExtension(path.getFileName().toString());
    }

    public String getBundleName() {
        return StringUtils.substringBefore(getName(), ".");
    }

    @Override
    public int compareTo(final Source o) {
        return Objects.compare(getPath(), o.getPath(), Path::compareTo);
    }

}
