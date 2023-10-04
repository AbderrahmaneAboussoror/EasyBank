package com.easybank.account.currentaccount;

import com.easybank.account.Account;
import com.easybank.account.AccountStatus;

import java.util.List;
import java.util.Optional;

public interface ICurrentAccountService {
    Optional<List<Account>> filter(String query);
    Optional<Account> findByOperation(int number);
    AccountStatus map(String accountStatusStr);
    Optional<Account> save(Account account);
    Optional<Account> update(Account account);
    boolean delete(String number);
    Optional<List<Account>> findAll();
    Optional<List<Account>> findByClient(String code);
    Optional<Account> findById(String number);
    Optional<Account> updateStatus(Account account);
}
