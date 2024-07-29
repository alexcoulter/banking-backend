package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<?> depositMoney(int accountId, Transaction transaction) {
        float amount = transaction.getAmount();
        if (amount <= 0) {
            System.out.println("Error:  can not make a deposit of $" + amount);
            return new ResponseEntity<>("Error:  can not make a deposit of $" + amount, HttpStatus.BAD_REQUEST);
        }
        Account account = accountRepository.findByAccountId(accountId);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    public ResponseEntity<?> withdrawMoney(int accountId, Transaction transaction) {
        float amount = transaction.getAmount();
        if (amount <= 0) {
            System.out.println("Error:  can not make a withdrawl of $" + amount);
            return new ResponseEntity<>("Error:  can not make a withdrawl of $" + amount, HttpStatus.BAD_REQUEST);
        }
        Account account = accountRepository.findByAccountId(accountId);
        Float balance = account.getBalance();
        if (amount > balance) {
            System.out.println("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + balance + " Requested withdrawl: ");
            return new ResponseEntity<>("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + balance + " Requested withdrawl: " + amount, HttpStatus.BAD_REQUEST);

        } else {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            transaction.setAccount(account);
            transactionRepository.save(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
    }
}
