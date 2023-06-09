package com.semoss.agricola.GamePlay.domain.resource;

import com.semoss.agricola.GamePlay.exception.MinusResourceException;
import lombok.*;

/**
 * 아그리콜라 자원과 해당 개수
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnimalStruct implements ResourceStructInterface {
    private AnimalType animal;
    private int count;

    public void addResource(int count) {
        this.count += count;
    }

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public String getName() {
        return animal == null ? null : animal.getName();
    }

    public void subResource(int count) { if (this.count - count < 0) throw new MinusResourceException("동물 수는 음수가 될 수 없습니다.");
        this.count -= count;
    }
}
