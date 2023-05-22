package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 양 시장 (누적 양 1개)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action11 extends DefaultAction {
    public Action11() {
        super(ActionName.ACTION11, 1);

        addAction(buildSimpleStackAction(AnimalType.SHEEP, 1), DoType.FINISH);
        
        log.debug("ACTION11 생성되었습니다: " + this.hashCode());
    }
}
