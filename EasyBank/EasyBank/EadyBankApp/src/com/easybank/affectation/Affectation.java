package com.easybank.affectation;

import com.easybank.Person.employee.Employee;
import com.easybank.mission.Mission;

import java.time.LocalDate;
import java.util.List;

public class Affectation {
    private Integer _id;
    private LocalDate _createdAt;
    private LocalDate _finalDate;
    private Employee _employee;
    private Mission _mission;

    public Affectation() {}

    public Affectation(LocalDate createdAt, LocalDate finalDate, Employee employee, Mission mission) {
        this._createdAt = createdAt;
        this._finalDate = finalDate;
        this._employee = employee;
        this._mission = mission;
    }
    public Affectation(Integer id, LocalDate createdAt, LocalDate finalDate, Employee employee, Mission mission) {
        this(createdAt, finalDate, employee, mission);
        this._id = id;
    }

    public void set_createdAt(LocalDate _createdAt) {
        this._createdAt = _createdAt;
    }

    public void set_employee(Employee _employee) {
        this._employee = _employee;
    }

    public void set_finalDate(LocalDate _finalDate) {
        this._finalDate = _finalDate;
    }

    public void set_mission(Mission _mission) {
        this._mission = _mission;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Employee get_employee() {
        return _employee;
    }

    public LocalDate get_createdAt() {
        return _createdAt;
    }

    public LocalDate get_finalDate() {
        return _finalDate;
    }

    public Mission get_mission() {
        return _mission;
    }

    public Integer get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "";
    }
}
