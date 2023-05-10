package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;
import lombok.Setter;

public class Occupation implements Card {
    @Getter
    private CardType cardType;
    private String cardID;
    @Getter
    @Setter
    private Long owner;

    public Occupation(String id){
        cardType = CardType.OCCUPATION;
        cardID = id;
        owner = null;
    }

    @Override
    public boolean checkPrerequisites(Player player) {
        return true;
    }

    @Override
    public void useResource(Player player) {

    }
}
