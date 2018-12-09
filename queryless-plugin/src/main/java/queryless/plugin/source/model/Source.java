package queryless.plugin.source.model;

import java.nio.file.Path;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;

import lombok.Data;

@Data
public class Source implements Comparable<Source> {

    private final Path path;
    private final SourceType type;

    private final String content;

    public String getName() {
        return FilenameUtils.removeExtension(path.getFileName().toString());
    }

    @Override
    public int compareTo(final Source o) {
        return Objects.compare(getPath(), o.getPath(), Path::compareTo);
    }

}