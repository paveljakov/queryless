package queryless.plugin.config;

import java.nio.file.Path;

public class PluginConfigurationBuilder {
    private String packageName;
    private Path generatePath;
    private String resourcesPath;
    private Path rootPath;

    public PluginConfigurationBuilder setPackageName(final String packageName) {
        this.packageName = packageName;
        return this;
    }

    public PluginConfigurationBuilder setGeneratePath(final Path generatePath) {
        this.generatePath = generatePath;
        return this;
    }

    public PluginConfigurationBuilder setResourcesPath(final String resourcesPath) {
        this.resourcesPath = resourcesPath;
        return this;
    }

    public PluginConfigurationBuilder setRootPath(final Path rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public PluginConfiguration createPluginConfiguration() {
        return new PluginConfiguration(packageName, generatePath, resourcesPath, rootPath);
    }
}