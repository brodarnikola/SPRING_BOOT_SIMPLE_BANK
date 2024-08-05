package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.AccountRepository;
import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void saveAll(Iterable<Account> accounts) {
        try {
            accountRepository.saveAll(accounts);
        } catch (Exception e) {
            System.out.println("Exception while saving accounts " + e.getMessage());
        }
    }

    @Override
    public void save(Account account) {
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            System.out.println("Exception while saving account " + e.getMessage());
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        try {
            return accountRepository.findByAccountNumber(accountNumber);
        } catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return Optional.empty();
        }
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        try {
            return accountRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return List.of();
        }
    }

    private AccountResponseDTO convertToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setPastMonthTurnover(account.getPastMonthTurnover());

        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setCustomerId(account.getCustomer().getCustomerId());
        customerDTO.setName(account.getCustomer().getName());
        customerDTO.setAddress(account.getCustomer().getAddress());
        customerDTO.setEmail(account.getCustomer().getEmail());

        dto.setCustomer(customerDTO);
        return dto;
    }

}