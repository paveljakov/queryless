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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NamingUtils {

    public static String toClassName(final String name) {
        final String escapedName = toNamingConvention(name);
        return CaseUtils.toCamelCase(escapedName, true, '_');
    }

    public static String toConstantName(final String name) {
        final String escapedName = toNamingConvention(name);
        return escapedName.toUpperCase();
    }

    private static String toNamingConvention(String name) {
        Objects.requireNonNull(name);

        String strippedName = name.replaceAll("[^A-Za-z0-9]", "_");
        if (StringUtils.isBlank(strippedName)) {
            throw new IllegalStateException("Can not generate name from: '" + name + "'");
        }

        if (isFirstCharNumber(strippedName)) {
            strippedName = "$" + strippedName;
        }

        return strippedName;
    }

    private static boolean isFirstCharNumber(final String str) {
        return Character.isDigit(str.charAt(0));
    }

}
