package com.example.simplebank.demo.dao;

import com.example.simplebank.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :account")
    Account findByAccountName(@Param("account") String account);

    Optional<Account> findByAccountNumber(String accountNumber);
}
