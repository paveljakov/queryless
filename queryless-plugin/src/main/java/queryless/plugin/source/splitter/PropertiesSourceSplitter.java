package queryless.plugin.source.splitter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.component.annotations.Component;

import queryless.plugin.bundle.model.Query;
import queryless.plugin.source.model.Source;
import queryless.plugin.source.model.SourceType;

@Component(role = SourceSplitter.class, hint = "properties")
public class PropertiesSourceSplitter implements SourceSplitter {

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
        return SourceType.PROPERTIES;
    }

    private Properties loadProperties(final Source source) {
        try (final InputStream stream = IOUtils.toInputStream(source.getContent(), StandardCharsets.UTF_8)) {
            final Properties properties = new Properties();
            properties.load(stream);

            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Query buildQuery(final Map.Entry<Object, Object> entry) {
        return new Query(entry.getKey().toString(), entry.getValue().toString());
    }

}