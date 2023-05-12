package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Barn implements Field {
    private FieldType fieldType;
    private ResourceType animalType = ResourceType.EMPTY;
    private int animalNum = 0;
    private boolean isStable = false;
    private int capacity;

    /**
     * capacity 1 when there is only a stable, and capacity 2 when surrounded by a fence.
     * @param fieldType stable(just 1 stable on field) or barn(fenced without a stable)
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
    }

    /**
     * Add stable to the barn.
     * @return method was successful or not.
     */
    public boolean addStable(){
        if (isStable) return false;

        isStable = true;
        capacity = capacity * 2;
        return true;
    }

    /**
     * Add animals to the barn.
     * @param animalType type of animal to add
     * @param num the number of animal to add
     * @return the number of newly accepted animals to the barn.
     */
    public int addAnimal(ResourceType animalType, int num){
        if (num < 1) return 0;
        if (this.animalType == ResourceType.EMPTY) this.animalType = animalType;
        if (this.animalType != animalType) return 0;

        num = Integer.min(capacity - animalNum,num);
        animalNum = animalNum + num;

        return num;
    }

    /**
     * Remove animals from the barn.
     * @param num the number of animal to remove
     * @return the number of animals removed from the barn.
     */
    public int removeAnimal(int num){
        if (animalType == ResourceType.EMPTY) return 0;

        num = Integer.min(animalNum,num);
        animalNum = animalNum - num;
        if (animalNum == 0) animalType = ResourceType.EMPTY;

        return num;
    }

    /**
     * Remove all animals from the barn.
     * @return the number of animals removed from the barn.
     */
    public int removeAllAnimals(){
        this.animalType = ResourceType.EMPTY;
        int num = animalNum;
        animalNum = 0;
        return num;
    }

    /**
     * set barn's capacity. just for barn not stable. 2 times each stable.
     * @param numberOfStable the number of stables in the entire area surrounded by same fences
     * @return method was successful or not.
     */
    public boolean setCapacity(int numberOfStable) {
        if (numberOfStable < 1) return false;
        if (fieldType == FieldType.STABLE) fieldType = FieldType.BARN;
        this.addStable();
        capacity = (int) Math.pow(2,(double)numberOfStable+1);
        return true;
    }

    public boolean isStable() {
        return isStable;
    }

    public ResourceType getAnimalType() {
        return animalType;
    }

    public int getAnimalNum() {
        return animalNum;
    }

}
