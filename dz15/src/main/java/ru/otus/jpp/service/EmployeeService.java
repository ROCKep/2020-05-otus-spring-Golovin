package ru.otus.jpp.service;

import ru.otus.jpp.domain.Employee;

import java.util.Collection;

public interface EmployeeService {
    Collection<Employee> generateEmployees();
}
