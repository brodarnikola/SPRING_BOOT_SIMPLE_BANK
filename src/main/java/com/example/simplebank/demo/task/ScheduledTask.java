package com.example.simplebank.demo.task;

import com.example.simplebank.demo.service.interfaces.AccountService;
import com.example.simplebank.demo.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    // second
    // minute
    // hour
    // day of month
    // month
    // day of week
    // execute every 2 hours
    @Scheduled(cron = "*/10 * * * * *")
    public void task1() {
        accountService.getAllOriginalAccounts().forEach(account -> accountService.getLastMonthTurnOver(account, transactionService));
    }
}
