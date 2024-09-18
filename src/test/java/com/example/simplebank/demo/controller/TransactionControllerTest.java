package com.example.simplebank.demo.controller;

import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.service.implementation.CustomerServiceImpl;
import com.example.simplebank.demo.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionServiceImpl transactionService;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private TransactionController transactionController;

    private Customer testCustomer;
    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testCustomer.setName("John Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setAddress("Cakovec, Varazdin");

        Account account1 = new Account();
        account1.setCustomer(testCustomer);
        account1.setAccountNumber("sender123");

        Account account2 = new Account();
        account2.setCustomer(testCustomer);
        account2.setAccountNumber("receiver456");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        testCustomer.setAccounts(accounts);

        testTransaction = new Transaction();
        testTransaction.setTransactionId(123456);
        testTransaction.setAmount(new BigDecimal("100"));
        testTransaction.setSenderAccount("sender123");
        testTransaction.setReceiverAccount("receiver456");
    }

    @Test
    void testGetTransactionHistoryWithFilter() {
        when(customerService.findCustomerById(1)).thenReturn(Optional.of(testCustomer));

//        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(testTransaction);
        when(transactionService.getTransactionHistoryFiltered(testCustomer, TransactionController.FILTER_NAME, "SIDE"))
                .thenReturn(Optional.of(transactions));

//        ResponseEntity<List<Transaction>> responseEntity = transactionController.getTransactionHistory(1, "SIDE");
        ResponseEntity<?> responseEntity = transactionController.getTransactionHistory(1, "SIDE");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(Optional.of(transactions));
    }

//    @Test
//    void testGetTransactionHistoryWithoutFilter() {
//        when(customerService.findCustomerById(1)).thenReturn(Optional.of(testCustomer));
//
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(testTransaction);
//        when(transactionService.getTransactionHistory(testCustomer))
//                .thenReturn(Optional.of(transactions));
//
////        ResponseEntity<List<Transaction>> responseEntity = transactionController.getTransactionHistory(1, null);
//        ResponseEntity<?> responseEntity = transactionController.getTransactionHistory(1, null);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isEqualTo(transactions);
//    }

    @Test
    void testGetTransactionHistoryCustomerNotFound() {
        when(customerService.findCustomerById(1)).thenReturn(Optional.empty());

//        ResponseEntity<List<Transaction>> responseEntity = transactionController.getTransactionHistory(1, null);
        ResponseEntity<?> responseEntity = transactionController.getTransactionHistory(1, null);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    void testProcessTransactionSuccess() {
        when(transactionService.processTransaction(any(Transaction.class))).thenReturn(123);

        ResponseEntity<String> responseEntity = transactionController.processTransaction(testTransaction);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("123");
    }

    @Test
    void testProcessTransactionFailure() {
        when(transactionService.processTransaction(any(Transaction.class))).thenReturn(null);

        ResponseEntity<String> responseEntity = transactionController.processTransaction(testTransaction);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isEqualTo("Transaction processing failed");
    }
}
