package ru.otus.jpp.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.jpp.domain.Employee;

import java.util.Collection;

@MessagingGateway
public interface Promoter {
    @Gateway(requestChannel = "employeesChannel", replyChannel = "managersChannel")
    Collection<Employee> process(Collection<Employee> employees);
}
