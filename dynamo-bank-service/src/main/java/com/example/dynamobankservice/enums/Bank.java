package com.example.dynamobankservice.enums;

import java.util.Optional;

public enum Bank {
    DB("DYNAMO BANK"), WEMA("WEMA"), NPSB("9 PAYMENTS SERVICE BANK");

    Bank(String name) {
        this.name = name;
    }

    private final String name;

    public static Optional<Bank> getBank(String name) {

        for (Bank v : values())
            if (v.name.equalsIgnoreCase(name)) return Optional.of(v);

        return Optional.empty();
    }
}
