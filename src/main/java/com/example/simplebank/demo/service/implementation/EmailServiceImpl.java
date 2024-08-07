package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.Transaction;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String sendTo, String subject, String body) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle any exceptions properly
        }
    }

    @Override
    public void sendEmailImpl(Transaction transaction, String accountNumber, Customer customer, boolean sent, BigDecimal currentBalance) {

        BigDecimal oldBalance;
        String addedTaken = sent ? "taken from" : "added to";
        if (sent) {
            oldBalance = currentBalance.add(transaction.getAmount());
        } else {
            oldBalance = currentBalance.subtract(transaction.getAmount());
        }

        String body = String.format("Hello!" + System.lineSeparator() +
                        "The transaction with ID: %s has been processed successfully," +
                        "  and the balance: %s has been %s your account." + System.lineSeparator() +
                        "  Old balance: %s" + System.lineSeparator() + "  New balance: %s\n" + System.lineSeparator() +
                        "Regards," + System.lineSeparator() +
                        "  Your XYZ bank",
                transaction.getTransactionId().toString(),
                transaction.getAmount().toString(),
                addedTaken,
                oldBalance,
                currentBalance
        );
        sendEmail(customer.getEmail(), "Bank notification", body);
    }
}
