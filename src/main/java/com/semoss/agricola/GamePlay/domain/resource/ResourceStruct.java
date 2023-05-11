package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 아그리콜라 자원과 해당 개수
 */
@Getter
@Setter
@Builder
public class ResourceStruct {
    private ResourceType resource;
    private int count;
}
