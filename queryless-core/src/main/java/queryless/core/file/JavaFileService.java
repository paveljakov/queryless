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
package queryless.core.file;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.squareup.javapoet.JavaFile;

import queryless.core.config.PluginConfiguration;

@Singleton
public class JavaFileService implements CodeFileService<JavaFile> {

    private final PluginConfiguration configuration;

    @Inject
    public JavaFileService(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void writeToFile(final JavaFile code) {
        try {
            code.writeTo(configuration.getGeneratePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
