package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * TODO: 카드 놓기
 * card마다 전제 조건 비용이 다르기에 해당 객체에서 비용을 저장하지 말고 외부 카드를 받아와서 처리하는게 좋아 보인다.
 */
public class PlaceAction implements SimpleAction {
    @Getter
    private final ActionType actionType = ActionType.PLACE;

    @Builder
    public PlaceAction(){
    }

//    /**
//     * Are there enough resources for the action. Doesn't check additional resources required place cards
//     * @param player player who check precondition
//     * @return result
//     */
    @Override
    public boolean checkPrecondition(Player player) {
        return true;
    }

    @Override
    public boolean runAction(Player player) {
        return true;
    }

    /**
     * player place own card(occupation,minor) or get major card
     * @param player player who place card
     * @param cardID card ID
     * @return result
     */
    public boolean runAction(Player player, String cardID){
        Card card = CardDictionary.cardDict.get((cardID));
//        if(card.getCardType() != cardType)
//            return false;
//        if(!card.checkPrerequisites(player))
//            return false;
//        card.useResource(player);
//        if(cardType == CardType.MAJOR)
//            return player.getMajorCard(cardID);
//        return player.placeCard(cardID);

        // 해당 card의 소유주가 있는지 확인

        // 해당 card의 전제조건 및 비용을 만족하는지 확인

        // 해당 card의 소유주를 player로 변경

        return true;
    }
}
