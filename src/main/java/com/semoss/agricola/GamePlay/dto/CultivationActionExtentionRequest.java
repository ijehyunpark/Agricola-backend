package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CultivationActionExtentionRequest {
    private int y;
    private int x;
    private ResourceType resourceType;
}
