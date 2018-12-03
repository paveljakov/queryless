package queryless.plugin.source.splitter;

import java.util.List;
import java.util.Objects;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import queryless.plugin.source.model.SourceType;

@Component(role = SourceSplitterFactory.class)
public class SourceSplitterFactory {

    @Requirement
    private List<SourceSplitter> splitters;

    public SourceSplitter get(final SourceType type) {
        return splitters.stream()
                .filter(s -> Objects.equals(s.getSupports(), type))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unsupported source type: " + type));
    }

}
