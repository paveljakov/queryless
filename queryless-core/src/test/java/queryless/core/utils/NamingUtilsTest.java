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
package queryless.core.utils;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class NamingUtilsTest {

    @Test
    public void toClassNameTest() {
        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(NamingUtils.toClassName("spare-parts-queries")).isEqualTo("SparePartsQueries");
        softly.assertThat(NamingUtils.toClassName("1-spare-parts-queries")).isEqualTo("$1SparePartsQueries");
        softly.assertThat(NamingUtils.toClassName("999spare-parts-queries")).isEqualTo("$999sparePartsQueries");
        softly.assertThat(NamingUtils.toClassName("class")).isEqualTo("Class");
        softly.assertThat(NamingUtils.toClassName("public")).isEqualTo("Public");
        softly.assertThat(NamingUtils.toClassName("private static final methodName()")).isEqualTo("PrivateStaticFinalMethodname");
        softly.assertThat(NamingUtils.toClassName("ONE_TWO_NAME")).isEqualTo("OneTwoName");
        softly.assertThat(NamingUtils.toClassName("ONE.TWO.NAME")).isEqualTo("OneTwoName");

        softly.assertAll();
    }

    @Test
    public void toConstantNameTest() {
        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(NamingUtils.toConstantName("spare-parts.merge")).isEqualTo("SPARE_PARTS_MERGE");
        softly.assertThat(NamingUtils.toConstantName("1-spare-parts.merge")).isEqualTo("$1_SPARE_PARTS_MERGE");
        softly.assertThat(NamingUtils.toConstantName("999spare-parts.merge")).isEqualTo("$999SPARE_PARTS_MERGE");
        softly.assertThat(NamingUtils.toConstantName("class")).isEqualTo("CLASS");
        softly.assertThat(NamingUtils.toConstantName("public")).isEqualTo("PUBLIC");
        softly.assertThat(NamingUtils.toConstantName("private static final methodName()")).isEqualTo("PRIVATE_STATIC_FINAL_METHODNAME__");
        softly.assertThat(NamingUtils.toConstantName("ONE_TWO_NAME")).isEqualTo("ONE_TWO_NAME");
        softly.assertThat(NamingUtils.toConstantName("ONE.TWO.NAME")).isEqualTo("ONE_TWO_NAME");

        softly.assertAll();
    }

}
