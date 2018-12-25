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
