package com.easybank.mission;

import java.util.Optional;

public class Mission {
    private String _code;
    private String _name;
    private String _description;

    public Mission() {}

    public Mission(String code, String name, String description) {
        this._code = code;
        this._name = name;
        this._description = description;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Optional<String> get_code() {
        return Optional.ofNullable(_code);
    }

    public Optional<String> get_description() {
        return Optional.ofNullable(_description);
    }

    public Optional<String> get_name() {
        return Optional.ofNullable(_name);
    }
    @Override
    public String toString() {
        return  "\nCode: " + this.get_code().orElse("CODE UNAVAILABLE")
                + "\nName: " + this.get_name().orElse("NAME UNAVAILABLE")
                + "\nDescription: " + this.get_description().orElse("DESCRIPTION UNAVAILABLE");
    }
}
