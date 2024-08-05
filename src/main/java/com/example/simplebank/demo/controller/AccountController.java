package com.example.simplebank.demo.controller;

import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.dto.AccountDTO;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountDtoService;
import com.example.simplebank.demo.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountDtoService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDTO>> getAccounts() {
        List<AccountResponseDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
