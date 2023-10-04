package com.easybank.account;

import com.easybank.Person.client.Client;
import com.easybank.Person.client.ClientDAO;
import com.easybank.Person.employee.Employee;

import java.time.LocalDate;
import java.util.Optional;

public abstract class Account {
    protected String _number;
    protected Double _balance;
    protected LocalDate _createdAt;
    protected AccountStatus _status;
    protected Employee _employee;
    protected Client _client;

    protected Account() {}
    protected Account(String number, Double balance, LocalDate createdAt, Employee employee, Client client) {
        this._number = number;
        this._balance = balance;
        this._createdAt = createdAt;
        this._employee = employee;
        this._client = client;
        this._status = AccountStatus.Active;
    }
    protected Account(String number, Double balance, LocalDate createdAt, AccountStatus status, Employee employee, Client client) {
        this(number, balance, createdAt, employee, client);
        this._status = status;
    }

    public Optional<String> get_number() {
        return Optional.ofNullable(_number);
    }

    public void set_number(String _number) {
        this._number = _number;
    }

    public Optional<Double> get_balance() {
        return Optional.ofNullable(_balance);
    }

    public void set_balance(Double _balance) {
        this._balance = _balance;
    }

    public Optional<LocalDate> get_createdAt() {
        return Optional.ofNullable(_createdAt);
    }

    public void set_createdAt(LocalDate _createdAt) {
        this._createdAt = _createdAt;
    }

    public Optional<AccountStatus> get_status() {
        return Optional.ofNullable(_status);
    }

    public void set_status(AccountStatus _status) {
        this._status = _status;
    }

    public Optional<Client> get_client() {
        return Optional.ofNullable(_client);
    }

    public void set_client(Client _client) {
        this._client = _client;
    }

    public Optional<Employee> get_employee() {
        return Optional.ofNullable(_employee);
    }

    public void set_employee(Employee _employee) {
        this._employee = _employee;
    }

    //abstract method
    public abstract String toString();


}
