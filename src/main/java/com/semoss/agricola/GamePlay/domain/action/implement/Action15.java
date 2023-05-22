package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import com.semoss.agricola.GamePlay.domain.action.RoomUpgradeAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 집 개조: 집 한번 고치기 그 후에 주요 설비 / 보조 설비 중 1개 내려놓기
 */
@Component
@Log4j2
public class Action15 extends DefaultAction {
    @Autowired
    public Action15(RoomUpgradeAction roomUpgradeAction, @Qualifier("placeMajorCardAction") PlaceAction placeMajorCardAction, @Qualifier("placeMinorCardAction") PlaceAction placeMinorCardAction) {
        super(ActionName.ACTION15, 2);

        addAction(roomUpgradeAction, DoType.ANDOR);
        addAction(placeMajorCardAction, DoType.OR);
        addAction(placeMinorCardAction, DoType.FINISH);

        log.debug("ACTION15 생성되었습니다: " + this.hashCode());
    }
}
