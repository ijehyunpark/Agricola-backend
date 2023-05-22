package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 시작 플레이어 되기 그리고/또는 보조 설비 1개 놓기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action2 extends DefaultAction {
    public Action2() {
        super(ActionName.ACTION2, 0);

        // 2-1. 시작 플레이어 되기
        addAction(GetStartingPositionAction.builder().build(), DoType.ANDOR);

        // 2-1. 보조 설비 1개 놓기
        addAction(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build(), DoType.FINISH);

        log.debug("ACTION2 생성되었습니다: " + this.hashCode());
    }
}
