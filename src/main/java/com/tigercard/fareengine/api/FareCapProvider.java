package com.tigercard.fareengine.api;

import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.exception.FareCapException;

import java.util.List;

public interface FareCapProvider {
    /***
     * Get the total fare caped for list of shuttles
     * @param shuttles which are not caped with prices
     * @return the total fare after capping the fare prices - Double
     * @throws FareCapException
     */
    Double getTotalFare(List<Shuttle> shuttles) throws FareCapException;
}
