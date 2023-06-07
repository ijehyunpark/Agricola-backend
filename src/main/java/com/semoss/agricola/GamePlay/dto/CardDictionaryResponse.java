package com.semoss.agricola.GamePlay.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CardDictionaryResponse {
    private Long playerId;
    private Long cardId;
}
