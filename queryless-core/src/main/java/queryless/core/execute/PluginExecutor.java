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
package queryless.core.execute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.squareup.javapoet.JavaFile;

import queryless.core.bundle.service.BundleService;
import queryless.core.config.PluginConfiguration;
import queryless.core.file.CodeFileService;
import queryless.core.generator.CodeGenerator;
import queryless.core.logging.Log;
import queryless.core.source.loader.SourcesLoader;
import queryless.core.source.model.Source;

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

    public void execute(final Set<Path> sources) throws IOException {
        Files.createDirectories(configuration.getGeneratePath());

        log.info("Generating query constants.");

        sourcesLoader.load(sources).stream()
                .collect(Collectors.groupingBy(Source::getBundleName))
                .entrySet().stream()
                .map(e -> bundleService.build(e.getKey(), e.getValue()))
                .map(codeGenerator::generate)
                .forEach(codeFileService::writeToFile);
    }

}
