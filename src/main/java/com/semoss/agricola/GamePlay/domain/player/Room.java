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
     * 거주자 인원수 반환
     */
    public int getResidentNumber(){
        return this.residents.size();
    }

    /**
     * 플레이하지 않은 가족말 여부
     * @return
     */
    protected boolean isCompletedPlayed() {
        return this.residents.stream()
                .allMatch(Resident::isPlayed);
    }

    /**
     * 플레이 여부 초기화
     */
    protected void initPlayed() {
        this.residents.stream()
            .forEach(resident -> resident.setPlayed(false));
    }

    /**
     * 플레이한다.
     */
    protected void play() {
        this.residents.stream()
                .filter(resident -> resident.isPlayed() == false && resident.residentType == ResidentType.ADULT)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setPlayed(true);
    }

    /**
     * 아이 성장
     */
    protected void growUpChild() {
        this.residents.stream()
                .filter(resident -> resident.getResidentType() == ResidentType.CHILD)
                .forEach(Resident::growUpChild);
    }


    /**
     * 음식 요구사항 계산
     * @return
     */
    public int calculateFoodNeeds() {
        return this.residents.stream()
                .mapToInt(resident -> resident.getResidentType() == ResidentType.ADULT ? 2 : 1)
                .sum();
    }

    /**
     * 빈방 여부 확인
     * @return
     */
    public boolean isEmptyRoom() {
        return this.residents.size() == 0;
    }
}
