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
package queryless.core;

import com.squareup.javapoet.JavaFile;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import queryless.core.bundle.service.BundleService;
import queryless.core.bundle.service.BundleServiceImpl;
import queryless.core.file.CodeFileService;
import queryless.core.file.JavaFileService;
import queryless.core.generator.CodeGenerator;
import queryless.core.generator.CodeGeneratorImpl;
import queryless.core.source.loader.SourcesLoader;
import queryless.core.source.loader.SourcesLoaderImpl;
import queryless.core.source.splitter.PropertiesSourceSplitter;
import queryless.core.source.splitter.PropertiesXmlSourceSplitter;
import queryless.core.source.splitter.SourceSplitter;
import queryless.core.source.splitter.SqlSourceSplitter;

@Module
abstract class QuerylessModule {

    @Binds
    @IntoSet
    abstract SourceSplitter providePropertiesSourceSplitter(PropertiesSourceSplitter splitter);

    @Binds
    @IntoSet
    abstract SourceSplitter providePropertiesXmlSourceSplitter(PropertiesXmlSourceSplitter splitter);

    @Binds
    @IntoSet
    abstract SourceSplitter provideSqlSourceSplitter(SqlSourceSplitter splitter);

    @Binds
    abstract BundleService provideBundleService(BundleServiceImpl bundleService);

    @Binds
    abstract SourcesLoader provideSourcesLoader(SourcesLoaderImpl sourcesLoader);

    @Binds
    abstract CodeGenerator provideCodeGenerator(CodeGeneratorImpl codeGenerator);

    @Binds
    abstract CodeFileService<JavaFile> provideJavaFileService(JavaFileService javaFileService);

}
