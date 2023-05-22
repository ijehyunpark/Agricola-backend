package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.BuildSimpleAction;
import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 만들기 그리고/또는 외양간 짓기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action1 extends DefaultAction {
    public Action1() {
        super(ActionName.ACTION1, 0);

        // 1-1. 방만들기
        List<ResourceStruct> requirements1 = new ArrayList<>();
        requirements1.add(ResourceStruct.builder()
                .resource(ResourceType.WOOD)
                .count(5)
                .build());
        requirements1.add(ResourceStruct.builder()
                .resource(ResourceType.GRAIN)
                .count(2)
                .build());
        addAction(BuildSimpleAction.builder()
                .fieldType(FieldType.ROOM)
                .buildMaxCount(-1)
                .requirements(requirements1)
                .build(), DoType.ANDOR);

        // 1-2 외양간 짓기
        List<ResourceStruct> requirements2 = new ArrayList<>();
        requirements2.add(ResourceStruct.builder()
                .resource(ResourceType.WOOD)
                .count(2)
                .build());
        addAction(BuildSimpleAction.builder()
                .fieldType(FieldType.STABLE)
                .buildMaxCount(-1)
                .requirements(requirements2)
                .build(), DoType.FINISH);

        log.debug("ACTION1 생성되었습니다: " + this.hashCode());
    }
}
