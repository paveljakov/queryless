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
package queryless.plugin.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import queryless.core.DaggerQuerylessPlugin;
import queryless.core.QuerylessPlugin;
import queryless.core.config.PluginConfiguration;
import queryless.core.logging.Log;
import queryless.plugin.logging.GradleLog;

public class QuerylessTask extends DefaultTask {

    @Input
    private String packageName;

    @OutputDirectory
    private File generatePath;

    @Input
    private String queryKeyMarker;

    @Input
    private String queryCommentPrefix;

    @Input
    private String nestedBundleSeparator;

    @InputFiles
    private FileCollection sources;

    @TaskAction
    void generate() throws IOException {
        Files.createDirectories(generatePath.toPath());

        if (sources == null) {
            return;
        }

        final QuerylessPlugin plugin = DaggerQuerylessPlugin
                .builder()
                .logger(buildLog())
                .configuration(buildConfiguration())
                .build();

        plugin.executor().execute(resolveSources(sources));
    }

    private PluginConfiguration buildConfiguration() {
        return new PluginConfiguration(
                getPackageName(),
                getProject().getBuildDir().toPath().resolve(getGeneratePath().toPath()),
                getQueryCommentPrefix(),
                getQueryKeyMarker(),
                getNestedBundleSeparator());
    }

    private Log buildLog() {
        return new GradleLog(getProject().getLogger());
    }

    private Set<Path> resolveSources(final FileCollection files) {
        if (files == null) {
            return Collections.emptySet();
        }

        return files.getFiles()
                .stream()
                .map(File::toPath)
                .collect(Collectors.toSet());
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public File getGeneratePath() {
        return generatePath;
    }

    public void setGeneratePath(final File generatePath) {
        this.generatePath = generatePath;
    }

    public String getQueryKeyMarker() {
        return queryKeyMarker;
    }

    public void setQueryKeyMarker(final String queryKeyMarker) {
        this.queryKeyMarker = queryKeyMarker;
    }

    public String getQueryCommentPrefix() {
        return queryCommentPrefix;
    }

    public void setQueryCommentPrefix(final String queryCommentPrefix) {
        this.queryCommentPrefix = queryCommentPrefix;
    }

    public String getNestedBundleSeparator() {
        return nestedBundleSeparator;
    }

    public void setNestedBundleSeparator(final String nestedBundleSeparator) {
        this.nestedBundleSeparator = nestedBundleSeparator;
    }

    public FileCollection getSources() {
        return sources;
    }

    public void setSources(final FileCollection sources) {
        this.sources = sources;
    }
}
