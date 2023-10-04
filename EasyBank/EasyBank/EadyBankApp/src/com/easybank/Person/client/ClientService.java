package com.easybank.Person.client;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;

import java.util.List;
import java.util.Optional;

public class ClientService implements  IClientService{
    private final IPersonDAO iPersonDAO;

    public ClientService(IPersonDAO iPersonDAO) {
        this.iPersonDAO = iPersonDAO;
    }

    @Override
    public Optional<Person> find(String query) {
        List<Person> clients = iPersonDAO.findAll();

        return clients.stream()
                .filter(client -> {
                    if (client instanceof Client){
                        Client c = (Client) client;
                        return query.equals(c.get_code().get()) ||
                                query.equals(c.get_address().get()) ||
                                query.equals(c.get_firstName().get()) ||
                                query.equals(c.get_lastName().get()) ||
                                query.equals(c.get_phoneNumber().get()) ||
                                query.equals(c.get_birthDate().toString());
                    }
                    return false;
                })
                .findFirst();
    }

    @Override
    public List<Person> findAll() {
        return iPersonDAO.findAll();
    }

    @Override
    public Optional<Person> update(Person client) {
        return Optional.ofNullable(iPersonDAO.update(client));
    }

    @Override
    public boolean delete(String code) {
        return iPersonDAO.delete(code);
    }

    @Override
    public Optional<Person> save(Person client) {
        return Optional.ofNullable(iPersonDAO.save(client));
    }
}
