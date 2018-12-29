package queryless.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import queryless.core.DaggerQuerylessPlugin;
import queryless.core.QuerylessPlugin;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.plugin.logging.MavenLog;
import queryless.plugin.source.SourcesResolver;

@Mojo(name = "generate",
      defaultPhase = LifecyclePhase.GENERATE_SOURCES,
      requiresDependencyResolution = ResolutionScope.COMPILE,
      requiresDependencyCollection = ResolutionScope.COMPILE,
      threadSafe = true)
public class MavenPlugin extends AbstractMojo {

    @Parameter(property = "queryless.sources",
               required = true)
    private String[] sources;

    @Parameter(property = "queryless.package",
               defaultValue = "queryless.generated")
    private String packageName;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/queryless",
               property = "queryless.generatePath",
               required = true)
    private File generatePath;

    @Parameter(property = "queryless.resourcesPath",
               defaultValue = "src/main/resources")
    private String resourcesPath;

    @Parameter(property = "queryless.sqlKeyPrefix",
               defaultValue = "id:")
    private String sqlKeyPrefix;

    @Parameter(defaultValue = "${project.basedir}")
    private File root;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        try {
            final QuerylessPlugin plugin = DaggerQuerylessPlugin
                    .builder()
                    .logger(buildLog())
                    .configuration(buildConfig())
                    .build();

            final Set<Path> sourcePaths = SourcesResolver.resolve(sources, getResourcesPath());

            plugin.executor().execute(sourcePaths);

            project.addCompileSourceRoot(generatePath.toString());
            project.addTestCompileSourceRoot(generatePath.toString());

        } catch (IOException e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private Log buildLog() {
        return new MavenLog(getLog());
    }

    private PluginConfiguration buildConfig() {
        return new PluginConfiguration(
                packageName,
                generatePath.toPath(),
                sqlKeyPrefix);
    }

    private Path getResourcesPath() {
        return root.toPath().resolve(resourcesPath);
    }

}
