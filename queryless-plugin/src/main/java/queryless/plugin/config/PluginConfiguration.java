package queryless.plugin.config;

import java.nio.file.Path;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PluginConfiguration {

    private final String packageName;
    private final Path generatePath;
    private final String resourcesPath;
    private final Path rootPath;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("packageName", packageName)
                .append("generatePath", generatePath)
                .append("resourcesPath", resourcesPath)
                .append("rootPath", rootPath)
                .toString();
    }

}
