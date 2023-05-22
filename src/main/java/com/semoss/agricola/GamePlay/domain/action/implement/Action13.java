package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 주요 설비나 보조 설비 1개 놓기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action13 extends DefaultAction {
    public Action13() {
        super(EventName.ACTION13, 1);

        addAction(PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build(), DoType.OR);

        addAction(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build(), DoType.FINISH);
        
        log.debug("ACTION13 생성되었습니다: " + this.hashCode());
    }
}
