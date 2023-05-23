package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 누적 흙 1개
 */
@Component
@Log4j2
public class Action8 extends DefaultAction {
    @Autowired
    public Action8(@Qualifier("stackClay1Action") StackResourceAction stackClay1Action) {
        super(ActionName.ACTION8, 0);

        addAction(stackClay1Action, DoType.FINISH);
        
        log.debug("ACTION8 생성되었습니다: " + this.hashCode());
    }
}
