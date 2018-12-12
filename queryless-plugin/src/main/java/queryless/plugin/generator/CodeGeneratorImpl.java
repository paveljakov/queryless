package queryless.plugin.generator;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.config.ConfigurationProvider;
import queryless.plugin.source.splitter.SourceSplitterFactory;
import queryless.plugin.utils.QueryTextUtils;

@Component(role = CodeGenerator.class)
public class CodeGeneratorImpl implements CodeGenerator {

    @Requirement
    private Logger logger;

    @Requirement
    private SourceSplitterFactory sourceSplitterFactory;

    @Requirement
    private ConfigurationProvider configurationProvider;

    @Override
    public JavaFile generate(final Bundle bundle) {
        final List<FieldSpec> constants = bundle.getQueries()
                .stream()
                .map(this::buildField)
                .collect(Collectors.toList());

        final String className = QueryTextUtils.toClassName(bundle.getName());

        final TypeSpec javaClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addFields(constants)
                .build();

        return JavaFile.builder(configurationProvider.getPackageName(), javaClass)
                .build();
    }

    private FieldSpec buildField(final Query query) {
        return FieldSpec.builder(String.class, QueryTextUtils.toConstantName(query.getId()))
                .addJavadoc("<pre>{@code $L}</pre>\n", query.getText())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", query.getText())
                .build();
    }

}
