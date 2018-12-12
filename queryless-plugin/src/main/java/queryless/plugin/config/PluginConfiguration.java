package queryless.plugin.config;

import java.nio.file.Path;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginConfiguration {

    private final String packageName;
    private final Path generatePath;
    private final String resourcesPath;
    private final String sqlKeyPrefix;
    private final Path rootPath;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("packageName", packageName)
                .append("generatePath", generatePath)
                .append("resourcesPath", resourcesPath)
                .append("sqlKeyPrefix", sqlKeyPrefix)
                .append("rootPath", rootPath)
                .toString();
    }

}
