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
package queryless.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtils {

    private static final String XML_EXTENSION = "xml";

    public static Map<String, String> loadProperties(final Path path) throws IOException {
        Objects.requireNonNull(path);

        final String extension = FilenameUtils.getExtension(path.getFileName().toString());

        try (final InputStream stream = new FileInputStream(path.toFile())) {
            if (XML_EXTENSION.equalsIgnoreCase(extension)) {
                return loadXmlProperties(stream);
            } else {
                return loadProperties(stream);
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, String> loadProperties(final InputStream stream) throws IOException {
        final Properties properties = new Properties();
        properties.load(stream);
        return (Map) properties;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, String> loadXmlProperties(final InputStream stream) throws IOException {
        final Properties properties = new Properties();
        properties.loadFromXML(stream);
        return (Map) properties;
    }

}
