package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.component.BuildFenceAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 울타리 치기
 */
@Component
@Log4j2
public class Action12 extends DefaultAction {
    @Autowired
    public Action12(BuildFenceAction buildFenceAction) {
        super(ActionName.ACTION12, 1);

        addAction(buildFenceAction, DoType.FINISH);
    }
}
