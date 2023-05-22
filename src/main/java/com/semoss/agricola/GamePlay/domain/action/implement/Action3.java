package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 곡식 1개 가져가기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action3 extends DefaultAction {
    public Action3() {
        super(EventName.ACTION3, 0);

        addAction(BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.GRAIN)
                        .count(1)
                        .build())
                .build(), DoType.FINISH);

        log.debug("ACTION3 생성되었습니다: " + this.hashCode());
    }
}
