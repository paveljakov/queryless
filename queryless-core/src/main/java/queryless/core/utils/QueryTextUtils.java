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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryTextUtils {

    public static List<String> splitLines(final String text) {
        return new ArrayList<>(Arrays.asList(text.split("\\r?\\n")));
    }

    public static String removeIndentation(final String text) {
        Objects.requireNonNull(text);

        final List<String> lines = trimEmptyLines(splitLines(text));
        if (lines.isEmpty()) {
            return text;
        }

        final int indentWidth = findMinimumCommonIndent(lines);

        if (indentWidth == 0) {
            return text;
        }

        return lines.stream()
                .map(line -> StringUtils.substring(line, indentWidth))
                .collect(Collectors.joining("\n"));
    }

    private static List<String> trimEmptyLines(final List<String> lines) {
        final List<String> result = new ArrayList<>(lines);

        for (final Iterator<String> iterator = result.listIterator(); iterator.hasNext(); ) {
            final String lineFromTop = iterator.next();
            if (StringUtils.isNotEmpty(StringUtils.trim(lineFromTop))) {
                break;
            }
            iterator.remove();
        }

        for (final ListIterator<String> iterator = result.listIterator(result.size()); iterator.hasPrevious(); ) {
            final String lineFromBottom = iterator.previous();
            if (StringUtils.isNotEmpty(StringUtils.trim(lineFromBottom))) {
                break;
            }
            iterator.remove();
        }

        return result;
    }

    private static int findMinimumCommonIndent(final List<String> lines) {
        return lines.stream()
                .filter(StringUtils::isNotBlank)
                .mapToInt(QueryTextUtils::calculateIndentWidth)
                .min()
                .orElse(0);
    }

    private static int calculateIndentWidth(final String line) {
        return IntStream.range(0, line.length())
                .filter(i -> !Character.isWhitespace(line.charAt(i)))
                .findFirst()
                .orElseGet(() -> line.length());
    }

}
