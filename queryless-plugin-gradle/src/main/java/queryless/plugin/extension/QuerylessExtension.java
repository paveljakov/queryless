package queryless.plugin.extension;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;

public class QuerylessExtension {

    private final static String DEFAULT_SOURCE_SET = "main";
    private final static String DEFAULT_GENERATE_PATH = "generated/queryless";
    private final static String DEFAULT_PACKAGE_NAME = "queryless.generated";
    private final static String DEFAULT_SQL_KEY_PREFIX = "id:";

    private String sourceSetName;

    private String packageName;
    private File generatePath;

    private String sqlKeyPrefix;

    private FileCollection sources;

    private final Project project;

    public QuerylessExtension(final Project project) {
        this.project = project;
    }

    public String getSourceSetName() {
        return StringUtils.isBlank(sourceSetName) ? DEFAULT_SOURCE_SET : sourceSetName;
    }

    public String getPackageName() {
        return StringUtils.isBlank(packageName) ? DEFAULT_PACKAGE_NAME : packageName;
    }

    public File getGeneratePath() {
        return generatePath == null ? new File(DEFAULT_GENERATE_PATH) : generatePath;
    }

    public String getSqlKeyPrefix() {
        return StringUtils.isBlank(sqlKeyPrefix) ? DEFAULT_SQL_KEY_PREFIX : sqlKeyPrefix;
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

    public void setSqlKeyPrefix(final String sqlKeyPrefix) {
        this.sqlKeyPrefix = sqlKeyPrefix;
    }

    public void setSources(final FileCollection sources) {
        this.sources = sources;
    }
}
