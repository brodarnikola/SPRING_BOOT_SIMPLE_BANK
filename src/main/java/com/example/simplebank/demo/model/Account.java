package com.example.simplebank.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(unique = true)
    private String accountNumber;
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal previousMonthTurnover = new BigDecimal(0);
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
//    @JsonIgnoreProperties("accounts")
    @JsonBackReference
//    @JsonManagedReference*/
    private Customer customer;

    public Account(String accountNumber) {
        this.setAccountNumber(accountNumber);
    }

    public synchronized void updateBalance(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.balance = this.balance.add(amount);
    }
}
