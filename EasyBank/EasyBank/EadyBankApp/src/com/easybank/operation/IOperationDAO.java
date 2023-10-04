package com.easybank.operation;

import java.util.Optional;

public interface IOperationDAO {
    Operation save(Operation operation);
    boolean delete(int number);
    Optional<Operation> findByID(int query);
}
