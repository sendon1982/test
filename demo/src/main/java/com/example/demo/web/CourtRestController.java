package com.example.demo.web;

import com.example.demo.exception.PastBookingDateException;
import com.example.demo.service.CourtManagementService;
import com.example.gen.api.CourtsApi;
import com.example.gen.model.CourtRegisterRequest;
import com.example.gen.model.CourtRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CourtRestController implements CourtsApi {

    private final CourtManagementService courtManagementService;

    public CourtRestController(CourtManagementService courtManagementService) {
        this.courtManagementService = courtManagementService;
    }

    @Override
    public ResponseEntity<CourtRegisterResponse> registerCourt(CourtRegisterRequest request) {
        if (request.getDate().isAfter(LocalDate.now())) {
            CourtRegisterResponse courtRegisterResponse = courtManagementService.registerCourt(request);
            return ResponseEntity.ok(courtRegisterResponse);
        }

        throw new PastBookingDateException("10001", "Booking date must be in the future");
    }
}
