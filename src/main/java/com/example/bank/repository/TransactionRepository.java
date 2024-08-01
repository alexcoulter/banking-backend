package com.example.bank.repository;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Account findByAccountAccountId(int id);
    List<Transaction> findTransactionsByAccountAccountId(int id);
}
