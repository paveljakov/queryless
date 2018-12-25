package queryless.plugin.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import queryless.plugin.bundle.model.Bundle;
import queryless.plugin.bundle.model.Query;
import queryless.plugin.config.PluginConfiguration;
import queryless.plugin.source.splitter.SourceSplitterFactory;
import queryless.plugin.utils.QueryTextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CodeGeneratorImpl implements CodeGenerator {

    private final SourceSplitterFactory sourceSplitterFactory;

    private final PluginConfiguration configuration;

    @Inject
    public CodeGeneratorImpl(final SourceSplitterFactory sourceSplitterFactory, final PluginConfiguration configuration) {
        this.sourceSplitterFactory = sourceSplitterFactory;
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
