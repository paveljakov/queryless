package queryless.plugin;

import static javax.lang.model.element.Modifier.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.squareup.javawriter.JavaWriter;

import queryless.org.springframework.util.ResourceUtils;

@Mojo(name = "generate-constants",
      defaultPhase = LifecyclePhase.GENERATE_SOURCES,
      requiresDependencyResolution = ResolutionScope.COMPILE,
      requiresDependencyCollection = ResolutionScope.COMPILE,
      threadSafe = true)
public class GenerateConstants extends AbstractMojo {

    @Parameter(property = "queryless.sources", required = true)
    private String sources[];

    @Parameter(property = "queryless.package", defaultValue = "queryless.generated")
    private String packageName;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/queryless",
               property = "queryless.generateDirectory",
               required = true)
    private File generateDirectory;

    @Parameter(defaultValue = "${project}",
               readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        try {
            generateDirectory.mkdirs();

            List<File> sourceLocations = Arrays.stream(sources).filter(ResourceUtils::isUrl).map(location -> {
                try {
                    return ResourceUtils.getFile(location);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            List<File> files = sourceLocations.stream().flatMap(location -> FileUtils.listFiles(location, new String[] {".xml"}, true).stream())
                    .collect(Collectors.toList());

            files.forEach(file -> getLog().info(file.toString()));

            File test = new File(generateDirectory.toString() + "/Person.java");
            test.createNewFile();

            StringWriter stringWriter = new StringWriter();
            JavaWriter writer = buildWriter(stringWriter);

            writer.emitPackage(packageName)
                    //.emitImports()
                    .emitJavadoc("test")
                    .beginType("Person", "class", EnumSet.of(PUBLIC, FINAL))
                    .emitEmptyLine()
                    .emitJavadoc("Query:<br /><br /><code><pre>SELECT<br />*<br />FROM</pre></code>")
                    .emitField("String", "QUERY_1", EnumSet.of(PUBLIC, STATIC, FINAL))
                    .emitEmptyLine()
                    .emitJavadoc("test")
                    .emitField("String", "lastName", EnumSet.of(PUBLIC, STATIC, FINAL))
                    .emitEmptyLine()
                    .beginConstructor(EnumSet.of(PRIVATE))
                    .endConstructor()
                    .emitEmptyLine()
                    .emitJavadoc("Returns the person's full name.")
                    .beginMethod("String", "getName", EnumSet.of(PUBLIC))
                    .emitStatement("return firstName + \" \" + lastName")
                    .endMethod()
                    .emitEmptyLine()
                    .endType();

            FileUtils.writeStringToFile(test, stringWriter.toString(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            getLog().error(e);
        }

        project.addCompileSourceRoot(generateDirectory.getPath());
        project.addTestCompileSourceRoot(generateDirectory.getPath());
    }

    private JavaWriter buildWriter(StringWriter stringWriter) {
        JavaWriter javaWriter = new JavaWriter(stringWriter);
        javaWriter.setIndent("    ");
        return javaWriter;
    }

}
