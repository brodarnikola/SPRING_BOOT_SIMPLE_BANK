package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.AccountDtoRepository;
import com.example.simplebank.demo.model.dto.AccountDTO;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AccountDtoServiceImpl implements AccountDtoService {
    @Autowired
    AccountDtoRepository accountDtoRepository;

    @Override
    public void saveAll(Iterable<AccountDTO> accounts) {
        try {
            accountDtoRepository.saveAll(accounts);
        } catch (Exception e) {
            System.out.println("Exception while saving accounts " + e.getMessage());
        }
    }

    @Override
    public void save(AccountDTO account) {
        try {
            accountDtoRepository.save(account);
        } catch (Exception e) {
            System.out.println("Exception while saving account " + e.getMessage());
        }
    }

    @Override
    public Optional<AccountDTO> findByAccountNumber(String accountNumber) {
        try {
            return accountDtoRepository.findByAccountNumber(accountNumber);
        } catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return Optional.empty();
        }
    }

   /* @Override
    public Optional<AccountResponseDTO> findByAccountNumber(String accountNumber) {
        try {
            Optional<AccountDTO> account = accountDtoRepository.findByAccountNumber(accountNumber);
            if (account.isPresent()) {
                AccountResponseDTO dto = new AccountResponseDTO();
                dto.setAccountId(account.get().getAccountId());
                dto.setAccountNumber(account.get().getAccountNumber());
                dto.setBalance(account.get().getBalance());
                dto.setPastMonthTurnover(account.get().getPastMonthTurnover());

                CustomerResponseDTO customerDTO = new CustomerResponseDTO();
                customerDTO.setCustomerId(account.get().getCustomer().getCustomerId());
                customerDTO.setName(account.get().getCustomer().getName());
                customerDTO.setAddress(account.get().getCustomer().getAddress());
                customerDTO.setEmail(account.get().getCustomer().getEmail());

                dto.setCustomer(customerDTO);
                return Optional.of(dto);
            }
            return Optional.empty();
        } catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return Optional.empty();
        }
    }*/

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        try {
            return accountDtoRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return List.of();
        }
    }

    private AccountResponseDTO convertToDTO(AccountDTO account) {
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