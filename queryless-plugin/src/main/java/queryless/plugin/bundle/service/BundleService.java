package queryless.plugin.bundle.service;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.source.model.Source;

public interface BundleService {

    Bundle build(Source source);

}
