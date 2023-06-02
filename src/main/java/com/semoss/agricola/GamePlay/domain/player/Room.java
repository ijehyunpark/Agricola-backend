package com.semoss.agricola.GamePlay.domain.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import com.semoss.agricola.GamePlay.exception.ServerError;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room implements Field {

    @Getter
    public class PetRoom {
        private AnimalStruct animal;
        public PetRoom(){
            animal = AnimalStruct.builder().animal(null).count(0).build();
        }

        @JsonIgnore
        public boolean isEmpty() {
            return animal.getCount() == 0;
        }

        @JsonIgnore
        public AnimalType getAnimalType(){
            return animal.getAnimal();
        }

        public boolean addPet(AnimalType animalType){
            if(isEmpty()) {
                animal.setAnimal(animalType);
                animal.setCount(1);
                return true;
            }
            return false;
        }

        public AnimalType removePet(){
            AnimalType animalType = animal.getAnimal();
            animal.setAnimal(null);
            animal.setCount(0);
            return animalType;
        }
    }

    private PetRoom petRoom;
    private final FieldType fieldType = FieldType.ROOM;
    private final boolean isPetRoom;
    private final List<Resident> residents;

    @Builder
    public Room(boolean isPetRoom){
        if(isPetRoom){
            this.petRoom = new PetRoom();
        }
        this.isPetRoom = isPetRoom;
        this.residents = new ArrayList<>();
    }

    /**
     * 새로운 거주자 추가
     * @param residentType 거주자 상태
     */
    public void addResident(ResidentType residentType) {
        this.residents.add(new Resident(residentType, false));
    }

    /**
     * 방의 거주자 인원수를 가져온다.
     */
    public int getResidentNumber(){
        return this.residents.size();
    }

    /**
     * 플레이하지 않은 거주자 여부를 확인한다.
     * @return 모든 거주자가 플레이 되었을 경우 true를 반환한다.
     */
    protected boolean isCompletedPlayed() {
        return this.residents.stream()
                .allMatch(Resident::isPlayed);
    }

    /**
     * 플레이 여부를 초기화한다.
     */
    protected void initPlayed() {
        this.residents.stream()
            .forEach(resident -> resident.setPlayed(false));
    }

    /**
     * 플레이하지 않은 임의의 거주자 한명을 플레이 상태로 변경한다.
     */
    protected void play() {
        this.residents.stream()
                .filter(resident -> !resident.isPlayed() && resident.getResidentType() == ResidentType.ADULT)
                .findFirst()
                .orElseThrow(ServerError::new)
                .setPlayed(true);
    }

    /**
     * 모든 아이를 성장시킨다.
     */
    protected void growUpChild() {
        this.residents.stream()
                .filter(resident -> resident.getResidentType() == ResidentType.CHILD)
                .forEach(Resident::growUpChild);
    }


    /**
     * 먹여살리기 단계에 필요한 총 음식 요구사항 계산한다.
     * @return 총 음식 요구사항 값
     */
    public int calculateFoodNeeds() {
        return this.residents.stream()
                .mapToInt(resident -> resident.getResidentType() == ResidentType.ADULT ? 2 : 1)
                .sum();
    }

    /**
     * 거주자가 없는 빈방 여부를 확인한다.
     * @return 거주자가 없을 경우 true를 반환한다.
     */
    @JsonIgnore
    public boolean isEmptyRoom() {
        return this.residents.size() == 0;
    }

    public boolean addPet(AnimalType animalType){
        if (!isPetRoom) throw new IllegalRequestException("펫이 없는 집입니다.");
        return petRoom.addPet(animalType);
    }

    public AnimalType removePet() {
        if (!isPetRoom) throw new IllegalRequestException("펫이 없는 집입니다.");
        return petRoom.removePet();
    }

    @JsonIgnore
    public AnimalType getPet() {
        if (!isPetRoom) throw new IllegalRequestException("펫이 없는 집입니다.");
        return petRoom.getAnimalType();
    }
}
