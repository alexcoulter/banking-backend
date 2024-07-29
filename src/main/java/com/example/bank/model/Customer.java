package com.example.bank.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
//@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String name;
    private String email;
    private String accountTypes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Account> accounts;
//    @OneToMany
//    @JoinColumn(name="customer_id", nullable = false)
//    private List<Account> accounts;

    public Customer() {}

    public Customer(int customerId, String name, String email, String accountTypes, List<Account> accounts) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.accountTypes = accountTypes;
        this.accounts = accounts;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(String accountTypes) {
        this.accountTypes = accountTypes;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}