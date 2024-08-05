/*
package com.example.simplebank.demo.service;

import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.service.init.InitDataService;
import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.CustomerService;
import com.example.simplebank.demo.service.interfaces.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitDataServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private InitDataService initDataService;

    private Account account1;
    private Account account2;
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        account1 = new Account();
        account1.setAccountNumber("00345");

        account2 = new Account();
        account2.setAccountNumber("00651");

        customer1 = Customer.builder()
                .name("Nikola Brodar")
                .address("Mali Mihaljevec, Rade koncara 53")
                .email("brodarnikola9@gmail.com")
                .accounts(new ArrayList<>() {
                })
                .build();

        customer2 = Customer.builder()
                .name("Netko Drugi")
                .address("Lopatinec, Augusta Senoe 21")
                .email("brodarnikola@gmail.com")
                .accounts(new ArrayList<>())
                .build();
    }

    @Test
    void testCreateDummyCustomer(){
        when(accountService.getAccountByNumber("00345")).thenReturn(account1);
        when(accountService.getAccountByNumber("00651")).thenReturn(account2);
        when(customerService.saveCustomer(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(account1, account2));

        initDataService.createDummyCustomer();

        verify(accountService).getAccountByNumber("00345");
        verify(accountService).getAccountByNumber("00651");

        verify(customerService, times(2)).saveCustomer(any(Customer.class));
        verify(accountService, times(2)).saveAccount(any(Account.class));

        verify(accountService).getAllAccounts();
        verify(accountService, times(2)).getLastMonthTurnOver(any(Account.class), eq(transactionService));
    }
}
*/
