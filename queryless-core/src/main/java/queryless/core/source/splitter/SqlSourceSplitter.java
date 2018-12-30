package queryless.core.source.splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;
import queryless.core.utils.QueryTextUtils;

@Singleton
public class SqlSourceSplitter implements SourceSplitter {

    private final Pattern keyMarkerRegex;

    @Inject
    public SqlSourceSplitter(final PluginConfiguration cfg) {
        keyMarkerRegex = Pattern.compile("^\\s*" + cfg.getQueryCommentPrefix() + "\\s+" + cfg.getQueryKeyMarker() + "\\s*([^\\s].*[^\\s])$",
                                         Pattern.CASE_INSENSITIVE);
    }

    @Override
    public List<Query> split(final Source source) {
        final List<Query> queries = new ArrayList<>();

        StringBuilder queryText = new StringBuilder();
        String queryId = null;

        final List<String> lines = QueryTextUtils.splitLines(source.getContent());
        for (final String line : lines) {
            final Matcher matcher = keyMarkerRegex.matcher(line);
            if (matcher.find()) {
                if (queryId != null) {
                    queries.add(buildQuery(queryId, queryText.toString()));
                }
                queryId = matcher.group(1);
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

    @Override
    public SourceType supports() {
        return SourceType.SQL;
    }

}
