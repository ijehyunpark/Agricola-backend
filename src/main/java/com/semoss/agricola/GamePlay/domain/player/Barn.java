package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Barn implements Field {
    private FieldType fieldType;
    private final ResourceStruct animal;
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
        animal = ResourceStruct.builder()
                .resource(ResourceType.EMPTY)
                .count(0)
                .build();
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
        if (animal.getResource() == ResourceType.EMPTY) animal.setResource(animalType);
        if (animal.getResource() != animalType) return 0;

        num = Integer.min(capacity - animal.getCount(),num);
        animal.addResource(num);

        return num;
    }

    /**
     * Remove animals from the barn.
     * @param num the number of animal to remove
     * @return the number of animals removed from the barn.
     */
    public int removeAnimal(int num){
        if (animal.getResource() == ResourceType.EMPTY) return 0;

        num = Integer.min(animal.getCount(),num);
        animal.subResource(num);
        if (animal.getCount() == 0) animal.setResource(ResourceType.EMPTY);

        return num;
    }

    /**
     * Remove all animals from the barn.
     * @return the number of animals removed from the barn.
     */
    public int removeAllAnimals(){
        animal.setResource(ResourceType.EMPTY);
        int num = animal.getCount();
        animal.setCount(0);
        return num;
    }

    /**
     * set barn's capacity. just for barn not stable. 2 times each stable.
     * @param numberOfStable the number of stables in the entire area surrounded by same fences
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
