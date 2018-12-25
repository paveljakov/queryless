package queryless.plugin.source.splitter;

import org.apache.commons.lang3.StringUtils;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;
import queryless.plugin.utils.QueryTextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

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
