package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.CultivationAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 밭 농사: 밭 하나 일구기 그리고/또는 씨 뿌리기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action22 extends DefaultAction {
    public Action22() {
        super(EventName.ACTION22, 5);

        addAction(buildActionToTillingFarm(), DoType.ANDOR);
        addAction(CultivationAction.builder().build(), DoType.FINISH);

        log.debug("ACTION22 생성되었습니다: " + this.hashCode());
    }
}
