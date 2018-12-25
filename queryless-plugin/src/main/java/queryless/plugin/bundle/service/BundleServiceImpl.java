package queryless.plugin.bundle.service;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.splitter.SourceSplitterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class BundleServiceImpl implements BundleService {

    private final SourceSplitterFactory sourceSplitterFactory;

    @Inject
    public BundleServiceImpl(final SourceSplitterFactory sourceSplitterFactory) {
        this.sourceSplitterFactory = sourceSplitterFactory;
    }

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
