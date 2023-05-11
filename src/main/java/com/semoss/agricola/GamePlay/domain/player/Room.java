package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;
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
    private final List<Resident> residents;
    private final ResourceStruct resource;

    @Builder
    public Room(boolean isPetRoom){
        if(isPetRoom){
            this.petRoom = new PetRoom();
        }
        this.residents = new ArrayList<>();
        this.resource = ResourceStruct.builder()
                .resource(ResourceType.EMPTY)
                .count(0)
                .build();
    }

    /**
     * 새로운 거주자 추가
     * @param residentType 거주자 상태
     */
    public void addResident(ResidentType residentType) {
        this.residents.add(new Resident(residentType, false));
    }

    /**
     * 플레이하지 않은 가족말 여부
     * @return
     */
    protected boolean isCompletedPlayed() {
        for (Resident resident : this.residents){
            if(resident.isPlayed() == false)
                return false;
        }
        return true;
    }

    /**
     * 플레이 여부 초기화
     */
    protected void initPlayed() {
        for (Resident resident : this.residents){
            resident.setPlayed(false);
        }
    }

    /**
     * 아이 성장
     */
    protected void growUpChild() {
        for (Resident resident : this.residents){
            if(resident.getResidentType() == ResidentType.CHILD)
                resident.growUpChild();
        }
    }


    /**
     * 음식 요구사항 계산
     * @return
     */
    public int calculateFoodNeeds() {
        int result = 0;
        for (Resident resident : this.residents){
            if(resident.getResidentType() == ResidentType.ADULT)
                result += 2;
            else if(resident.getResidentType() == ResidentType.CHILD)
                result += 1;
        }
        return result;
    }

    public boolean isEmptyRoom() {
        return this.residents.size() == 0;
    }
}
