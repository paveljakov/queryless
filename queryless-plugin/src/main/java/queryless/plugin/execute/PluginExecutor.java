package queryless.plugin.execute;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.sonatype.guice.plexus.config.Hints;

import com.squareup.javapoet.JavaFile;

import queryless.plugin.QuerylessPlugin;
import queryless.plugin.bundle.service.BundleService;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.config.PluginConfigurationBuilder;
import queryless.plugin.generator.CodeGenerator;
import queryless.plugin.source.loader.SourcesLoader;

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

    private String[] sourcesLocations;

    private PluginConfiguration pluginConfiguration;

    public void execute(QuerylessPlugin plugin) throws IOException {
        init(plugin);
        executeInternal();
        finish();
    }

    private void executeInternal() throws IOException {
        logger.info("Generating query constants.");

        sourcesLoader.load(sourcesLocations).stream()
                .map(bundleService::build)
                .map(codeGenerator::generate)
                .forEach(this::writeToFile);
    }

    private void init(QuerylessPlugin plugin) throws IOException {
        sourcesLocations = plugin.getSources();

        pluginConfiguration = new PluginConfigurationBuilder()
                .setPackageName(plugin.getPackageName())
                .setGeneratePath(plugin.getGeneratePath().toPath())
                .setResourcesPath(plugin.getResourcesPath())
                .setRootPath(plugin.getRoot().toPath())
                .createPluginConfiguration();

        container.addComponent(pluginConfiguration, PluginConfiguration.class, Hints.DEFAULT_HINT);

        Files.createDirectories(pluginConfiguration.getGeneratePath());
    }

    private void finish() {
        project.addCompileSourceRoot(pluginConfiguration.getGeneratePath().toString());
        project.addTestCompileSourceRoot(pluginConfiguration.getGeneratePath().toString());
    }

    private void writeToFile(final JavaFile javaFile) {
        try {
            javaFile.writeTo(pluginConfiguration.getGeneratePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
