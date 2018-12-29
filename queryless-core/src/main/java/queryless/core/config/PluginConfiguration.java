package queryless.core.config;

import java.nio.file.Path;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginConfiguration {

    private final String packageName;
    private final Path generatePath;
    private final String sqlKeyPrefix;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("packageName", packageName)
                .append("generatePath", generatePath)
                .append("sqlKeyPrefix", sqlKeyPrefix)
                .toString();
    }

}
