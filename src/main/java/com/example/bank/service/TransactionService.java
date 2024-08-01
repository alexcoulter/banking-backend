package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<?> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountAccountId(accountId);
        if(transactions.isEmpty()) {
            return new ResponseEntity<>("Error: No transactions found for Account ID: " + accountId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    public ResponseEntity<?> depositMoney(int accountId, Transaction transaction) {
        float amount = transaction.getAmount();
        if (amount <= 0) {
            String formattedAmount = String.format("%.2f", amount);
            System.out.println("Error:  can not make a deposit of $" + formattedAmount);
            return new ResponseEntity<>("Error:  can not make a deposit of $" + formattedAmount, HttpStatus.BAD_REQUEST);
        }
        Account account = null;
        try {
            account = accountRepository.findByAccountId(accountId);
            if(account == null) {
                return new ResponseEntity<>("Error:  can not find an account with Account Id:  " + accountId, HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Error:  can not find an account with Account Id:  " + accountId, HttpStatus.NOT_FOUND);
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    public ResponseEntity<?> withdrawMoney(int accountId, Transaction transaction) {
        Float amount = transaction.getAmount();
        String formattedAmount = String.format("%.2f", amount);
        if (amount <= 0) {
            System.out.println("Error:  can not make a withdrawl of $" + formattedAmount);
            return new ResponseEntity<>("Error:  can not make a withdrawl of $" + formattedAmount, HttpStatus.BAD_REQUEST);
        }

        Account account = null;
        try {
            account = accountRepository.findByAccountId(accountId);
            if(account == null) {
                return new ResponseEntity<>("Error:  can not find an account with Account Id:  " + accountId, HttpStatus.NOT_FOUND);
            }

        }
        catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Error:  can not find an account with Account Id:  " + accountId, HttpStatus.NOT_FOUND);
        }
        Float balance = account.getBalance();
        String formattedBalance = String.format("%.2f", balance);
        if (amount > balance) {
            System.out.println("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + formattedBalance + " Requested withdrawl: " + formattedAmount);
            return new ResponseEntity<>("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + formattedBalance + " Requested withdrawl: " + formattedAmount, HttpStatus.BAD_REQUEST);

        } else {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            transaction.setAccount(account);
            transactionRepository.save(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
    }
}
