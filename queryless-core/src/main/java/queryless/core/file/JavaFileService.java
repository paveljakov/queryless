package queryless.core.file;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.squareup.javapoet.JavaFile;

import queryless.core.config.PluginConfiguration;

@Singleton
public class JavaFileService implements CodeFileService<JavaFile> {

    private final PluginConfiguration configuration;

    @Inject
    public JavaFileService(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void writeToFile(final JavaFile code) {
        try {
            code.writeTo(configuration.getGeneratePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
