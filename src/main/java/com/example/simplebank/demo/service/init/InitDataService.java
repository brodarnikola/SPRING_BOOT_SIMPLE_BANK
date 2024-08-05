/*
package com.example.simplebank.demo.service.init;

import com.example.simplebank.demo.model.Account;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.CustomerService;
import com.example.simplebank.demo.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitDataService {
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;


    public void createDummyCustomer() {

        try {
            Customer c = Customer
                    .builder()
                    .name("Nikola Brodar")
                    .address("Mali Mihaljevec, Rade koncara 53")
                    .email("brodarnikola9@gmail.com")
                    .accounts(new ArrayList<>() {
                    })
                    .build();
            Account account = accountService.getAccountByNumber("00345");
            c.addAccount(account);
            Customer customer = customerService.saveCustomer(c);
            account.setCustomer(customer);
            accountService.saveAccount(account);

            Customer c1 = Customer
                    .builder()
                    .name("Netko Drugi")
                    .address("Lopatinec, Augusta Senoe 21")
                    .email("brodarnikola@gmail.com")
                    .accounts(new ArrayList<>())
                    .build();
            account = accountService.getAccountByNumber("00651");
            c1.addAccount(account);
            Customer customer1 = customerService.saveCustomer(c1);
            account.setCustomer(customer1);
            accountService.saveAccount(account);

            //update turnovers and balances
            accountService.getAllAccounts()
                    .parallelStream()
                    .forEach(acc -> {
                        accountService.getLastMonthTurnOver(acc, transactionService);
                        accountService.updateBalance(acc, transactionService);
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
*/
