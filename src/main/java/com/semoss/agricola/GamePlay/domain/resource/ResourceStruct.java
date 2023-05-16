package com.semoss.agricola.GamePlay.domain.resource;

import lombok.*;

/**
 * 아그리콜라 자원과 해당 개수
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResourceStruct {
    private ResourceType resource;
    private int count;

    public void addResource(int count) {
        this.count += count;
    }
}
