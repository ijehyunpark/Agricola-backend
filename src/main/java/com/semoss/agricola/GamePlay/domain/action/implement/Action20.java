package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.StackAnimalAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 소 시장: 누적 소 1개
 */
@Component
@Log4j2
public class Action20 extends DefaultAction {
    @Autowired
    public Action20(@Qualifier("cattleMarketAction") StackAnimalAction cattleMarketAction) {
        super(ActionName.ACTION20, 4);

        addAction(cattleMarketAction, DoType.FINISH);

        log.debug("ACTION20 생성되었습니다: " + this.hashCode());
    }
}
