package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/new-account/customer/{customerId}")
    public ResponseEntity<?> createAccount(@PathVariable int customerId, @RequestBody Account account) {
        return accountService.createAccount(customerId, account);
    }

    @GetMapping("/accounts/{customerId}")
    public ResponseEntity<?> getAccountsByCustomerId(@PathVariable int customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        if(accounts.isEmpty()) {
            return new ResponseEntity<>("No account found for customer ID: " + customerId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable int accountId) {
        Account account = accountService.getAccountByAccountId(accountId);
        if(account == null) {
            return new ResponseEntity<>("No account found for account ID: " + accountId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

//    @GetMapping("/account/{accountId}/deposit/{amount}")
//    public ResponseEntity<?> depositMoney(@PathVariable int accountId, @PathVariable Float amount) {
//          return accountService.depositMoney(accountId, amount);
//    }
//
//
//    @GetMapping("/account/{accountId}/withdraw/{amount}")
//    public ResponseEntity<?> withdrawMoney(@PathVariable int accountId, @PathVariable Float amount) {
//        return accountService.withdrawMoney(accountId, amount);
//    }

}
