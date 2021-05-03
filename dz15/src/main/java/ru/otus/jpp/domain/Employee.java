package ru.otus.jpp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String name;
    private int salary;
    private Position position;

    public enum Position {
        MORTAL,
        MANAGER
    }
}
