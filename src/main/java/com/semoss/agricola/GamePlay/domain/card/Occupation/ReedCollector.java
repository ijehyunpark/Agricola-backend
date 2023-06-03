package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.StackResource;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 라운드에 자원을 쌓아둡니다.
 */
@Getter
@Component
@Scope("prototype")
public class ReedCollector extends DefaultOccupation implements StackResource {
    private final ResourceStruct stackResource = ResourceStruct.builder().resource(ResourceType.REED).count(1).build();
    private final int[] rounds = new int[]{1,2,3,4};
    private final boolean isStaticRound = false;
    private final int playerRequirement;

    public ReedCollector(@Value("${mendicant.id}") Long cardID,
                           @Value("${mendicant.name}") String name,
                           @Value("${mendicant.players}") Integer playerRequirement,
                           @Value("${mendicant.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void place(Player player) {
        super.place(player);
        ArrayList<Integer> list = new ArrayList<>();

        // 플레이어의 라운드 스택에 자원 추가
        if (isStaticRound) {
            int now = player.getGame().getGameState().getRound();
            for (int round : rounds) {
                if (round > now) list.add(round);
            }
        }
        else {
            for (int round : rounds) {
                int sum = round + player.getGame().getGameState().getRound();;
                if (sum > 14) break;
                list.add(sum);
            }
        }
        player.addRoundStack(list.stream().mapToInt(i -> i).toArray(),stackResource);
    }
}
