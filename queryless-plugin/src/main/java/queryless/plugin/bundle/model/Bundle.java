package queryless.plugin.bundle.model;

import lombok.Data;

import java.util.List;

@Data
public class Bundle {

    private final String name;
    private final List<Query> queries;

}
