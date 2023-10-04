package com.easybank.affectation;

import java.util.List;
import java.util.Optional;

public class AffectationService implements IAffectationService{
    private final IAffectationDAO _iAffectationDAO;
    public AffectationService(IAffectationDAO iAffectationDAO) {
        this._iAffectationDAO = iAffectationDAO;
    }
    @Override
    public int total() {
        List<Affectation> affectations = _iAffectationDAO.findAll();

        return affectations.size();
    }
}
