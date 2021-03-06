// Generated by Queryless (https://github.com/paveljakov/queryless)
package queryless.generated;

import java.lang.String;
import javax.annotation.Generated;

/**
 * <i>Queryless query bundle.</i><br/><h1>Test</h1>
 */
@Generated("Generated by Queryless (https://github.com/paveljakov/queryless)")
public final class Test {
  /**
   * <h2>Query text:</h2><pre>{@code SELECT
   *     ID,
   *     NAME,
   *     AGE,
   *     DEPARTMENT
   * FROM
   *     EMPLOYEE
   * }</pre>
   */
  public static final String GET_ALL_EMPLOYEES = "SELECT\n"
      + "    ID,\n"
      + "    NAME,\n"
      + "    AGE,\n"
      + "    DEPARTMENT\n"
      + "FROM\n"
      + "    EMPLOYEE\n";

  private Test() {
  }

  /**
   * <i>Queryless query bundle.</i><br/><h1>Employee</h1>
   */
  @Generated("Generated by Queryless (https://github.com/paveljakov/queryless)")
  public static final class Employee {
    /**
     * <h2>Query text:</h2><pre>{@code -- Finds employees by ID
     * SELECT
     *     ID,
     *     NAME,
     *     AGE,
     *     DEPARTMENT
     * FROM
     *     EMPLOYEE
     * WHERE
     *     ID = :id
     *
     * }</pre>
     */
    public static final String FIND = "-- Finds employees by ID\n"
        + "SELECT\n"
        + "    ID,\n"
        + "    NAME,\n"
        + "    AGE,\n"
        + "    DEPARTMENT\n"
        + "FROM\n"
        + "    EMPLOYEE\n"
        + "WHERE\n"
        + "    ID = :id\n"
        + "\n";

    /**
     * <h2>Query text:</h2><pre>{@code INSERT INTO
     *     EMPLOYEE(
     *         ID,
     *         NAME,
     *         AGE,
     *         DEPARTMENT
     *     )
     * VALUES(
     *     EMPLOYEE_SEQ.NEXTVAL,
     *     ?,
     *     ?,
     *     ?
     * )
     *
     * }</pre>
     */
    public static final String INSERT = "INSERT INTO\n"
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
        + ")\n"
        + "\n";

    private Employee() {
    }
  }
}
