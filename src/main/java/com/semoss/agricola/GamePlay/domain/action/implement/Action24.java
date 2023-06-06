package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.component.BuildFenceAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.component.RoomUpgradeAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 농장 개조: 집 한번 고치기 그 후에 울타리 치기
 */
@Component
@Log4j2
public class Action24 extends DefaultAction {
    @Autowired
    public Action24(RoomUpgradeAction roomUpgradeAction, BuildFenceAction buildFenceAction) {
        super(ActionName.ACTION24, 6);

        addAction(roomUpgradeAction, DoType.ANDOR);
        addAction(buildFenceAction, DoType.FINISH);
    }
}
