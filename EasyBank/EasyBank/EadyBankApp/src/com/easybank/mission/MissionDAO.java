package com.easybank.mission;

import com.easybank.shared.db.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MissionDAO implements IMissionDAO{

    @Override
    public Mission save(Mission mission) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MissionSQLQueries.getInsertMissionQuery());
            preparedStatement.setString(1, mission.get_code().get());
            preparedStatement.setString(2, mission.get_name().get());
            preparedStatement.setString(3, mission.get_description().get());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                return new Mission(code, name, description);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(String number) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MissionSQLQueries.getDeleteMissionQuery());
            preparedStatement.setString(1, number);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Mission> findAll() {
        List<Mission> missions = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MissionSQLQueries.getFindAllMissionsQuery());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Mission mission = new Mission(code, name, description);
                missions.add(mission);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return missions;
    }

    @Override
    public Mission findById(String query) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MissionSQLQueries.getFindByIdQuery());
            preparedStatement.setString(1, query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                return new Mission(code, name, description);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
