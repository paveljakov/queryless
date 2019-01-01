package queryless.core.bundle.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import queryless.core.bundle.model.Bundle;
import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Source;
import queryless.core.source.splitter.SourceSplitters;

@Singleton
public class BundleServiceImpl implements BundleService {

    private final SourceSplitters sourceSplitters;
    private final PluginConfiguration configuration;

    @Inject
    public BundleServiceImpl(final SourceSplitters sourceSplitters, final PluginConfiguration configuration) {
        this.sourceSplitters = sourceSplitters;
        this.configuration = configuration;
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
        final Map<String, List<Query>> groupedQueries = queries.stream()
                .map(q -> stripParentBundleName(q, bundleName))
                .collect(Collectors.groupingBy(q -> resolveBundleName(bundleName, q)));

        final List<Query> bundleQueries = Optional.ofNullable(groupedQueries.remove(bundleName))
                .orElseGet(ArrayList::new);

        final List<Bundle> nested = groupedQueries.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> buildBundle(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new Bundle(bundleName, bundleQueries, nested);
    }

    private Query stripParentBundleName(final Query query, final String bundleName) {
        final String newId = StringUtils.substringAfter(query.getId(), bundleName + configuration.getNestedBundleSeparator());
        return StringUtils.isBlank(newId) ? query : new Query(newId, query.getText());
    }

    private String resolveBundleName(final String bundleName, final Query query) {
        return isNestedQuery(query) ? StringUtils.substringBefore(query.getId(), configuration.getNestedBundleSeparator()) : bundleName;
    }

    private boolean isNestedQuery(final Query query) {
        return StringUtils.contains(query.getId(), configuration.getNestedBundleSeparator());
    }

}
