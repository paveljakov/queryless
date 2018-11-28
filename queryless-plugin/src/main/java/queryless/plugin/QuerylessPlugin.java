package queryless.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.lang.model.element.Modifier;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import queryless.plugin.source.SourcesLoader;

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

    @Component
    private MavenProject project;

    @Component(role = SourcesLoader.class)
    private SourcesLoader sourcesLoader;

    public void execute() throws MojoExecutionException {
        try {
            Files.createDirectories(generatePath.toPath());

            sourcesLoader.resolveSources(sources, root.toPath(), resourcesPath).forEach(p -> getLog().info("Source: " + p));

            Path test = Paths.get(generatePath.toString(), "Test.java");
            Files.createFile(test);

            MethodSpec flux = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(String.class, "greeting")
                    .addStatement("this.$N = $N", "greeting", "greeting")
                    .build();

            TypeSpec helloWorld = TypeSpec.classBuilder("Test")
                    .addModifiers(Modifier.PUBLIC)
                    .addField(String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)
                    .addMethod(flux)
                    .build();

            //            writer.emitPackage(packageName)
            //                    //.emitImports()
            //                    .emitJavadoc("test")
            //                    .beginType("Person", "class", EnumSet.of(PUBLIC, FINAL))
            //                    .emitEmptyLine()
            //                    .emitJavadoc("Query:<br /><br /><code><pre>SELECT<br />*<br />FROM</pre></code>")
            //                    .emitField("String", "QUERY_1", EnumSet.of(PUBLIC, STATIC, FINAL))
            //                    .emitEmptyLine()
            //                    .emitJavadoc("test")
            //                    .emitField("String", "lastName", EnumSet.of(PUBLIC, STATIC, FINAL))
            //                    .emitEmptyLine()
            //                    .beginConstructor(EnumSet.of(PRIVATE))
            //                    .endConstructor()
            //                    .emitEmptyLine()
            //                    .emitJavadoc("Returns the person's full name.")
            //                    .beginMethod("String", "getName", EnumSet.of(PUBLIC))
            //                    .emitStatement("return firstName + \" \" + lastName")
            //                    .endMethod()
            //                    .emitEmptyLine()
            //                    .endType();

            FileUtils.writeStringToFile(test.toFile(), helloWorld.toString(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            getLog().error(e);
        }

        project.addCompileSourceRoot(generatePath.getPath());
        project.addTestCompileSourceRoot(generatePath.getPath());
    }

}
