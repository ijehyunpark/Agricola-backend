package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 집 개조: 집 한번 고치기 그 후에 주요 설비 / 보조 설비 중 1개 내려놓기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action15 extends DefaultAction {
    public Action15() {
        super(ActionName.ACTION15, 2);

        addAction(RoomUpgradeAction.builder().build(), DoType.ANDOR);
        addAction(PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build(), DoType.OR);
        addAction(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build(), DoType.FINISH);

        log.debug("ACTION15 생성되었습니다: " + this.hashCode());
    }
}
