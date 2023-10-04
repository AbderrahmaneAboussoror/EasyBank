package com.easybank.operation;

import com.easybank.Person.employee.Employee;
import com.easybank.account.currentaccount.CurrentAccount;
import com.easybank.account.savingaccount.SavingAccount;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

public class Operation {
    private int _number;
    private LocalDate _createdAt;
    private Double _price;
    private PaymentType _paymentType;
    private Employee _employee;
    private CurrentAccount _currentAccount;
    private SavingAccount _savingAccount;

    public Operation() {}

    public Operation(LocalDate createdAt, Double price, PaymentType paymentType, Employee employee, CurrentAccount currentAccount, SavingAccount savingAccount) {
        this._createdAt = createdAt;
        this._price = price;
        this._paymentType = paymentType;
        this._employee = employee;
        this._currentAccount = currentAccount;
        this._savingAccount = savingAccount;
    }

    public Operation(int number, LocalDate createdAt, Double price, PaymentType paymentType, Employee employee, CurrentAccount currentAccount, SavingAccount savingAccount) {
        this(createdAt, price, paymentType, employee, currentAccount, savingAccount);
        this._number = number;
    }

    public void set_employee(Employee _employee) {
        this._employee = _employee;
    }

    public void set_createdAt(LocalDate _createdAt) {
        this._createdAt = _createdAt;
    }

    public void set_number(int _number) {
        this._number = _number;
    }

    public void set_currentAccount(CurrentAccount _currentAccount) {
        this._currentAccount = _currentAccount;
    }

    public void set_paymentType(PaymentType _paymentType) {
        this._paymentType = _paymentType;
    }

    public void set_price(Double _price) {
        this._price = _price;
    }

    public void set_savingAccount(SavingAccount _savingAccount) {
        this._savingAccount = _savingAccount;
    }

    public Optional<CurrentAccount> get_currentAccount() {
        return Optional.ofNullable(_currentAccount);
    }

    public Optional<Double> get_price() {
        return Optional.ofNullable(_price);
    }

    public Optional<Employee> get_employee() {
        return Optional.ofNullable(_employee);
    }

    public Optional<LocalDate> get_createdAt() {
        return Optional.ofNullable(_createdAt);
    }

    public Optional<PaymentType> get_paymentType() {
        return Optional.ofNullable(_paymentType);
    }

    public Optional<SavingAccount> get_savingAccount() {
        return Optional.ofNullable(_savingAccount);
    }

    public Optional<Integer> get_number() {
        return Optional.of(_number);
    }

    @Override
    public String toString(){
        return  "\nNumber : " + this.get_number().map(Object::toString).orElse("NUMBER UNAVAILABLE") + "\nCreated At : " + this.get_createdAt().map(LocalDate::toString).orElse("CREATED AT UNAVAILABLE")
                + "\nPrice : " + this.get_price().map(Object::toString).orElse("PRICE UNAVAILABLE") + "\nPayment Type : " + this.get_paymentType().map(Enum::toString).orElse("PAYMENT TYPE UNAVAILABLE")
                + "\nEmployee : " + this.get_employee().map(employee -> employee.get_firstName().get() + " " + employee.get_lastName().get()).orElse("EMPLOYEE UNAVAILABLE") + "\nCurrent Account Number : " + this.get_currentAccount().map(currentAccount -> currentAccount.get_number().get()).orElse("CURRENT ACCOUNT UNAVAILABLE")
                + "\nSaving Account Number : " + this.get_savingAccount().map(savingAccount -> savingAccount.get_number().get()).orElse("SAVING ACCOUNT UNAVAILABLE");
    }
}
