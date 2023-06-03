package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

public interface StackResource extends Card {
    ResourceStruct getStackResource();
    int[] getRounds();
    boolean isStaticRound();
}
