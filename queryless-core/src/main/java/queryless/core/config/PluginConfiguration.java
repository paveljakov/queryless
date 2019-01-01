package queryless.core.config;

import java.nio.file.Path;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class PluginConfiguration {

    private final String packageName;
    private final Path generatePath;
    private final String queryCommentPrefix;
    private final String queryKeyMarker;
    private final String nestedBundleSeparator;

}
