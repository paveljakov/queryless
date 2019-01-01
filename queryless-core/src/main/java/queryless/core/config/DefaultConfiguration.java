package queryless.core.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultConfiguration {

    public final static String DEFAULT_PACKAGE_NAME = "queryless.generated";
    public final static String DEFAULT_QUERY_KEY_MARKER = "id:";
    public final static String DEFAULT_QUERY_COMMENT_PREFIX = "--";
    public final static String DEFAULT_NESTED_BUNDLE_SEPARATOR = ".";

}
