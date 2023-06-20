package com.example.dynamobankservice.enums;

import java.util.Optional;

public enum BankAccountType {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    BankAccountType(String name) {
        this.name = name;
    }

    private final String name;

    public static Optional<BankAccountType> getType(String name) {

        for (BankAccountType v : values())
            if (v.name.equalsIgnoreCase(name)) return Optional.of(v);

        return Optional.empty();
    }
}
