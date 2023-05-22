package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 낚시 (누적 음식 1개)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action10 extends DefaultAction {
    public Action10() {
        super(EventName.ACTION10, 0);

        addAction(buildSimpleStackAction(ResourceType.FOOD, 1), DoType.FINISH);
        
        log.debug("ACTION10 생성되었습니다: " + this.hashCode());
    }
}
