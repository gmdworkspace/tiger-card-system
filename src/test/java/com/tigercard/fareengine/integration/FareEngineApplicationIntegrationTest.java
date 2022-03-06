package com.tigercard.fareengine.integration;

import com.tigercard.fareengine.api.FareCapProvider;
import com.tigercard.fareengine.api.PeakHourProvider;
import com.tigercard.fareengine.api.TripFareProvider;
import com.tigercard.fareengine.exception.FareCapException;
import com.tigercard.fareengine.service.DayOfWeekPeakHourService;
import com.tigercard.fareengine.service.WeeklyFareCapService;
import com.tigercard.fareengine.service.ZonedHourTripFareService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tigercard.fareengine.builder.ShuttleTestBuilder.getShuttles;
import static com.tigercard.fareengine.builder.ShuttleTestBuilder.getShuttlesForWeek;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FareEngineApplicationIntegrationTest {

    private static PeakHourProvider peakHourDetector;
    private static TripFareProvider tripFareFinder;
    private static FareCapProvider weeklyFareCapProvider;

    @BeforeAll
    public static void setup() {
        peakHourDetector = new DayOfWeekPeakHourService();
        tripFareFinder = new ZonedHourTripFareService();
        weeklyFareCapProvider = new WeeklyFareCapService();
    }

    @Test
    public void shouldTestDailyCapReachedProblem() throws FareCapException {
        log.info("---------------------------------- Daily Cap Reached Problem ----------------------------------");
        Double totalFare = weeklyFareCapProvider.getTotalFare(getShuttles("2022-03-02", peakHourDetector, tripFareFinder));
        log.info("Total Fare:" + totalFare);
        assertEquals(120, totalFare);
    }

    @Test
    public void shouldTestWeeklyCapReachedProblem() throws FareCapException {
        log.info("---------------------------------- Weekly Cap Reached Problem ----------------------------------");
        Double totalFare = weeklyFareCapProvider.getTotalFare(getShuttlesForWeek(peakHourDetector, tripFareFinder));
        log.info("Total Fare:" + totalFare);
        assertEquals(700, totalFare);
    }
}
