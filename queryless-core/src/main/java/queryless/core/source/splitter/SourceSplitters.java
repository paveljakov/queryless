package queryless.core.source.splitter;

import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import queryless.core.source.model.SourceType;

@Singleton
public class SourceSplitters {

    private final Set<SourceSplitter> splitters;

    @Inject
    public SourceSplitters(final Set<SourceSplitter> splitters) {
        this.splitters = splitters;
    }

    public SourceSplitter get(final SourceType type) {
        return splitters.stream()
                .filter(s -> Objects.equals(s.supports(), type))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unsupported source type: " + type));
    }

}
