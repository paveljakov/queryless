package queryless.core.source.splitter;

import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;

@RunWith(MockitoJUnitRunner.class)
public class SqlSourceSplitterTest {

    private SqlSourceSplitter splitter;

    @Before
    public void setUp() {
        final PluginConfiguration configuration = Mockito.mock(PluginConfiguration.class);
        when(configuration.getSqlKeyPrefix()).thenReturn("id:");

        splitter = new SqlSourceSplitter(configuration);
    }

    @Test
    public void testSplit() throws Exception {
        final File sqlFile = new File(getClass().getResource("/queries/test3.sql").toURI());
        final String sql = FileUtils.readFileToString(sqlFile, StandardCharsets.UTF_8);

        List<Query> queries = splitter.split(new Source(sqlFile.toPath(), SourceType.SQL, sql));

        // TODO use assertj
        Assert.assertEquals(3, queries.size());
    }

}