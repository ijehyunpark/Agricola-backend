package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.IncreaseFamily;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 급하지 않은 가족 늘리기: 빈 방이 있어야만 가족 늘리기 그 후에 보조 설비 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action17 extends DefaultAction {
    public Action17() {
        super(ActionName.ACTION17, 2);

        addAction(IncreaseFamily.builder()
                .precondition(true)
                .build(), DoType.After);
        addAction(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build(), DoType.FINISH);

        log.debug("ACTION17 생성되었습니다: " + this.hashCode());
    }
}
