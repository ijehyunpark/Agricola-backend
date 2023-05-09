package com.semoss.agricola.GamePlay.domain.action;

import lombok.Getter;

@Getter
public class BuildAction extends Action{
    private Object resource;
    private int fieldType;
    private int fieldNum;
}
