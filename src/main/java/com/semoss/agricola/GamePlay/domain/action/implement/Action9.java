package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 누적 갈대 1개
 */
@Component
@Log4j2
public class Action9 extends DefaultAction {
    @Autowired
    public Action9(@Qualifier("stackReed1Action") StackResourceAction stackReed1Action) {
        super(ActionName.ACTION9, 0);

        addAction(stackReed1Action, DoType.FINISH);

    }
}
