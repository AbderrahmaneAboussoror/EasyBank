package com.easybank.operation;

import java.util.Optional;

public interface IOperationService{
    Optional<Operation> save(Operation operation);
    boolean delete(int number);
    Optional<Operation> findById(int number);
}
