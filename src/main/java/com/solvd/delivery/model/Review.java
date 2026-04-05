package com.solvd.delivery.model;

import java.util.Objects;

public class Review {
    private int rate;
    private String comment;

    public Review(int rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

    public Review(int rate) {
        this(rate, null);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        if (comment == null || comment.isEmpty()) {
            return "Review of " + rate + "/5 stars.";
        } else {
            return "Review of " + rate + "/5 stars with the comment: " + comment;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return this.rate == review.getRate()
                && Objects.equals(this.comment, review.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, comment);
    }
}
