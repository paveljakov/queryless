package queryless.core.generator;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import queryless.core.bundle.model.Bundle;
import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.splitter.SourceSplitters;
import queryless.core.utils.QueryTextUtils;

@Singleton
public class CodeGeneratorImpl implements CodeGenerator {

    private final SourceSplitters sourceSplitters;

    private final PluginConfiguration configuration;

    @Inject
    public CodeGeneratorImpl(final SourceSplitters sourceSplitters, final PluginConfiguration configuration) {
        this.sourceSplitters = sourceSplitters;
        this.configuration = configuration;
    }

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

        return JavaFile.builder(configuration.getPackageName(), javaClass)
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
