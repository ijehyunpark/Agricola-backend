package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.PlaceAction;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 교습
 * 직업 내려놓기
 * 직업 당 1 식량 토큰 필요, 플레이어마다 첫 직업은 무료
 */
@Component
@Log4j2
public class Action4 extends DefaultAction {
    @Autowired
    public Action4(@Qualifier("placeOccupationCardAction") PlaceAction placeOccupationCardAction) {
        super(ActionName.ACTION4, 0);

        addAction(placeOccupationCardAction, DoType.FINISH);
    }

    @Override
    public History runAction(Player player, Player startingPlayer, int round, List<AgricolaActionRequest.ActionFormat> acts, List<ResourceStructInterface> stacks, CardDictionary cardDictionary, History history) {
        // 입력 행동값 검증
        super.isCollectRequest(acts);

        // TODO: Memento로 실패 시 롤백

        PlaceAction placeAction = (PlaceAction) getActions().get(0);
        Long cardId = (Long) acts.get(0).getActs();

        // 플레이어가 내려놓은 직업 카드 계산
        int occupationCount = 0;
        List<Card> cards = cardDictionary.findCardsByOwner(player);
        for (Card component : cards) {
            if(component.getCardType() == CardType.OCCUPATION){
                occupationCount++;
            }
        }

        // 식량 토큰 소모
        if(occupationCount >= 1){
            player.useResource(ResourceType.FOOD, 1);
        }

        placeAction.runAction(player, cardId, cardDictionary, round);
        history.writeActionType(placeAction.getActionType(), 1);
        return history;
    }
}