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

import org.junit.Test;

public class QueryTextUtilsTest {

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
