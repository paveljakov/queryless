package queryless.core.source.splitter.queries;

import queryless.core.bundle.model.Query;

public class SqlSplitterQueries {

    public static final Query EMPLOYEE_INSERT = new Query("employee.insert", "INSERT INTO\n"
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
            + ")");

    public static final Query EMPLOYEE_FIND = new Query("employee.find", "-- Finds employees by ID\n"
            + "SELECT\n"
            + "    ID,\n"
            + "    NAME,\n"
            + "    AGE,\n"
            + "    DEPARTMENT\n"
            + "FROM\n"
            + "    EMPLOYEE\n"
            + "WHERE\n"
            + "    ID = :id");

    public static final Query GET_ALL_EMPLOYEES = new Query("get all employees", "SELECT\n"
            + "    ID,\n"
            + "    NAME,\n"
            + "    AGE,\n"
            + "    DEPARTMENT\n"
            + "FROM\n"
            + "    EMPLOYEE");
}
