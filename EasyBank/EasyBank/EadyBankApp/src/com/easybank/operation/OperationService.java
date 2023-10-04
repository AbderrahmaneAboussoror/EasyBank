package com.easybank.operation;

import java.util.Optional;

public class OperationService implements IOperationService{
    private final IOperationDAO iOperationDAO;

    public OperationService(IOperationDAO iOperationDAO) {
        this.iOperationDAO = iOperationDAO;
    }
    @Override
    public Optional<Operation> findById(int number) {
        return iOperationDAO.findByID(number);
    }

    @Override
    public Optional<Operation> save(Operation operation) {
        return Optional.ofNullable(iOperationDAO.save(operation));
    }

    @Override
    public boolean delete(int number) {
        return iOperationDAO.delete(number);
    }
}
