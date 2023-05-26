package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BakeAction;
import com.semoss.agricola.GamePlay.domain.action.CultivationAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 씨 뿌리기 그리고/또는 빵 굽기
 */
@Component
@Log4j2
public class Action14 extends DefaultAction {
    @Autowired
    public Action14(CultivationAction cultivationAction, BakeAction bakeAction) {
        super(ActionName.ACTION14, 1);

        addAction(cultivationAction, DoType.ANDOR);
        addAction(bakeAction, DoType.FINISH);

        log.debug("ACTION14 생성되었습니다: " + this.hashCode());
    }
}
