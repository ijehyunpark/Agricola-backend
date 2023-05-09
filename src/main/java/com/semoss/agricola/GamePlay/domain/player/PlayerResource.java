package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.Resource;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 플레이어가 가진 자원의 상태
 */
@Getter
public class PlayerResource {
    private Map<Resource, Integer> resources;

    @Builder
    public PlayerResource(boolean isStartingPlayer) {
        // 아그리콜라 자원 최초 설정
        resources = new HashMap<>();
        for (Resource resource : Resource.values()) {
            resources.put(resource, 0);
        }

        // 시작 플레이어는 2개, 다른 플레이어는 3개의 음식 토큰을 수령한다.
        addResource(Resource.FOOD, (isStartingPlayer ? 2 : 3));
    }

    public void addResource(Resource resource, int count) {
        resources.compute(resource, (key, value) -> value + count);
    }

    public void addResource(List<ResourceStruct> resources) {
        for (ResourceStruct resource : resources) {
            addResource(resource.getResource(), resource.getCount());
        }
    }
}
