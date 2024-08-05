package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.TransactionRepository;
import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.model.dto.AccountResponseDTO;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
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

    @Override
    @Transactional
    public Integer processTransaction(Transaction transaction) {
        Transaction trans = transactionRepository.save(transaction);

        Optional<Account> senderAccount = accountService.findByAccountNumber(transaction.getSenderAccount());
        AccountResponseDTO sender = getAccountResponseDTO(senderAccount);
        Optional<Account> receiverAccount = accountService.findByAccountNumber(transaction.getSenderAccount());
        AccountResponseDTO receiver = getAccountResponseDTO(receiverAccount);

        updateAccountAmount(transaction);
        sendEmailsToReceiverAndSender(transaction, sender, receiver);
        return trans.getTransactionId();
    }

    private static AccountResponseDTO getAccountResponseDTO(Optional<Account> account) {
        AccountResponseDTO sender = new AccountResponseDTO();
        if (account.isPresent()) {
            sender.setAccountId(account.get().getAccountId());
            sender.setAccountNumber(account.get().getAccountNumber());
            sender.setBalance(account.get().getBalance());
            sender.setPastMonthTurnover(account.get().getPreviousMonthTurnover());

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
    public Optional<List<Transaction>> getTransactionHistoryFiltered(Customer customer, String filterName, String filterValue) {
        if (filterName.equalsIgnoreCase(FILTER_NAME)) {
            switch (filterValue.toUpperCase()) {
                case FILTER_SENDER:
                    // fetch, get sender transactions
                    return fetchSenderTransactions(customer);
                case FILTER_RECEIVER:
                    return fetchReceiverTransactions(customer);
                default:
            }
        }
        return Optional.of(new ArrayList<>());
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

    @Override
    public List<Transaction> fetchReceiverTransactionsForAccount(String account) {
        return transactionRepository.findByReceiverAccount(account);
    }

    @Override
    public List<Transaction> fetchSenderTransactionsForAccount(String account) {
        return transactionRepository.findBySenderAccount(account);
    }

    private void sendEmailsToReceiverAndSender(Transaction transaction, AccountResponseDTO sender, AccountResponseDTO receiver) {
        emailService.sendEmailImpl(transaction, sender.getAccountNumber(), sender.getCustomer(), true, sender.getBalance());
        emailService.sendEmailImpl(transaction, receiver.getAccountNumber(), receiver.getCustomer(), false, receiver.getBalance());
    }

    private void updateAccountAmount(Transaction transaction) {

        Account sender = accountService.findByAccountNumber(transaction.getSenderAccount()).orElseGet(Account::new);
        Account receiver = accountService.findByAccountNumber(transaction.getReceiverAccount()).orElseGet(Account::new);

        setCustomerForAccount(sender);

        setCustomerForAccount(receiver);

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));
        accountService.save(sender);
        accountService.save(receiver);
    }

    private void setCustomerForAccount(Account sender) {
        Customer senderCustomer = new Customer();
        senderCustomer.setCustomerId(sender.getCustomer().getCustomerId());
        senderCustomer.setName(sender.getCustomer().getName());
        senderCustomer.setAddress(sender.getCustomer().getAddress());
        senderCustomer.setEmail(sender.getCustomer().getEmail());
        sender.setCustomer(senderCustomer);
    }


}
