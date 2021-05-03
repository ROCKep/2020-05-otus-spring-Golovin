package ru.otus.jpp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.jpp.domain.Employee;
import ru.otus.jpp.integration.Promoter;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.otus.jpp.domain.Employee.Position.MANAGER;
import static ru.otus.jpp.domain.Employee.Position.MORTAL;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private final EmployeeService employeeService;
    private final Promoter promoter;

    @Override
    public void execute() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Collection<Employee> employees = employeeService.generateEmployees();
            System.out.println("New employees: " +
                    employees.stream().map(Employee::toString)
                            .collect(Collectors.joining(", ")));
            Collection<Employee> managers = promoter.process(employees);
            System.out.println("Ready managers: " + managers.stream()
                    .map(Employee::toString)
                    .collect(Collectors.joining(", ")));
        }
    }

    @Override
    public Employee promoteToManager(Employee employee) {
        System.out.println("Promoting " + employee.getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (employee.getPosition() == MORTAL) {
            System.out.println("Promoting " + employee.getName() + " done");
            employee.setSalary(employee.getSalary() + 100000);
            employee.setPosition(MANAGER);
        } else {
            System.out.println(employee.getName() + " already a manager");
        }
        return employee;
    }
}
