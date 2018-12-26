package queryless.plugin;

import org.gradle.api.Named;

import java.io.File;

public class QuerylessExtension {

    private String packageName;
    private File generatePath;
    private String resourcesPath;
    private String sqlKeyPrefix;
    private File rootPath;

    private String sourceSetName;

    public String getSourceSetName() {
        if (sourceSetName == null) {
            return "main";
        } else {
            return sourceSetName;
        }
    }

    public void setSourceSetName(final String sourceSetName) {
        this.sourceSetName = sourceSetName;
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

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(final String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public String getSqlKeyPrefix() {
        return sqlKeyPrefix;
    }

    public void setSqlKeyPrefix(final String sqlKeyPrefix) {
        this.sqlKeyPrefix = sqlKeyPrefix;
    }

    public File getRootPath() {
        return rootPath;
    }

    public void setRootPath(final File rootPath) {
        this.rootPath = rootPath;
    }

}
