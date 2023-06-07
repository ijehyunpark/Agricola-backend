package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 더미 보조설비 카드 입니다.
 * 구걸카드 1장 획득
 */
@Getter
@Log4j2
public class DummyMinorCard extends DefaultMinorCard {
    private final ResourceStruct bonusResource = ResourceStruct.builder().resource(ResourceType.BEGGING).count(1).build();
    private static Long nextID = 1001L;

    public DummyMinorCard() {
        super(nextID, 0, new ResourceStruct[]{}, null, 0, "더미 보조설비", "구걸토큰 1개를 획득합니다.");
        log.debug("더미 카드 생성 :" + this.hashCode());
        nextID += 2;
    }


    @Override
    public void place(Player player, CardDictionary CardDictionary, int round) {
        super.place(player, CardDictionary, round);
        player.addResource(bonusResource);
    }
}
