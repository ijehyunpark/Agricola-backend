package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;

public class Reservation extends ResourceStruct {
    private Long userId;

    @Builder(builderMethodName = "ReservationBuilder")
    Reservation(ResourceType resource, int count) {
        super(resource, count);
    }
}
