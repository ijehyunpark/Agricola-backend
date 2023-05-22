package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BasicAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 날품팔이 (식량 2개 가져가기)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action6 extends DefaultAction {
    public Action6() {
        super(ActionName.ACTION6, 0);

        addAction(BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(2)
                        .build())
                .build(), DoType.FINISH);

        log.debug("ACTION6 생성되었습니다: " + this.hashCode());
    }
}
