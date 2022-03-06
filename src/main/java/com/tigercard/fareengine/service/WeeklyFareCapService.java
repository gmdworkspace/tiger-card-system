package com.tigercard.fareengine.service;

import com.tigercard.fareengine.api.FareCapProvider;
import com.tigercard.fareengine.config.FareCapConfig;
import com.tigercard.fareengine.config.WeeklyFareCapConfig;
import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.exception.FareCapException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tigercard.fareengine.helper.FareCapHelper.*;
import static com.tigercard.fareengine.util.DateTimeUtil.getWeekOfYear;

@Slf4j
public class WeeklyFareCapService implements FareCapProvider {

    private FareCapProvider dailyFareCapProvider;
    private FareCapConfig fareCapConfig;

    public WeeklyFareCapService() {
        dailyFareCapProvider = new DailyFareCapService();
        fareCapConfig = new WeeklyFareCapConfig();
    }

    @Override
    public Double getTotalFare(List<Shuttle> shuttles) throws FareCapException {
        Map<Integer, Double> fareForTheWeeks = new HashMap<>();
        Double farthestFareCap = getFarthestCap(shuttles, fareCapConfig);
        if (shuttles == null || shuttles.isEmpty())
            throw new FareCapException("Shuttles should not be null or empty");

        for (LocalDate shuttleDate : getDistinctShuttleDates(shuttles)) {
            Integer weekNum = getWeekOfYear(shuttleDate);
            Double dailyCappedFare = dailyFareCapProvider.getTotalFare(getShuttlesForDates(shuttleDate, shuttles));
            Double weeksTotalFare = fareForTheWeeks.get(weekNum) != null ? fareForTheWeeks.get(weekNum) : 0.00;
            weeksTotalFare += getActualCapedFare(weeksTotalFare, dailyCappedFare, farthestFareCap);
            fareForTheWeeks.put(weekNum, weeksTotalFare);
        }
        log.debug("Fare for respective weeks" + fareForTheWeeks);
        return fareForTheWeeks.values().stream().reduce(Double::sum).orElse(RATE_ZERO_AMOUNT);
    }

    private List<Shuttle> getShuttlesForDates(LocalDate shuttleDate, List<Shuttle> shuttles) {
        return shuttles.stream()
                .filter(s -> s.getDateTimeOfTravel().toLocalDate().equals(shuttleDate))
                .collect(Collectors.toList());
    }

    private List<LocalDate> getDistinctShuttleDates(List<Shuttle> shuttles) {
        return shuttles.stream()
                .map(s -> s.getDateTimeOfTravel().toLocalDate())
                .distinct()
                .collect(Collectors.toList());
    }
}
