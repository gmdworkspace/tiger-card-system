package com.tigercard.fareengine.service;

import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.domain.Zone;
import com.tigercard.fareengine.exception.FareCapException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeeklyFareCapServiceTest {

    private static WeeklyFareCapService weeklyFareCapService;

    @BeforeAll
    public static void setup() {
        weeklyFareCapService = new WeeklyFareCapService();
    }

    @Test
    public void shouldGetTotalFareWeeklyCapNotReached() throws FareCapException {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 3, 4, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 5, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 6, 16, 15), 25.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 7, 18, 15), 30.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 8, 19, 0), 35.00);
        assertEquals(150, weeklyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5)));
    }

    @Test
    public void shouldGetTotalFareWeeklyCapReachedFarthestZoneOneAndZoneTwo() throws FareCapException {
        Shuttle shuttle1 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 2, 28, 10, 20), 150.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 1, 10, 45), 150.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 2, 16, 15), 150.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 3, 18, 15), 150.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 4, 19, 0), 80.00);
        Shuttle shuttle6 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 5, 19, 0), 150.00);
        Shuttle shuttle7 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 6, 19, 0), 150.00);
        Shuttle shuttle8 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 3, 7, 19, 0), 100.00);
        assertEquals(700, weeklyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5, shuttle6, shuttle7, shuttle8)));
    }

    @Test
    public void shouldGetTotalFareWeeklyCapReachedFarthestZoneOneAndZoneOne() throws FareCapException {
        Shuttle shuttle1 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 2, 28, 10, 20), 120.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 1, 10, 45), 120.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 2, 16, 15), 120.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 3, 18, 15), 120.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 4, 19, 0), 80.00);
        Shuttle shuttle6 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 5, 19, 0), 120.00);
        Shuttle shuttle7 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 6, 19, 0), 120.00);
        Shuttle shuttle8 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 3, 7, 19, 0), 100.00);
        assertEquals(600, weeklyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5, shuttle6, shuttle7, shuttle8)));
    }

    @Test
    public void shouldGetTotalFareWeeklyCapReachedFarthestZoneTwoAndZoneTwo() throws FareCapException {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 2, 28, 10, 20), 120.00);
        Shuttle shuttle2 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 1, 10, 45), 120.00);
        Shuttle shuttle3 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 2, 16, 15), 120.00);
        Shuttle shuttle4 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 3, 18, 15), 120.00);
        Shuttle shuttle5 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 4, 19, 0), 80.00);
        Shuttle shuttle6 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 5, 19, 0), 120.00);
        Shuttle shuttle7 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 6, 19, 0), 120.00);
        Shuttle shuttle8 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 3, 7, 19, 0), 100.00);
        assertEquals(480, weeklyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5, shuttle6, shuttle7, shuttle8)));
    }

    @Test
    public void shouldThrowExceptionWhenShuttleIsNull() {
        assertThrows(FareCapException.class, () -> weeklyFareCapService.getTotalFare(null));
    }
}
