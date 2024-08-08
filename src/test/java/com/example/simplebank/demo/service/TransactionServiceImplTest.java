package com.example.simplebank.demo.service;

import com.example.simplebank.demo.dao.TransactionRepository;
import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.service.implementation.TransactionServiceImpl;
import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Customer senderCustomer;
    private Customer receiverCustomer;
    private Account senderAccount;
    private Account receiverAccount;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        senderCustomer = new Customer();
        senderCustomer.setEmail("sender@example.com");

        receiverCustomer = new Customer();
        receiverCustomer.setEmail("receiver@example.com");

        senderAccount = new Account();
        senderAccount.setAccountNumber("SENDER123");
        senderAccount.setBalance(new BigDecimal("1000.00"));

        receiverAccount = new Account();
        receiverAccount.setAccountNumber("RECEIVER123");
        receiverAccount.setBalance(new BigDecimal("500.00"));

        transaction = new Transaction();
        transaction.setAmount(new BigDecimal("200.00"));
        transaction.setSenderAccount("SENDER123");
        transaction.setReceiverAccount("RECEIVER123");
    }

    @Test
    void testSaveTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.saveTransaction(transaction);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void testProcessTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
//        when(accountService.getAccountByNumber("SENDER123")).thenReturn(senderAccount);
//        when(accountService.getAccountByNumber("RECEIVER123")).thenReturn(receiverAccount);
//
//        Integer transactionId = transactionService.processTransaction(transaction);
//
//        assertEquals(transaction.getTransactionId(), transactionId);
//        assertEquals(new BigDecimal("800.00"), senderAccount.getBalance());
//        assertEquals(new BigDecimal("700.00"), receiverAccount.getBalance());
//
//        verify(transactionRepository, times(1)).save(transaction);
//        verify(accountService, times(1)).getAccountByNumber("SENDER123");
//        verify(accountService, times(1)).getAccountByNumber("RECEIVER123");
//        verify(accountService, times(1)).saveAccount(senderAccount);
//        verify(accountService, times(1)).saveAccount(receiverAccount);
        verify(emailService, times(1)).sendEmailImpl(transaction, "SENDER123", senderAccount.getCustomer(), true, new BigDecimal("800.00"));
        verify(emailService, times(1)).sendEmailImpl(transaction, "RECEIVER123", receiverAccount.getCustomer(), false, new BigDecimal("700.00"));
    }

    @Test
    void testFetchReceiverTransactionsForAccount() {
        when(transactionRepository.findByReceiverAccount("receiverAccount")).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.fetchReceiverTransactionsForAccount("receiverAccount");

        assertEquals(1, transactions.size()); // Assuming one transaction is returned
        verify(transactionRepository, times(1)).findByReceiverAccount(anyString());
    }

    @Test
    void testFetchSenderTransactionsForAccount() {
        when(transactionRepository.findBySenderAccount("senderAccount")).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.fetchSenderTransactionsForAccount("senderAccount");

        assertEquals(1, transactions.size()); // Assuming one transaction is returned
        verify(transactionRepository, times(1)).findBySenderAccount(anyString());
    }

}