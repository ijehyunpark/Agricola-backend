package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.player.type.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.type.ResidentType;
import com.semoss.agricola.GamePlay.domain.player.type.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room implements Field {

    @Getter
    private class Resident {
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

    @Getter
    private class PetRoom {
        private AnimalType animalType;
    }

    private PetRoom petRoom;
    private RoomType roomType;
    private List<Resident> residents;

    @Builder
    public Room(boolean isPetRoom, RoomType roomType){
        if(isPetRoom){
            this.petRoom = new PetRoom();
        }

        this.roomType = roomType;
        this.residents = new ArrayList<>();
    }

    /**
     * 새로운 거주자 추가
     * @param residentType 거주자 상태
     */
    public void addResident(ResidentType residentType) {
        residents.add(new Resident(residentType, false));
    }

    /**
     * 플레이하지 않은 가족말 여부
     * @return
     */
    protected boolean isCompletedPlayed() {
        for (Resident resident : residents){
            if(resident.isPlayed() == false)
                return false;
        }
        return true;
    }

    /**
     * 플레이 여부 초기화
     */
    protected void initPlayed() {
        for (Resident resident : residents){
            resident.setPlayed(false);
        }
    }

    /**
     * 아이 성장
     */
    protected void growUpChild() {
        for (Resident resident : residents){
            if(resident.getResidentType() == ResidentType.CHILD)
                resident.growUpChild();
        }
    }
}
