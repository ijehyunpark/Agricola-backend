package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 누적 나무 1개
 */
@Component
@Log4j2
public class Action25 extends DefaultAction {

    @Autowired
    public Action25(@Qualifier("stackWood1Action") StackResourceAction stackWood1Action) {
        super(ActionName.ACTION25, 0);

        addAction(stackWood1Action, DoType.FINISH);
    }
}
