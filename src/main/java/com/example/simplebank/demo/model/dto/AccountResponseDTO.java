package com.example.simplebank.demo.model.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {
    private Integer accountId;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal pastMonthTurnover;
    private CustomerResponseDTO customer;

    public AccountResponseDTO() {

    }

    public AccountResponseDTO(Integer accountId, String accountNumber, BigDecimal balance, BigDecimal pastMonthTurnover, CustomerResponseDTO customer) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pastMonthTurnover = pastMonthTurnover;
        this.customer = customer;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPastMonthTurnover() {
        return pastMonthTurnover;
    }

    public void setPastMonthTurnover(BigDecimal pastMonthTurnover) {
        this.pastMonthTurnover = pastMonthTurnover;
    }

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDTO customer) {
        this.customer = customer;
    }

    // Constructors, getters and setters
}
