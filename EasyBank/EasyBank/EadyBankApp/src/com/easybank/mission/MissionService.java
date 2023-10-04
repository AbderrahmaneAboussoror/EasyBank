package com.easybank.mission;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MissionService implements IMissionService{
    private final IMissionDAO _iMissionDAO;
    public MissionService(IMissionDAO iMissionDAO) {
        this._iMissionDAO = iMissionDAO;
    }
    @Override
    public boolean delete(String code) {
        return _iMissionDAO.delete(code);
    }

    @Override
    public Optional<List<Mission>> findAll() {
        List<Mission> missions = _iMissionDAO.findAll();
        return Optional.of(missions.isEmpty() ? Collections.emptyList() : missions);
    }

    @Override
    public Optional<Mission> save(Mission mission) {
        return Optional.ofNullable(_iMissionDAO.save(mission));
    }
}
