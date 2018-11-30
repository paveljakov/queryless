package queryless.plugin.config;

import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

@Component(role = ConfigurationProvider.class)
public class ConfigurationProvider {

    @Requirement
    private PlexusContainer container;

    public PluginConfiguration getConfiguration() {
        try {
            return container.lookup(PluginConfiguration.class);

        } catch (ComponentLookupException e) {
            throw new RuntimeException(e);
        }
    }

}
