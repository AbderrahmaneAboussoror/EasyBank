package com.easybank.Person.client;

import com.easybank.Person.Person;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    Optional<Person> find(String query);
    Optional<Person> save(Person client);
    boolean delete(String code);
    Optional<Person> update(Person client);
    List<Person> findAll();
}
