package com.semoss.agricola.GamePlay.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BuildFenceActionExtensionRequest {
    private int y;
    private int x;
    private boolean isCol;
}
