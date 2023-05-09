package com.semoss.agricola.GamePlay.domain.Field;

import com.semoss.agricola.GamePlay.domain.ResourceType;

public class Room implements Field{
    private FieldType fieldType;
    private ResourceType resourceType = ResourceType.EMPTY;
    private int numberOfResource = 0;

    public Room(){
        fieldType = FieldType.WOOD;
    }

    public Room(FieldType fieldType){
        this.fieldType = fieldType;
    }

    public void upgradeRoom(){
        if (fieldType == FieldType.WOOD) fieldType = FieldType.CLAY;
        else if (fieldType == FieldType.CLAY) fieldType = FieldType.STONE;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public FieldType getFieldType() {
        return fieldType;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public int getNumberOfResource() {
        return numberOfResource;
    }
}
