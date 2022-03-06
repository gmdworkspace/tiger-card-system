package com.tigercard.fareengine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Shuttle {
    private Zone source;
    private Zone destination;
    private LocalDateTime dateTimeOfTravel;
    private Double fare;
}
