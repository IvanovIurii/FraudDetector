package com.fraud_detection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionDTO {
    private String email;
    private Double amount;
    private String location;
    private Boolean skipLocation;

    public TransactionDTO(String email, double amount, String location, Boolean skipLocation) {
        this.email = email;
        this.amount = amount;
        this.location = location;
        this.skipLocation = skipLocation;
    }

    @NotBlank(message = "email is mandatory")
    public String getEmail() {
        return email;
    }

    @NotNull(message = "amount is mandatory")
    public Double getAmount() {
        return amount;
    }

    // used in unit-tests only
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @NotBlank(message = "location is mandatory")
    public String getLocation() {
        return location;
    }

    // used in unit-tests only
    public void setSkipLocation(Boolean skipLocation) {
        this.skipLocation = skipLocation;
    }

    @JsonProperty("skip_location")
    public Boolean getSkipLocation() {
        return skipLocation;
    }
}
