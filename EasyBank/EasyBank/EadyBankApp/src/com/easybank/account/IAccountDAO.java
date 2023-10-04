package com.easybank.account;

import com.easybank.Person.Person;
import com.easybank.shared.generic.IData;

import java.util.List;

public interface IAccountDAO extends IData<Account, String> {
    Account updateStatus(Account account);
    List<Account> findByClient(String code);
    Account findById(String query);
    Account updateBalance(Account account);
    Account update(Account account);
}
