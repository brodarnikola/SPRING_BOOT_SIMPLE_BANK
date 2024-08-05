package com.example.simplebank.demo.dao;

import com.example.simplebank.demo.model.dto.AccountDTO;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDtoRepository extends JpaRepository<AccountDTO, Integer> {
    Optional<AccountDTO> findByAccountNumber(String accountNumber);
}
