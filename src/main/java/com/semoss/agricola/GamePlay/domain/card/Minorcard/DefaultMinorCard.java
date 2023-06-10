package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import lombok.Getter;

/**
 * 기본 보조 설비 카드
 */
@Getter
public abstract class DefaultMinorCard implements MinorCard {
    protected final CardType cardType = CardType.MINOR;
    protected final Long cardID;
    protected final int bonusPoint;
    protected final ResourceStruct[] ingredients;
    // 전제조건으로 필요한 카드 종류와 필드에 깔아둔 최소 개수
    protected final CardType preconditionCardType;
    protected final int minCardNum;
    protected final String name;
    protected final String description;

    protected DefaultMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description) {
        this.cardID = cardID;
        this.bonusPoint = bonusPoint;
        this.ingredients = ingredients;
        this.preconditionCardType = preconditionCardType;
        this.minCardNum = minCardNum;
        this.name = name;
        this.description = description;
    }

    /**
     * 플레이어가 카드를 가져가기에 충분한 자원을 가지고 있는지 확인
     * @param player 확인할 플레이어
     * @return 확인 결과
     */
    @Override
    public boolean checkPrerequisites(Player player, CardDictionary cardDictionary){
        boolean result = true;
        for (ResourceStruct ingredient : ingredients){
            result &= player.getResource(ingredient.getResource()) >= ingredient.getCount();
        }
        if (preconditionCardType == null) return result;
        return result && (cardDictionary.getCards(player).size() >= minCardNum);
    }

    /**
     * 카드를 가져간다.
     * 카드를 가져가는데 필요한 자원만큼 player 의 자원을 가져간다.
     * @param player 카드를 가져갈 플레이어
     */
    @Override
    public void place(Player player, CardDictionary cardDictionary, int round) {
        if (!checkPrerequisites(player, cardDictionary)) throw new IllegalRequestException("전제조건 미달성");
        for (ResourceStruct ingredient : ingredients){
            player.useResource(ingredient.getResource(), ingredient.getCount());
        }

        if (!cardDictionary.getCardInPlayerHand(player).contains(this)) throw new IllegalRequestException("해당 보조설비카드는 이 플레이어의 카드가 아닙니다.");
        cardDictionary.place(player, this);
    }
}
