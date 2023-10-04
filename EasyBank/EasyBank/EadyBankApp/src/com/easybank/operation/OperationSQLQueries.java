package com.easybank.operation;

public class OperationSQLQueries {
    private static final String _FIND_BY_ID = "SELECT * FROM operation WHERE number = ?;";
    private static final String _INSERT_OPERATION = "INSERT INTO operation (created_at, price, payment, employee_code, current_account_number, saving_account_number) VALUES (?, ?, ?, ?, ?, ?) RETURNING *;";
    private static final String _DELETE_OPERATION = "DELETE FROM operation WHERE number = ?";

    private OperationSQLQueries() {}

    public static String getDeleteOperationQuery() {
        return _DELETE_OPERATION;
    }

    public static String getFindByIdQuery() {
        return _FIND_BY_ID;
    }

    public static String getInsertOperationQuery() {
        return _INSERT_OPERATION;
    }
}
