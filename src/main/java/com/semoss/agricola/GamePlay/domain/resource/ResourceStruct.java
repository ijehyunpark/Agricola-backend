package com.semoss.agricola.GamePlay.domain.resource;

import com.semoss.agricola.GamePlay.domain.resource.Resource;
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
    private Resource resource;
    private int count;
}
