package queryless.core.bundle.model;

import lombok.Data;

import java.util.List;

@Data
public class Bundle {

    private final String name;
    private final List<Query> queries;
    private List<Bundle> nested;

}
