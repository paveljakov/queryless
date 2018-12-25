package queryless.plugin;

import dagger.BindsInstance;
import dagger.Component;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.execute.PluginExecutor;
import queryless.plugin.logging.Log;

import javax.inject.Singleton;

@Singleton
@Component(modules = QuerylessModule.class)
interface QuerylessPlugin {
    PluginExecutor executor();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder configuration(PluginConfiguration configuration);

        @BindsInstance
        Builder logger(Log logger);

        QuerylessPlugin build();

    }

}
