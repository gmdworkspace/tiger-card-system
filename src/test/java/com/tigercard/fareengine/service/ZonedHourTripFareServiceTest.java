package com.tigercard.fareengine.service;

import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Zone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZonedHourTripFareServiceTest {
    private static ZonedHourTripFareService zonedHourTripFareService;

    @BeforeAll
    public static void setup() {
        zonedHourTripFareService = new ZonedHourTripFareService();
    }

    @Test
    public void shouldTestGetFareForTheTrip() throws Exception {
        assertEquals(30, zonedHourTripFareService.getFareForTheTrip(Zone.ONE, Zone.ONE, HourType.PEAK_HOUR));
        assertEquals(35, zonedHourTripFareService.getFareForTheTrip(Zone.ONE, Zone.TWO, HourType.PEAK_HOUR));
        assertEquals(35, zonedHourTripFareService.getFareForTheTrip(Zone.TWO, Zone.ONE, HourType.PEAK_HOUR));
        assertEquals(25, zonedHourTripFareService.getFareForTheTrip(Zone.TWO, Zone.TWO, HourType.PEAK_HOUR));

        assertEquals(25, zonedHourTripFareService.getFareForTheTrip(Zone.ONE, Zone.ONE, HourType.OFF_PEAK_HOUR));
        assertEquals(30, zonedHourTripFareService.getFareForTheTrip(Zone.ONE, Zone.TWO, HourType.OFF_PEAK_HOUR));
        assertEquals(30, zonedHourTripFareService.getFareForTheTrip(Zone.TWO, Zone.ONE, HourType.OFF_PEAK_HOUR));
        assertEquals(20, zonedHourTripFareService.getFareForTheTrip(Zone.TWO, Zone.TWO, HourType.OFF_PEAK_HOUR));
    }

    @Test
    public void shouldTestGetFareForTheTripThrowsException() {
        assertThrows(Exception.class, () -> zonedHourTripFareService.getFareForTheTrip(null, null, null));
        assertThrows(Exception.class, () -> zonedHourTripFareService.getFareForTheTrip(null, Zone.TWO, HourType.PEAK_HOUR));
        assertThrows(Exception.class, () -> zonedHourTripFareService.getFareForTheTrip(Zone.ONE, null, HourType.PEAK_HOUR));
        assertThrows(Exception.class, () -> zonedHourTripFareService.getFareForTheTrip(Zone.ONE, Zone.ONE, null));
    }
}
