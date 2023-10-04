package com.easybank.account.currentaccount;

import com.easybank.account.Account;
import com.easybank.account.AccountStatus;
import com.easybank.account.IAccountDAO;
import com.easybank.operation.IOperationDAO;
import com.easybank.operation.Operation;

import java.util.*;
import java.util.stream.Collectors;

public class CurrentAccountService implements ICurrentAccountService{
    private final IAccountDAO _iAccountDAO;
    private final IOperationDAO _iOperationDAO;
    public CurrentAccountService(IAccountDAO iAccountDAO, IOperationDAO iOperationDAO) {
        this._iAccountDAO = iAccountDAO;
        this._iOperationDAO = iOperationDAO;
    }

    @Override
    public Optional<List<Account>> filter(String query) {
        List<Account> accounts = _iAccountDAO.findAll();
        List<Account> filteredAccounts =  accounts.stream()
                .filter(
                        currentAccount -> query.equals(currentAccount.get_status().toString()) ||
                                        query.equals(currentAccount.get_createdAt().toString())

                ).toList();

        return Optional.of(filteredAccounts.isEmpty() ? Collections.emptyList() : filteredAccounts);
    }

    @Override
    public Optional<Account> findByOperation(int number) {
        Optional<Operation> operation = _iOperationDAO.findByID(number);

        return operation.flatMap(Operation::get_currentAccount);
    }

    @Override
    public AccountStatus map(String accountStatusStr) {
        return switch (accountStatusStr) {
            case "Frozen" -> AccountStatus.Frozen;
            case "Closed" -> AccountStatus.Closed;
            default -> AccountStatus.Active;
        };
    }

    @Override
    public boolean delete(String number) {
        return _iAccountDAO.delete(number);
    }

    @Override
    public Optional<Account> update(Account account) {
        return Optional.ofNullable(_iAccountDAO.update(account));
    }

    @Override
    public Optional<List<Account>> findAll() {
        List<Account> accounts = _iAccountDAO.findAll();
        return Optional.of(accounts.isEmpty() ? Collections.emptyList() : accounts);
    }

    @Override
    public Optional<Account> save(Account account) {
        return Optional.ofNullable(_iAccountDAO.save(account));
    }

    @Override
    public Optional<List<Account>> findByClient(String code) {
        List<Account> accounts = _iAccountDAO.findByClient(code);
        return  Optional.of(accounts.isEmpty() ? Collections.emptyList() : accounts);
    }

    @Override
    public Optional<Account> findById(String number) {
        return Optional.ofNullable(_iAccountDAO.findById(number));
    }

    @Override
    public Optional<Account> updateStatus(Account account) {
        return Optional.ofNullable(_iAccountDAO.updateStatus(account));
    }
}
