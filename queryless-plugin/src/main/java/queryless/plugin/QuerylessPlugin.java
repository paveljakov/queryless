package queryless.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import lombok.Getter;
import queryless.plugin.execute.PluginExecutor;

@Getter
@Mojo(name = "generate",
      defaultPhase = LifecyclePhase.GENERATE_SOURCES,
      requiresDependencyResolution = ResolutionScope.COMPILE,
      requiresDependencyCollection = ResolutionScope.COMPILE,
      threadSafe = true)
public class QuerylessPlugin extends AbstractMojo {

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

    @Parameter(defaultValue = "${project.basedir}")
    private File root;

    @Component(role = PluginExecutor.class)
    private PluginExecutor executor;

    public void execute() throws MojoExecutionException {
        try {
            executor.init(this);

            executor.execute();

        } catch (IOException e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
