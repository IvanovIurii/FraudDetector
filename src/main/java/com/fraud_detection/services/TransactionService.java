package com.fraud_detection.services;

import com.fraud_detection.enums.Threshold;
import com.fraud_detection.models.User;

public interface TransactionService {
    // current frequency based on transactions in DB
    double calculateFrequency(User user);

    Boolean isAmountTooBig(User user, double amount);

    Threshold getFrequencyThreshold(double frequencyRatio);
}
