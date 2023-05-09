package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.Seed;
import lombok.Getter;

@Getter
public class Farm implements Field {
    private Seed seed;

    public Farm() {
        seed = Seed.seedBuilder()
                .resource(null)
                .count(0)
                .build();
    }

    /**
     * 수확 시간
     * @return
     */
    protected ResourceStruct harvest() {
        return seed.harvest();
    }
}
