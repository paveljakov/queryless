/*
 * ==============================LICENSE_START=============================
 * Queryless (query constants generation)
 * ========================================================================
 * Copyright (C) 2018 - 2019 Pavel Jakovlev
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============================LICENSE_END==============================
 */
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
        final String queryCommentPrefix = Pattern.quote(cfg.getQueryCommentPrefix());
        final String queryKeyMarker = Pattern.quote(cfg.getQueryKeyMarker());
        keyMarkerRegex = Pattern.compile("^\\s*" + queryCommentPrefix + "\\s+" + queryKeyMarker + "\\s*([^\\s].*[^\\s])$",
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
