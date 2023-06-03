package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import lombok.Getter;

/**
 * 기본 주설비 카드
 */
@Getter
public abstract class DefaultMajorCard implements MajorCard{
    protected final CardType cardType = CardType.MAJOR;
    protected final Long cardID;
    protected final int bonusPoint;
    protected final ResourceStruct[] ingredients;
    protected final String name;
    protected final String description;

    protected DefaultMajorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, String name, String description) {
        this.cardID = cardID;
        this.bonusPoint = bonusPoint;
        this.ingredients = ingredients;
        this.name = name;
        this.description = description;
    }

    /**
     * 플레이어가 카드를 가져가기에 충분한 자원을 가지고 있는지 확인
     * @param player 확인할 플레이어
     * @return 확인 결과
     */
    @Override
    public boolean checkPrerequisites(Player player){
        boolean result = true;
        for (ResourceStruct ingredient : ingredients){
            result &= player.getResource(ingredient.getResource()) >= ingredient.getCount();
        }
        return result;
    }

    /**
     * 카드를 가져간다.
     * 카드를 가져가는데 필요한 자원만큼 player 의 자원을 가져간다.
     * @param player 카드를 가져갈 플레이어
     */
    @Override
    public void place(Player player) {
        if (!checkPrerequisites(player)) throw new IllegalRequestException("전제조건 미달성");
        for (ResourceStruct ingredient : ingredients){
            player.useResource(ingredient.getResource(), ingredient.getCount());
        }
        player.addMajorCard(cardID);
    }
}