package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.Action;
import com.semoss.agricola.GamePlay.domain.action.BasicAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 누적 나무 3개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action7 extends DefaultAction {
    public Action7() {
        super(EventName.ACTION7, 0);

        addAction(buildSimpleStackAction(ResourceType.WOOD, 3), DoType.FINISH);

        log.debug("ACTION7 생성되었습니다: " + this.hashCode());
    }
}
