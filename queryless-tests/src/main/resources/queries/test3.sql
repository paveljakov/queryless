-- id: employee.insert
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

-- id: employee.find
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
