package queryless.core.source.model;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.Objects;

@Data
public class Source implements Comparable<Source> {

    private final Path path;
    private final SourceType type;

    private final String content;

    public String getName() {
        return FilenameUtils.removeExtension(path.getFileName().toString());
    }

    public String getBundleName() {
        return StringUtils.substringBefore(getName(), ".");
    }

    @Override
    public int compareTo(final Source o) {
        return Objects.compare(getPath(), o.getPath(), Path::compareTo);
    }

}
