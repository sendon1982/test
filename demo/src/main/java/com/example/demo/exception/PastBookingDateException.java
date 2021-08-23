package com.example.demo.exception;

public class PastBookingDateException extends DemoException {

    public PastBookingDateException(String code, String message) {
        super(code, message);
    }
}
