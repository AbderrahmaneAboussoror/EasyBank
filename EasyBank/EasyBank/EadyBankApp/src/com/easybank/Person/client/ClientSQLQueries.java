package com.easybank.Person.client;

public class ClientSQLQueries {
    private static final String _FIND_ALL = "SELECT * FROM client";
    private static final String _FIND_BY_ID = "SELECT * FROM client WHERE code = ?";
    private static final String _INSERT_CLIENT = "INSERT INTO client(code, first_name, last_name, birth_date, phone_number, address) VALUES(?, ?, ?, ?, ?, ?) RETURNING *;";
    private static final String _UPDATE_CLIENT = "UPDATE client SET first_name = ?, last_name = ?, birth_date = ?, phone_number = ?, address = ? WHERE code = ?;";
    private static final String _DELETE_CLIENT = "DELETE FROM client WHERE code = ?;";

    private ClientSQLQueries() {}

    public static String getDeleteClientQuery() {
        return _DELETE_CLIENT;
    }

    public static String getFindAllQuery() {
        return _FIND_ALL;
    }

    public static String getInsertClientQuery() {
        return _INSERT_CLIENT;
    }

    public static String getUpdateClientQuery() {
        return _UPDATE_CLIENT;
    }

    public static String getFindByIdQuery() {
        return _FIND_BY_ID;
    }
}
