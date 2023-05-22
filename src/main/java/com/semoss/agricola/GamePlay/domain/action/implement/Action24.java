package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.RoomUpgradeAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 농장 개조: 집 한번 고치기 그 후에 울타리 치기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action24 extends DefaultAction {
    public Action24() {
        super(ActionName.ACTION24, 6);

        addAction(RoomUpgradeAction.builder().build(), DoType.ANDOR);
        addAction(buildActionToFence(), DoType.FINISH);

        log.debug("ACTION24 생성되었습니다: " + this.hashCode());
    }
}
