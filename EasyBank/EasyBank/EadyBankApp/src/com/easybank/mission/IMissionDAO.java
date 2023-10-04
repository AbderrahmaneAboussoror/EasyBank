package com.easybank.mission;

import com.easybank.shared.generic.IData;

import java.util.List;

public interface IMissionDAO extends IData<Mission, String> {
    Mission findById(String query);
}
