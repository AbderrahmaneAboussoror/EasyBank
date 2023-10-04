package com.easybank.Person.client;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.shared.db.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IPersonDAO {
    @Override
    public List<Person> findAll() {
        List<Person> clients = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ClientSQLQueries.getFindAllQuery());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Person client = new Client(code, firstName, lastName, birthDate, phoneNumber, address);
                clients.add(client);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    @Override
    public Person findByID(String code) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ClientSQLQueries.getFindByIdQuery());
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                return new Client(code, firstName, lastName, birthDate, phoneNumber, address);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Person save(Person person) {
        try{
            Client client = (Client) person;
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ClientSQLQueries.getInsertClientQuery());
            preparedStatement.setString(1, client.get_code().get());
            preparedStatement.setString(2, client.get_firstName().get());
            preparedStatement.setString(3, client.get_lastName().get());
            preparedStatement.setDate(4, java.sql.Date.valueOf(client.get_birthDate().get()));
            preparedStatement.setString(5,client.get_phoneNumber().get());
            preparedStatement.setString(6, client.get_address().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                return new Client(code, firstName, lastName,birthDate, phoneNumber, address);
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
            PreparedStatement preparedStatement = connection.prepareStatement(ClientSQLQueries.getUpdateClientQuery());
            preparedStatement.setString(1, person.get_firstName().get());
            preparedStatement.setString(2, person.get_lastName().get());
            preparedStatement.setDate(3, java.sql.Date.valueOf(person.get_birthDate().get()));
            preparedStatement.setString(4, person.get_phoneNumber().get());
            preparedStatement.setString(5, ((Client) person).get_address().get());
            preparedStatement.setString(6, ((Client) person).get_code().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                return new Client(code, firstName, lastName, birthDate, phoneNumber, address);
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
            PreparedStatement preparedStatement = connection.prepareStatement(ClientSQLQueries.getDeleteClientQuery());
            preparedStatement.setString(1, code);
            int clientDeleted = preparedStatement.executeUpdate();
            return clientDeleted > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
