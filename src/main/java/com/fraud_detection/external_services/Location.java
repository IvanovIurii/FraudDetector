package com.fraud_detection.external_services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fraud_detection.enums.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String status;
    private String country;
    private String city;

    public Location(String status, String country, String city) {
        this.status = status;
        this.country = country;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return status.equalsIgnoreCase(Status.SUCCESS.name());
    }
}
