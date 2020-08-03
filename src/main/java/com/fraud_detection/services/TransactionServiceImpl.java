package com.fraud_detection.services;

import com.fraud_detection.enums.Threshold;
import com.fraud_detection.models.Transaction;
import com.fraud_detection.models.User;
import com.fraud_detection.repository.TransactionRepository;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Override
    public double calculateFrequency(User user) {
        Optional<Iterable<Transaction>> transactions = getTransactionsByUserId(user.getId());

        if (transactions.isPresent()) {
            Iterable<Transaction> resolvedTransactions = transactions.get();
            Map<Integer, Long> groups = StreamSupport.stream(resolvedTransactions.spliterator(), false)
                    .collect(Collectors.groupingBy(
                            timestamp -> getMonth(timestamp.getTimestamp()), Collectors.counting()
                    ));

            Optional<Long> sum = groups.values().stream().reduce(Long::sum);

            // frequency calculated as sum of transactions for all months divided by amount of months
            if (sum.isPresent())
                return sum.get() / groups.size();
        }
        return 0.0;
    }

    private int getMonth(Timestamp timestamp) {
        DateTime dateTime = new DateTime(timestamp);
        return dateTime.getMonthOfYear();
    }

    public Threshold getFrequencyThreshold(double frequencyRatio) {
        if (frequencyRatio > Threshold.YELLOW.getThreshold())
            return Threshold.GREEN;
        if (frequencyRatio > Threshold.RED.getThreshold() || frequencyRatio <= Threshold.YELLOW.getThreshold())
            return Threshold.YELLOW;

        return Threshold.RED;
    }

    @Override
    public Boolean isAmountTooBig(User user, double amount) {
        Optional<Iterable<Transaction>> transactions = getTransactionsByUserId(user.getId());

        // get all successful transactions (their amount)
        if (transactions.isPresent()) {
            List<Double> amounts = StreamSupport.stream(transactions.get().spliterator(), false)
                    .filter(Transaction::isSuccess).map(Transaction::getAmount).collect(Collectors.toList());

            amounts.add(amount);
            return amount > getHighBoundary(amounts);
        }
        return null;
    }

    private double getHighBoundary(List<Double> amounts) {
        // here is IQR (iterquartile range) used to find outlines
        Collections.sort(amounts);

        DescriptiveStatistics stats = new DescriptiveStatistics();
        amounts.forEach(stats::addValue);

        // first quartile
        double q1 = stats.getPercentile(25);
        // third quartile
        double q3 = stats.getPercentile(75);

        // high outliers
        return q3 + 1.5 * (q3 - q1); // (q3 - q1) is iterquartile range
    }

    private Optional<Iterable<Transaction>> getTransactionsByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }
}
