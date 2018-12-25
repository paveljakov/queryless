package queryless.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.execute.PluginExecutor;
import queryless.plugin.logging.MavenLog;

import java.io.File;
import java.io.IOException;

@Mojo(name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresDependencyCollection = ResolutionScope.COMPILE,
        threadSafe = true)
public class QuerylessMavenPlugin extends AbstractMojo {

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
            final PluginConfiguration configuration = new PluginConfiguration(
                    packageName,
                    generatePath.toPath(),
                    resourcesPath,
                    sqlKeyPrefix,
                    root.toPath());

            final PluginExecutor executor = DaggerQuerylessPlugin
                    .builder()
                    .logger(new MavenLog(getLog()))
                    .configuration(configuration)
                    .build()
                    .executor();

            executor.execute(sources);

            project.addCompileSourceRoot(generatePath.toString());
            project.addTestCompileSourceRoot(generatePath.toString());

        } catch (IOException e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
