package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Zone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TripFareConfigTest {
    private static TripFareConfig tripFareConfig;

    @BeforeAll
    public static void setup() {
        tripFareConfig = new TripFareConfig();
    }

    @Test
    public void shouldFindTripFareForHourType() {
        assertEquals(30, tripFareConfig.findTripFareForHourType(Zone.ONE, Zone.ONE, HourType.PEAK_HOUR));
        assertEquals(35, tripFareConfig.findTripFareForHourType(Zone.ONE, Zone.TWO, HourType.PEAK_HOUR));
        assertEquals(35, tripFareConfig.findTripFareForHourType(Zone.TWO, Zone.ONE, HourType.PEAK_HOUR));
        assertEquals(25, tripFareConfig.findTripFareForHourType(Zone.TWO, Zone.TWO, HourType.PEAK_HOUR));

        assertEquals(25, tripFareConfig.findTripFareForHourType(Zone.ONE, Zone.ONE, HourType.OFF_PEAK_HOUR));
        assertEquals(30, tripFareConfig.findTripFareForHourType(Zone.ONE, Zone.TWO, HourType.OFF_PEAK_HOUR));
        assertEquals(30, tripFareConfig.findTripFareForHourType(Zone.TWO, Zone.ONE, HourType.OFF_PEAK_HOUR));
        assertEquals(20, tripFareConfig.findTripFareForHourType(Zone.TWO, Zone.TWO, HourType.OFF_PEAK_HOUR));
    }

    @Test
    public void shouldReturnNullIfSourceOrDestinationOrHourTypeIsNull() {
        assertNull(tripFareConfig.findTripFareForHourType(null, null, null));
        assertNull(tripFareConfig.findTripFareForHourType(Zone.ONE, null, HourType.PEAK_HOUR));
        assertNull(tripFareConfig.findTripFareForHourType(Zone.ONE, Zone.ONE, null));
    }
}
