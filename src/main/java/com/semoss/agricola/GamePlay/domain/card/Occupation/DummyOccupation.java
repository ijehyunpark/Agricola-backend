package com.semoss.agricola.GamePlay.domain.card.Occupation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 더미 직업카드 입니다.
 */

@Getter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class DummyOccupation extends DefaultOccupation {
    private final int playerRequirement;

    private static long DUMMY_OCCUPATION_NEXT_ID = 100000000L;
    public DummyOccupation(@Value("${dummy.name}") String name,
                              @Value("${dummy.players}") Integer playerRequirement,
                              @Value("${dummy.description}") String description) {
        super(DUMMY_OCCUPATION_NEXT_ID++, name, description);
        log.debug("더미 직업 카드 생성: " + this.hashCode());
        this.playerRequirement = playerRequirement;
    }
}
