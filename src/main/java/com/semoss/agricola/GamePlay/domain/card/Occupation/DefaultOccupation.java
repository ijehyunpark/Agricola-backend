package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;

@Getter
public abstract class DefaultOccupation implements Occupation {
    private CardType cardType = CardType.OCCUPATION;

    @JsonProperty("playerId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
    private Player owner;

    @Override
    public void setOwner(Player player) {
        if(owner != null)
            throw new RuntimeException("이미 사용 중인 카드입니다.");
    }

}
