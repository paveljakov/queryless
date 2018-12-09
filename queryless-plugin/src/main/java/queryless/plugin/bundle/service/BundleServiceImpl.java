package queryless.plugin.bundle.service;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.splitter.SourceSplitter;
import queryless.plugin.source.splitter.SourceSplitterFactory;

@Component(role = BundleService.class)
public class BundleServiceImpl implements BundleService {

    @Requirement
    private Logger logger;

    @Requirement
    private SourceSplitterFactory sourceSplitterFactory;

    @Override
    public Bundle build(final Source source) {
        final SourceSplitter splitter = sourceSplitterFactory.get(source.getType());
        final List<Query> queries = splitter.split(source);
        return new Bundle(source.getName(), queries);
    }

}
