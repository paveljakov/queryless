package queryless.plugin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.util.Objects;

// TODO needs lots more work
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NameUtils {

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

        String escapedName = name.replaceAll("[^A-Za-z0-9]", "_");
        if (StringUtils.isBlank(escapedName)) {
            throw new IllegalStateException("Can not generate class name from: " + name);
        }

        if (Character.isDigit(escapedName.charAt(0))) {
            escapedName = "$" + escapedName;
        }

        return escapedName;
    }

}
