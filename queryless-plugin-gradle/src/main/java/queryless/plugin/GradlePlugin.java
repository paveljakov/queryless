package queryless.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import queryless.core.DaggerQuerylessPlugin;
import queryless.core.QuerylessPlugin;

import java.io.IOException;

public class GradlePlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        try {

            project.getTasks().create("queryless", Generate.class, (task) -> {
                task.setMessage("Hello");
                task.setRecipient("World");
            });

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
