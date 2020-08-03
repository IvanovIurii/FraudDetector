package com.fraud_detection.enums;

public enum Threshold {
    GREEN(1),
    YELLOW(0.75),
    RED(0.5);

    private double threshold;

    Threshold(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }
}
