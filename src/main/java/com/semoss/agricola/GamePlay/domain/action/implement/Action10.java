package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 낚시 (누적 음식 1개)
 */
@Component
@Log4j2
public class Action10 extends DefaultAction {
    @Autowired
    public Action10(StackResourceAction fishingAction) {
        super(ActionName.ACTION10, 0);

        addAction(fishingAction, DoType.FINISH);
        
        log.debug("ACTION10 생성되었습니다: " + this.hashCode());
    }
}
