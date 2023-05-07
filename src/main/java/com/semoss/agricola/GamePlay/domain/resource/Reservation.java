package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;

public class Reservation extends ResourceStruct {
    private Long userId;

    @Builder(builderMethodName = "ReservationBuilder")
    Reservation(Resource resource, int count) {
        super(resource, count);
    }
}
