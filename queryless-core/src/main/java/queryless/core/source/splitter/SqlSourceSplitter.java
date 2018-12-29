package queryless.core.source.splitter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;
import queryless.core.utils.QueryTextUtils;

@Singleton
public class SqlSourceSplitter implements SourceSplitter {

    private final PluginConfiguration configuration;

    @Inject
    public SqlSourceSplitter(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<Query> split(final Source source) {
        final List<Query> queries = new ArrayList<>();

        StringBuilder queryText = new StringBuilder();
        String queryId = null;

        final List<String> lines = QueryTextUtils.splitLines(source.getContent());
        for (final String line : lines) {
            if (isIdLine(line)) {
                if (queryId != null) {
                    queries.add(buildQuery(queryId, queryText.toString()));
                }
                queryId = extractKey(line);
                queryText = new StringBuilder();

            } else {
                queryText.append(line).append("\n");
            }
        }

        if (queryId != null) {
            queries.add(buildQuery(queryId, queryText.toString()));
        }

        return queries;
    }

    private Query buildQuery(final String id, final String text) {
        return new Query(id, QueryTextUtils.removeIndentation(text));
    }

    private String extractKey(final String line) {
        final String leveledLine = StringUtils.upperCase(line);
        final String keyPrefix = StringUtils.upperCase(configuration.getSqlKeyPrefix());

        return StringUtils.trim(StringUtils.substringAfter(leveledLine, keyPrefix));
    }

    private boolean isIdLine(final String line) {
        final String trimmed = StringUtils.trim(line);
        return StringUtils.startsWith(trimmed, "--") && StringUtils.containsIgnoreCase(trimmed, configuration.getSqlKeyPrefix());
    }

    @Override
    public SourceType supports() {
        return SourceType.SQL;
    }

}
