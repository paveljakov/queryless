package queryless.core.bundle.service;

import queryless.core.bundle.model.Bundle;
import queryless.core.source.model.Source;

import java.util.List;

public interface BundleService {

    Bundle build(String bundleName, List<Source> sources);

}
