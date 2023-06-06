package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;

/**
 * 기본 직업 카드
 */
@Getter
public abstract class DefaultOccupation implements Occupation {
    private final CardType cardType = CardType.OCCUPATION;
    private final int bonusPoint = 0;
    protected final Long cardID;
    private final String name;
    private final String description;

    protected DefaultOccupation(Long cardID, String name, String description) {
        this.cardID = cardID;
        this.name = name;
        this.description = description;
    }

    @Override
    public void place(Player player) {
        player.addOccupations(this);
    }

    @Override
    public boolean checkPrerequisites(Player player) {
        return true;
    }

}
