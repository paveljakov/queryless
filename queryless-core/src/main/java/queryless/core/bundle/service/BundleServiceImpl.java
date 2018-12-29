package queryless.core.bundle.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import queryless.core.bundle.model.Bundle;
import queryless.core.bundle.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.splitter.SourceSplitters;

@Singleton
public class BundleServiceImpl implements BundleService {

    private final SourceSplitters sourceSplitters;

    @Inject
    public BundleServiceImpl(final SourceSplitters sourceSplitters) {
        this.sourceSplitters = sourceSplitters;
    }

    @Override
    public Bundle build(final String bundleName, final List<Source> sources) {
        Objects.requireNonNull(sources);

        final List<Query> queries = sources.stream()
                .flatMap(s -> sourceSplitters.get(s.getType()).split(s).stream())
                .collect(Collectors.toList());

        return buildBundle(bundleName, queries);
    }

    private Bundle buildBundle(final String bundleName, final List<Query> queries) {
        return new Bundle(bundleName, queries);
    }

}
