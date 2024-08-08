package com.example.bank.utils;

import com.example.bank.DTO.CustomerDto;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoUtils {

    public CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setAccountTypes(customer.getAccountTypes());
        dto.setAccounts(customer.getAccounts());
        return dto;
    }

    public List<CustomerDto> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(customer-> new CustomerDto(customer.getName(), customer.getEmail(), customer.getAccountTypes(), customer.getAccounts()))
                .collect(Collectors.toList());
    }
}


