package queryless.plugin.execute;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.lang.model.element.Modifier;

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.sonatype.guice.plexus.config.Hints;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import queryless.plugin.QuerylessPlugin;
import queryless.plugin.config.PluginConfiguration;
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

    private PluginConfiguration pluginConfiguration;

    public void init(QuerylessPlugin plugin) {

        pluginConfiguration = new PluginConfiguration(plugin.getSources(), plugin.getPackageName(), plugin.getGeneratePath(),
                                                      plugin.getResourcesPath(), plugin.getRoot());

        container.addComponent(pluginConfiguration, PluginConfiguration.class, Hints.DEFAULT_HINT);
    }

    public void execute() throws IOException {
        Files.createDirectories(pluginConfiguration.getGeneratePath().toPath());

        sourcesLoader.load(pluginConfiguration.getSources(), pluginConfiguration.getRoot().toPath(), pluginConfiguration.getResourcesPath())
                .forEach(p -> logger.info("Source: " + p));

        //getLog().info("Splitters size: " +sourceSplitterFactory.getSplitters().size());

        Path test = Paths.get(pluginConfiguration.getGeneratePath().toString(), "Test.java");
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

        project.addCompileSourceRoot(pluginConfiguration.getGeneratePath().getPath());
        project.addTestCompileSourceRoot(pluginConfiguration.getGeneratePath().getPath());
    }

}
