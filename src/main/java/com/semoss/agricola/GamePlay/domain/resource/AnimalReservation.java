package com.semoss.agricola.GamePlay.domain.resource;

import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;

public class AnimalReservation extends AnimalStruct {
    private Player player;

    @Builder(builderMethodName = "AnimalReservationBuilder")
    AnimalReservation(Player player, AnimalType animal, int count) {
        super(animal, count);
        this.player = player;
    }

    /**
     * 예약 자원을 해당플레이어에게 전달한다.
     */
    public void resolve() {
        //TODO
    }
}
