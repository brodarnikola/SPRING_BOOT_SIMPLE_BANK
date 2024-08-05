package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.AccountRepository;
import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.TransactionService;
import com.example.simplebank.demo.utils.MonthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

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
    public List<Account> getAllOriginalAccounts() {
        try {
            return accountRepository.findAll() ;
        }
        catch (Exception e){
            System.out.println("Exception while finding account by account number");
            return List.of();
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

    @Override
    public void getLastMonthTurnOver(Account account, TransactionService transactionService) {
        BigDecimal turnover = new BigDecimal(0);

        Date firstDay = MonthUtils.getFirstDayOfMonth();
        Date lastDay = MonthUtils.getLastDayOfMonth();

        logger.info("firstDay is: {}", firstDay);
        logger.info("lastDay is: {}", lastDay);

        List<Transaction> transactionsSender = transactionService.fetchSenderTransactionsForAccount(account.getAccountNumber())
                .stream()
                .filter(transaction -> {
                    Date date = transaction.getTimestamp();
                    return date != null && !date.before(firstDay) && !date.after(lastDay);
                })
                .toList();

        List<Transaction> transactionsReceiver = transactionService.fetchReceiverTransactionsForAccount(account.getAccountNumber())
                .stream()
                .filter(transaction -> {
                    Date date = transaction.getTimestamp();
                    return date != null && !date.before(firstDay) && !date.after(lastDay);
                })
                .toList();

        logger.info("transactionsReceiver size: {}", transactionsReceiver.size());

        for (Transaction tran : transactionsSender) {
            turnover = turnover.subtract(tran.getAmount());
            logger.info("transactionsSender: {}", turnover);
        }

        for (Transaction tran : transactionsReceiver) {
            turnover = turnover.add(tran.getAmount());
            logger.info("transactionsReceiver: {}", turnover);
        }

        logger.info("turnover: {}", turnover);

        account.setPreviousMonthTurnover(turnover);
        accountRepository.save(account);

    }

    private AccountResponseDTO convertToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setPastMonthTurnover(account.getPreviousMonthTurnover());

        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setCustomerId(account.getCustomer().getCustomerId());
        customerDTO.setName(account.getCustomer().getName());
        customerDTO.setAddress(account.getCustomer().getAddress());
        customerDTO.setEmail(account.getCustomer().getEmail());

        dto.setCustomer(customerDTO);
        return dto;
    }

}