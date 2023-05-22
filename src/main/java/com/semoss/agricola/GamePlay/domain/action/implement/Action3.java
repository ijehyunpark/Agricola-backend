package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BasicAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 곡식 1개 가져가기
 */
@Component
@Log4j2
public class Action3 extends DefaultAction {
    @Autowired
    public Action3(@Qualifier("getGrain1Action") BasicAction getGrain1Action) {
        super(ActionName.ACTION3, 0);

        addAction(getGrain1Action, DoType.FINISH);

        log.debug("ACTION3 생성되었습니다: " + this.hashCode());
    }
}
