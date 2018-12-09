package queryless.plugin.config;

import java.nio.file.Path;

import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

@Component(role = ConfigurationProvider.class)
public class ConfigurationProvider {

    @Requirement
    private PlexusContainer container;

    private PluginConfiguration configuration;

    public String getPackageName() {
        return getConfiguration().getPackageName();
    }

    public Path getGeneratePath() {
        return getConfiguration().getGeneratePath();
    }

    public Path getResourcesPath() {
        return getConfiguration().getRootPath().resolve(getConfiguration().getResourcesPath());
    }

    public Path getRootPath() {
        return getConfiguration().getRootPath();
    }

    private PluginConfiguration getConfiguration() {
        try {
            if (configuration == null) {
                configuration = container.lookup(PluginConfiguration.class);
            }

            return configuration;

        } catch (ComponentLookupException e) {
            throw new RuntimeException(e);
        }
    }

}
