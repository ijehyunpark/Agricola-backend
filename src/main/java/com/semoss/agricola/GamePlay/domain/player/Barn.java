package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.exception.AlreadyExistException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Barn implements Field {
    private FieldType fieldType;
    private final AnimalStruct animal;
    private boolean isStable = false;
    private int capacity;

    /**
     * 외양간만 있으면 수용량이 1, 헛간만 있으면 수용량이 2, 같은 범위내 외양간 하나당 수용량 2배
     * @param fieldType stable or barn (개발중에 Stable 과 barn 이 하나로 합쳐질수도있음)
     */
    @Builder
    public Barn(FieldType fieldType){
        this.fieldType = fieldType;
        if (fieldType == FieldType.STABLE){
            isStable = true;
            capacity = 1;
        } else {
            capacity = 2;
        }
        animal = AnimalStruct.builder()
                .animal(null)
                .count(0)
                .build();
    }

    /**
     * 헛간에 외양간 추가
     * @return method was successful or not.
     */
    public boolean addStable(){
        if (isStable) throw new AlreadyExistException("이미 외양간이 지어져 있습니다.");

        isStable = true;
        capacity = capacity * 2;
        return true;
    }

    /**
     * 동물 추가
     * @param animalType 추가할 동물 타입
     * @param num 추가할 동물 수
     * @return 이번 명령으로 수용한 동물 수
     */
    public int addAnimal(AnimalType animalType, int num){
        if (num < 1) return 0;
        if (animal.getAnimal() == null) animal.setAnimal(animalType);
        if (animal.getAnimal() != animalType) return 0;

        num = Integer.min(capacity - animal.getCount(),num);
        animal.addResource(num);

        return num;
    }

    /**
     * 동물 제거
     * @param num 제거할 동물 수
     * @return 제거된 동물 수
     */
    public int removeAnimal(int num){
        if (animal.getAnimal() == null) return 0;

        num = Integer.min(animal.getCount(),num);
        animal.subResource(num);
        if (animal.getCount() == 0) animal.setAnimal(null);

        return num;
    }

    /**
     * 모든 동물 제거
     * @return 제거된 동물 수
     */
    public int removeAllAnimals(){
        animal.setAnimal(null);
        int num = animal.getCount();
        animal.setCount(0);
        return num;
    }

    /**
     * 헛간의 수용량을 재설정 - 외양간에 사용시 헛간으로 변경
     * @param numberOfStable 현재 같은 공간에 있는 외양간 수
     */
    public void setCapacity(int numberOfStable) {
        if (numberOfStable < 1) return;
        if (fieldType == FieldType.STABLE) fieldType = FieldType.BARN;
        this.addStable();
        capacity = (int) Math.pow(2,(double)numberOfStable+1);
    }

    public boolean isStable() {
        return isStable;
    }

}
