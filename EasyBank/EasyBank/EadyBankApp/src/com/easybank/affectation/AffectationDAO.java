package com.easybank.affectation;

import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.Person.employee.Employee;
import com.easybank.mission.IMissionDAO;
import com.easybank.mission.Mission;
import com.easybank.mission.MissionDAO;
import com.easybank.shared.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AffectationDAO implements IAffectationDAO{
    private final IPersonDAO iPersonDAO;
    private final IMissionDAO iMissionDAO;
    public AffectationDAO(IPersonDAO iPersonDAO, IMissionDAO iMissionDAO) {
        this.iPersonDAO = iPersonDAO;
        this.iMissionDAO = iMissionDAO;
    }
    @Override
    public Affectation save(Affectation affectation) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AffectationSQLQueries.getInsertAffectation());
            preparedStatement.setString(1, affectation.get_employee().get_code().get());
            preparedStatement.setString(2, affectation.get_mission().get_code().get());
            preparedStatement.setDate(3, java.sql.Date.valueOf(affectation.get_createdAt()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(affectation.get_finalDate()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String employeeCode = resultSet.getString("employee_code");
                String missionCode = resultSet.getString("mission_code");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                LocalDate finalDate = resultSet.getDate("final_date").toLocalDate();
                Person employee = iPersonDAO.findByID(employeeCode);
                Mission mission = iMissionDAO.findById(missionCode);
                return new Affectation(id, createdAt, finalDate, (Employee) employee, mission);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AffectationSQLQueries.getDeleteAffectation());
            preparedStatement.setInt(1, id);
            return  preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Affectation> history(String query) {
        List<Affectation> affectations = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AffectationSQLQueries.getSelectEmployeeMissions());
            preparedStatement.setString(1, query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String employeeCode = resultSet.getString("employee_code");
                String missionCode = resultSet.getString("mission_code");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                LocalDate finalDate = resultSet.getDate("final_date").toLocalDate();
                Person employee = iPersonDAO.findByID(employeeCode);
                Mission mission = iMissionDAO.findById(missionCode);
                Affectation affectation = new Affectation(id, createdAt, finalDate, (Employee) employee, mission);
                affectations.add(affectation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return affectations;
    }

    @Override
    public List<Affectation> findAll() {
        List<Affectation> affectations = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AffectationSQLQueries.getSelectAllAffectations());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String employeeCode = resultSet.getString("employee_code");
                String missionCode = resultSet.getString("mission_code");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                LocalDate finalDate = resultSet.getDate("final_date").toLocalDate();
                Person employee = iPersonDAO.findByID(employeeCode);
                Mission mission = iMissionDAO.findById(missionCode);
                Affectation affectation = new Affectation(id, createdAt, finalDate, (Employee) employee, mission);
                affectations.add(affectation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return affectations;
    }
}
