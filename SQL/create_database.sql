DROP TABLE IF EXISTS employee, department;

DROP TYPE IF EXISTS sex;

CREATE TYPE sex AS ENUM ('MALE','FEMALE','UNDECIDED');

--Table department
CREATE TABLE department
(
    id_department   BIGINT NOT NULL,
    name            VARCHAR(20) NOT NULL,
    address         VARCHAR(100) NOT NULL,
    max_capacity    INT NOT NULL,

    CONSTRAINT PK_department PRIMARY KEY (id_department),
    CONSTRAINT CHK_max_capacity CHECK(max_capacity >= 1)
);

CREATE SEQUENCE department_sequence
   start 1
   increment 1
   OWNED BY department.id_department;


--Table employee
CREATE TABLE employee
(
    id_employee     BIGINT NOT NULL,
    firstname       VARCHAR(20) NOT NULL,
    lastname        VARCHAR(20) NOT NULL,
    hire_date       DATE,
    sex             sex NOT NULL,
    id_department   BIGINT NOT NULL,
    CONSTRAINT PK_employee PRIMARY KEY (id_employee),
    CONSTRAINT FK_employee_department FOREIGN KEY (id_department)
    REFERENCES department (id_department)
);

CREATE SEQUENCE employee_sequence
   start 1
   increment 1
   OWNED BY employee.id_employee;