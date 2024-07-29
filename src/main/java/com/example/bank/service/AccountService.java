package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<?> createAccount(int customerId, Account account) {
        Customer customer = null;
        try {
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Could not find a customer with ID: " + customerId, HttpStatus.BAD_REQUEST);
        }

        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);
        String accountType = savedAccount.getAccountType();
        String accountTypes = customer.getAccountTypes();

        if (accountTypes == null || accountTypes.isEmpty()) {
            customer.setAccountTypes(accountType);
        } else {
            List<String> accountTypeList = Arrays.asList(accountTypes.split(","));
            if (!accountTypeList.contains(accountType)) {
                customer.setAccountTypes(accountTypes + "," + accountType);
            }
        }
        customerRepository.save(customer);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountByAccountId(int accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        List<Account> accounts = accountRepository.findByCustomerCustomerId(customerId);
        return accounts;
    }

    public ResponseEntity<?> depositMoney(int accountId, float amount) {
        if (amount <= 0) {
            System.out.println("Error:  can not make a deposit of $" + amount);
            return new ResponseEntity<>("Error:  can not make a deposit of $" + amount, HttpStatus.BAD_REQUEST);
        }
        Account account = getAccountByAccountId(accountId);
        account.setBalance(account.getBalance() + amount);
        updateAccount(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    public ResponseEntity<?> withdrawMoney(int accountId, float amount) {
        if (amount <= 0) {
            System.out.println("Error:  can not make a withdrawl of $" + amount);
            return new ResponseEntity<>("Error:  can not make a withdrawl of $" + amount, HttpStatus.BAD_REQUEST);
        }
        Account account = getAccountByAccountId(accountId);
        Float balance = account.getBalance();
        if (amount > balance) {
            System.out.println("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + balance + " Requested withdrawl: ");
            return new ResponseEntity<>("Error:  can not make a withdrawl of more than the balance of your account.  Balance: " + balance + " Requested withdrawl: " + amount, HttpStatus.BAD_REQUEST);

        } else {
            account.setBalance(account.getBalance() - amount);
            updateAccount(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }
}