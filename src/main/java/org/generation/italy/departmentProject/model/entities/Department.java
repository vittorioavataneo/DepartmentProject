package org.generation.italy.departmentProject.model.entities;

import java.util.Set;

public class Department {
    private long id;
    private String name;
    private String address;
    private int maxCapacity;
    private Set<Employee> employees;

    public Department(long id, String name, String address, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
    }
    public Department(long id, String name, String address, int maxCapacity, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.employees = employees;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
