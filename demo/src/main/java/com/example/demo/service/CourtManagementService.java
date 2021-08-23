package com.example.demo.service;

import com.example.gen.model.CourtRegisterRequest;
import com.example.gen.model.CourtRegisterResponse;

public interface CourtManagementService {

    CourtRegisterResponse registerCourt(CourtRegisterRequest request);

}
