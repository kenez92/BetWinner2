package com.kenez92.betwinner.common.enums;

public enum UserStrategy {
    AGGRESSIVE_STRATEGY("aggressive strategy"),
    DEFENSIVE_STRATEGY("defensive strategy"),
    EVERYTHING_STRATEGY("everything strategy"),
    NORMAL_STRATEGY("normal strategy"),
    PERCENT_70_STRATEGY("70 percent strategy");


    private String value;

    UserStrategy(String value) {
        this.value = value;
    }

    UserStrategy() {
    }

    @Override
    public String toString() {
        return value;
    }
}
