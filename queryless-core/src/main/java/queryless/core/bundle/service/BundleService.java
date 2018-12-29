package queryless.core.bundle.service;

import java.util.List;

import queryless.core.bundle.model.Bundle;
import queryless.core.source.model.Source;

public interface BundleService {

    Bundle build(String bundleName, List<Source> sources);

}
