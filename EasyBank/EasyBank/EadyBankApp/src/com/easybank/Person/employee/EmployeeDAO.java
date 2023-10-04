package com.easybank.Person.employee;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.shared.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IPersonDAO {
    @Override
    public List<Person> findAll() {
        List<Person> employees = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EmployeeSQLQueries.getFindAllQuery());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                LocalDate recruitedAt = resultSet.getDate("recruited_at").toLocalDate();
                Person employee = new Employee(code, firstName, lastName, birthDate, phoneNumber, email, recruitedAt);
                employees.add(employee);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    @Override
    public Person findByID(String code) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EmployeeSQLQueries.getFindByIdQuery());
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                LocalDate recruitedAt = resultSet.getDate("recruited_at").toLocalDate();
                return new Employee(code, firstName, lastName, birthDate, phoneNumber, email,recruitedAt);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Person save(Person person) {
        try{
            Employee employee = (Employee) person;
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EmployeeSQLQueries.getInsertEmployeeQuery());
            preparedStatement.setString(1, employee.get_code().get());
            preparedStatement.setString(2, employee.get_firstName().get());
            preparedStatement.setString(3, employee.get_lastName().get());
            preparedStatement.setDate(4, Date.valueOf(employee.get_birthDate().get()));
            preparedStatement.setString(5, employee.get_phoneNumber().get());
            preparedStatement.setString(6, employee.get_email().get());
            preparedStatement.setDate(7, Date.valueOf(employee.get_recruitedAt().get()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                LocalDate recruitedAt = resultSet.getDate("recruited_at").toLocalDate();
                employee = new Employee(code, firstName, lastName,birthDate, phoneNumber, email, recruitedAt);
                return employee;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Person update(Person person) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EmployeeSQLQueries.getUpdateEmployeeQuery());
            preparedStatement.setString(1, person.get_firstName().get());
            preparedStatement.setString(2, person.get_lastName().get());
            preparedStatement.setDate(3, java.sql.Date.valueOf(person.get_birthDate().get()));
            preparedStatement.setString(4, person.get_phoneNumber().get());
            preparedStatement.setString(5, ((Employee) person).get_email().get());
            preparedStatement.setDate(6, java.sql.Date.valueOf(((Employee) person).get_recruitedAt().get()));
            preparedStatement.setString(7, ((Employee) person).get_code().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                LocalDate recruitedAt = resultSet.getDate("recruited_at").toLocalDate();
                return new Employee(code, firstName, lastName,birthDate, phoneNumber, email, recruitedAt);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean delete(String code) {
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EmployeeSQLQueries.getDeleteEmployeeQuery());
            preparedStatement.setString(1, code);
            int clientDeleted = preparedStatement.executeUpdate();
            return clientDeleted > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
