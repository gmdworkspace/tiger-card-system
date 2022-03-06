package com.tigercard.fareengine.builder;

import com.tigercard.fareengine.api.PeakHourProvider;
import com.tigercard.fareengine.api.TripFareProvider;
import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.domain.Zone;
import com.tigercard.fareengine.exception.PeakHourException;
import com.tigercard.fareengine.exception.TripFareException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tigercard.fareengine.util.DateTimeUtil.getDateTime;

@Slf4j
public class ShuttleTestBuilder {
    public static Shuttle getShuttle(Zone source, Zone destination, LocalDateTime timeOfTravel, PeakHourProvider peakHourProvider, TripFareProvider tripFareProvider) throws PeakHourException, TripFareException {
        HourType hourType = peakHourProvider.getHourType(timeOfTravel.getDayOfWeek(), timeOfTravel.toLocalTime());
        Double tripFare = tripFareProvider.getFareForTheTrip(source, destination, hourType);
        return new Shuttle(source, destination, timeOfTravel, tripFare);
    }

    public static List<Shuttle> getShuttles(String date, PeakHourProvider peakHourProvider, TripFareProvider tripFareProvider) {
        Map<LocalDateTime, ArrayList<Zone>> shuttleMap = new LinkedHashMap<>();
        shuttleMap.put(getDateTime(date + " 10:20"), new ArrayList<>() {{
            add(Zone.TWO);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 10:45"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 16:15"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 18:15"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 19:00"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.TWO);
        }});
        return shuttleMap.entrySet().stream()
                .map(s -> {
                    try {
                        return getShuttle(s.getValue().get(0), s.getValue().get(1), s.getKey(), peakHourProvider, tripFareProvider);
                    } catch (PeakHourException | TripFareException e) {
                        log.error("Unable to get shuttle details for source:" + s.getValue().get(0) + " destination:" + s.getValue().get(1) + " on time:" + s.getKey());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public static List<Shuttle> getShuttlesForWeek(PeakHourProvider peakHourProvider, TripFareProvider tripFareProvider) {
        List<Shuttle> shuttles = new ArrayList<>();
        shuttles.addAll(getShuttles("2022-02-28", peakHourProvider, tripFareProvider));
        shuttles.addAll(getShuttles("2022-03-01", peakHourProvider, tripFareProvider));
        shuttles.addAll(getShuttles("2022-03-02", peakHourProvider, tripFareProvider));
        shuttles.addAll(getShuttles("2022-03-03", peakHourProvider, tripFareProvider));
        //daily cap not reached
        shuttles.addAll(getShuttleOnNoCapReached("2022-03-04", peakHourProvider, tripFareProvider));
        shuttles.addAll(getShuttles("2022-03-05", peakHourProvider, tripFareProvider));
        shuttles.addAll(getShuttles("2022-03-06", peakHourProvider, tripFareProvider));
        //new week
        shuttles.addAll(getShuttleOnNoCapReachedWeekly("2022-03-07", peakHourProvider, tripFareProvider));
        return shuttles;
    }

    public static List<Shuttle> getShuttleOnNoCapReachedWeekly(String date, PeakHourProvider peakHourProvider, TripFareProvider tripFareProvider) {
        Map<LocalDateTime, ArrayList<Zone>> shuttleMap = new LinkedHashMap<>();
        shuttleMap.put(getDateTime(date + " 07:20"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.TWO);
        }});
        shuttleMap.put(getDateTime(date + " 09:45"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.TWO);
        }});
        shuttleMap.put(getDateTime(date + " 17:15"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        return shuttleMap.entrySet().stream()
                .map(s -> {
                    try {
                        return getShuttle(s.getValue().get(0), s.getValue().get(1), s.getKey(), peakHourProvider, tripFareProvider);
                    } catch (PeakHourException | TripFareException e) {
                        log.error("Unable to get shuttle details for source:" + s.getValue().get(0) + " destination:" + s.getValue().get(1) + " on time:" + s.getKey());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public static List<Shuttle> getShuttleOnNoCapReached(String date, PeakHourProvider peakHourProvider, TripFareProvider tripFareProvider) {
        Map<LocalDateTime, ArrayList<Zone>> shuttleMap = new LinkedHashMap<>();
        shuttleMap.put(getDateTime(date + " 10:20"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 12:45"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        shuttleMap.put(getDateTime(date + " 13:15"), new ArrayList<>() {{
            add(Zone.ONE);
            add(Zone.ONE);
        }});
        return shuttleMap.entrySet().stream()
                .map(s -> {
                    try {
                        return getShuttle(s.getValue().get(0), s.getValue().get(1), s.getKey(), peakHourProvider, tripFareProvider);
                    } catch (PeakHourException | TripFareException e) {
                        log.error("Unable to get shuttle details for source:" + s.getValue().get(0) + " destination:" + s.getValue().get(1) + " on time:" + s.getKey());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
