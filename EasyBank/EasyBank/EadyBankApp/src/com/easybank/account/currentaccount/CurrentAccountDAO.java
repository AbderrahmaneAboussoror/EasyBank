package com.easybank.account.currentaccount;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.Person.client.Client;
import com.easybank.Person.employee.Employee;
import com.easybank.account.Account;
import com.easybank.account.AccountStatus;
import com.easybank.account.IAccountDAO;
import com.easybank.shared.db.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentAccountDAO implements IAccountDAO {
    private final IPersonDAO iClientDAO;
    private final IPersonDAO iEmployeeDAO;

    public CurrentAccountDAO(IPersonDAO iClientDAO, IPersonDAO iEmployeeDAO) {
        this.iClientDAO = iClientDAO;
        this.iEmployeeDAO = iEmployeeDAO;
    }

    @Override
    public List<Account> findByClient(String code) {
        List<Account> accounts = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getFindAccountsByClientQuery());
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String clientCode = resultSet.getString("code_client");
                Person client = iClientDAO.findByID(clientCode);
                String employeeCode = resultSet.getString("code_employe");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                CurrentAccount currentAccount = new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
                accounts.add(currentAccount);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    @Override
    public Account save(Account account) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getInsertCurrentAccountQuery());
            preparedStatement.setString(1, account.get_number().get());
            preparedStatement.setDouble(2, account.get_balance().get());
            preparedStatement.setDate(3, java.sql.Date.valueOf(account.get_createdAt().get()));
            preparedStatement.setObject(4, account.get_status().get(), Types.OTHER);
            preparedStatement.setDouble(5, ((CurrentAccount) account).get_overdraft().get());
            preparedStatement.setString(6, account.get_employee().get().get_code().get());
            preparedStatement.setString(7, account.get_client().get().get_code().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String employeeCode = resultSet.getString("code_employe");
                String clientCode = resultSet.getString("code_client");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                Person client = iClientDAO.findByID(clientCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                return new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getFindAllCurrentAccountsQuery());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String employeeCode = resultSet.getString("code_employe");
                String clientCode = resultSet.getString("code_client");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                Person client = iClientDAO.findByID(clientCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                Account currentAccount = new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
                accounts.add(currentAccount);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(String number) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getFindAccountByIdQuery());
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                String employeeCode = resultSet.getString("code_employe");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                String clientCode = resultSet.getString("code_client");
                Person client = iClientDAO.findByID(clientCode);
                return new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account updateStatus(Account account) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getUpdateCurrentAccountStatusQuery());
            preparedStatement.setObject(1, account.get_status().get(), Types.OTHER);
            preparedStatement.setString(2, account.get_number().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String employeeCode = resultSet.getString("code_employe");
                String clientCode = resultSet.getString("code_client");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                Person client = iClientDAO.findByID(clientCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                return new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(String number) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getDeleteCurrentAccountQuery());
            preparedStatement.setString(1, number);
            return preparedStatement.executeUpdate() > 0;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account updateBalance(Account account) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getUpdateAccountBalanceQuery());
            preparedStatement.setDouble(1, account.get_balance().get());
            preparedStatement.setString(2, account.get_number().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String employeeCode = resultSet.getString("code_employe");
                String clientCode = resultSet.getString("code_client");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                Person client = iClientDAO.findByID(clientCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                return new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account update(Account account) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CurrentAccountSQLQueries.getUpdateCurrentAccountQuery());
            preparedStatement.setDouble(1, account.get_balance().get());
            preparedStatement.setDate(2, java.sql.Date.valueOf(account.get_createdAt().get()));
            preparedStatement.setObject(3, account.get_status().get(), Types.OTHER);
            preparedStatement.setDouble(4, ((CurrentAccount) account).get_overdraft().get());
            preparedStatement.setString(5, account.get_number().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String employeeCode = resultSet.getString("code_employe");
                String clientCode = resultSet.getString("code_client");
                Person employee = iEmployeeDAO.findByID(employeeCode);
                Person client = iClientDAO.findByID(clientCode);
                String number = resultSet.getString("number");
                Double balance = resultSet.getDouble("balance");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                AccountStatus accountStatus = null;
                switch (resultSet.getString("account_status")) {
                    case "Active" -> accountStatus = AccountStatus.Active;
                    case "Frozen" -> accountStatus = AccountStatus.Frozen;
                    case "Closed" -> accountStatus = AccountStatus.Closed;
                }
                Double overdraft = resultSet.getDouble("overdraft");
                return new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee, (Client) client, overdraft);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
