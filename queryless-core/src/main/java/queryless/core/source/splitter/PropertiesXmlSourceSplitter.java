package queryless.core.source.splitter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;

import queryless.core.bundle.model.Query;
import queryless.core.source.model.Source;
import queryless.core.source.model.SourceType;
import queryless.core.utils.QueryTextUtils;

@Singleton
public class PropertiesXmlSourceSplitter implements SourceSplitter {

    @Inject
    public PropertiesXmlSourceSplitter() {
    }

    @Override
    public List<Query> split(final Source source) {
        final Properties properties = loadProperties(source);
        return properties.entrySet()
                .stream()
                .map(this::buildQuery)
                .collect(Collectors.toList());
    }

    @Override
    public SourceType supports() {
        return SourceType.XML;
    }

    private Properties loadProperties(final Source source) {
        try (final InputStream stream = IOUtils.toInputStream(source.getContent(), StandardCharsets.UTF_8)) {
            final Properties properties = new Properties();
            properties.loadFromXML(stream);

            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Query buildQuery(final Map.Entry<Object, Object> entry) {
        return new Query(entry.getKey().toString(), QueryTextUtils.removeIndentation(entry.getValue().toString()));
    }

}
