package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.StackResourceAction;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 누적 나무 3개
 */
@Component
@Log4j2
public class Action7 extends DefaultAction {
    @Autowired
    public Action7(@Qualifier("stackWood3Action") StackResourceAction stackWood3Action) {
        super(ActionName.ACTION7, 0);

        addAction(stackWood3Action, DoType.FINISH);

        log.debug("ACTION7 생성되었습니다: " + this.hashCode());
    }
}
