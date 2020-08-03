package com.fraud_detection.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "last_location")
    private String lastLocation;

    @Column
    private Double frequency;

    @Column
    private Double balance;

    public User() {

    }

    public User(Integer id, String name, String email, String lastLocation, Double frequency, Double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLocation = lastLocation;
        this.frequency = frequency;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public Double getBalance() {
        return balance;
    }

    // used in unit-tests only
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFrequency() {
        return frequency;
    }

    // used in unit-tests only
    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getEmail() {
        return email;
    }
}
