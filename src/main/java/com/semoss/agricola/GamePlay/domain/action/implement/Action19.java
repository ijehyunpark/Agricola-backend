package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BasicAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 채소 종자: 채소 1개
 */
@Component
@Log4j2
public class Action19 extends DefaultAction {
    @Autowired
    public Action19(@Qualifier("getVegetable1Action") BasicAction getVegetable1Action) {
        super(ActionName.ACTION19, 3);

        addAction(getVegetable1Action, DoType.FINISH);

        log.debug("ACTION19 생성되었습니다: " + this.hashCode());
    }
}
