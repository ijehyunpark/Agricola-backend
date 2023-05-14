package com.semoss.agricola.GamePlay.domain.resource;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;

public class Reservation extends ResourceStruct {
    private Player player;

    @Builder(builderMethodName = "ReservationBuilder")
    Reservation(ResourceType resource, int count) {
        super(resource, count);
    }

    /**
     * 예약 자원을 해당플레이어에게 전달한다.
     */
    public void resolve() {
        player.addResource(ResourceStruct.builder()
                .resource(super.getResource())
                .count(super.getCount())
                .build());
    }
}
