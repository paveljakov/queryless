package queryless.plugin.config;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginConfiguration {

    private final String[] sources;
    private final String packageName;
    private final File generatePath;
    private final String resourcesPath;
    private final File root;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sources", sources)
                .append("packageName", packageName)
                .append("generatePath", generatePath)
                .append("resourcesPath", resourcesPath)
                .append("root", root)
                .toString();
    }
}
