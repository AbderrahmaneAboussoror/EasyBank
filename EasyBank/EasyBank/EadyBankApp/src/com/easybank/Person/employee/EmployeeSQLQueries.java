package com.easybank.Person.employee;

public class EmployeeSQLQueries {
    private static final String _FIND_ALL = "SELECT * FROM employee";
    private static final String _FIND_BY_ID = "SELECT * FROM employee WHERE code = ?";
    private static final String _INSERT_EMPLOYEE = "INSERT INTO employee(code, first_name, last_name, birth_date, phone_number, email, recruited_at) VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING *;";
    private static final String _UPDATE_EMPLOYEE = "UPDATE employee SET first_name = ?, last_name = ?, birth_date = ?, phone_number = ?, email = ?, recruited_at = ? WHERE code = ? RETURNING *;";
    private static final String _DELETE_EMPLOYEE = "DELETE FROM employee WHERE code = ?;";

    private EmployeeSQLQueries() {}

    public static String getFindAllQuery() {
        return _FIND_ALL;
    }

    public static String getInsertEmployeeQuery() {
        return _INSERT_EMPLOYEE;
    }

    public static String getUpdateEmployeeQuery() {
        return _UPDATE_EMPLOYEE;
    }

    public static String getDeleteEmployeeQuery() {
        return _DELETE_EMPLOYEE;
    }

    public static String getFindByIdQuery() {
        return _FIND_BY_ID;
    }
}
