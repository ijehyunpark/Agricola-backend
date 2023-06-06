package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.action.component.Action;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 추가적인 입력을 필요로 하는 액션
 */
public interface MultiInputAction extends Action {
    /**
     * when players select this action
     */
    void runAction(Player player, Object detail);
}
