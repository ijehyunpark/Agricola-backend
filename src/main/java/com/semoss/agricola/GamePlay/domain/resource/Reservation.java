package com.semoss.agricola.GamePlay.domain.resource;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.PlayerBoard;
import lombok.Builder;

/**
 * 플레이어에게 추후 제공될 예약된 자원
 */
public class Reservation extends ResourceStruct {
    private Player player;

    @Builder(builderMethodName = "ReservationBuilder")
    Reservation(Player player, ResourceType resource, int count) {
        super(resource, count);
        this.player = player;
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
