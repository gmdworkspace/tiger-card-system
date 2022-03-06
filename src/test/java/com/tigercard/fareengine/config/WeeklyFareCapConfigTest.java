package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.Zone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WeeklyFareCapConfigTest {
    private static FareCapConfig fareCapConfig;

    @BeforeAll
    public static void setup() {
        fareCapConfig = new WeeklyFareCapConfig();
    }

    @Test
    public void shouldFetchFareCap() {
        assertEquals(500.00, fareCapConfig.fetchFareCap(Zone.ONE, Zone.ONE));
        assertEquals(600.00, fareCapConfig.fetchFareCap(Zone.ONE, Zone.TWO));
        assertEquals(600.00, fareCapConfig.fetchFareCap(Zone.TWO, Zone.ONE));
        assertEquals(400.00, fareCapConfig.fetchFareCap(Zone.TWO, Zone.TWO));
    }

    @Test
    public void shouldReturnFetchFareCapAsNullWhenSourceAndDestinationAreNull() {
        assertNull(fareCapConfig.fetchFareCap(null, null));
        assertNull(fareCapConfig.fetchFareCap(Zone.ONE, null));
        assertNull(fareCapConfig.fetchFareCap(null, Zone.ONE));
    }
}
