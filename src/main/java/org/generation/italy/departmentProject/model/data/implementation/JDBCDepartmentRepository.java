package org.generation.italy.departmentProject.model.data.implementation;

import org.generation.italy.departmentProject.model.data.abstraction.DepartmentRepository;
import org.generation.italy.departmentProject.model.data.exeptions.DataException;
import org.generation.italy.departmentProject.model.data.exeptions.EntityNotFoundException;
import org.generation.italy.departmentProject.model.entities.Department;
import org.generation.italy.departmentProject.model.entities.Employee;
import org.generation.italy.departmentProject.model.entities.Sex;

import java.sql.*;
import java.util.*;

import static org.generation.italy.departmentProject.model.data.JDBCConstants.*;

public class JDBCDepartmentRepository implements DepartmentRepository {
    private Connection con;

    public JDBCDepartmentRepository(Connection connection) {
        this.con = connection;
    }
    @Override
    public Department insertNewDepartment(Department department) throws DataException {
        try (PreparedStatement st = con.prepareStatement(INSERT_DEPARTMENT_RETURNING_ID,Statement.RETURN_GENERATED_KEYS)){
            st.setString(1, department.getName());
            st.setString(2, department.getAddress());
            st.setInt(3, department.getMaxCapacity());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()){
                rs.next();
                long key = rs.getLong(1);
                department.setId(key);
                return department;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }
    @Override
    public void deleteDepartmentById(long id) throws EntityNotFoundException, DataException {
        try(PreparedStatement st = con.prepareStatement(DELETE_DEPARTMENT_AND_ALL_ITS_EMPLOYEES_BY_ID_DEPARTMENT)){
            st.setLong(1, id);
            st.setLong(2, id);
            int result = st.executeUpdate();
            if (result != 1) {
                throw new EntityNotFoundException("Department not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }

    @Override
    public List<Department> findDepartmentsByNamePart(String part) throws DataException, EntityNotFoundException {
        try(PreparedStatement st = con.prepareStatement(FIND_DEPARTMENT_BY_NAME_PART)){
            st.setString(1, "%"+part+"%");
            try (ResultSet rs = st.executeQuery()) {
                List<Department> departments = new ArrayList<>();
                while (rs.next()){
                    departments.add(addEmployeesToDepartment(databaseToDepartment(rs)));
                }
                return departments;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }

    @Override
    public Optional<Department> findDepartmentById(long id) throws DataException, EntityNotFoundException {
        try(PreparedStatement st = con.prepareStatement(FIND_DEPARTMENT_BY_ID)){
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(databaseToDepartment(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }

    private Employee databaseToEmployee(ResultSet rs, Department department) throws DataException{
        try {
            return new Employee(rs.getLong("id_employee"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    null,
                    Sex.valueOf(rs.getString("sex")), //???
                    department);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }

    public Set<Employee> databaseToDepartmentEmployees(long id, Department department) throws DataException{
        try(PreparedStatement st = con.prepareStatement(GET_EMPLOYEES_BY_DEPARTMENT_ID)){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                Set<Employee> employees = new HashSet<>();
                while (rs.next()) {
                    employees.add(databaseToEmployee(rs, department));
                }
                return employees;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }


    private Department databaseToDepartment(ResultSet rs) throws SQLException, DataException{
        try {
            return new Department(rs.getLong("id_department"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getInt("max_capacity"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error using the database",e);
        }
    }

    private Department addEmployeesToDepartment(Department department) throws DataException, EntityNotFoundException {
        department.setEmployees(databaseToDepartmentEmployees(department.getId(), department));
        return department;
    }

}
