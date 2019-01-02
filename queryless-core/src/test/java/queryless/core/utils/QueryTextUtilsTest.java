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

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class QueryTextUtilsTest {

    @Test
    public void toClassNameTest() {
        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(QueryTextUtils.toClassName("spare-parts-queries")).isEqualTo("SparePartsQueries");
        softly.assertThat(QueryTextUtils.toClassName("1-spare-parts-queries")).isEqualTo("$1SparePartsQueries");
        softly.assertThat(QueryTextUtils.toClassName("999spare-parts-queries")).isEqualTo("$999sparePartsQueries");
        softly.assertThat(QueryTextUtils.toClassName("class")).isEqualTo("Class");
        softly.assertThat(QueryTextUtils.toClassName("public")).isEqualTo("Public");
        softly.assertThat(QueryTextUtils.toClassName("private static final methodName()")).isEqualTo("PrivateStaticFinalMethodname");
        softly.assertThat(QueryTextUtils.toClassName("ONE_TWO_NAME")).isEqualTo("OneTwoName");
        softly.assertThat(QueryTextUtils.toClassName("ONE.TWO.NAME")).isEqualTo("OneTwoName");

        softly.assertAll();
    }

    @Test
    public void toConstantNameTest() {
        final SoftAssertions softly = new SoftAssertions();

        softly.assertThat(QueryTextUtils.toConstantName("spare-parts.merge")).isEqualTo("SPARE_PARTS_MERGE");
        softly.assertThat(QueryTextUtils.toConstantName("1-spare-parts.merge")).isEqualTo("$1_SPARE_PARTS_MERGE");
        softly.assertThat(QueryTextUtils.toConstantName("999spare-parts.merge")).isEqualTo("$999SPARE_PARTS_MERGE");
        softly.assertThat(QueryTextUtils.toConstantName("class")).isEqualTo("CLASS");
        softly.assertThat(QueryTextUtils.toConstantName("public")).isEqualTo("PUBLIC");
        softly.assertThat(QueryTextUtils.toConstantName("private static final methodName()")).isEqualTo("PRIVATE_STATIC_FINAL_METHODNAME__");
        softly.assertThat(QueryTextUtils.toConstantName("ONE_TWO_NAME")).isEqualTo("ONE_TWO_NAME");
        softly.assertThat(QueryTextUtils.toConstantName("ONE.TWO.NAME")).isEqualTo("ONE_TWO_NAME");

        softly.assertAll();
    }

    @Test
    public void removeSpacesIndentationTest() {
        final String indentedQuery = "\n"
                + "        INSERT INTO\n"
                + "            EMPLOYEE(\n"
                + "                ID,\n"
                + "                NAME,\n"
                + "                AGE,\n"
                + "                DEPARTMENT\n"
                + "            )\n"
                + "        VALUES(\n"
                + "            EMPLOYEE_SEQ.NEXTVAL,\n"
                + "            ?,\n"
                + "            ?,\n"
                + "            ?\n"
                + "        )\n"
                + "    ";

        final String unindentedQuery =
                "INSERT INTO\n"
                + "    EMPLOYEE(\n"
                + "        ID,\n"
                + "        NAME,\n"
                + "        AGE,\n"
                + "        DEPARTMENT\n"
                + "    )\n"
                + "VALUES(\n"
                + "    EMPLOYEE_SEQ.NEXTVAL,\n"
                + "    ?,\n"
                + "    ?,\n"
                + "    ?\n"
                + ")";

        assertThat(QueryTextUtils.removeIndentation(indentedQuery)).isEqualTo(unindentedQuery);
    }

    @Test
    public void removeTabsIndentationTest() {
        final String indentedQuery = "\n"
                + "\t\tINSERT INTO\n"
                + "\t\t\tEMPLOYEE(\n"
                + "\t\t\t\tID,\n"
                + "\t\t\t\tNAME,\n"
                + "\t\t\t\tAGE,\n"
                + "\t\t\t\tDEPARTMENT\n"
                + "\t\t\t)\n"
                + "\t\tVALUES(\n"
                + "\t\t\tEMPLOYEE_SEQ.NEXTVAL,\n"
                + "\t\t\t?,\n"
                + "\t\t\t?,\n"
                + "\t\t\t?\n"
                + "\t\t)\n"
                + "\t";

        final String unindentedQuery =
                "INSERT INTO\n"
                + "\tEMPLOYEE(\n"
                + "\t\tID,\n"
                + "\t\tNAME,\n"
                + "\t\tAGE,\n"
                + "\t\tDEPARTMENT\n"
                + "\t)\n"
                + "VALUES(\n"
                + "\tEMPLOYEE_SEQ.NEXTVAL,\n"
                + "\t?,\n"
                + "\t?,\n"
                + "\t?\n"
                + ")";

        assertThat(QueryTextUtils.removeIndentation(indentedQuery)).isEqualTo(unindentedQuery);
    }

}
