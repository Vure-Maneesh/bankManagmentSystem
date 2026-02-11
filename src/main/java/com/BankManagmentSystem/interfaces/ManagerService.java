package com.BankManagmentSystem.interfaces;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.BankManagmentSystem.exceptions.AccountException;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.model.Account;

public interface ManagerService {

    void approveAccount(Long accountId)
            throws ManagerNotFound, AccountNotFoundException, AccountException;

    public void rejectAccount(Long accountId);

    List<Account> getAllAccounts();
}
