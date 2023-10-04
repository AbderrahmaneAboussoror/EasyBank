package com.easybank.Person.client;

import com.easybank.Person.Person;

import java.time.LocalDate;
import java.util.Optional;

public class Client extends Person {
    private String _code;
    private String _address;

    public Client() {}
    public Client(String code, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address) {
        super(firstName, lastName, birthDate, phoneNumber);
        this._code = code;
        this._address = address;
    }

    public Optional<String> get_code() {
        return Optional.ofNullable(_code);
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public Optional<String> get_address() {
        return Optional.ofNullable(_address);
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    @Override
    public String toString() {
        return "\nCode : " + this.get_code().orElse("CODE UNAVAILABLE") + "\nFirst Name : " + this.get_firstName().orElse("FIRST NAME UNAVAILABLE")
        + "\nLast Name : " + this.get_lastName().orElse("LAST NAME UNAVAILABLE") + "\nBirth Date : " + this.get_birthDate().map(
                LocalDate::toString).orElse("BIRTH DATE UNAVAILABLE") + "\nAddress : " + this.get_address().orElse("ADDRESS UNAVAILABLE")
        + "\nPhone Number : " + this.get_phoneNumber().orElse("PHONE NUMBER UNAVAILABLE");
    }
}
