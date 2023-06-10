package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackAnimalAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 양 시장 (누적 양 1개)장
 */
@Component
@Log4j2
public class Action11 extends DefaultAction {
    @Autowired
    public Action11(@Qualifier("sheepMarketAction") StackAnimalAction sheepMarketAction) {
        super(ActionName.ACTION11, 1);

        addAction(sheepMarketAction, DoType.FINISH);

    }
}
