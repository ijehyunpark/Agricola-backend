package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 씨 뿌리기 그리고/또는 빵 굽기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action14 extends DefaultAction {
    public Action14() {
        super(EventName.ACTION14, 1);

        addAction(CultivationAction.builder().build(), DoType.ANDOR);
        addAction(BakeAction.builder().build(), DoType.FINISH);

        log.debug("ACTION14 생성되었습니다: " + this.hashCode());
    }
}
