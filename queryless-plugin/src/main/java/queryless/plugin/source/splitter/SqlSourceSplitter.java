package queryless.plugin.source.splitter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import lombok.Setter;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.config.ConfigurationProvider;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;
import queryless.plugin.utils.QueryTextUtils;

@Setter
@Component(role = SourceSplitter.class, hint = "sql")
public class SqlSourceSplitter implements SourceSplitter {

    @Requirement
    private ConfigurationProvider configurationProvider;

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
        final String keyPrefix = StringUtils.upperCase(configurationProvider.getSqlKeyPrefix());

        return StringUtils.trim(StringUtils.substringAfter(leveledLine, keyPrefix));
    }

    private boolean isIdLine(final String line) {
        final String trimmed = StringUtils.trim(line);
        return StringUtils.startsWith(trimmed, "--") && StringUtils.containsIgnoreCase(trimmed, configurationProvider.getSqlKeyPrefix());
    }

    @Override
    public SourceType supports() {
        return SourceType.SQL;
    }

}
