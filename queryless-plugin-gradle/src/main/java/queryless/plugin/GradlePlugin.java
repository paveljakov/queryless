package queryless.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

public class GradlePlugin implements Plugin<Project> {

    private QuerylessExtension extension;

    @Override
    public void apply(final Project project) {
        extension = project.getExtensions().findByType(QuerylessExtension.class);

        if (extension == null) {
            extension = project.getExtensions().create("queryless", QuerylessExtension.class, project);
        }

        Configuration configuration = project.getConfigurations().findByName("queryless");

        if (configuration == null) {
            configuration = project.getConfigurations().create("queryless");
        }

        configuration
                .setVisible(false)
                .setTransitive(false)
                .setDescription("Queryless configuration is used for code generation");

        Task queryless = project.getTasks().findByName("queryless");

        if (queryless == null) {

            project.getTasks().create("queryless", Generate.class, (task) -> {
                task.setMessage("Hello");
                task.setRecipient("World");
            });

            queryless.setGroup("queryless code generation");
            queryless.setDescription("Queryless code generation tasks");
        }

        project.afterEvaluate(p -> {
            if (extension.getSourceSetName() != null
                    && project.getPlugins().hasPlugin(JavaBasePlugin.class)
                    && !project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().isEmpty()) {

                SourceSet sourceSet = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().findByName(extension.getSourceSetName());
                if (sourceSet != null) {
                    if (!sourceSet.getJava().getSrcDirs().contains(extension.getGeneratePath())) {
                        sourceSet.getJava().srcDir(extension.getGeneratePath());
                    }
                    project.getTasks().getByName(sourceSet.getCompileJavaTaskName()).dependsOn(queryless);
                }
            }
        });


        try {

//            final QuerylessPlugin plugin = DaggerQuerylessPlugin
//                    .builder()
//                    .logger(buildLog())
//                    .configuration(buildConfig())
//                    .build();
//
//            plugin.executor().execute(sources);

            //project.addCompileSourceRoot(generatePath.toString());
            //project.addTestCompileSourceRoot(generatePath.toString());

        } catch (Exception e) {
            //getLog().error(e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
