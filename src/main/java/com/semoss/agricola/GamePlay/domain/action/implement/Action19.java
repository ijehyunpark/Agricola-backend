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
 * 채소 종자: 채소 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action19 extends DefaultAction {
    public Action19() {
        super(ActionName.ACTION19, 3);

        addAction(BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.VEGETABLE)
                        .count(1)
                        .build())
                .build(), DoType.FINISH);

        log.debug("ACTION19 생성되었습니다: " + this.hashCode());
    }
}
