package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 직업 1개 놓기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action4 extends DefaultAction {
    public Action4() {
        super(ActionName.ACTION4, 0);

        addAction(PlaceAction.builder()
                .cardType(CardType.OCCUPATION)
                .build(), DoType.FINISH);

        log.debug("ACTION4 생성되었습니다: " + this.hashCode());
    }
}
