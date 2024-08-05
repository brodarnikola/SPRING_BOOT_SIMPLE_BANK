package com.example.simplebank.demo.model.dto;

import com.example.simplebank.demo.model.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(unique = true)
    private String accountNumber;
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal pastMonthTurnover = new BigDecimal(0);
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
//    @JsonIgnoreProperties("accounts")
    @JsonBackReference
//    @JsonManagedReference*/
    private Customer customer;

    public AccountDTO(String accountNumber) {
        this.setAccountNumber(accountNumber);
    }

    public synchronized void updateBalance(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.balance = this.balance.add(amount);
    }
}
