package queryless.plugin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryTextUtils {

    public static String toClassName(final String name) {
        final String escapedName = toNamingConvention(name);
        return CaseUtils.toCamelCase(escapedName, true, '_');
    }

    public static String toConstantName(final String name) {
        final String escapedName = toNamingConvention(name);
        return escapedName.toUpperCase();
    }

    public static List<String> splitLines(final String text) {
        return new ArrayList<>(Arrays.asList(text.split("\\r?\\n")));
    }

    public static String removeIndentation(final String text) {
        Objects.requireNonNull(text);

        final String[] lines = trimEmptyStrings(splitLines(text));
        if (ArrayUtils.isEmpty(lines)) {
            return text;
        }

        final String[] result = new String[lines.length];
        result[0] = StringUtils.trim(lines[0]);

        final int indentCount = lines[0].indexOf(result[0]);

        for (int i = 1; i < lines.length; i++) {
            result[i] = StringUtils.substring(lines[i], indentCount);
        }

        return String.join("\n", result);
    }

    private static String[] trimEmptyStrings(final List<String> strings) {
        final List<String> result = new ArrayList<>(strings);

        for (final Iterator<String> iterator = result.listIterator(); iterator.hasNext(); ) {
            final String str = iterator.next();
            if (StringUtils.isNotEmpty(StringUtils.trim(str))) {
                break;
            }
            iterator.remove();
        }

        for (final ListIterator<String> iterator = result.listIterator(result.size()); iterator.hasPrevious(); ) {
            final String str = iterator.previous();
            if (StringUtils.isNotEmpty(StringUtils.trim(str))) {
                break;
            }
            iterator.remove();
        }

        return result.toArray(new String[0]);
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
