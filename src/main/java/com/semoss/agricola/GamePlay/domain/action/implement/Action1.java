package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BuildRoomAction;
import com.semoss.agricola.GamePlay.domain.action.BuildSimpleAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 방 만들기 그리고/또는 외양간 짓기
 */
@Component
@Log4j2
public class Action1 extends DefaultAction {
    @Autowired
    public Action1(BuildRoomAction buildRoomAction, @Qualifier("buildStableAction") BuildSimpleAction buildStableAction) {
        super(ActionName.ACTION1, 0);

        // 1-1. 방만들기
        addAction(buildRoomAction, DoType.ANDOR);

        // 1-2 외양간 짓기
        addAction(buildStableAction, DoType.FINISH);

        log.debug("ACTION1 생성되었습니다: " + this.hashCode());
    }
}
