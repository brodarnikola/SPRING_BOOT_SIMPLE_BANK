package com.example.simplebank.demo.service.interfaces;

import com.example.simplebank.demo.model.Account;

import java.math.BigDecimal;
import java.util.List;


public interface AccountService {

    Account getAccountByNumber(String accountNumber);

    Account saveAccount(Account account);

    void saveAccountIfNew(Account account);

    List<Account> getAllAccounts();

    BigDecimal getLastMonthTurnOver(Account account, TransactionService transactionService);

    void updateBalance(Account acc, TransactionService transactionService);
}
