package com.easybank.Person;

import com.easybank.shared.generic.IData;

import java.util.List;

public interface IPersonDAO extends IData<Person, String> {
    Person update(Person person);
    Person findByID(String code);
}
