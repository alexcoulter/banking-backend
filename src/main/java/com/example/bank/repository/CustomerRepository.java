package com.example.bank.repository;
import com.example.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByCustomerId(int customerId);
    void deleteByCustomerId(int customerId);
}