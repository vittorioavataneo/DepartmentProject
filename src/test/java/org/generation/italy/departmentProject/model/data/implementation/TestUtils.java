package org.generation.italy.departmentProject.model.data.implementation;

import org.generation.italy.departmentProject.model.entities.Department;
import org.generation.italy.departmentProject.model.entities.Employee;

import java.sql.*;
import java.util.Optional;

import static org.generation.italy.departmentProject.model.data.JDBCConstants.*;

public class TestUtils {
    public static int insert(String query, Connection con, boolean withSequence, Object... params){
        try (PreparedStatement st = withSequence? con.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS) :  con.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                if (params[i] instanceof Enum<?>) {
                    st.setObject(i+1, params[i], Types.OTHER);
                } else {
                    st.setObject(i+1,params[i]);
                }
            }
            if(withSequence){
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    keys.next();
                    long key = keys.getLong(1);
                    return (int) key;
                }
            }else {
                return st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertDepartmentSequence(Department department, Connection con) {
        department.setId(insert(INSERT_DEPARTMENT_RETURNING_ID, con, true,
                department.getName(), department.getAddress(), department.getMaxCapacity()));

    }
    public static void insertEmployeeSequence(Employee employee, Connection con) {
        employee.setId(insert(INSERT_EMPLOYEE_RETURNING_ID, con, true,
                employee.getFirstname(), employee.getLastname(), null,
                employee.getSex(), employee.getDepartment().getId()));

    }

}
