package com.example.simplebank.demo.service.interfaces;


import com.example.simplebank.demo.model.dto.AccountDTO;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AccountDtoService {
    void saveAll(Iterable<AccountDTO> accounts);
    void save(AccountDTO account);

    Optional<AccountDTO> findByAccountNumber(String accountNumber);

    List<AccountResponseDTO> getAllAccounts();
}