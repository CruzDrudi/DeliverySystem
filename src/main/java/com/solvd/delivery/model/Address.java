package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;

@EntityInfo("This represents a real address")
public record Address(String street, int number, int floor, String apartment) {
    public Address(String street, int number) {
        this(street, number, 0, null);
    }
}
