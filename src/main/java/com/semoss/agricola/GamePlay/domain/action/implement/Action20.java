package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 소 시장: 누적 소 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action20 extends DefaultAction {
    public Action20() {
        super(ActionName.ACTION20, 4);

        addAction(buildSimpleStackAction(AnimalType.CATTLE, 1), DoType.FINISH);

        log.debug("ACTION20 생성되었습니다: " + this.hashCode());
    }
}
