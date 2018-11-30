package queryless.plugin.source.model;

import java.nio.file.Path;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Source {

    private Path path;
    private SourceType type;

    private String content;

}
