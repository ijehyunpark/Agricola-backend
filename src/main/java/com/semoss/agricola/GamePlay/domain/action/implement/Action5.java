package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 밭 1개 일구기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action5 extends DefaultAction {
    public Action5() {
        super(ActionName.ACTION5, 0);

        addAction(buildActionToTillingFarm(), DoType.FINISH);

        log.debug("ACTION5 생성되었습니다: " + this.hashCode());
    }
}
