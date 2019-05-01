/*
 * ==============================LICENSE_START=============================
 * Queryless (query constants generation)
 * ========================================================================
 * Copyright (C) 2018 - 2019 Pavel Jakovlev
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============================LICENSE_END==============================
 */
package queryless.core.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.AnnotationSpec.Builder;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.text.WordUtils;
import queryless.core.bundle.model.Bundle;
import queryless.core.config.PluginConfiguration;
import queryless.core.source.model.Query;
import queryless.core.utils.NamingUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CodeGeneratorImpl implements CodeGenerator {
    private static final String GENERATED_COMMENT = "Generated by Queryless (https://github.com/paveljakov/queryless)";

    private final PluginConfiguration configuration;

    @Inject
    public CodeGeneratorImpl(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JavaFile generate(final Bundle bundle) {
        final TypeSpec javaClass = generateClass(bundle, Modifier.PUBLIC, Modifier.FINAL);

        return JavaFile.builder(configuration.getPackageName(), javaClass)
                .addFileComment(GENERATED_COMMENT)
                .build();
    }

    private TypeSpec generateClass(final Bundle bundle, final Modifier... modifiers) {
        final List<FieldSpec> constants = bundle.getQueries()
                .stream()
                .map(this::buildField)
                .collect(Collectors.toList());

        final List<TypeSpec> nestedClasses = bundle.getNested().stream()
                .map(b -> generateClass(b, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))
                .collect(Collectors.toList());

        final String className = NamingUtils.toClassName(bundle.getName());

        return TypeSpec.classBuilder(className)
                .addJavadoc("<i>Queryless query bundle.</i><br><h1>$L</h1>\n", WordUtils.capitalizeFully(bundle.getName()))
                .addAnnotation(buildGeneratedAnnotation())
                .addModifiers(modifiers)
                .addFields(constants)
                .addTypes(nestedClasses)
                .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())
                .build();
    }

    private FieldSpec buildField(final Query query) {
        return FieldSpec.builder(String.class, NamingUtils.toConstantName(query.getId()))
                .addJavadoc("<h2>Query text:</h2><pre>{@code $L}</pre>\n", query.getText())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", query.getText())
                .build();
    }

    private AnnotationSpec buildGeneratedAnnotation() {
        final Builder generated = AnnotationSpec.builder(resolveGeneratedAnnotation())
                .addMember("value", "$S", GENERATED_COMMENT);

        if (configuration.isAddGenerateTimestamp()) {
            generated.addMember("date", "$S", LocalDateTime.now());
        }

        return generated.build();
    }

    private Class<?> resolveGeneratedAnnotation() {
        try {
            final String generatedAnnotation = SourceVersion.latestSupported().compareTo(SourceVersion.RELEASE_8) > 0
                    ? "javax.annotation.processing.Generated"
                    : "javax.annotation.Generated";

            return Class.forName(generatedAnnotation);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
