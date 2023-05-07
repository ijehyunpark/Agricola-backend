package com.semoss.agricola.GamePlay.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlayerResource {
    private int wood; // 나무
    private int clay; // 흙
    private int stone; // 돌
    private int reed; // 갈대
    private int grain; // 곡식
    private int vegetable; // 채소
    private int food; // 음식 토큰
    private int begging; // 구걸 토큰
    private int wildBoar; // 돼지
    private int sheep; // 양
    private int cattle; // 소

    @Builder
    public PlayerResource(boolean isStartingPlayer) {
        this.wood = 0;
        this.clay = 0;
        this.stone = 0;
        this.reed = 0;
        this.grain = 0;
        this.vegetable = 0;
        this.food = isStartingPlayer ? 2 : 3;
        this.begging = 0;
        this.wildBoar = 0;
        this.sheep = 0;
        this.cattle = 0;
    }
}
