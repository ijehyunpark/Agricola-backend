package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

/**
 * 라운드에 자원을 쌓아둡니다.
 */
public interface StackResource extends Card {
    ResourceStruct getStackResource();
    int[] getRounds();
    boolean isStaticRound();
}
