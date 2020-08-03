package com.fraud_detection.services;

import com.fraud_detection.exceptions.UserNotFoundException;
import com.fraud_detection.models.User;
import com.fraud_detection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getUserByEmail(String userEmail) {
        return repository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException(String.format("User with email %s has not been found", userEmail))
        );
    }
}
