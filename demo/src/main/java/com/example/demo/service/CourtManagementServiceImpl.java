package com.example.demo.service;

import com.example.demo.exception.CourtNotFoundException;
import com.example.demo.exception.MaxBookingLimitReachedException;
import com.example.gen.model.CourtEnum;
import com.example.gen.model.CourtInfo;
import com.example.gen.model.CourtRegisterRequest;
import com.example.gen.model.CourtRegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class CourtManagementServiceImpl implements CourtManagementService {

    public static int MAX_PLAYER_NUMBER = 12;

    public static final int MAX_SPOT_PER_COURT = 4;

    private static final Map<CourtEnum, Map<LocalDate, CourtInfo>> courtIdDateMapping;

    static {
        courtIdDateMapping = new LinkedHashMap<>();

        for (CourtEnum courtEnum : CourtEnum.values()) {
            courtIdDateMapping.put(courtEnum, new LinkedHashMap<>());
        }
    }

    @Value("${demo.maxPlayerNumber:12}")
    private int maxPlayerNumber;

    @Override
    public CourtRegisterResponse registerCourt(CourtRegisterRequest request) {
        CourtEnum courtNumber = request.getCourtNumber();
        LocalDate bookingDate = request.getDate();

        if (!courtIdDateMapping.containsKey(courtNumber)) {
            String message = String.format("Unable to find court %s on date %s", courtNumber, bookingDate);
            throw new CourtNotFoundException("10000", message);
        }

        CourtInfo courtInfo = null;

        Map<LocalDate, CourtInfo> dateCourtInfoMap = courtIdDateMapping.get(courtNumber);

        if (dateCourtInfoMap.containsKey(bookingDate)) {
            courtInfo = dateCourtInfoMap.get(bookingDate);
            int availableSpot = courtInfo.getAvailableSpot();

            if (hasAvailableSpot(availableSpot)) {
                availableSpot--;
                courtInfo.setAvailableSpot(availableSpot);
            }

            if (isCourtFullyBooked(availableSpot)) {
                log.info("Court number {} on date {} is ready to play, please notify players", courtNumber, bookingDate);
            }

        } else {
            courtInfo = new CourtInfo();
            courtInfo.setDate(bookingDate);
            courtInfo.setCourtNumber(courtNumber);
            courtInfo.setAvailableSpot(MAX_SPOT_PER_COURT - 1);

            dateCourtInfoMap.put(bookingDate, courtInfo);
        }

        maxPlayerNumber--;
        if (isCourtFullyBooked(maxPlayerNumber)) {
            String message = String.format("Reach max limit booking for the club date %s", bookingDate);
            throw new MaxBookingLimitReachedException("20000", message);
        }

        CourtRegisterResponse response = new CourtRegisterResponse();

        response.setPlayer(request.getPlayer());
        response.setDate(bookingDate);
        response.setCourtNumber(courtNumber);
        response.setAvailableSpot(courtInfo.getAvailableSpot());

        return response;
    }

    private boolean hasAvailableSpot(int availableSpot) {
        return availableSpot > 0;
    }

    private boolean isCourtFullyBooked(int availableSpot) {
        return availableSpot == 0;
    }
}
