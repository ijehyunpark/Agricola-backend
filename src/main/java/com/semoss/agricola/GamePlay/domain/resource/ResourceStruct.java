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
public class ResourceStruct implements ResourceStructInterface {
    private ResourceType resource;
    private int count;


    @Override
    public String getName() {
        return resource == null ? null : resource.getName();
    }

    public void addResource(int count) {
        this.count += count;
    }

    @Override
    public boolean isResource() {
        return true;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    public void subResource(int count) { this.count -= count; }
}
