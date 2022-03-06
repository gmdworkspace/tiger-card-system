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

public class DailyFareCapServiceTest {

    private static DailyFareCapService dailyFareCapService;

    @BeforeAll
    public static void setup() {
        dailyFareCapService = new DailyFareCapService();
    }

    @Test
    public void shouldGetTotalFareCappedForADayTravelOnZoneOneAndTwo() throws Exception {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 18, 15), 30.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.TWO, LocalDateTime.of(2022, 03, 04, 19, 00), 35.00);
        assertEquals(120, dailyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5)));
    }

    @Test
    public void shouldGetTotalFareCappedForADayTravelOnZoneOne() throws Exception {
        Shuttle shuttle1 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 18, 15), 30.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 19, 00), 35.00);
        assertEquals(100, dailyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5)));
    }

    @Test
    public void shouldGetTotalFareCappedForADayTravelOnZoneTwo() throws Exception {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        Shuttle shuttle4 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 18, 15), 30.00);
        Shuttle shuttle5 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 19, 00), 35.00);
        assertEquals(80, dailyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3, shuttle4, shuttle5)));
    }

    @Test
    public void shouldGetTotalFareCappedForADayLimitNotReached() throws Exception {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(85, dailyFareCapService.getTotalFare(List.of(shuttle1, shuttle2, shuttle3)));
    }

    @Test
    public void shouldThrowExceptionWhenShuttlesNullOrEmpty() throws Exception {
        assertThrows(FareCapException.class, () -> dailyFareCapService.getTotalFare(null));
        assertThrows(FareCapException.class, () -> dailyFareCapService.getTotalFare(List.of()));
    }

    @Test
    public void shouldGetTotalFareThrowExceptionWhenShufftlesAreInDifferentDates() {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 05, 10, 45), 25.00);
        assertThrows(FareCapException.class, () -> dailyFareCapService.getTotalFare(List.of(shuttle1, shuttle2)));

        Shuttle shuttle3 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 04, 04, 10, 45), 25.00);
        assertThrows(FareCapException.class, () -> dailyFareCapService.getTotalFare(List.of(shuttle3, shuttle4)));
    }
}
