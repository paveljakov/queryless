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

import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum ResourceType {
    SQL("sql"),
    XML("xml"),
    PROPERTIES("properties"),
    OTHER(null);

    private final String extension;

    ResourceType(final String extension) {
        this.extension = extension;
    }

    public static ResourceType resolve(final String extension) {
        return EnumSet.complementOf(EnumSet.of(OTHER))
                .stream()
                .filter(type -> type.getExtension().equalsIgnoreCase(extension))
                .findAny()
                .orElse(OTHER);
    }

}
