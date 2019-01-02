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

import org.apache.commons.text.WordUtils;

@Singleton
public class CodeGeneratorImpl implements CodeGenerator {

    private final PluginConfiguration configuration;

    @Inject
    public CodeGeneratorImpl(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JavaFile generate(final Bundle bundle) {
        final TypeSpec javaClass = generateClass(bundle, Modifier.PUBLIC);

        return JavaFile.builder(configuration.getPackageName(), javaClass)
                .addFileComment("Generated by Queryless (https://github.com/paveljakov/queryless).")
                .build();
    }

    private TypeSpec generateClass(final Bundle bundle, final Modifier... modifiers) {
        final List<FieldSpec> constants = bundle.getQueries()
                .stream()
                .map(this::buildField)
                .collect(Collectors.toList());

        final List<TypeSpec> nestedClasses = bundle.getNested().stream()
                .map(b -> generateClass(b, Modifier.PUBLIC, Modifier.STATIC))
                .collect(Collectors.toList());

        final String className = QueryTextUtils.toClassName(bundle.getName());

        return TypeSpec.classBuilder(className)
                .addJavadoc("<i>Queryless query bundle.</i><br/><h1>$L</h1>\n", WordUtils.capitalizeFully(bundle.getName()))
                .addModifiers(modifiers)
                .addFields(constants)
                .addTypes(nestedClasses)
                .build();
    }

    private FieldSpec buildField(final Query query) {
        return FieldSpec.builder(String.class, QueryTextUtils.toConstantName(query.getId()))
                .addJavadoc("<h2>Query text:</h2><pre>{@code $L}</pre>\n", query.getText())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", query.getText())
                .build();
    }

}
