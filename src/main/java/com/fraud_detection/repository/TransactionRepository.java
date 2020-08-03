package com.fraud_detection.repository;

import com.fraud_detection.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Optional<Iterable<Transaction>> findAllByUserId(Integer userId);
}
