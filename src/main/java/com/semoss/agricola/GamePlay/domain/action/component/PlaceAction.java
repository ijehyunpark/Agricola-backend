package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * Card 내려놓기 행동
 */
@Getter
public class PlaceAction implements Action {
    private final ActionType actionType = ActionType.PLACE;
    private final CardType placeType;

    @Builder
    public PlaceAction(CardType cardType){
        this.placeType = cardType;
    }

    /**
     * 플레이어가 카드를 내려놓는다.
     * @param player player who place card
     * @param card card
     * @return result
     */
    public void runAction(Player player, Card card){
        if(!(card.getCardType() == this.placeType))
            throw new RuntimeException("허용되지 않은 카드 타입입니다.");

        // 해당 card의 소유주가 있는지 확인
        if (card.getCardType() == CardType.MAJOR && player.getGame().getCardDictionary().getOwner(card.getCardID()) != null)
            throw new RuntimeException("이미 다른 플레이어가 사용한 주설비 카드입니다.");

        // 해당 card의 전제조건 및 비용을 만족하는지 확인
        if (!card.checkPrerequisites(player))
            throw new RuntimeException("카드 내려놓기 전제조건 비용 불만족");

        // 해당 card의 소유주를 player로 변경
        // card.useResource 에서 재료 사용 및 소유자 이전을 모두 수행
        card.place(player);
    }
}
