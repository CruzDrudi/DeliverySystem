package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;

@EntityInfo
public class Evaluation<T> {
    private T subject;
    private int rate;
    private String comment;

    public Evaluation(T subject, int rate, String comment) {
        this.subject = subject;
        this.rate = rate;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Evaluation for " + subject.getClass().getSimpleName() +
                           ": " + rate + "/5 stars. Comment: " + comment;
    }
}