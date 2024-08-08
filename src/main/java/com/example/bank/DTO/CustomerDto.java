package com.example.bank.DTO;

import com.example.bank.model.Account;

import java.util.List;

public class CustomerDto {
    private String name;
    private String email;
    private String accountTypes;
    private List<Account> accounts;

    public CustomerDto() {}

    public CustomerDto(String name, String email, String accountTypes, List<Account> accounts) {
        this.name = name;
        this.email = email;
        this.accountTypes = accountTypes;
        this.accounts = accounts;
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

