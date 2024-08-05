package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.TransactionRepository;
import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.model.dto.AccountDTO;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.AccountDtoService;
import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.EmailService;
import com.example.simplebank.demo.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final String FILTER_NAME = "SIDE";
    public static final String FILTER_SENDER = "SENDER";
    public static final String FILTER_RECEIVER = "RECEIVER";

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDtoService accountDtoService;

    @Override
    @Transactional
    public Integer processTransaction(Transaction transaction) {
        Transaction trans = transactionRepository.save(transaction);
        /*Account sender = accountService.getAccountByNumber(transaction.getSenderAccount());
        Account receiver = accountService.getAccountByNumber(transaction.getReceiverAccount());*/
        // Set up your default AccountDTO here
        /*AccountDTO sender = accountDtoService.findByAccountNumber(transaction.getSenderAccount()).orElseGet(AccountDTO::new);
        AccountDTO receiver = accountDtoService.findByAccountNumber(transaction.getReceiverAccount()).orElseGet(AccountDTO::new);*/
        Optional<AccountDTO> senderAccount = accountDtoService.findByAccountNumber(transaction.getSenderAccount());
        AccountResponseDTO sender = getAccountResponseDTO(senderAccount);
        Optional<AccountDTO> receiverAccount = accountDtoService.findByAccountNumber(transaction.getSenderAccount());
        AccountResponseDTO receiver = getAccountResponseDTO(receiverAccount);
        updateBalances(transaction);
        handleBothEmails(transaction, sender, receiver);
        return trans.getTransactionId();
    }

    private static AccountResponseDTO getAccountResponseDTO(Optional<AccountDTO> account) {
        AccountResponseDTO sender = new AccountResponseDTO();
        if (account.isPresent()) {
            sender.setAccountId(account.get().getAccountId());
            sender.setAccountNumber(account.get().getAccountNumber());
            sender.setBalance(account.get().getBalance());
            sender.setPastMonthTurnover(account.get().getPastMonthTurnover());

            CustomerResponseDTO customerDTO = new CustomerResponseDTO();
            customerDTO.setCustomerId(account.get().getCustomer().getCustomerId());
            customerDTO.setName(account.get().getCustomer().getName());
            customerDTO.setAddress(account.get().getCustomer().getAddress());
            customerDTO.setEmail(account.get().getCustomer().getEmail());

            sender.setCustomer(customerDTO);
        }
        return sender;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Optional<List<Transaction>> getTransactionHistory(Customer customer) {
        List<Transaction> transactions = new ArrayList<>();
        customer.getAccounts().forEach(account -> transactions.addAll(transactionRepository.getTransactionHistory(account.getAccountNumber())));

        System.out.println(transactions.size());
        return Optional.of(transactions);
    }

    @Override
    public Optional<List<Transaction>> getTransactionHistoryFiltered(Customer customer, String filter_name, String filter_value) {
        if (filter_name.equalsIgnoreCase(FILTER_NAME)) {
            switch (filter_value.toUpperCase()) {
                case FILTER_SENDER:
                    return fetchSenderTransactions(customer);
                case FILTER_RECEIVER:
                    return fetchReceiverTransactions(customer);
                default:
            }
        }
        return Optional.of(new ArrayList<>());
    }

    @Override
    public List<Transaction> fetchReceiverTransactionsForAccount(String account) {
        return transactionRepository.findByReceiverAccount(account);
    }

    @Override
    public List<Transaction> fetchSenderTransactionsForAccount(String account) {
        return transactionRepository.findBySenderAccount(account);
    }

    @Override
    public Optional<List<Transaction>> fetchSenderTransactions(Customer customer) {
        List<Transaction> transactions = new ArrayList<>();
        customer.getAccounts().forEach(account -> transactions.addAll(transactionRepository.findBySenderAccount(account.getAccountNumber())));

        return Optional.of(transactions);
    }

    @Override
    public Optional<List<Transaction>> fetchReceiverTransactions(Customer customer) {
        List<Transaction> transactions = new ArrayList<>();
        customer.getAccounts().forEach(account -> transactions.addAll(transactionRepository.findByReceiverAccount(account.getAccountNumber())));

        return Optional.of(transactions);
    }

    private void handleBothEmails(Transaction transaction, AccountResponseDTO sender, AccountResponseDTO receiver) {
        emailService.sendEmailImpl(transaction, sender.getAccountNumber(), sender.getCustomer(), true, sender.getBalance());
        emailService.sendEmailImpl(transaction, receiver.getAccountNumber(), receiver.getCustomer(), false, receiver.getBalance());
    }

    private void updateBalances(Transaction transaction) {

        AccountDTO sender = accountDtoService.findByAccountNumber(transaction.getSenderAccount()).orElseGet(AccountDTO::new);
        AccountDTO receiver = accountDtoService.findByAccountNumber(transaction.getReceiverAccount()).orElseGet(AccountDTO::new);

        Customer senderCustomer = new Customer();
        senderCustomer.setCustomerId(sender.getCustomer().getCustomerId());
        senderCustomer.setName(sender.getCustomer().getName());
        senderCustomer.setAddress(sender.getCustomer().getAddress());
        senderCustomer.setEmail(sender.getCustomer().getEmail());

        sender.setCustomer(senderCustomer);

        Customer receiverCustomer = new Customer();
        receiverCustomer.setCustomerId(receiver.getCustomer().getCustomerId());
        receiverCustomer.setName(receiver.getCustomer().getName());
        receiverCustomer.setAddress(receiver.getCustomer().getAddress());
        receiverCustomer.setEmail(receiver.getCustomer().getEmail());

        receiver.setCustomer(receiverCustomer);

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));
        accountDtoService.save(sender);
        accountDtoService.save(receiver);
//        accountService.saveAccount(sender);
//        accountService.saveAccount(receiver);

    }


}
