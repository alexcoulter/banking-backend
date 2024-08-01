package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;


    @Test
    public void testDepositMoney_InvalidAmount() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(-100);
        ResponseEntity<?> response = transactionService.depositMoney(accountId, transaction);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error:  can not make a deposit of $-100.00", response.getBody());
    }

    @Test
    public void testDepositMoney_invalidID() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        Mockito.when(accountRepository.findByAccountId(accountId)).thenReturn(null);
        ResponseEntity<?> response = transactionService.depositMoney(accountId, transaction);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error:  can not find an account with Account Id:  1", response.getBody());
    }


    @Test
    public void testDepositMoney_Success() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(1000f);
        Mockito.when(accountRepository.findByAccountId(accountId)).thenReturn(account);
        ResponseEntity<?> response = transactionService.depositMoney(accountId, transaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testWithdrawMoney_InvalidAmount() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(-100);
        ResponseEntity<?> response = transactionService.withdrawMoney(accountId, transaction);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error:  can not make a withdrawl of $-100.00", response.getBody());
    }

    @Test
    public void testWithdrawMoney_invalidID() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        Mockito.when(accountRepository.findByAccountId(accountId)).thenReturn(null);
        ResponseEntity<?> response = transactionService.withdrawMoney(accountId, transaction);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error:  can not find an account with Account Id:  1", response.getBody());
    }

    @Test
    public void testWithdrawMoney_insufficientBalance() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        Account account = new Account();
        account.setBalance(50f);
        Mockito.when(accountRepository.findByAccountId(accountId)).thenReturn(account);
        ResponseEntity<?> response = transactionService.withdrawMoney(accountId, transaction);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error:  can not make a withdrawl of more than the balance of your account.  Balance: 50.00 Requested withdrawl: 100.00", response.getBody());
    }


    @Test
    public void testWithdrawMoney_Success() {
        int accountId = 1;
        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(1000f);
        Mockito.when(accountRepository.findByAccountId(accountId)).thenReturn(account);
        ResponseEntity<?> response = transactionService.withdrawMoney(accountId, transaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testGetTransactionsByAccountId_Success() {
        int accountId = 1;
        Transaction transaction1  = new Transaction();
        transaction1.setTransactionId(5);
        transaction1.setTransactionType("deposit");
        transaction1.setAmount(500);
        Transaction transaction2  = new Transaction();
        transaction2.setTransactionId(6);
        transaction2.setTransactionType("withdraw");
        transaction2.setAmount(200);
        List<Transaction> transactions = Arrays.asList(new Transaction[]{transaction1, transaction2});

        Mockito.when(transactionRepository.findTransactionsByAccountAccountId(accountId)).thenReturn(transactions);
        ResponseEntity<?> response = transactionService.getTransactionsByAccountId(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());

    }

    @Test
    public void testGetTransactionsByAccountId_NotFound() {
        int accountId = 1;
        List<Transaction> transactions = new ArrayList<>();
        Mockito.when(transactionRepository.findTransactionsByAccountAccountId(accountId)).thenReturn(transactions);
        ResponseEntity<?> response = transactionService.getTransactionsByAccountId(accountId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: No transactions found for Account ID: 1", response.getBody());


    }
}