package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.component.BasicAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 날품팔이 (식량 2개 가져가기)
 */
@Component
@Log4j2
public class Action6 extends DefaultAction {
    @Autowired
    public Action6(@Qualifier("datallerAction") BasicAction datallerAction) {
        super(ActionName.ACTION6, 0);

        addAction(datallerAction, DoType.FINISH);

    }
}
