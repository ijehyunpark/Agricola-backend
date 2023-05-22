package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BuildSimpleAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 밭 1개 일구기
 */
@Component
@Log4j2
public class Action5 extends DefaultAction {
    @Autowired
    public Action5(@Qualifier("buildFarmAction") BuildSimpleAction buildFarmAction) {
        super(ActionName.ACTION5, 0);

        addAction(buildFarmAction, DoType.FINISH);

        log.debug("ACTION5 생성되었습니다: " + this.hashCode());
    }
}
