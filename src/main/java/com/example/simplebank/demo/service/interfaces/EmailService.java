package com.example.simplebank.demo.service.interfaces;

import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;

import java.math.BigDecimal;

public interface EmailService {
    void sendEmail(String sendTo, String subject, String body);

    void sendEmailImpl(Transaction transaction, String accountNumber, Customer customer, boolean sent, BigDecimal currentBalance);
}
