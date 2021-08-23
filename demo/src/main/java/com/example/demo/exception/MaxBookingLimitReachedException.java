package com.example.demo.exception;

public class MaxBookingLimitReachedException extends DemoException {

    public MaxBookingLimitReachedException(String code, String message) {
        super(code, message);
    }
}
