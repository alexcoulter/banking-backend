package com.example.bank.controller;

import com.example.bank.service.TransactionService;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @MockBean
    TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTransactionsByAccountId_NoTransactionsFound() throws Exception {
        int accountId = 1;
        ResponseEntity<?> response = new ResponseEntity<>("Error: No transactions found for Account ID: 1", HttpStatus.NOT_FOUND);
//        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(response);
        doReturn(response).when(transactionService).getTransactionsByAccountId(accountId);

        mockMvc.perform(get("/api/transactions/{accountId}", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: No transactions found for Account ID: " + accountId));
    }

    @Test
    public void testGetTransactionsByAccountId_Success() throws Exception {
        int accountId = 1;
        String transactions = "[{\"id\":1,\"amount\":100.0}]";  // Example JSON response
        ResponseEntity<?> response =  new ResponseEntity<>(transactions, HttpStatus.OK);
//        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(new ResponseEntity<>(transactions, HttpStatus.OK));
        Mockito.doReturn(response).when(transactionService).getTransactionsByAccountId(accountId);


        mockMvc.perform(get("/api/transactions/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().json(transactions));
    }


    @Test
    public void getTransactionsByAccountId() {
    }

    @Test
    public void depositMoney() {
    }
}