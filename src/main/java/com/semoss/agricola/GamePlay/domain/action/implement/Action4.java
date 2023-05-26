package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.PlaceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 직업 1개 놓기
 */
@Component
@Log4j2
public class Action4 extends DefaultAction {
    @Autowired
    public Action4(@Qualifier("placeOccupationCardAction") PlaceAction placeOccupationCardAction) {
        super(ActionName.ACTION4, 0);

        addAction(placeOccupationCardAction, DoType.FINISH);

        log.debug("ACTION4 생성되었습니다: " + this.hashCode());
    }
}
