package com.example.simplebank.demo.controller;

import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private List<AccountResponseDTO> mockAccounts;

    @BeforeEach
    void setUp() {
        mockAccounts = Arrays.asList(
                new AccountResponseDTO(),
                new AccountResponseDTO()
        );
    }

    @Test
    void testGetAccounts() {
        when(accountService.getAllAccounts()).thenReturn(mockAccounts);

        ResponseEntity<List<AccountResponseDTO>> responseEntity = accountController.getAccounts();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(mockAccounts);
    }
}
