package com.easybank.affectation;

public class AffectationSQLQueries {
    private static final String _INSERT_AFFECTATION = "INSERT INTO affectation (employee_code, mission_code, created_at, final_date) VALUES (?, ?, ?, ?) RETURNING *;";
    private static final String _DELETE_AFFECTATION = "DELETE FROM affectation WHERE id = ?;";
    private static final String _SELECT_EMPLOYEE_MISSIONS = "SELECT * FROM affectation WHERE employee_code = ?";
    private static final String _SELECT_ALL_AFFECTATIONS = "SELECT * FROM affectation";

    private AffectationSQLQueries () {}

    public static String getDeleteAffectation() {
        return _DELETE_AFFECTATION;
    }

    public static String getInsertAffectation() {
        return _INSERT_AFFECTATION;
    }

    public static String getSelectEmployeeMissions() {
        return _SELECT_EMPLOYEE_MISSIONS;
    }

    public static String getSelectAllAffectations() {
        return _SELECT_ALL_AFFECTATIONS;
    }
}
