package queryless.plugin.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.config.ConfigurationProvider;
import queryless.plugin.source.splitter.SourceSplitterFactory;
import queryless.plugin.utils.NameUtils;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component(role = CodeGenerator.class)
public class CodeGeneratorImpl implements CodeGenerator {

    @Requirement
    private Logger logger;

    @Requirement
    private SourceSplitterFactory sourceSplitterFactory;

    @Requirement
    private ConfigurationProvider configurationProvider;

    @Override
    public void generate(final Bundle bundle) {
        writeToFile(generateJavaFile(bundle));
    }

    private JavaFile generateJavaFile(final Bundle bundle) {
        final List<FieldSpec> constants = bundle.getQueries()
                .stream()
                .map(this::buildField)
                .collect(Collectors.toList());

        final String className = NameUtils.toClassName(bundle.getName());

        final TypeSpec javaClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addFields(constants)
                .build();

        return JavaFile.builder(configurationProvider.getConfiguration().getPackageName(), javaClass)
                .build();
    }

    private FieldSpec buildField(final Query query) {
        return FieldSpec.builder(String.class, NameUtils.toConstantName(query.getId()))
                .addJavadoc("Query:<br /><pre>{@code $L}</pre>\n", query.getText())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", query.getText())
                .build();
    }

    private void writeToFile(final JavaFile javaFile) {
        try {
            javaFile.writeTo(resolveGeneratePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path resolveGeneratePath() {
        return configurationProvider.getConfiguration().getGeneratePath().toPath();
    }

}
