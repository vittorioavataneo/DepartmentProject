package org.generation.italy.departmentProject.model.entities;

import java.time.LocalDate;

public class Employee {
    private long id;
    private String firstname;
    private String lastname;
    private LocalDate hireDate;
    private Sex sex;
    private Department department;

    public Employee(long id, String firstname, String lastname, Sex sex, Department department) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.department = department;
    }
    public Employee(long id, String firstname, String lastname, LocalDate hireDate, Sex sex, Department department) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.hireDate = hireDate;
        this.sex = sex;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Sex getSex() {
        return sex;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(long id) {
        this.id = id;
    }
}
