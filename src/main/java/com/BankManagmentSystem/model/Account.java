package com.BankManagmentSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long accountNumber;

    @ManyToOne
    private User customer;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

}
