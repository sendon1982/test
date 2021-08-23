package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DemoException extends RuntimeException {

    private final String code;

    private final String message;
}
