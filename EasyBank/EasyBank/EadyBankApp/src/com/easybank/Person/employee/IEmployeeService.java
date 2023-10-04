package com.easybank.Person.employee;

import com.easybank.Person.Person;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Optional<Person> find(String query);
    Optional<Person> save(Person employee);
    boolean delete(String code);
    Optional<Person> update(Person employee);
    List<Person> findAll();
}
