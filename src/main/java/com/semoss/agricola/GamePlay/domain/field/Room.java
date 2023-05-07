package com.semoss.agricola.GamePlay.domain.field;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room implements Field {
    private boolean isPetRoom;
    private AnimalType animalType;
    private RoomType roomType;
    private List<ResidentType> residents;

    @Builder
    public Room(boolean isPetRoom, RoomType roomType){
        this.isPetRoom = isPetRoom;
        this.animalType = null;
        this.roomType = roomType;
        this.residents = new ArrayList<>();
    }

    public void addResident(ResidentType residentType) {
        residents.add(residentType);
    }
}
