package queryless.plugin.source.splitter;

import queryless.plugin.source.model.SourceType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Set;

@Singleton
public class SourceSplitterFactory {

    private final Set<SourceSplitter> splitters;

    @Inject
    public SourceSplitterFactory(final Set<SourceSplitter> splitters) {
        this.splitters = splitters;
    }

    public SourceSplitter get(final SourceType type) {
        return splitters.stream()
                .filter(s -> Objects.equals(s.supports(), type))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unsupported source type: " + type));
    }

}
