package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.IncreaseFamilyAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 급한 가족 늘리기: 빈 방이 없어도 가족 늘리기
 */
@Component
@Log4j2
public class Action23 extends DefaultAction {
    @Autowired
    public Action23(@Qualifier("growFamilyWithUrgencyAction") IncreaseFamilyAction growFamilyWithUrgencyAction) {
        super(ActionName.ACTION23, 5);

        addAction(growFamilyWithUrgencyAction, DoType.FINISH);
    }
}
