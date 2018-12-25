package queryless.plugin.execute;

import com.squareup.javapoet.JavaFile;
import queryless.plugin.bundle.service.BundleService;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.generator.CodeGenerator;
import queryless.plugin.logging.Log;
import queryless.plugin.source.loader.SourcesLoader;
import queryless.plugin.source.model.Source;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Log log;
    private final PluginConfiguration configuration;

    private final SourcesLoader sourcesLoader;
    private final BundleService bundleService;
    private final CodeGenerator codeGenerator;

    @Inject
    public PluginExecutor(final Log log, final PluginConfiguration configuration, final SourcesLoader sourcesLoader,
                          final BundleService bundleService, final CodeGenerator codeGenerator) {

        this.log = log;
        this.configuration = configuration;
        this.sourcesLoader = sourcesLoader;
        this.bundleService = bundleService;
        this.codeGenerator = codeGenerator;
    }

    public void execute(final String[] sourcesLocations) throws IOException {
        Files.createDirectories(configuration.getGeneratePath());

        log.info("Generating query constants.");

        sourcesLoader.load(sourcesLocations).stream()
                .collect(Collectors.groupingBy(Source::getBundleName))
                .entrySet().stream()
                .map(e -> bundleService.build(e.getKey(), e.getValue()))
                .map(codeGenerator::generate)
                .forEach(this::writeToFile);
    }

    private void writeToFile(final JavaFile javaFile) {
        try {
            javaFile.writeTo(configuration.getGeneratePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
