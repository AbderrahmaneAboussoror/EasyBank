package com.easybank.Person;

import java.time.LocalDate;
import java.util.Optional;

public abstract class Person {
    protected String _firstName;
    protected String _lastName;
    protected LocalDate _birthDate;
    protected String _phoneNumber;

    protected Person() {}

    protected Person(String firstName, String lastName, LocalDate birthDate, String phoneNumber) {
        this._firstName = firstName;
        this._lastName = lastName;
        this._birthDate = birthDate;
        this._phoneNumber = phoneNumber;
    }

    public Optional<String> get_firstName() {
        return Optional.ofNullable(_firstName);
    }

    public void set_firstName(String firstName) {
        this._firstName = firstName;
    }

    public Optional<String> get_lastName() {
        return Optional.ofNullable(_lastName);
    }

    public void set_lastName(String lastName) {
        this._lastName = lastName;
    }

    public Optional<LocalDate> get_birthDate() {
        return Optional.ofNullable(_birthDate);
    }

    public void set_birthDate(LocalDate birthDate) {
        this._birthDate = birthDate;
    }

    public Optional<String> get_phoneNumber() {
        return Optional.ofNullable(_phoneNumber);
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    //abstract method
    public abstract String toString();
}
