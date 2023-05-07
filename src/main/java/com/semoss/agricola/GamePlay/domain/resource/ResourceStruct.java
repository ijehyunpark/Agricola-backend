package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;
import lombok.Getter;

/**
 * 아그리콜라 자원과 해당 개수
 */
@Getter
@Builder
public class ResourceStruct {
    private Resource resource;
    private int count;

    public void addResource(int count){
        if(this.count + count < 0)
            throw new IllegalStateException("자원의 상태는 음수가 될 수 없습니다.");

        // TODO: 자원 최대 개수에 따른 예외처리

        this.count += count;
    }
}
