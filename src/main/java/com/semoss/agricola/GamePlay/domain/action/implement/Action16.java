package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 서부 채석장: 누적 돌 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action16 extends DefaultAction {
    public Action16() {
        super(ActionName.ACTION16, 2);

        addAction(buildSimpleStackAction(ResourceType.STONE, 1), DoType.FINISH);

        log.debug("ACTION16 생성되었습니다: " + this.hashCode());
    }
}
