package com.tigercard.fareengine.helper;

import com.tigercard.fareengine.config.DailyFareCapConfig;
import com.tigercard.fareengine.config.FareCapConfig;
import com.tigercard.fareengine.config.WeeklyFareCapConfig;
import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.domain.Zone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.tigercard.fareengine.helper.FareCapHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FareCapHelperTest {
    private static FareCapConfig dailyFareCapConfig;
    private static FareCapConfig weeklyFareCapConfig;

    @BeforeAll
    public static void setup() {
        dailyFareCapConfig = new DailyFareCapConfig();
        weeklyFareCapConfig = new WeeklyFareCapConfig();
    }

    @Test
    public void shouldGetFarthestCapForDailyFareCap() {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(120, getFarthestCap(List.of(shuttle1, shuttle2, shuttle3), dailyFareCapConfig));

        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(100, getFarthestCap(List.of(shuttle4, shuttle5), dailyFareCapConfig));

        Shuttle shuttle6 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle7 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(80, getFarthestCap(List.of(shuttle6, shuttle7), dailyFareCapConfig));
    }

    @Test
    public void shouldGetFarthestCapForWeeklyFareCap() {
        Shuttle shuttle1 = new Shuttle(Zone.TWO, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 20), 35.00);
        Shuttle shuttle2 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle3 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(600, getFarthestCap(List.of(shuttle1, shuttle2, shuttle3), weeklyFareCapConfig));

        Shuttle shuttle4 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle5 = new Shuttle(Zone.ONE, Zone.ONE, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(500, getFarthestCap(List.of(shuttle4, shuttle5), weeklyFareCapConfig));

        Shuttle shuttle6 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        Shuttle shuttle7 = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 16, 15), 25.00);
        assertEquals(400, getFarthestCap(List.of(shuttle6, shuttle7), weeklyFareCapConfig));
    }

    @Test
    public void shouldReturnNullForGetFarthestCapWhenShuttleOrFareCapConfigIsNull() {
        Shuttle shuttle = new Shuttle(Zone.TWO, Zone.TWO, LocalDateTime.of(2022, 03, 04, 10, 45), 25.00);
        assertNull(getFarthestCap(List.of(shuttle), null));
        assertNull(getFarthestCap(null, dailyFareCapConfig));
        assertNull(getFarthestCap(null, null));
    }

    @Test
    public void shouldGetActualCapedFare() {
        assertEquals(20.00, getActualCapedFare(100.00, 35.00, 120.00));
        assertEquals(15.00, getActualCapedFare(385.00, 20.00, 400.00));
    }

    @Test
    public void shouldGetActualCapedFareIfTotalFareAndFarthestFareAreEqual() {
        assertEquals(RATE_ZERO_AMOUNT, getActualCapedFare(120.00, 20.00, 120.00));
        assertEquals(RATE_ZERO_AMOUNT, getActualCapedFare(400.00, 35.00, 400.00));
    }

    @Test
    public void shouldGetActualCapedFarIfLimitDoesNotExceed() {
        assertEquals(20.00, getActualCapedFare(100.00, 20.00, 120.00));
        assertEquals(15.00, getActualCapedFare(385.00, 15.00, 400.00));
        assertEquals(15.00, getActualCapedFare(200.00, 15.00, 400.00));
    }
}
