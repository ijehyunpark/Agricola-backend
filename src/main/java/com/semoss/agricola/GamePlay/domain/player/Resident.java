package com.semoss.agricola.GamePlay.domain.player;

import lombok.Getter;
import lombok.Setter;

/**
 * 방에 거주하는 거주자 객체
 */
@Getter
public class Resident {
    private ResidentType residentType;
    @Setter
    private boolean played;

    public Resident(ResidentType residentType, boolean played) {
        this.residentType = residentType;
        this.played = played;
    }

    public void growUpChild(){
        this.residentType = ResidentType.ADULT;
    }
}