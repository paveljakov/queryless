package queryless.plugin.execute;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.sonatype.guice.plexus.config.Hints;
import queryless.plugin.QuerylessPlugin;
import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.service.BundleService;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.generator.CodeGenerator;
import queryless.plugin.source.loader.SourcesLoader;
import queryless.plugin.source.model.Source;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Component(role = PluginExecutor.class)
public class PluginExecutor {

    @Requirement
    private Logger logger;

    @Requirement
    private PlexusContainer container;

    @Requirement
    private MavenProject project;

    @Requirement
    private SourcesLoader sourcesLoader;

    @Requirement
    private BundleService bundleService;

    @Requirement
    private CodeGenerator codeGenerator;

    private PluginConfiguration pluginConfiguration;

    public void execute(QuerylessPlugin plugin) throws IOException {
        init(plugin);
        executeInternal();
        finish();
    }

    private void executeInternal() throws IOException {
        logger.info("Generating query constants.");

        List<Source> sources = sourcesLoader
                .load(pluginConfiguration.getSources(), pluginConfiguration.getRoot().toPath(), pluginConfiguration.getResourcesPath());

        List<Bundle> bundles = sources.stream().map(bundleService::build).collect(Collectors.toList());

        bundles.forEach(codeGenerator::generate);
    }

    private void init(QuerylessPlugin plugin) throws IOException {
        pluginConfiguration = new PluginConfiguration(
                plugin.getSources(),
                plugin.getPackageName(),
                plugin.getGeneratePath(),
                plugin.getResourcesPath(),
                plugin.getRoot());

        container.addComponent(pluginConfiguration, PluginConfiguration.class, Hints.DEFAULT_HINT);

        Files.createDirectories(pluginConfiguration.getGeneratePath().toPath());
    }

    private void finish() {
        project.addCompileSourceRoot(pluginConfiguration.getGeneratePath().getPath());
        project.addTestCompileSourceRoot(pluginConfiguration.getGeneratePath().getPath());
    }

}