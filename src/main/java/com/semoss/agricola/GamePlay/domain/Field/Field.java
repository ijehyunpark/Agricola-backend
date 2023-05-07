package com.semoss.agricola.GamePlay.domain.Field;

import com.semoss.agricola.GamePlay.domain.ResourceType;

public interface Field {
    FieldType fieldType = FieldType.EMPTY;
    ResourceType resourceType = ResourceType.EMPTY;
    int numberOfResource = 0;

    FieldType getFieldType();
    ResourceType getResourceType();
    int getNumberOfResource();
}
