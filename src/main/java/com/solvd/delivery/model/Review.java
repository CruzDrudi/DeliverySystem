package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;

@EntityInfo("This represents a review consisting on 5 stars")
public record Review(int rate, String comment) {
    public Review(int rate) {
        this(rate, null);
    }
}