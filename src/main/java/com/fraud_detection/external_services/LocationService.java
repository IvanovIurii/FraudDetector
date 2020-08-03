package com.fraud_detection.external_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    private final String API_URL = "http://ip-api.com/json/%s?fields=status,country,city";

    @Autowired
    private RestTemplate restTemplate;

    public Location getLocation(String ipAddress) {
        return restTemplate.getForObject(String.format(API_URL, ipAddress), Location.class);
    }
}