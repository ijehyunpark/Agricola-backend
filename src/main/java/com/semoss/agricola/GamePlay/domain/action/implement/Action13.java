package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 주요 설비나 보조 설비 1개 놓기
 */
@Component
@Log4j2
public class Action13 extends DefaultAction {
    @Autowired
    public Action13(@Qualifier("placeMajorCardAction") PlaceAction placeMajorCardAction, @Qualifier("placeMinorCardAction") PlaceAction placeMinorCardAction) {
        super(ActionName.ACTION13, 1);

        addAction(placeMajorCardAction, DoType.OR);
        addAction(placeMinorCardAction, DoType.FINISH);
        
        log.debug("ACTION13 생성되었습니다: " + this.hashCode());
    }
}
