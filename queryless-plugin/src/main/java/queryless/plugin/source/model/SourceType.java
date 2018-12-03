package queryless.plugin.source.model;

import java.util.Arrays;

import lombok.Getter;

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
