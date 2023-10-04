package com.easybank.Person.employee;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;

import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService{
    private final IPersonDAO _employeeDAO;
    public EmployeeService(IPersonDAO employeeDAO) {
        this._employeeDAO = employeeDAO;
    }
    @Override
    public Optional<Person> find(String query) {
        List<Person> employees = _employeeDAO.findAll();

        return employees.stream()
                .filter(
                        employee -> {
                            if (employee instanceof Employee e) {
                                return query.equals(e.get_code().get()) ||
                                        query.equals(e.get_email().get()) ||
                                        query.equals(e.get_firstName().get()) ||
                                        query.equals(e.get_lastName().get()) ||
                                        query.equals(e.get_phoneNumber().get()) ||
                                        query.equals(e.get_birthDate().toString()) ||
                                        query.equals(e.get_recruitedAt().toString());
                            }
                            return false;
                        }
                ).findFirst();
    }

    @Override
    public Optional<Person> save(Person employee) {
        return Optional.ofNullable(_employeeDAO.save(employee));
    }

    @Override
    public boolean delete(String code) {
        return _employeeDAO.delete(code);
    }

    @Override
    public Optional<Person> update(Person employee) {
        return Optional.ofNullable(_employeeDAO.update(employee));
    }

    @Override
    public List<Person> findAll() {
        return _employeeDAO.findAll();
    }
}
