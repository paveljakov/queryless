package queryless.plugin.generator;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import queryless.plugin.query.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.splitter.SourceSplitter;
import queryless.plugin.source.splitter.SourceSplitterFactory;

@Component(role = ConstantsGenerator.class)
public class ConstantsGeneratorImpl implements ConstantsGenerator {

    @Requirement
    private Logger logger;

    @Requirement
    private SourceSplitterFactory sourceSplitterFactory;

    @Override
    public void generate(final Source source) {
        final SourceSplitter splitter = sourceSplitterFactory.get(source.getType());
        final List<Query> queries = splitter.split(source);

        queries.forEach(s -> logger.info(s.getText()));
    }

}
