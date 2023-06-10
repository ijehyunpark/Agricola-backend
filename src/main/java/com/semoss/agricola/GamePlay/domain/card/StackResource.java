package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

/**
 * 라운드에 자원을 쌓아둡니다.
 */
public interface StackResource extends Card {
    @JsonIgnore
    ResourceStruct getStackResource();
    @JsonIgnore
    int[] getRounds();
    @JsonIgnore
    boolean isStaticRound();
}
