package queryless.plugin.bundle.service;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.source.model.Source;

import java.util.List;

public interface BundleService {

    Bundle build(String bundleName, List<Source> sources);

}
