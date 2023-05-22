package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 돼지 시장: 누적 돼지 1개
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action18 extends DefaultAction {
    public Action18() {
        super(ActionName.ACTION18, 3);

        addAction(buildSimpleStackAction(AnimalType.WILD_BOAR, 1), DoType.FINISH);

        log.debug("ACTION18 생성되었습니다: " + this.hashCode());
    }
}
