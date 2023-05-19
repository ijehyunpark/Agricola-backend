package com.semoss.agricola.GamePlay.domain.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 아그리콜라 자원과 해당 개수
 */
@Getter
@Setter
@Builder
public class AnimalStruct {
    private AnimalType animal;
    private int count;

    public void addResource(int count) {
        this.count += count;
    }
    public void subResource(int count) { if (this.count - count < 0) throw new RuntimeException("동물 수는 음수가 될 수 없습니다.");
        this.count -= count;
    }
}
