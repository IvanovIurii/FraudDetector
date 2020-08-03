package com.fraud_detection.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "transaction", schema = "public")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private UUID uuid;

    @Column
    private Double amount;

    @Column
    private Timestamp timestamp;

    @Column
    private String status;

    @Column(name = "user_id")
    private Integer userId;

    public Boolean isSuccess() {
        return this.status.equals("success");
    }

    public Double getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
