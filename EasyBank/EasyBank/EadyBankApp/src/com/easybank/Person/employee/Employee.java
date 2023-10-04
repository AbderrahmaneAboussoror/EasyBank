package com.easybank.Person.employee;

import com.easybank.Person.Person;

import java.time.LocalDate;
import java.util.Optional;

public class Employee extends Person {
    private String _code;
    private String _email;
    private LocalDate _recruitedAt;

    public Employee() {}

    public Employee(String code, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, LocalDate recruitedAt) {
        super(firstName, lastName, birthDate, phoneNumber);
        this._code = code;
        this._email = email;
        this._recruitedAt = recruitedAt;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_recruitedAt(LocalDate _recruitedAt) {
        this._recruitedAt = _recruitedAt;
    }

    public Optional<String> get_code() {
        return Optional.ofNullable(_code);
    }

    public Optional<String> get_email() {
        return Optional.ofNullable(_email);
    }

    public Optional<LocalDate> get_recruitedAt() {
        return Optional.ofNullable(_recruitedAt);
    }

    @Override
    public String toString() {
        return "\nCode : " + this.get_code().orElse("CODE UNAVAILABLE") + "\nFirst Name : " + this.get_firstName().orElse("FIRST NAME UNAVAILABLE")
                + "\nLast Name : " + this.get_lastName().orElse("LAST NAME UNAVAILABLE") + "\nBirth Date : " + this.get_birthDate().map(
                LocalDate::toString).orElse("BIRTH DATE UNAVAILABLE") + "\nEmail : " + this.get_email().orElse("EMAIL UNAVAILABLE")
                + "\nPhone Number : " + this.get_phoneNumber().orElse("PHONE NUMBER UNAVAILABLE") + "\nRecruited at : " +
                this.get_recruitedAt().map(LocalDate::toString).orElse("DATE OF RECRUITEMENT UNAVAILABLE");
    }
}
