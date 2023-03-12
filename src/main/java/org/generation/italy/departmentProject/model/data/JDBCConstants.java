package org.generation.italy.departmentProject.model.data;

public class JDBCConstants {
    public static final String URL = "jdbc:postgresql://localhost:5432/department";
    public static final String USERNAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";
    public static final String INSERT_EMPLOYEE_RETURNING_ID = """
            INSERT INTO employee(id_employee, firstname, lastname, hire_date, sex, id_department)
            VALUES (nextval('employee_sequence'), ?, ?, ?, ?, ?)
            RETURNING id_employee
            """;

    public static final String INSERT_DEPARTMENT_RETURNING_ID = """
            INSERT INTO department (id_department, name, address, max_capacity)
            VALUES (nextval('department_sequence'), ?, ?, ?)
            RETURNING id_department;
            """;
    public static final String DELETE_DEPARTMENT_AND_ALL_ITS_EMPLOYEES_BY_ID_DEPARTMENT = """
            DELETE FROM employee as e
            WHERE e.id_department = ?;
            DELETE FROM department as d
            WHERE d.id_department = ?;
            """;
    public static final String FIND_DEPARTMENT_BY_ID = """
            SELECT id_department, name, address, max_capacity
            FROM department
            WHERE id_department = ?
            """;
    public static final String FIND_DEPARTMENT_BY_NAME_PART = """
            SELECT id_department, name, address, max_capacity
            FROM department
            WHERE name LIKE ?
            """;
    public static final String GET_EMPLOYEES_BY_DEPARTMENT_ID = """
            SELECT id_employee, firstname, lastname, hire_date, sex, id_department
            FROM employee
            WHERE id_department = ?
            """;
}
