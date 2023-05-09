package com.semoss.agricola.GamePlay.domain.action;

import lombok.Getter;

@Getter
public class StackAction extends Action{
    private int resourceType;
    private int resourceNum;
}
