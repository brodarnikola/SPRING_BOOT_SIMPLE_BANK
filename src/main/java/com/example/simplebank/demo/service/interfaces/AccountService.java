package com.example.simplebank.demo.service.interfaces;


import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void saveAll(Iterable<Account> accounts);
    void save(Account account);

    Optional<Account> findByAccountNumber(String accountNumber);

    void getLastMonthTurnOver(Account account, TransactionService transactionService);

    List<Account> getAllOriginalAccounts();

    List<AccountResponseDTO> getAllAccounts();
}