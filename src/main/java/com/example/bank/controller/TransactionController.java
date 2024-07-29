package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
    @PostMapping("/transaction/{accountId}")
    public ResponseEntity<?> depositMoney(@PathVariable int accountId, @RequestBody Transaction transaction) {
        String transactionType = transaction.getTransactionType();
        System.out.println(transactionType + "/" + transaction.getAmount());

        if(transactionType.equals("deposit")) {
            return transactionService.depositMoney(accountId, transaction);
        }
        if(transactionType.equals("withdraw")) {
            return transactionService.withdrawMoney(accountId, transaction);
        }
        else {
            return new ResponseEntity<>("Error: Unknown transaction type: " + transactionType, HttpStatus.BAD_REQUEST);
        }
    }


}
