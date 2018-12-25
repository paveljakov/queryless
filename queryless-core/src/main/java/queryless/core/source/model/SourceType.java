package queryless.core.source.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceType {
    SQL("sql"),
    XML("xml"),
    PROPERTIES("properties");

    private final String extension;

    SourceType(final String extension) {
        this.extension = extension;
    }

    public static SourceType resolve(final String extension) {
        return Arrays.stream(values())
                .filter(type -> type.getExtension().equalsIgnoreCase(extension))
                .findAny()
                .orElse(null);
    }

}
