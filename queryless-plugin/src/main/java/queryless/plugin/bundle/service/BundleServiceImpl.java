package queryless.plugin.bundle.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.splitter.SourceSplitterFactory;

@Component(role = BundleService.class)
public class BundleServiceImpl implements BundleService {

    @Requirement
    private Logger logger;

    @Requirement
    private SourceSplitterFactory sourceSplitterFactory;

    @Override
    public Bundle build(final String bundleName, final List<Source> sources) {
        Objects.requireNonNull(sources);

        final List<Query> queries = sources.stream()
                .flatMap(s -> sourceSplitterFactory.get(s.getType()).split(s).stream())
                .collect(Collectors.toList());

        return buildBundle(bundleName, queries);
    }

    private Bundle buildBundle(final String bundleName, final List<Query> queries) {
        return new Bundle(bundleName, queries);
    }

}
