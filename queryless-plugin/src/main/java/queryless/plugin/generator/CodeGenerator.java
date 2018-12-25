package queryless.plugin.generator;

import com.squareup.javapoet.JavaFile;
import queryless.plugin.bundle.model.Bundle;

public interface CodeGenerator {

    JavaFile generate(Bundle bundle);

}
