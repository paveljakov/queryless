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
package queryless.core.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultConfiguration {

    public final static String DEFAULT_PACKAGE_NAME = "queryless.generated";
    public final static String DEFAULT_QUERY_KEY_MARKER = "id:";
    public final static String DEFAULT_QUERY_COMMENT_PREFIX = "--";
    public final static String DEFAULT_NESTED_BUNDLE_SEPARATOR = ".";
    public final static String DEFAULT_FILENAME_BUNDLE_SEPARATOR = ".";

}
