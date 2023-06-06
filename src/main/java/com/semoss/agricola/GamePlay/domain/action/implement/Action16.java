package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 서부 채석장: 누적 돌 1개
 */
@Component
@Log4j2
public class Action16 extends DefaultAction {
    @Autowired
    public Action16(@Qualifier("westernQuarryAction") StackResourceAction westernQuarryAction) {
        super(ActionName.ACTION16, 2);

        addAction(westernQuarryAction, DoType.FINISH);
    }
}
