package com.easybank.mission;

import java.util.List;
import java.util.Optional;

public interface IMissionService {
    Optional<Mission> save(Mission mission);
    boolean delete(String code);
    Optional<List<Mission>> findAll();
}
