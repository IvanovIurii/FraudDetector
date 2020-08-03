package com.fraud_detection.services;

import com.fraud_detection.models.User;

public interface UserService {
    User getUserByEmail(String userEmail);
}
