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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import queryless.core.DaggerQuerylessPlugin;
import queryless.core.QuerylessPlugin;
import queryless.core.config.DefaultConfiguration;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.plugin.logging.MavenLog;
import queryless.plugin.source.PathResolver;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

@Mojo(name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresDependencyCollection = ResolutionScope.COMPILE,
        threadSafe = true)
public class MavenPlugin extends AbstractMojo {

    @Parameter(property = "queryless.sources",
            required = true)
    private Set<String> sources;

    @Parameter(property = "queryless.package",
            defaultValue = DefaultConfiguration.DEFAULT_PACKAGE_NAME)
    private String packageName;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/queryless",
            property = "queryless.generatePath",
            required = true)
    private File generatePath;

    @Parameter(property = "queryless.resourcesPath",
            defaultValue = "src/main/resources")
    private String resourcesPath;

    @Parameter(property = "queryless.queryKeyMarker",
            defaultValue = DefaultConfiguration.DEFAULT_QUERY_KEY_MARKER)
    private String queryKeyMarker;

    @Parameter(property = "queryless.queryCommentPrefix",
            defaultValue = DefaultConfiguration.DEFAULT_QUERY_COMMENT_PREFIX)
    private String queryCommentPrefix;

    @Parameter(property = "queryless.nestedBundleSeparator",
            defaultValue = DefaultConfiguration.DEFAULT_NESTED_BUNDLE_SEPARATOR)
    private String nestedBundleSeparator;

    @Parameter(property = "queryless.variables")
    private Map<String, String> variables;

    @Parameter(property = "queryless.variableSources")
    private Set<String> variableSources;

    @Parameter(defaultValue = "${project.basedir}")
    private File root;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        try {
            project.addCompileSourceRoot(generatePath.toString());

            if (sources == null || sources.isEmpty()) {
                return;
            }

            final QuerylessPlugin plugin = DaggerQuerylessPlugin
                    .builder()
                    .logger(buildLog())
                    .configuration(buildConfig())
                    .build();

            final Set<Path> sourcePaths = PathResolver.resolve(sources, getResourcesPath());

            plugin.executor().execute(sourcePaths);

        } catch (Exception e) {
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
                queryCommentPrefix,
                queryKeyMarker,
                nestedBundleSeparator,
                variables,
                PathResolver.resolve(variableSources, getResourcesPath()));
    }

    private Path getResourcesPath() {
        return root.toPath().resolve(resourcesPath);
    }

}
