package queryless.core.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
public class PluginConfiguration {

    private final String packageName;
    private final Path generatePath;
    private final String resourcesPath;
    private final String sqlKeyPrefix;
    private final Path rootPath;

    public Path getResourcesPath() {
        return rootPath.resolve(resourcesPath);
    }

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
