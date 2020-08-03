package com.fraud_detection.controllers;

import com.fraud_detection.dto.TransactionDTO;
import com.fraud_detection.exceptions.InvalidTransactionException;
import com.fraud_detection.exceptions.TransactionsNotFoundException;
import com.fraud_detection.exceptions.UserNotFoundException;
import com.fraud_detection.services.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@RestController
// https://restfulapi.net/resource-naming/
@RequestMapping("/fraud-detection")
public class TransactionController {
    @Autowired
    private FraudDetectionService fraudDetectionService;

    @PostMapping("/transaction/check")
    public ResponseEntity<List<String>> checkTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);
        return ResponseEntity.ok(listOfIssues);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundError(UserNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TransactionsNotFoundException.class)
    public String transactionNotFoundError(TransactionsNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTransactionException.class)
    public String invalidTransactionError(InvalidTransactionException e) {
        return e.getMessage();
    }
}
