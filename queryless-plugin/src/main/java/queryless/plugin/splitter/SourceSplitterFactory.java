package queryless.plugin.splitter;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import lombok.Getter;

@Getter
@Component(role = SourceSplitterFactory.class)
public class SourceSplitterFactory {

    @Requirement
    private List<SourceSplitter> splitters;

}
