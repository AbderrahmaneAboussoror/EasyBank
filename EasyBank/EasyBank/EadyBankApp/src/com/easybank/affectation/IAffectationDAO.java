package com.easybank.affectation;

import com.easybank.shared.generic.IData;

import java.util.List;

public interface IAffectationDAO extends IData<Affectation, Integer> {
    List<Affectation> history(String query);
}
