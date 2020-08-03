package com.fraud_detection.services;


import com.fraud_detection.dto.TransactionDTO;
import com.fraud_detection.enums.Threshold;
import com.fraud_detection.exceptions.InvalidTransactionException;
import com.fraud_detection.exceptions.TransactionsNotFoundException;
import com.fraud_detection.external_services.Location;
import com.fraud_detection.external_services.LocationService;
import com.fraud_detection.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FraudDetectionServiceImpl implements FraudDetectionService {
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private TransactionService transactionService;

    private List<String> listOfIssues;

    @Override
    public List<String> getListOfIssues(TransactionDTO transactionDTO) {
        listOfIssues = new ArrayList<>();
        User user = userService.getUserByEmail(transactionDTO.getEmail());

        if (transactionDTO.getAmount() < 0 || transactionDTO.getAmount() > user.getBalance())
            throw new InvalidTransactionException("Not enough amount of money");

        checkFrequency(user);
        checkLocation(transactionDTO, user);
        checkAmount(transactionDTO, user);

        return listOfIssues;
    }

    private void checkLocation(TransactionDTO transactionDTO, User user) {
        // if there is no skip_location field or it false, we check location all the time
        if (Objects.isNull(transactionDTO.getSkipLocation()) || !transactionDTO.getSkipLocation()) {
            Location location = locationService.getLocation(transactionDTO.getLocation());

            if (!location.isValid())
                // check the status field of response
                throw new InvalidTransactionException("Invalid location");

            // here the last location from where user run a transaction
            Location userLastLocation = locationService.getLocation(user.getLastLocation());

            // if country or city is different -> sent notification
            if (!location.getCountry().equals(userLastLocation.getCountry())
                    || !location.getCity().equals(userLastLocation.getCity())) {
                listOfIssues.add(
                        String.format("Suspicious location: %s, %s", location.getCountry(), location.getCity())
                );
            }
        }
    }

    private void checkFrequency(User user) {
        // assume that frequency of transaction is calculated by CRON or K8S job
        double lastFrequency = user.getFrequency();

        if (lastFrequency == 0.0) {
            throw new InvalidTransactionException("User does not have transactions");
        }

        Threshold threshold = transactionService.getFrequencyThreshold(
                transactionService.calculateFrequency(user) / lastFrequency
        );

        if (!threshold.equals(Threshold.GREEN))
            listOfIssues.add("Frequency deviation per month was changed");
    }

    private void checkAmount(TransactionDTO transactionDTO, User user) {
        boolean isAmountTooBig = transactionService.isAmountTooBig(user, transactionDTO.getAmount());

        if (Objects.isNull(isAmountTooBig)) {
            throw new TransactionsNotFoundException(
                    String.format("Transactions have not been found for user %s", user.getEmail()));
        }

        if (isAmountTooBig)
            listOfIssues.add("Payment amount is much bigger than before");
    }
}
