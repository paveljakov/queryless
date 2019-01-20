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
package queryless.plugin.extension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;

import queryless.core.config.DefaultConfiguration;

public class QuerylessExtension {

    private final static String DEFAULT_SOURCE_SET = "main";
    private final static String DEFAULT_GENERATE_PATH = "generated/queryless";

    private String sourceSetName;

    private String packageName;
    private File generatePath;

    private String queryKeyMarker;
    private String queryCommentPrefix;
    private String nestedBundleSeparator;

    private Map<String, String> variables;
    private FileCollection variableSources;

    private FileCollection sources;

    private final Project project;

    public QuerylessExtension(final Project project) {
        this.project = project;
    }

    public String getSourceSetName() {
        return StringUtils.isBlank(sourceSetName) ? DEFAULT_SOURCE_SET : sourceSetName;
    }

    public String getPackageName() {
        return StringUtils.isBlank(packageName) ? DefaultConfiguration.DEFAULT_PACKAGE_NAME : packageName;
    }

    public File getGeneratePath() {
        final Path buildDir = project.getBuildDir().toPath();
        final Path generateDir = generatePath == null ? Paths.get(DEFAULT_GENERATE_PATH) : generatePath.toPath();
        return buildDir.resolve(generateDir).toFile();
    }

    public String getQueryKeyMarker() {
        return StringUtils.isBlank(queryKeyMarker) ? DefaultConfiguration.DEFAULT_QUERY_KEY_MARKER : queryKeyMarker;
    }

    public String getQueryCommentPrefix() {
        return StringUtils.isBlank(queryCommentPrefix) ? DefaultConfiguration.DEFAULT_QUERY_COMMENT_PREFIX : queryCommentPrefix;
    }

    public String getNestedBundleSeparator() {
        return StringUtils.isBlank(nestedBundleSeparator) ? DefaultConfiguration.DEFAULT_NESTED_BUNDLE_SEPARATOR : nestedBundleSeparator;
    }

    public Map<String, String> getVariables() {
        return variables == null ? new HashMap<>() : variables;
    }

    public FileCollection getVariableSources() {
        return variableSources == null ? project.files() : variableSources;
    }

    public FileCollection getSources() {
        return sources;
    }

    public void setSourceSetName(final String sourceSetName) {
        this.sourceSetName = sourceSetName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public void setGeneratePath(final File generatePath) {
        this.generatePath = generatePath;
    }

    public void setQueryKeyMarker(final String queryKeyMarker) {
        this.queryKeyMarker = queryKeyMarker;
    }

    public void setQueryCommentPrefix(final String queryCommentPrefix) {
        this.queryCommentPrefix = queryCommentPrefix;
    }

    public void setNestedBundleSeparator(final String nestedBundleSeparator) {
        this.nestedBundleSeparator = nestedBundleSeparator;
    }

    public void setVariables(final Map<String, String> variables) {
        this.variables = variables;
    }

    public void setSources(final FileCollection sources) {
        this.sources = sources;
    }

    public void setVariableSources(final FileCollection variableSources) {
        this.variableSources = variableSources;
    }
}
