package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BuildActionExtentionRequest {
    private int y;
    private int x;
}
