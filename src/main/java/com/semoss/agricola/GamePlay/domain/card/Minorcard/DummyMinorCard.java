package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 더미 보조설비 카드 입니다.
 * 구걸카드 1장 획득
 */
@Getter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class DummyMinorCard extends DefaultMinorCard {
    @JsonIgnore
    private final ResourceStruct bonusResource = ResourceStruct.builder().resource(ResourceType.BEGGING).count(1).build();
    private static long DUMMY_MINOR_NEXT_ID = 10000000000L;

    public DummyMinorCard() {
        super(DUMMY_MINOR_NEXT_ID++, 0, new ResourceStruct[]{}, null, 0, "더미 보조설비", "구걸토큰 1개를 획득합니다.");
        log.debug("더미 카드 생성 :" + this.hashCode());
    }


    @Override
    public void place(Player player, CardDictionary CardDictionary, int round) {
        super.place(player, CardDictionary, round);
        player.addResource(bonusResource);
    }
}
