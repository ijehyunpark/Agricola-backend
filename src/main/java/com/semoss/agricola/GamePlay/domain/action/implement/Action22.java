package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.component.BuildSimpleAction;
import com.semoss.agricola.GamePlay.domain.action.component.CultivationAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 밭 농사: 밭 하나 일구기 그리고/또는 씨 뿌리기
 */
@Component
@Log4j2
public class Action22 extends DefaultAction {
    @Autowired
    public Action22(@Qualifier("buildFarmAction") BuildSimpleAction buildFarmAction,
                    CultivationAction cultivationAction) {
        super(ActionName.ACTION22, 5);

        addAction(buildFarmAction, DoType.ANDOR);
        addAction(cultivationAction, DoType.FINISH);
    }
}
