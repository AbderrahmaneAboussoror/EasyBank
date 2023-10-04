package com.easybank.account.savingaccount;

public class SavingAccountSQLQueries {
    private static final String _FIND_ALL_SAVING_ACCOUNTS = "SELECT * FROM saving_account c, client cl, employee e WHERE c.code_client = cl.code AND c.code_employe = e.code;";
    private static final String _INSERT_SAVING_ACCOUNT = "INSERT INTO saving_account(number, balance, created_at, account_status, interest_rate, code_employe, code_client) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *;";
    private static final String _DELETE_SAVING_ACCOUNT = "DELETE FROM saving_account WHERE number = ?;";
    private static final String _UPDATE_SAVING_ACCOUNT_STATUS = "UPDATE saving_account SET account_status = ? WHERE number = ? RETURNING *;";
    private static final String _FIND_ACCOUNTS_BY_CLIENT = "SELECT * FROM saving_account c, client cl WHERE c.code_client = cl.code AND cl.code = ?;";
    private static final String _FIND_ACCOUNT_BY_ID = "SELECT * FROM saving_account WHERE number = ?;";
    private static final String _UPDATE_ACCOUNT_BALANCE = "UPDATE saving_account SET balance = ? WHERE number = ? RETURNING *";
    private static final String _UPDATE_SAVING_ACCOUNT = "UPDATE saving_account SET balance = ?, created_at = ?, account_status = ?, interest_rate = ? WHERE number = ? RETURNING *;";

    private SavingAccountSQLQueries() {}

    public static String getFindAllSavingAccountsQuery() {
        return _FIND_ALL_SAVING_ACCOUNTS;
    }

    public static String getDeleteSavingAccountQuery() {
        return _DELETE_SAVING_ACCOUNT;
    }

    public static String getInsertSavingAccountQuery() {
        return _INSERT_SAVING_ACCOUNT;
    }

    public static String getUpdateSavingAccountStatusQuery() {
        return _UPDATE_SAVING_ACCOUNT_STATUS;
    }

    public static String getFindAccountsByClientQuery() {
        return _FIND_ACCOUNTS_BY_CLIENT;
    }

    public static String getFindAccountByIdQuery() {
        return _FIND_ACCOUNT_BY_ID;
    }

    public static String getUpdateAccountBalanceQuery() {
        return _UPDATE_ACCOUNT_BALANCE;
    }

    public static String getUpdateSavingAccountQuery() {
        return _UPDATE_SAVING_ACCOUNT;
    }
}
