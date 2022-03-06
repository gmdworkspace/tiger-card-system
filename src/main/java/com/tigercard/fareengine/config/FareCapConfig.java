package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.Zone;
import lombok.Data;

@Data
public abstract class FareCapConfig {
    public abstract Double fetchFareCap(Zone source, Zone destination);
}
