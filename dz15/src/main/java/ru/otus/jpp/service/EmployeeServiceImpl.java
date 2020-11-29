package ru.otus.jpp.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.jpp.domain.Employee;
import ru.otus.jpp.domain.Employee.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String[] NAMES = {"John", "James", "Elyse", "Ryan", "Andrew"};

    @Override
    public Collection<Employee> generateEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            employees.add(generateEmployee());
        }
        return employees;
    }

    private Employee generateEmployee() {
        int salary = RandomUtils.nextInt(50000, 150000);
        Position position = salary > 100000 ? Position.MANAGER : Position.MORTAL;
        return new Employee(
                NAMES[RandomUtils.nextInt(0, NAMES.length)],
                salary,
                position);
    }
}
