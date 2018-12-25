package queryless.core.execute;

import com.squareup.javapoet.JavaFile;
import queryless.core.bundle.service.BundleService;
import queryless.core.config.PluginConfiguration;
import queryless.core.file.CodeFileService;
import queryless.core.generator.CodeGenerator;
import queryless.core.logging.Log;
import queryless.core.source.loader.SourcesLoader;
import queryless.core.source.model.Source;

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

    private final CodeFileService<JavaFile> codeFileService;

    @Inject
    public PluginExecutor(final Log log, final PluginConfiguration configuration, final SourcesLoader sourcesLoader,
                          final BundleService bundleService, final CodeGenerator codeGenerator,
                          final CodeFileService<JavaFile> codeFileService) {

        this.log = log;
        this.configuration = configuration;
        this.sourcesLoader = sourcesLoader;
        this.bundleService = bundleService;
        this.codeGenerator = codeGenerator;
        this.codeFileService = codeFileService;
    }

    public void execute(final String[] sourcesLocations) throws IOException {
        Files.createDirectories(configuration.getGeneratePath());

        log.info("Generating query constants.");

        sourcesLoader.load(sourcesLocations).stream()
                .collect(Collectors.groupingBy(Source::getBundleName))
                .entrySet().stream()
                .map(e -> bundleService.build(e.getKey(), e.getValue()))
                .map(codeGenerator::generate)
                .forEach(codeFileService::writeToFile);
    }

}
