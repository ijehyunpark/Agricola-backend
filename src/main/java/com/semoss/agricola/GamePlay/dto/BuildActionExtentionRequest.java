package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildActionExtentionRequest {
    private int y;
    private int x;
    private FieldType fieldType;
}
