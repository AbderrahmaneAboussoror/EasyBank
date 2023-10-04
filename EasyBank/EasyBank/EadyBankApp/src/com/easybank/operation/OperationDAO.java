package com.easybank.operation;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.Person.employee.Employee;
import com.easybank.account.Account;
import com.easybank.account.IAccountDAO;
import com.easybank.account.currentaccount.CurrentAccount;
import com.easybank.account.savingaccount.SavingAccount;
import com.easybank.shared.db.DatabaseConnection;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class OperationDAO implements IOperationDAO{
    private final IPersonDAO iPersonDAO;
    private final IAccountDAO iCurrentAccountDAO;
    private final IAccountDAO iSavingAccountDAO;

    public OperationDAO(IPersonDAO iPersonDAO, IAccountDAO iCurrentAccountDAO, IAccountDAO iSavingAccountDAO) {
        this.iPersonDAO = iPersonDAO;
        this.iCurrentAccountDAO = iCurrentAccountDAO;
        this.iSavingAccountDAO = iSavingAccountDAO;
    }

    @Override
    public Optional<Operation> findByID(int number) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(OperationSQLQueries.getFindByIdQuery());
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                Double price = resultSet.getDouble("price");
                PaymentType paymentType = null;
                switch (resultSet.getString("payment")) {
                    case "Deposit" -> paymentType = PaymentType.Deposit;
                    case "Withdrawal" -> paymentType = PaymentType.Withdrawal;
                }
                String employeeCode = resultSet.getString("employee_code");
                Person employee = iPersonDAO.findByID(employeeCode);
                String currentAccountNumber = resultSet.getString("current_account_number");
                String savingAccountNumber = resultSet.getString("saving_account_number");
                Account currentAccount = iCurrentAccountDAO.findById(currentAccountNumber);
                Account savingAccount = iSavingAccountDAO.findById(savingAccountNumber);
                return Optional.of(new Operation(number, createdAt, price, paymentType, (Employee) employee, (CurrentAccount) currentAccount, (SavingAccount) savingAccount));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Operation save(Operation operation) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(OperationSQLQueries.getInsertOperationQuery());
            preparedStatement.setDate(1, java.sql.Date.valueOf(operation.get_createdAt().get()));
            preparedStatement.setDouble(2, operation.get_price().get());
            if(operation.get_paymentType().get() == PaymentType.Withdrawal) {
                double withdrawalAmount = operation.get_price().orElse(0.0);
                double currentAccountBalance = operation.get_currentAccount().flatMap(Account::get_balance).orElse(0.0);
                double savingAccountBalance = operation.get_savingAccount().flatMap(Account::get_balance).orElse(0.0);
                if (withdrawalAmount > currentAccountBalance && withdrawalAmount > savingAccountBalance) { return null; }
                operation.get_savingAccount().ifPresent(
                    savingAccount -> {
                        Double newBalance = savingAccount.get_balance().get() - operation.get_price().get();
                        savingAccount.set_balance(newBalance);
                        iSavingAccountDAO.updateBalance(operation.get_savingAccount().get());
                    }
                );
                operation.get_currentAccount().ifPresent(
                        currentAccount -> {
                            Double newBalance = currentAccount.get_balance().get() - operation.get_price().get();
                            currentAccount.set_balance(newBalance);
                            iCurrentAccountDAO.updateBalance(operation.get_currentAccount().get());
                        }
                );
                preparedStatement.setObject(3, PaymentType.Withdrawal, Types.OTHER);
            }
            else {
                operation.get_savingAccount().ifPresent(
                        savingAccount -> {
                            Double newBalance = savingAccount.get_balance().get() + operation.get_price().get();
                            savingAccount.set_balance(newBalance);
                            iSavingAccountDAO.updateBalance(operation.get_savingAccount().get());
                        }
                );
                operation.get_currentAccount().ifPresent(
                        currentAccount -> {
                            Double newBalance = currentAccount.get_balance().get() + operation.get_price().get();
                            currentAccount.set_balance(newBalance);
                            iCurrentAccountDAO.updateBalance(operation.get_currentAccount().get());
                        }
                );
                preparedStatement.setObject(3, PaymentType.Deposit, Types.OTHER);
            }
            preparedStatement.setString(4, operation.get_employee().get().get_code().get());
            preparedStatement.setString(5, operation.get_currentAccount().flatMap(Account::get_number).orElse(null));
            preparedStatement.setString(6, operation.get_savingAccount().flatMap(Account::get_number).orElse(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("number");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                Double price = resultSet.getDouble("price");
                PaymentType paymentType = null;
                switch (resultSet.getString("payment")) {
                    case "Deposit" -> paymentType = PaymentType.Deposit;
                    case "Withdrawal" -> paymentType = PaymentType.Withdrawal;
                }
                String employeeCode = resultSet.getString("employee_code");
                Person employee = iPersonDAO.findByID(employeeCode);
                String currentAccountNumber = resultSet.getString("current_account_number");
                String savingAccountNumber = resultSet.getString("saving_account_number");
                Account currentAccount = iCurrentAccountDAO.findById(currentAccountNumber);
                Account savingAccount = iSavingAccountDAO.findById(savingAccountNumber);
                return new Operation(number, createdAt, price, paymentType, (Employee) employee, (CurrentAccount) currentAccount, (SavingAccount) savingAccount);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int number) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(OperationSQLQueries.getDeleteOperationQuery());
            preparedStatement.setInt(1, number);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
