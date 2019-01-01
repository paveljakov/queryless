package queryless.core.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import queryless.core.bundle.model.Bundle;
import queryless.core.bundle.model.Query;
import queryless.core.config.PluginConfiguration;
import queryless.core.utils.QueryTextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CodeGeneratorImpl implements CodeGenerator {

    private final PluginConfiguration configuration;

    @Inject
    public CodeGeneratorImpl(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JavaFile generate(final Bundle bundle) {
        final TypeSpec javaClass = generateInternal(bundle, new Modifier[]{Modifier.PUBLIC});

        return JavaFile.builder(configuration.getPackageName(), javaClass)
                .build();
    }

    private TypeSpec generateInternal(final Bundle bundle, final Modifier[] modifiers) {
        final List<FieldSpec> constants = bundle.getQueries()
                .stream()
                .map(this::buildField)
                .collect(Collectors.toList());

        final List<TypeSpec> nestedClasses = bundle.getNested().stream()
                .map(b -> generateInternal(b, new Modifier[]{Modifier.PUBLIC, Modifier.STATIC}))
                .collect(Collectors.toList());

        final String className = QueryTextUtils.toClassName(bundle.getName());

        return TypeSpec.classBuilder(className)
                .addModifiers(modifiers)
                .addFields(constants)
                .addTypes(nestedClasses)
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
