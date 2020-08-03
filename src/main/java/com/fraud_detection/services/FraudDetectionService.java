package com.fraud_detection.services;

import com.fraud_detection.dto.TransactionDTO;

import java.util.List;

public interface FraudDetectionService {
    List<String> getListOfIssues(TransactionDTO transactionDTO);
}
