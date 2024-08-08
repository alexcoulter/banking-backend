package com.example.bank.controller;

// CustomerController.java
import com.example.bank.DTO.CustomerDto;
import com.example.bank.model.Customer;
import com.example.bank.service.CustomerService;
import com.example.bank.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        DtoUtils dtoUtils = new DtoUtils();
        List<CustomerDto> customerDtoList = dtoUtils.toDtoList(customers);
        return customerDtoList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable int id) {
        return customerService.getCustomerByCustomerId(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }
}
