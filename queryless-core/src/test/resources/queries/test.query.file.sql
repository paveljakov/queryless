-- id: insert
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

-- ID: find
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

  -- Id:  get all employees
SELECT
    ID,
    NAME,
    AGE,
    DEPARTMENT
FROM
    EMPLOYEE
