package com.semoss.agricola.GamePlay.domain.action;

import lombok.Getter;

import java.util.List;

@Getter
public class Event {
    private List<Action> actions;
    private boolean usable;
    private int doType;
    private boolean face;
}
