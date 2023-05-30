package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.GetStartingPositionAction;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 시작 플레이어 되기 그리고/또는 보조 설비 1개 놓기
 */
@Component
@Log4j2
public class Action2 extends DefaultAction {
    @Autowired
    public Action2(GetStartingPositionAction getStartingPositionAction, @Qualifier("placeMinorCardAction") PlaceAction placeMinorCardAction) {
        super(ActionName.ACTION2, 0);

        // 2-1. 시작 플레이어 되기
        addAction(getStartingPositionAction, DoType.AFTER);

        // 2-1. 보조 설비 1개 놓기
        addAction(placeMinorCardAction, DoType.FINISH);

        log.debug("ACTION2 생성되었습니다: " + this.hashCode());
    }
}
