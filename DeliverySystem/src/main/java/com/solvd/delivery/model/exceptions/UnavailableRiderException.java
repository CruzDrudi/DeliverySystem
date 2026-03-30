package com.solvd.delivery.model.exceptions;

public class UnavailableRiderException extends RuntimeException {
    public UnavailableRiderException(String message) {
        super(message);
    }
}
