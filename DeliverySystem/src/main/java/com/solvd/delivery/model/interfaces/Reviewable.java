package com.solvd.delivery.model.interfaces;

public interface Reviewable {
    void addReview(int rate, String comment);
    void addReview(int rate);
}
