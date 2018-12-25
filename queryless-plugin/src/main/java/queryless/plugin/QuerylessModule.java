package queryless.plugin;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import queryless.plugin.bundle.service.BundleService;
import queryless.plugin.bundle.service.BundleServiceImpl;
import queryless.plugin.generator.CodeGenerator;
import queryless.plugin.generator.CodeGeneratorImpl;
import queryless.plugin.source.loader.SourcesLoader;
import queryless.plugin.source.loader.SourcesLoaderImpl;
import queryless.plugin.source.splitter.PropertiesSourceSplitter;
import queryless.plugin.source.splitter.PropertiesXmlSourceSplitter;
import queryless.plugin.source.splitter.SourceSplitter;
import queryless.plugin.source.splitter.SqlSourceSplitter;

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

}
