-- id: employee-insert
INSERT INTO
    EMPLOYEE(
        ID,
        NAME,
        AGE,
        DEPARTMENT
    )
VALUES(
    EMPLOYEE_SEQ.NEXTVAL,
    ?,
    ?,
    ?
)

-- ID: employee-find
-- Finds employees by ID
SELECT
    ID,
    NAME,
    AGE,
    DEPARTMENT
FROM
    EMPLOYEE
WHERE
    ID = :id

  -- id:  get all employees
SELECT
    ID,
    NAME,
    AGE,
    DEPARTMENT
FROM
    EMPLOYEE
