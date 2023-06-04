package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 누적 나무 2개
 */
@Component
@Log4j2
public class Action26 extends DefaultAction {

    @Autowired
    public Action26(@Qualifier("stackWood2Action") StackResourceAction stackWood2Action) {
        super(ActionName.ACTION26, 0);

        addAction(stackWood2Action, DoType.FINISH);
    }
}
