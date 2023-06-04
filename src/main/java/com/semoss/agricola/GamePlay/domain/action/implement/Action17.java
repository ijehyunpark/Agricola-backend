package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.IncreaseFamilyAction;
import com.semoss.agricola.GamePlay.domain.action.component.PlaceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 급하지 않은 가족 늘리기: 빈 방이 있어야만 가족 늘리기 그 후에 보조 설비 1개
 */
@Component
@Log4j2
public class Action17 extends DefaultAction {
    @Autowired
    public Action17(@Qualifier("growFamilyWithoutUrgencyAction") IncreaseFamilyAction growFamilyWithoutUrgencyAction, @Qualifier("placeMinorCardAction") PlaceAction placeMinorCardAction) {
        super(ActionName.ACTION17, 2);

        addAction(growFamilyWithoutUrgencyAction, DoType.AFTER);
        addAction(placeMinorCardAction, DoType.FINISH);
    }
}
