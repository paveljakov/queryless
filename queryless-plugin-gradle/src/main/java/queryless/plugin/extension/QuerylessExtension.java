package queryless.plugin.extension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void setSources(final FileCollection sources) {
        this.sources = sources;
    }
}
