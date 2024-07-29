package com.example.bank.service;

import com.example.bank.model.Customer;
import com.example.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        System.out.println("creating user in userService with: " + customer.getCustomerId());
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public ResponseEntity<?> getCustomerByCustomerId(int id) {
        Optional<Customer> customer = customerRepository.findByCustomerId(id);
        if(customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Could not find a customer with Customer ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    public Customer updateCustomer(int id, Customer customer) {
        customer.setCustomerId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteByCustomerId(id);
    }
}