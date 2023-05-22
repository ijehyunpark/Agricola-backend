package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 누적 갈대 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action9 extends DefaultAction {
    public Action9() {
        super(EventName.ACTION9, 0);

        addAction(buildSimpleStackAction(ResourceType.REED, 1), DoType.FINISH);
        
        log.debug("ACTION9 생성되었습니다: " + this.hashCode());
    }
}
