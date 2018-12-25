package queryless.core;

import dagger.BindsInstance;
import dagger.Component;
import queryless.core.config.PluginConfiguration;
import queryless.core.execute.PluginExecutor;
import queryless.core.logging.Log;

import javax.inject.Singleton;

@Singleton
@Component(modules = QuerylessModule.class)
public interface QuerylessPlugin {
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
