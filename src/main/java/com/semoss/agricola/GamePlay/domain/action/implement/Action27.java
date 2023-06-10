package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.BasicAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 갈대 1개, 돌 1개, 식량 토큰 1개 획득
 */
@Component
@Log4j2
public class Action27 extends DefaultAction {

    @Autowired
    public Action27(@Qualifier("resourceMarketAction") BasicAction resourceMarketAction) {
        super(ActionName.ACTION27, 0);

        addAction(resourceMarketAction, DoType.FINISH);
    }
}
