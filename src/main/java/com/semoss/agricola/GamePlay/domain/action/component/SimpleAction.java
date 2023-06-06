package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 추가 입력 없이 수행할 수 있는 액션
 */
public interface SimpleAction extends Action {
    /**
     * when players select this action
     */
    void runAction(Player player, History history);
}
