package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 점토 채굴장 (누적 흙 2개)
 */
@Component
@Log4j2
public class Action30 extends DefaultAction {

    @Autowired
    public Action30(@Qualifier("flowExtremesAction") StackResourceAction flowExtremesAction) {
        super(ActionName.ACTION30, 0);

        addAction(flowExtremesAction, DoType.FINISH);
    }
}
