package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 누적 흙 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action8 extends DefaultAction {
    public Action8() {
        super(EventName.ACTION8, 0);

        addAction(buildSimpleStackAction(ResourceType.STONE, 1), DoType.FINISH);
        
        log.debug("ACTION8 생성되었습니다: " + this.hashCode());
    }
}
