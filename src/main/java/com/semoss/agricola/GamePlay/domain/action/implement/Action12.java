package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.DoType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 울타리 치기
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class Action12 extends DefaultAction {
    public Action12() {
        super(ActionName.ACTION12, 1);

        addAction(buildActionToFence(), DoType.FINISH);
        
        log.debug("ACTION12 생성되었습니다: " + this.hashCode());
    }
}
