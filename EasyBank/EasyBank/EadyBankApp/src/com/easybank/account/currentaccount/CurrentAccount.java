package com.easybank.account.currentaccount;

import com.easybank.Person.client.Client;
import com.easybank.Person.employee.Employee;
import com.easybank.account.Account;
import com.easybank.account.AccountStatus;

import java.time.LocalDate;
import java.util.Optional;

public class CurrentAccount extends Account {
    private Double _overdraft;

    public CurrentAccount() {}
    public CurrentAccount(String number, Double balance, LocalDate createdAt, Employee employee, Client client, Double overdraft) {
        super(number, balance, createdAt, employee, client);
        this._overdraft = overdraft;
    }
    public CurrentAccount(String number, Double balance, LocalDate createdAt, AccountStatus status, Employee employee, Client client, Double overdraft) {
        super(number, balance, createdAt, status, employee, client);
        this._overdraft = overdraft;
    }
    public Optional<Double> get_overdraft() {
        return Optional.ofNullable(_overdraft);
    }

    public void set_overdraft(Double _overdraft) {
        this._overdraft = _overdraft;
    }

    @Override
    public String toString() {
        return "\n\nCurrent Account Details:\n" +
                "Account Number: " + this.get_number().orElse("ACCOUNT NUMBER UNAVAILABLE") + "\n" +
                "Balance: " + this.get_balance().map(Object::toString).orElse("BALANCE UNAVAILABLE") + "\n" +
                "Created At: " + this.get_createdAt().map(LocalDate::toString).orElse("CREATED DATE UNAVAILABLE") + "\n" +
                "Status: " + this.get_status().map(AccountStatus::toString).orElse("STATUS UNAVAILABLE") + "\n" +
                "Overdraft: " + this.get_overdraft().map(Object::toString).orElse("OVERDRAFT UNAVAILABLE") + "\n" +
                "Employee: " + this.get_employee().map(employee -> employee.get_firstName().get() + ' ' + employee.get_lastName().get() ).orElse("EMPLOYEE UNAVAILABLE") + "\n" +
                "Client: " + this.get_client().map(client -> client.get_firstName().get() + ' ' + client.get_lastName().get()).orElse("CLIENT UNAVAILABLE");
    }
}
