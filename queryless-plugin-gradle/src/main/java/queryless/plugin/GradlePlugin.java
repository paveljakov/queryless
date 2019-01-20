/*
 * ==============================LICENSE_START=============================
 * Queryless (query constants generation)
 * ========================================================================
 * Copyright (C) 2018 - 2019 Pavel Jakovlev
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============================LICENSE_END==============================
 */
package queryless.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import queryless.plugin.extension.QuerylessExtension;
import queryless.plugin.task.QuerylessTask;

public class GradlePlugin implements Plugin<Project> {

    private static final String QUERYLESS_CONFIGURATION_NAME = "queryless";
    private static final String QUERYLESS_CONFIGURATION_DESCRIPTION = "Configuration for Queryless plugin code generation";

    private static final String QUERYLESS_TASK_NAME = "queryless";
    private static final String QUERYLESS_TASK_GROUP = "queryless";
    private static final String QUERYLESS_TASK_DESCRIPTION = "Queryless code generation tasks";

    @Override
    public void apply(final Project project) {
        setupQuerylessExtension(project);
        setupConfiguration(project);
        setupQuerylessTask(project);

        project.afterEvaluate(this::configure);
    }

    private void setupConfiguration(final Project project) {
        getConfiguration(project)
                .setVisible(false)
                .setTransitive(false)
                .setDescription(QUERYLESS_CONFIGURATION_DESCRIPTION);
    }

    private void setupQuerylessTask(final Project project) {
        if (project.getTasks().findByName(QUERYLESS_TASK_NAME) == null) {
            QuerylessTask queryless = project.getTasks().create(QUERYLESS_TASK_NAME, QuerylessTask.class);
            queryless.setGroup(QUERYLESS_TASK_GROUP);
            queryless.setDescription(QUERYLESS_TASK_DESCRIPTION);
        }
    }

    private Configuration getConfiguration(final Project project) {
        final Configuration configuration = project.getConfigurations().findByName(QUERYLESS_CONFIGURATION_NAME);
        return configuration != null ? configuration : project.getConfigurations().create(QUERYLESS_CONFIGURATION_NAME);
    }

    private void setupQuerylessExtension(final Project project) {
        if (project.getExtensions().findByType(QuerylessExtension.class) == null) {
            project.getExtensions().create(QUERYLESS_CONFIGURATION_NAME, QuerylessExtension.class, project);
        }
    }

    private void configure(final Project project) {
        final QuerylessExtension extension = project.getExtensions().getByType(QuerylessExtension.class);
        if (!extension.isEnabled()) {
            return;
        }

        final QuerylessTask queryless = (QuerylessTask) project.getTasks().getByName(QUERYLESS_TASK_NAME);

        queryless.setSources(extension.getSources());
        queryless.setQueryKeyMarker(extension.getQueryKeyMarker());
        queryless.setQueryCommentPrefix(extension.getQueryCommentPrefix());
        queryless.setNestedBundleSeparator(extension.getNestedBundleSeparator());
        queryless.setPackageName(extension.getPackageName());
        queryless.setGeneratePath(extension.getGeneratePath());
        queryless.setVariables(extension.getVariables());
        queryless.setVariableSources(extension.getVariableSources());

        project.getPlugins().withType(JavaPlugin.class, javaPlugin -> configureJavaPlugin(project, queryless, extension));
    }

    private void configureJavaPlugin(final Project project, final QuerylessTask queryless, final QuerylessExtension extension) {
        final JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        final SourceSet sourceSet = javaConvention.getSourceSets().findByName(extension.getSourceSetName());

        if (sourceSet != null) {
            if (!sourceSet.getJava().getSrcDirs().contains(extension.getGeneratePath())) {
                sourceSet.getJava().srcDir(extension.getGeneratePath());
            }

            project.getTasks().getByName(sourceSet.getCompileJavaTaskName()).dependsOn(queryless);
        }

        project.getPlugins().withType(IdeaPlugin.class, ideaPlugin ->
                ideaPlugin.getModel().getModule().getGeneratedSourceDirs().add(extension.getGeneratePath()));
    }

}
