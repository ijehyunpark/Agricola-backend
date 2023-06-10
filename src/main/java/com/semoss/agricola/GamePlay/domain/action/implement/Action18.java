package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackAnimalAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 돼지 시장: 누적 돼지 1개
 */
@Component
@Log4j2
public class Action18 extends DefaultAction {
    @Autowired
    public Action18(@Qualifier("wildBoarMarketAction") StackAnimalAction wildBoarMarketAction) {
        super(ActionName.ACTION18, 3);

        addAction(wildBoarMarketAction, DoType.FINISH);
    }
}
