package com.semoss.agricola.GamePlay.domain.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "count"})
public interface ResourceStructInterface {


    void addResource(int count);
    @JsonIgnore
    boolean isResource();
    @JsonIgnore
    boolean isAnimal();

    String getName();

    int getCount();
}
