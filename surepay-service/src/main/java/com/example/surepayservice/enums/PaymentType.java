package com.example.surepayservice.enums;

import java.util.Optional;

public enum PaymentType {
    PWT("PAY WITH BANK TRANSFER"), PWC("PAY WITH CARD");

    PaymentType(String name) {
        this.name = name;
    }

    private final String name;

    public static Optional<PaymentType> getType(String name) {

        for (PaymentType v : values())
            if (v.name.equalsIgnoreCase(name)) return Optional.of(v);

        return Optional.empty();
    }
}
