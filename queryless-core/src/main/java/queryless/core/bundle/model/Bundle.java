package queryless.core.bundle.model;

import java.util.List;

import lombok.Data;

@Data
public class Bundle {

    private final String name;
    private final List<Query> queries;
    private List<Bundle> nested;

}
