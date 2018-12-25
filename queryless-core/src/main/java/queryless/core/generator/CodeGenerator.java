package queryless.core.generator;

import com.squareup.javapoet.JavaFile;
import queryless.core.bundle.model.Bundle;

public interface CodeGenerator {

    JavaFile generate(Bundle bundle);

}
