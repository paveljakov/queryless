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
package queryless.core.source.splitter.queries;

import queryless.core.source.model.Query;

public class SplitterQueries {

    public static final Query EMPLOYEE_INSERT = new Query("insert", "INSERT INTO\n"
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
            + ")\n\n");

    public static final Query EMPLOYEE_FIND = new Query("find", "-- Finds employees by ID\n"
            + "SELECT\n"
            + "    ID,\n"
            + "    NAME,\n"
            + "    AGE,\n"
            + "    DEPARTMENT\n"
            + "FROM\n"
            + "    EMPLOYEE\n"
            + "WHERE\n"
            + "    ID = :id\n\n");

    public static final Query GET_ALL_EMPLOYEES = new Query("get all employees", "SELECT\n"
            + "    ID,\n"
            + "    NAME,\n"
            + "    AGE,\n"
            + "    DEPARTMENT\n"
            + "FROM\n"
            + "    EMPLOYEE\n");
}
