package com.semoss.agricola.GamePlay.domain.card.Majorcard;

/**
 * 빵굽기시 행동
 */
public interface    BakeTrigger extends MajorCard{
    int getBakeEfficiency(); // 빵굽기 효율 == 곡물 하나당 음식 개수
}
