package org.generation.italy.departmentProject.model.data.implementation;

import org.generation.italy.departmentProject.model.data.exeptions.DataException;
import org.generation.italy.departmentProject.model.data.exeptions.EntityNotFoundException;
import org.generation.italy.departmentProject.model.entities.Department;
import org.generation.italy.departmentProject.model.entities.Employee;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.generation.italy.departmentProject.model.data.JDBCConstants.*;
import static org.generation.italy.departmentProject.model.data.implementation.TestConstant.*;
import static org.generation.italy.departmentProject.model.data.implementation.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class JDBCDepartmentRepositoryTest {
    private Department d1;
    private Department d2;
    private Employee e1;
    private Employee e2;
    private Employee e3;
    private Connection con;
    private JDBCDepartmentRepository repo;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws SQLException {

        d1 = new Department(0, DEPARTMENT_NAME1, DEPARTMENT_ADDRESS1, DEPARTMENT_CAPACITY1, null);
        d2 = new Department(0, DEPARTMENT_NAME2, DEPARTMENT_ADDRESS2, DEPARTMENT_CAPACITY2, null);

        e1 = new Employee(0, EMPLOYEE_FIRSTNAME1, EMPLOYEE_LASTNAME1, null, EMPLOYEE_SEX1, d1);
        e2 = new Employee(0, EMPLOYEE_FIRSTNAME2, EMPLOYEE_LASTNAME2, null, EMPLOYEE_SEX2, d1);
        e3 = new Employee(0, EMPLOYEE_FIRSTNAME3, EMPLOYEE_LASTNAME3, null, EMPLOYEE_SEX3, d2);

        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        con.setAutoCommit(false);

        insertDepartmentSequence(d1, con);
        insertDepartmentSequence(d2, con);
        insertEmployeeSequence(e1, con);
        insertEmployeeSequence(e2, con);
        insertEmployeeSequence(e3, con);

        repo = new JDBCDepartmentRepository(con);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void insertNewDepartment() {
        try {
            repo.insertNewDepartment(d1);
            assertEquals("Java Course",d1.getName());
        } catch (DataException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void deleteDepartmentById() {
        try {
            repo.deleteDepartmentById(d2.getId());
            assertTrue(repo.findDepartmentById(d2.getId()).isEmpty());
        } catch (DataException | EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findDepartmentsByNamePart() {
        try{
            List<Department> departments = repo.findDepartmentsByNamePart("Java");
            assertEquals(1, departments.size());
            assertEquals(2, departments.get(0).getEmployees().size());
        }catch (DataException | EntityNotFoundException e){
            e.printStackTrace();
        }
    }
}