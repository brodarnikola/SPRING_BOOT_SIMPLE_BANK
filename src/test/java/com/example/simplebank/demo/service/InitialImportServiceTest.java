package com.example.simplebank.demo.service;

import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.service.init.InitialAccountImport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InitialImportServiceTest {

    @InjectMocks
    private InitialAccountImport initialImportService;

    @Test
    void testImportAllTransactions(){

        List<Transaction> transactions = initialImportService.importAllTransactions("src/main/resources/transactions");

        assertEquals(100000, transactions.size());

    }
}