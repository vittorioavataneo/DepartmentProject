package org.generation.italy.departmentProject.model.data.abstraction;

import org.generation.italy.departmentProject.model.data.exeptions.DataException;
import org.generation.italy.departmentProject.model.data.exeptions.EntityNotFoundException;
import org.generation.italy.departmentProject.model.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Department insertNewDepartment(Department department) throws DataException;
    void deleteDepartmentById(long id) throws EntityNotFoundException, DataException;
    List<Department> findDepartmentsByNamePart(String part) throws DataException, EntityNotFoundException;
    Optional<Department> findDepartmentById (long id) throws DataException, EntityNotFoundException;
}
