package com.example.demo.web;

import com.example.demo.exception.CourtNotFoundException;
import com.example.demo.exception.MaxBookingLimitReachedException;
import com.example.demo.exception.PastBookingDateException;
import com.example.gen.model.CourtEnum;
import com.example.gen.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class CourtControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);

        ErrorResponse response = new ErrorResponse();
        response.setCode("10002");
        response.setMessage("Invalid court number, must be one of " + Arrays.toString(CourtEnum.values()));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CourtNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(CourtNotFoundException e) {
        log.error("CourtNotFoundException", e);

        ErrorResponse response = new ErrorResponse();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MaxBookingLimitReachedException.class)
    public ResponseEntity<ErrorResponse> handle(MaxBookingLimitReachedException e) {
        log.error("MaxBookingLimitReachedException", e);

        ErrorResponse response = new ErrorResponse();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(PastBookingDateException.class)
    public ResponseEntity<ErrorResponse> handle(PastBookingDateException e) {
        log.error("PastBookingDateException", e);

        ErrorResponse response = new ErrorResponse();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
