package com.easybank.mission;

public class MissionSQLQueries {
    private static final String _DELETE_MISSION = "DELETE FROM mission WHERE code = ?;";
    private static final String _INSERT_MISSION = "INSERT INTO mission (code, name, description) VALUES (?, ?, ?) RETURNING *;";
    private static final String _FIND_ALL_MISSIONS = "SELECT * FROM mission;";
    private static final String _FIND_BY_ID = "SELECT * FROM mission WHERE code = ?";

    private MissionSQLQueries() {}

    public static String getDeleteMissionQuery() {
        return _DELETE_MISSION;
    }

    public static String getInsertMissionQuery() {
        return _INSERT_MISSION;
    }

    public static String getFindAllMissionsQuery() {
        return _FIND_ALL_MISSIONS;
    }

    public static String getFindByIdQuery() {
        return _FIND_BY_ID;
    }
}
