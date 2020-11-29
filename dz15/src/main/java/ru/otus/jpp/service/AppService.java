package ru.otus.jpp.service;

import ru.otus.jpp.domain.Employee;

public interface AppService {
    void execute();
    Employee promoteToManager(Employee employee);
}
