package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
//    Optional<List<Account>> findByCustomerId(int id);
    List<Account> findByCustomerCustomerId(int customerId);
    Account findByAccountId(int id);
}